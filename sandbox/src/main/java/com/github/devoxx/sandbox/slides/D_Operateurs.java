package com.github.devoxx.sandbox.slides;

import rx.Observable;

/**
 * L'idée de ce slide est de construire opération par opération l'observable
 * pour montrer l'ensemble des opérateurs disponibles.
 */
// D code et parle. B peut avoir des points d'attentions
public class D_Operateurs {

    public static void main(String[] args) throws InterruptedException {

        Observable.range(1, 102)
                .filter(i -> i % 2 == 0)
                .buffer(5)
                .skipLast(2)
                .flatMap(Observable::from)
                .map(Position::new)
                .subscribe(System.out::println);

    }

    private static class Position {
        private final int pos;

        public Position(int pos) {
            this.pos = pos;
        }

        @Override
        public String toString() {
            return "Position ~> " + pos;
        }
    }
}
