package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainStreams {
    private static final int[] VALUES = {1, 2, 3, 3, 2, 3};
    private static final int[] VALUES2 = {9, 8, 9};
    private static final List<Integer> INTEGERS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    private static final List<Integer> INTEGERS2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

    public static void main(String[] args) {
        System.out.println(minValue(VALUES2));
        System.out.println(minValue(VALUES));
        System.out.println(oddOrEven(INTEGERS));
        System.out.println(oddOrEven(INTEGERS2));
    }

    private static int minValue(int[] values) {
        final int[] multiply = {Arrays.stream(values).distinct().toArray().length};
        return Arrays.stream(values).distinct().reduce(0, (left, right) -> {
            multiply[0]--;
            return left + (right * (int) Math.pow(10, multiply[0]));
        });
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int remainder = integers.stream().reduce(Integer::sum).get() % 2;
        return integers.stream().flatMap((Function<Integer, Stream<Integer>>) integer -> {
            switch (integer % 2) {
                case 0:
                    if (remainder == 0) {
                        return Stream.of(integer);
                    }
                    break;
                case 1:
                    if (remainder == 1) {
                        return Stream.of(integer);
                    }
            }
            return Stream.empty();
        }).collect(Collectors.toList());
    }
}
