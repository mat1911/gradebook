package com.app.controller;

public interface Controller<T> {

    default void addModel(T[] model){}
}
