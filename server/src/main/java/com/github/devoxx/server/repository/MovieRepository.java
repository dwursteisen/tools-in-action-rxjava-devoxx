package com.github.devoxx.server.repository;

import static com.github.devoxx.server.model.Movie.titleToId;
import static java.util.Arrays.asList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;

public class MovieRepository {

    private final List<Movie> allMovies = Arrays.asList(
            new Movie("The Shawshank Redemption"),
            new Movie("The Godfather"),
            new Movie("The Godfather: Part II"),
            new Movie("The Dark Knight"),
            new Movie("Pulp Fiction"),
            new Movie("12 Angry Men"),
            new Movie("Schindler's List"),
            new Movie("Il buono, il brutto, il cattivo."),
            new Movie("The Lord of the Rings: The Return of the King"),
            new Movie("Fight Club")
    );

    public List<Movie> movies() {
            return allMovies;
    }

    private final Map<String, List<Actor>> ACTORS = new HashMap<>();
    private final Map<String, Synopsis> SYNOPSYS = new HashMap<>();
    private final Map<String, String> TRANSLATION = new HashMap<>();

    public MovieRepository(@Qualifier("port") int port) {

        ACTORS.put(titleToId("The Shawshank Redemption"), asList(new Actor("Tim", "Robbins"),
                new Actor("Morgan", "Freeman"),
                new Actor("Bob", "Gunton"),
                new Actor("William", "Sadler")));

        ACTORS.put(titleToId("The Godfather"), asList(new Actor("Marlon", "Brando"),
                new Actor("Al", "Pacino"),
                new Actor("James", "Caan"),
                new Actor("Richard", "Castellano")));

        ACTORS.put(titleToId("The Godfather: Part II"), asList(new Actor("Marlon", "Brando"),
                new Actor("Al", "Pacino"),
                new Actor("James", "Caan"),
                new Actor("Richard", "Castellano")));

        ACTORS.put(titleToId("The Dark Knight"), asList(new Actor("Christian", "Bale"),
                new Actor("Heath", "Ledger"),
                new Actor("Aaron", "Eckhart"),
                new Actor("Michael", "Caine")));

        ACTORS.put(titleToId("Pulp Fiction"), asList(new Actor("Tim", "Roth"),
                new Actor("Amanda", "Plummer"),
                new Actor("John", "Travolta"),
                new Actor("Samuel L.", "Jackson"),
                new Actor("Bruce", "Willis")));

        ACTORS.put(titleToId("12 Angry Men"), asList(new Actor("Martin", "Balsam"),
                new Actor("John", "Fiedler"),
                new Actor("Lee J.", "Cobb"),
                new Actor("E.G.", "Marshall")));

        ACTORS.put(titleToId("Schindler's List"), asList(new Actor("Liam", "Neeson"),
                new Actor("Ben", "Kingsley"),
                new Actor("Ralph", "Fiennes"),
                new Actor("Caroline", "Goodall")));

        ACTORS.put(titleToId("Il buono, il brutto, il cattivo."), asList(new Actor("Eli", "Wallach"),
                new Actor("Clint", "Eastwood"),
                new Actor("Lee Van", "Cleef")));

        ACTORS.put(titleToId("The Lord of the Rings: The Return of the King"), asList(new Actor("Noel", "Appleby"),
                new Actor("Ali", "Astin"),
                new Actor("Sean", "Astin")));

        ACTORS.put(titleToId("Fight Club"), asList(new Actor("Edward", "Norton"),
                new Actor("Brad", "Pitt"),
                new Actor("Helena Bonham", "Carter"),
                new Actor("Meat", "Loaf")));

        SYNOPSYS.put(titleToId("The Shawshank Redemption"), new Synopsis(
                "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                "http://localhost:" + port + "/poster/evades.jpg"));

        SYNOPSYS.put(titleToId("The Godfather"), new Synopsis(
                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                "http://localhost:" + port + "/poster/godfather.jpg"));

        SYNOPSYS.put(titleToId("The Godfather: Part II"), new Synopsis(
                "The early life and career of Vito Corleone in 1920s New York is portrayed while his son, Michael, expands and tightens his grip on his crime syndicate stretching from Lake Tahoe, Nevada to pre-revolution 1958 Cuba.",
                "http://localhost:" + port + "/poster/godfather2.jpg"));

        SYNOPSYS.put(titleToId("The Dark Knight"), new Synopsis(
                "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.",
                "http://localhost:" + port + "/poster/dark_knight.jpg"));

        SYNOPSYS.put(titleToId("Pulp Fiction"), new Synopsis(
                "The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                "http://localhost:" + port + "/poster/pulp_fiction.jpg"));

        SYNOPSYS.put(titleToId("12 Angry Men"), new Synopsis(
                "A dissenting juror in a murder trial slowly manages to convince the others that the case is not as obviously clear as it seemed in court.",
                "http://localhost:" + port + "/poster/angry_men.jpg"));

        SYNOPSYS.put(titleToId("Schindler's List"), new Synopsis(
                "In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
                "http://localhost:" + port + "/poster/schindler.jpg"));

        SYNOPSYS.put(titleToId("Il buono, il brutto, il cattivo."), new Synopsis(
                "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.",
                "http://localhost:" + port + "/poster/le_bon.jpg"));

        SYNOPSYS.put(titleToId("The Lord of the Rings: The Return of the King"), new Synopsis(
                "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.",
                "http://localhost:" + port + "/poster/lord.jpg"));

        SYNOPSYS.put(titleToId("Fight Club"), new Synopsis(
                "An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much, much more...",
                "http://localhost:" + port + "/poster/fight_club.jpg"));

        TRANSLATION.put(titleToId("The Shawshank Redemption"), "Les évadés");

        TRANSLATION.put(titleToId("The Godfather"), "Le parrain");

        TRANSLATION.put(titleToId("The Godfather: Part II"), "Le parrain, 2ème partie");

        TRANSLATION.put(titleToId("The Dark Knight"), "The Dark Knight: Le chevalier noir ");

        TRANSLATION.put(titleToId("Pulp Fiction"), "Pulp Fiction");

        TRANSLATION.put(titleToId("12 Angry Men"), "12 hommes en colère");

        TRANSLATION.put(titleToId("Schindler's List"), "La liste de Schindler");

        TRANSLATION.put(titleToId("Il buono, il brutto, il cattivo."), "Le bon, la brute et le truand");

        TRANSLATION.put(titleToId("The Lord of the Rings: The Return of the King"), "Le seigneur des anneaux: Le retour du roi ");

        TRANSLATION.put(titleToId("Fight Club"), "Fight Club");
    }

    public List<Actor> cast(String id) {
        return ACTORS.getOrDefault(id, Collections.emptyList());
    }

    public Synopsis synopsis(String id) {
        return SYNOPSYS.get(id);
    }

    public List<Actor> actors() {
        return ACTORS.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
    }

    public Optional<Movie> translation(String id, String lang) {
        if (!"FR".equalsIgnoreCase(lang)) {
            return Optional.empty();
        }
        return Optional.ofNullable(TRANSLATION.get(id)).map((title) -> new Movie(id, title));
    }

    public Optional<Movie> movie(String id) {
        return allMovies.stream().collect(Collectors.groupingBy(movie -> movie.id, Collectors.reducing(new BinaryOperator<Movie>() {
            @Override
            public Movie apply(Movie current, Movie ignored) {
                return current;
            }
        }))).get(id);
    }
}
