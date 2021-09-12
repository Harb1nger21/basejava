package ru.javawebinar.basejava.util;

import java.io.IOException;

@FunctionalInterface
public interface CollectionConsumer<T>{
   void accept(T t) throws IOException;
}