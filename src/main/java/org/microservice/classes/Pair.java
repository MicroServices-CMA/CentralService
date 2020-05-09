package org.microservice.classes;

public class Pair<T, U> {
    public final T t;
    public final U u;

    public Pair(T t, U u) {
        this.t = t;
        this.u = u;
    }
}