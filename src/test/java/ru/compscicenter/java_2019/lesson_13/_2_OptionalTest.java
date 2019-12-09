package ru.compscicenter.java_2019.lesson_13;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static ru.compscicenter.java_2019.Util.__;

@FixMethodOrder(MethodSorters.JVM)
public class _2_OptionalTest {

    @Test
    public void emptyOptional() {
        Optional<String> empty = Optional.empty();
        assertEquals(__, empty.isPresent());
        assertEquals(__, empty.isEmpty());
        assertEquals(__, empty.orElse(getValue()));
        assertEquals(__, empty.orElseGet(() -> getValue()));
        empty.ifPresent(s -> {
            assertEquals(__, s);
        });
        empty.ifPresentOrElse(s -> {
            assertEquals(__, s);
        }, () -> {
            assertFalse(Boolean.valueOf(__));
        });
    }

    private String getValue() {
        System.out.println("getValue was called"); //TODO pay attention to how many times it's been printed
        return "very cool";
    }

    @Test
    public void notEmptyOptional() {
        Optional<String> some = Optional.of("cool");
        assertEquals(__, some.isPresent());
        assertEquals(__, some.isEmpty());
        assertEquals(__, some.orElse(getValue()));
        assertEquals(__, some.orElseGet(() -> getValue()));
        some.ifPresent(s -> {
            assertEquals(__, s);
        });
        some.ifPresentOrElse(s -> {
            assertEquals(__, s);
        }, () -> {
            assertFalse(Boolean.valueOf(__));
        });
    }

    @Test(expected = NullPointerException.class)
    public void ofOptionalCreation() {
        Optional<String> empty = Optional.of(null);
    }

    @Test(expected = NullPointerException.class)
    public void ofNullableOptionalCreation() {
        Optional<String> some = Optional.ofNullable(null);
        some.orElseThrow(NullPointerException::new);
    }

    @Test
    public void stringOptionalCreation() {
        Optional<String> some = Optional.of("cool optional");
        String string = some.or(() -> Optional.of("another string")).get();
        assertEquals(__, string);
    }

    @Test
    public void testOptionalApi() {
        String plainPassword = "qwerty";
        Optional<Integer> passwordHashOpt = getPasswordHash("some_login");
        int passwordHash = passwordHashOpt.orElseThrow(() -> new IllegalStateException("unexisted login tries to login"));
        boolean passwordIsCorrect = plainPassword.hashCode() == passwordHash;
        assertEquals(__, passwordIsCorrect);
    }


    private Optional<Integer> getPasswordHash(String login) { //TODO switch to Optional.of(123)
        return Optional.empty();
    }

    @Test
    public void filterOptional() {
        Optional<String> empty = Optional.of("cool");
        assertEquals(__, empty.filter(s -> s.contains("oo")).isPresent());
        assertEquals(__, empty.filter(s -> s.contains("oo")).isEmpty());
    }

    @Test
    public void mapOptional() {
        Optional<Optional<String>> optionalOptional =
                Optional.of("cool")
                        .map(o -> !o.contains("oo") ? Optional.empty() : Optional.of("not so cool"));
        assertEquals(__, optionalOptional.orElseThrow().getClass().getSimpleName());
        assertEquals(__, optionalOptional.orElseThrow().orElseThrow().getClass().getSimpleName());
        assertEquals(__, optionalOptional.orElseThrow().orElseThrow());

    }

    @Test
    public void flatMapOptional() {
        Optional<Integer> optionalOptional =
                Optional.of("cool")
                        .flatMap(o -> !o.contains("oo") ? Optional.empty() : Optional.of("not so cool"))
                        .flatMap(f -> Optional.of(f.length()));
        assertEquals(__, optionalOptional.orElseThrow().getClass().getSimpleName());
        assertEquals(__, optionalOptional.orElseThrow());

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
    public void chainingOptionalsInStream() {
        Optional<String> firstNotEmpty = Stream.<Optional<String>>of(getEmpty(), getCool(), getVeryCool())
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        assertEquals(__, firstNotEmpty.orElse("not very cool"));
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
