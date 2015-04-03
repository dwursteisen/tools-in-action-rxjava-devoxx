package com.github.devoxx.sandbox.slides;

import rx.Observable;

/**
 * L'idée de ce slide est de construire opération par opération l'observable
 * pour montrer l'ensemble des opérateurs disponibles.
 */
// D code et parle. B rajoute des informations
public class D_Operateurs {

    public static void main(String[] args) throws InterruptedException {

        // range -> filter -> map binary-> take(5) -> flatMap(i, "-") -> skipLast

        Observable.range(1, 100)
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
