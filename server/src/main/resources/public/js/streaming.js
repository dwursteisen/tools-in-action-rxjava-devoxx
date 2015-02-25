function serverSideEvent(streamingUrl) {
    streamingUrl = streamingUrl || "/streaming/";
    return Rx.Observable.create(function (observer) {
        var evtSource = new EventSource(streamingUrl);
        evtSource.onmessage = function (e) {
            observer.onNext(JSON.parse(e.data));
        };

        evtSource.onerror = function (e) {
            observer.onError(e);
        };
        return function () {
            evtSource.close();
        };

    });
}


$(document).ready(function () {

    var movieTemplate = doT.template($("#movie").html());
    var actorTemplate = doT.template($("#actor").html());
    var synopsisTemplate = doT.template($("#synopsis").html());
    var source = serverSideEvent()
        .map(function (json) {
            // movie
            if (json.title != undefined) {
                return {
                    id: "#movies",
                    html: movieTemplate(json)
                }
            } else if (json.actor != undefined) {
                return {
                    id: "#" + json.movie + " " + ".actors",
                    html: actorTemplate(json.actor)
                }
            } else if (json.synopsis != undefined) {
                return {
                    id: "#" + json.movie + " " + ".synopsis",
                    html: synopsisTemplate(json.synopsis)
                }
            } else {
                return {
                    id: "oups",
                    html: "<h1>oups</h1>"
                }
            }
        }).subscribe(function (template) {
            $(template.html).appendTo(template.id);
        });
});
