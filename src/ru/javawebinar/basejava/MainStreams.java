package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (left, right) -> left * 10 + right);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int remainder = integers.stream().reduce(0, Integer::sum) % 2;
        return integers.stream()
                .filter(integer -> (remainder == 0 && integer % 2 == 0) || (remainder == 1 && integer % 2 == 1))
                .collect(Collectors.toList());
    }
}
