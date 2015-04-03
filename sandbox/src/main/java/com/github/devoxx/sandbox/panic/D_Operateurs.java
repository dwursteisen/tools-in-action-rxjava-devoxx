package com.github.devoxx.sandbox.panic;

import rx.Observable;

/**
 * L'idée de ce slide est de construire opération par opération l'observable
 * pour montrer l'ensemble des opérateurs disponibles.
 */
// D code et parle. B peut avoir des points d'attentions
public class D_Operateurs {

    public static void main(String[] args) throws InterruptedException {

        // range -> filter -> map -> take(5) -> flatMap(i, "-") -> skipLast

        Observable.range(1, 100)
                .filter(i -> i % 2 == 0)
                .map(i -> Integer.toBinaryString(i))
                .take(5)
                .flatMap(i -> Observable.just(i, "-"))
                .skipLast(1)
                .subscribe(System.out::print);


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
