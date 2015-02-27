package com.github.devoxx.server.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletResponse;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by david.wursteisen on 25/02/2015.
 */
@RestController("streaming")
public class StreamingApi {

    @RequestMapping(value = "/streaming/", method = RequestMethod.GET)
    public void status(HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("text/event-stream");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Connection", "keep-alive");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setStatus(200);
        PrintWriter out = response.getWriter();

        Gson marshaller = new Gson();

        Observable<String> models = modelToStream()
                .map(marshaller::toJson)
                .map(data -> "data: " + data + "\n\n");

        Observable.just("event: poll\r\n")
                .concatWith(models)
                .concatWith(Observable.just("\n"))
                .toBlocking()
                .forEach((line) -> {
                    out.write(line);
                    out.flush();
                });

    }

    private Observable<Object> modelToStream() {
        Subject<Object, Object> subject = PublishSubject.create();

        RestApi api = buildRestApi();

        api.movies().flatMap(Observable::from)
                .flatMap((m) -> api.translation(m.id, "FR"))
                .doOnNext(subject::onNext)
                .flatMap((m) -> api.actors(m.id)
                        .flatMap(Observable::from)
                        .map((data) -> ImmutableMap.of("movie", m.id, "actor", data))
                        .mergeWith(api.synopsis(m.id).filter((d) -> d != null)
                                .map((data) -> ImmutableMap.of("movie", m.id, "synopsis", data))))
                .subscribe(subject);
        return subject;
    }

    private RestApi buildRestApi() {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://localhost:8080")
                .setExecutors(Executors.newFixedThreadPool(4), Executors.newFixedThreadPool(4))
                .build();

        return adapter.create(RestApi.class);
    }

    private interface RestApi {
        @GET("/movies")
        Observable<List<Movie>> movies();

        @GET("/movies/cast/{id}")
        Observable<List<Actor>> actors(@Path("id") String movieId);

        @GET("/movies/synopsis/{id}")
        Observable<Synopsis> synopsis(@Path("id") String title);

        @GET("/movies/translation/{id}/{lang}")
        Observable<Movie> translation(@Path("id") String id, @Path("lang") String lang);
    }
}
