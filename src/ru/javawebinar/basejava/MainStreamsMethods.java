package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainStreamsMethods {
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
        final int[] multiply = {Arrays.stream(values).distinct().toArray().length - 1};
        return Arrays.stream(values).distinct().sorted().map(operand -> {
            int result = (int) (operand * Math.pow(10, multiply[0]));
            multiply[0]--;
            return result;
        }).sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().map(integer -> {
            if ((integers.stream().reduce(Integer::sum).get() % 2) == 0) {
                if ((integer % 2) == 0) {
                    return integer;
                }
            } else if ((integers.stream().reduce(Integer::sum).get() % 2) != 0) {
                if ((integer % 2) != 0) {
                    return integer;
                }
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
