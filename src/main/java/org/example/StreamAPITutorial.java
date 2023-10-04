package org.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.ArrayList;
import java.util.stream.Collectors;



public class StreamAPITutorial {

    public static void main(String[] args) {

        List<String> myList =
                Arrays.asList("a1", "a2", "b1", "c2", "c1");

        myList
                .stream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        // C1
        // C2

        // TODO add more tutorial code here

        //I like to add comment on what it is doing or supposed to do, or what I was trying to do with certain instruction.
        // How streams work
        Stream<String> myStream = myList.stream();
        myStream.forEach(System.out::println);

        // Different kind of streams
        Stream<String> parallelStream = myList.parallelStream();
        parallelStream.forEach(System.out::println);

        // Processing Order
        myList.stream()
                .filter(s -> s.startsWith("a"))
                .forEach(System.out::println);

        myList.stream()
                .filter(s -> s.startsWith("b"))
                .forEach(System.out::println);

        // Why Order Matters
        myList.stream()
                .sorted()
                .forEach(System.out::println);

        // Reusing Streams
        Stream<String> stream = myList.stream();
        Optional<String> anyElement = stream.findAny();
        anyElement.ifPresent(System.out::println);

        // Advanced Operations
        List<Foo> foos = IntStream.range(1, 4)
                .mapToObj(i -> new Foo("Foo" + i))
                .peek(f -> IntStream.range(1, 4)
                        .mapToObj(i -> new Bar("Bar" + i + " <- " + f.name))
                        .forEach(f.bars::add))
                .collect(Collectors.toList());

        foos.stream()
                .flatMap(f -> f.bars.stream())
                .forEach(b -> System.out.println(b.name));

        // Collect
        List<String> resultList = myList.stream()
                .filter(s -> s.startsWith("c"))
                .collect(Collectors.toList());

        // Reduce
        String reduced = myList.stream()
                .reduce("", (partialResult, element) -> partialResult + element);

        // Parallel Streams
        myList.parallelStream()
                .filter(s -> s.startsWith("c"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);

        // Extra Credit: Collect
        String resultString = myList.stream()
                .filter(s -> s.startsWith("b"))
                .collect(Collectors.joining(", "));

        // Extra Credit: FlatMap with 3 classes
        Stream<Outer> outerStream = Stream.of(new Outer());
        outerStream.flatMap(outer -> Stream.of(outer.nested))
                .flatMap(nested -> Stream.of(nested.inner))
                .forEach(inner -> System.out.println(inner.foo));

        // Extra Credit: Reduce with multiple variable names
        int ageSum = myList.stream()
                .map(s -> Integer.parseInt(s.substring(1))) // Extract the numeric part and parse as integer
                .reduce(0, Integer::sum);

        List<Person> persons = Arrays.asList(
                new Person("Alice", 28),
                new Person("Bob", 35),
                new Person("Charlie", 22)
        );

        int ageSum2 = persons.stream()
                .map(Person::getAge)
                .reduce(0, Integer::sum);

        // Extra Credit: Parallel Streams
        myList.parallelStream()
                .filter(s -> s.startsWith("a"))
                .map(String::toUpperCase)
                .sorted()
                .forEach(System.out::println);
    }

    // Define the Foo and Bar classes here as mentioned in the instructions.
    static class Foo {
        String name;
        List<Bar> bars = new ArrayList<>();

        Foo(String name) {
            this.name = name;
        }
    }

    static class Bar {
        String name;

        Bar(String name) {
            this.name = name;
        }
    }

    // Define the Person class for the "Reduce with multiple variable names" extra credit section.
    static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        int getAge() {
            return age;
        }
    }

    // Define the Inner, Nested, and Outer classes for the "FlatMap with 3 classes" extra credit section.
    static class Inner {
        String foo;

        Inner(String foo) {
            this.foo = foo;
        }
    }

    static class Nested {
        Inner inner = new Inner("Nested");

        Nested() {
        }
    }

    static class Outer {
        Nested nested = new Nested();

        Outer() {
        }

    }
}