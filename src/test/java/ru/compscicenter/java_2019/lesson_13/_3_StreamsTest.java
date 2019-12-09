package ru.compscicenter.java_2019.lesson_13;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _3_StreamsTest {

    String str = "";

    List<String> places = Arrays.asList("Belgrade", "Zagreb", "Sarajevo", "Skopje", "Ljubljana", "Podgorica");

    @Test
    public void simpleCount() {
        long count = places.stream()
                .count();
        assertEquals(__, count);
    }

    @Test
    public void filteredCount() {
        long count = places.stream()
                .filter(s -> s.startsWith("S"))
                .count();
        assertEquals(__, count);
    }

    @Test
    public void max() {
        String longest = places.stream()
                .max(Comparator.comparing(cityName -> cityName.length()))
                .get();
        assertEquals(__, longest);
    }

    @Test
    public void min() {
        String shortest = places.stream()
                .min(Comparator.comparing(cityName -> cityName.length()))
                .get();
        assertEquals(__, shortest);
    }

    @Test
    public void reduce() {
        String join = places.stream()
                .reduce("", String::concat);
        assertEquals(__, join);
    }

    @Test
    public void reduceWithoutStarterReturnsOptional() {
        Optional<String> join = places.stream()
                .reduce(String::concat);
        assertEquals(__, join.get());
    }

    @Test
    public void join() {
        String join = places.stream()
                .reduce((accumulated, cityName) -> accumulated + "\", \"" + cityName)
                .get();
        assertEquals(__, join);
    }

    @Test
    public void reduceWithBinaryOperator() {
        String join = places.stream()
                .reduce("", String::concat);
        assertEquals(__, join);
    }

    @Test
    public void stringJoin() {
        String join = places.stream()
                .collect(Collectors.joining("\", \""));
        assertEquals(__, join);
    }

    @Test
    public void mapReduce() {
        OptionalDouble averageLengthOptional = places.stream()
                .mapToInt(String::length)
                .average();
        double averageLength = Math.round(averageLengthOptional.getAsDouble());
        assertEquals(__, averageLength);
    }

    @Test
    public void parallelMapReduce() {
        int lengthSum = places.parallelStream()
                .mapToInt(String::length)
                .sum();
        assertEquals(__, lengthSum);
    }

    @Test
    public void limitSkip() {
        int lengthSum_Limit_3_Skip_1 = places.stream()
                .mapToInt(String::length)
                .limit(3)
                .skip(1)
                .sum();
        assertEquals(__, lengthSum_Limit_3_Skip_1);
    }

    @Test
    public void lazyEvaluation() {
        Stream stream = places.stream()
                .filter(s -> {
                    str = "hello";
                    return s.startsWith("S");
                });
        assertEquals(__, str);
    }

    @Test
    public void sumRange() {
        int sum = IntStream.range(1, 4).sum();
        assertEquals(__, sum);
    }

    @Test
    public void rangeToList() {
        List<Integer> range = IntStream.range(1, 4)
                .map(operand -> operand++)
                .map(operand -> ++operand)
                .boxed()
                .collect(Collectors.toList());
        assertEquals(__, range.stream().mapToInt(s -> s).sum());
    }

    private Optional<String> getEmpty() {
        return Optional.<String>empty();
    }

    private Optional<String> getCool() {
        return Optional.of("cool");
    }

    private Optional<String> getVeryCool() {
        return Optional.of("very cool");
    }

    @Test
    public void givenThreeOptionals_whenChaining_thenFirstNonEmptyIsReturnedAndRestNotEvaluated() {
        String found = Stream.<Supplier<Optional<String>>>of(this::getEmpty, this::getCool, this::getVeryCool)
                .map(Supplier::get)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseGet(() -> "aha!");
        assertEquals(__, found);
    }

}
