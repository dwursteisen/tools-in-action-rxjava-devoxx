package com.github.devoxx.server.repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.devoxx.server.model.Actor;
import com.github.devoxx.server.model.Movie;
import com.github.devoxx.server.model.Synopsis;

/**
 * Created by david.wursteisen on 12-02-2015.
 */
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

        private static Map<String, List<Actor>> actors = new HashMap<>();
        private static Map<String, Synopsis> synopsis = new HashMap<>();

        static {

                actors.put("The Shawshank Redemption",
                        Arrays.asList(new Actor("Tim", "Robbins"),
                                new Actor("Morgan", "Freeman"),
                                new Actor("Bob", "Gunton"),
                                new Actor("William", "Sadler")));

                actors.put("The Godfather",
                        Arrays.asList(new Actor("Marlon", "Brando"),
                                new Actor("Al", "Pacino"),
                                new Actor("James", "Caan"),
                                new Actor("Richard", "Castellano")));

                actors.put("The Godfather: Part II",
                        Arrays.asList(new Actor("Marlon", "Brando"),
                                new Actor("Al", "Pacino"),
                                new Actor("James", "Caan"),
                                new Actor("Richard", "Castellano")));

                actors.put("The Dark Knight",
                        Arrays.asList(new Actor("Christian", "Bale"),
                                new Actor("Heath", "Ledger"),
                                new Actor("Aaron", "Eckhart"),
                                new Actor("Michael", "Caine")));

                actors.put("Pulp Fiction",
                        Arrays.asList(new Actor("Tim", "Roth"),
                                new Actor("Amanda", "Plummer"),
                                new Actor("John", "Travolta"),
                                new Actor("Samuel L.", "Jackson"),
                                new Actor("Bruce", "Willis")));

                actors.put("12 Angry Men",
                        Arrays.asList(new Actor("Martin", "Balsam"),
                                new Actor("John", "Fiedler"),
                                new Actor("Lee J.", "Cobb"),
                                new Actor("E.G.", "Marshall")));

                actors.put("Schindler's List",
                        Arrays.asList(new Actor("Liam", "Neeson"),
                                new Actor("Ben", "Kingsley"),
                                new Actor("Ralph", "Fiennes"),
                                new Actor("Caroline", "Goodall")));

                actors.put("Il buono, il brutto, il cattivo.",
                        Arrays.asList(new Actor("Eli", "Wallach"),
                                new Actor("Clint", "Eastwood"),
                                new Actor("Lee Van", "Cleef")));

                actors.put("The Lord of the Rings: The Return of the King",
                        Arrays.asList(new Actor("Noel", "Appleby"),
                                new Actor("Ali", "Astin"),
                                new Actor("Sean", "Astin")));

                actors.put("Fight Club",
                        Arrays.asList(new Actor("Edward", "Norton"),
                                new Actor("Brad", "Pitt"),
                                new Actor("Helena Bonham", "Carter"),
                                new Actor("Meat", "Loaf")));

                synopsis.put("The Shawshank Redemption",
                        new Synopsis("", ""));

                synopsis.put("The Godfather",
                        new Synopsis(
                                "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.",
                                ""));

                synopsis.put("The Godfather: Part II",
                        new Synopsis(
                                "The early life and career of Vito Corleone in 1920s New York is portrayed while his son, Michael, expands and tightens his grip on his crime syndicate stretching from Lake Tahoe, Nevada to pre-revolution 1958 Cuba.",
                                ""));

                synopsis.put("The Dark Knight",
                        new Synopsis("", ""));

                synopsis.put("Pulp Fiction",
                        new Synopsis(
                                "The lives of two mob hit men, a boxer, a gangster's wife, and a pair of diner bandits intertwine in four tales of violence and redemption.",
                                ""));

                synopsis.put("12 Angry Men",
                        new Synopsis(
                                "A dissenting juror in a murder trial slowly manages to convince the others that the case is not as obviously clear as it seemed in court.",
                                ""));

                synopsis.put("Schindler's List",
                        new Synopsis(
                                "In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.",
                                ""));

                synopsis.put("Il buono, il brutto, il cattivo.",
                        new Synopsis(
                                "A bounty hunting scam joins two men in an uneasy alliance against a third in a race to find a fortune in gold buried in a remote cemetery.",
                                ""));

                synopsis.put("The Lord of the Rings: The Return of the King",
                        new Synopsis("", ""));

                synopsis.put("Fight Club",
                        new Synopsis(
                                "An insomniac office worker looking for a way to change his life crosses paths with a devil-may-care soap maker and they form an underground fight club that evolves into something much, much more...",
                                "http://localhost:8080/TODO/fight.png"));
        }

        public List<Actor> cast(String id) {
                return actors.getOrDefault(id, Collections.emptyList());
        }

        public Synopsis synopsis(String id) {
                return synopsis.get(id);
        }
}
