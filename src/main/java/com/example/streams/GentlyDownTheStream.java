package com.example.streams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Enhanced coding kata on the Stream API with exception handling, generics, and advanced concepts.
 * All methods include proper validation and can be completed with a single return statement plus validation.
 */
public class GentlyDownTheStream {

    protected List<String> fruits;
    protected List<String> veggies;
    protected List<Integer> integerValues;

    public GentlyDownTheStream() {
        fruits = Arrays.asList("Apple", "Orange", "Banana", "Pear", "Peach", "Tomato");
        veggies = Arrays.asList("Corn", "Potato", "Carrot", "Pea", "Tomato");
        integerValues = new Random().ints(0, 1001)
                .boxed()
                .limit(1000)
                .collect(Collectors.toList());
    }

    /**
     * Example method showing proper exception handling and validation
     * Returns a sorted list of fruits with comprehensive error checking
     */
    public List<String> sortedFruits() throws InvalidDataException {
        validateCollection(fruits, "Fruits collection");

        try {
            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e; // preserve expected test contract
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to sort fruits", e);
        }
    }

    /**
     * Enhanced version with custom predicate and exception handling
     */
    public List<String> sortedFruitsException() throws InvalidDataException {
        return sortedFruitsWithFilter(fruit -> !fruit.startsWith("A"));
    }

    // Return the first two elements from the sorted fruits list.
    public List<String> sortedFruitsFirstTwo() throws InvalidDataException {
        validateCollection(fruits, "Fruits collection");

        try {
            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .limit(2)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to get first two sorted fruits", e);
        }
    }

    // Return a comma-separated string of sorted fruits.
    public String commaSeparatedListOfFruits() throws InvalidDataException {
        validateCollection(fruits, "Fruits collection");

        try {
            return fruits.stream()
                    .filter(Objects::nonNull)
                    .sorted()
                    .collect(Collectors.joining(", "));
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to create comma-separated fruit list", e);
        }
    }

    // Return veggies sorted in reverse (descending) order.
    public List<String> reverseSortedVeggies() throws InvalidDataException {
        validateCollection(veggies, "Veggies collection");

        try {
            return veggies.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to reverse sort veggies", e);
        }
    }

    // Return reverse-sorted veggies converted to uppercase.
    public List<String> reverseSortedVeggiesInUpperCase() throws InvalidDataException {
        validateCollection(veggies, "Veggies collection");

        try {
            return veggies.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .map(v -> v.toUpperCase(Locale.ROOT))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to reverse sort and uppercase veggies", e);
        }
    }

    // Return the top 10 values from integerValues.
    public List<Integer> topTen() throws InvalidDataException {
        validateCollection(integerValues, "Integer values collection");

        try {
            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to get top ten values", e);
        }
    }

    // Return the top 10 unique values from integerValues.
    public List<Integer> topTenUnique() throws InvalidDataException {
        validateCollection(integerValues, "Integer values collection");

        try {
            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .distinct()
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to get top ten unique values", e);
        }
    }

    // Return the top 10 unique odd values from integerValues.
    public List<Integer> topTenUniqueOdd() throws InvalidDataException {
        validateCollection(integerValues, "Integer values collection");

        try {
            return integerValues.stream()
                    .filter(Objects::nonNull)
                    .sorted(Comparator.reverseOrder())
                    .distinct()
                    .filter(x -> x % 2 != 0)
                    .limit(10)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException | EmptyCollectionException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to get top ten unique odd values", e);
        }
    }

    // Return the average of all valid integer values.
    public Double average() throws InvalidDataException {
        if (integerValues == null) {
            throw new IllegalArgumentException("Integer values collection cannot be null");
        }

        try {
            return safeAverage(integerValues)
                    .orElseThrow(() -> new InvalidDataException("Cannot calculate average: no valid integer values"));
        } catch (IllegalArgumentException | InvalidDataException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new InvalidDataException("Failed to calculate average", e);
        }
    }

    // Generic method for safe collection operations
    private <T> void validateCollection(Collection<T> collection, String collectionName) throws EmptyCollectionException {
        if (collection == null) {
            throw new IllegalArgumentException(collectionName + " cannot be null");
        }
        if (collection.isEmpty()) {
            throw new EmptyCollectionException(collectionName + " cannot be empty");
        }
    }

    // Helper method demonstrating advanced generics and functional programming
    private <T> List<T> sortedWithFilter(Collection<T> collection,
                                         Predicate<T> filter,
                                         Comparator<T> comparator) throws InvalidDataException {
        try {
            validateCollection(collection, "Input collection");

            return collection.stream()
                    .filter(Objects::nonNull)
                    .filter(filter)
                    .sorted(comparator)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidDataException("Failed to sort and filter collection: " + e.getMessage());
        }
    }

    // Specialized method using the generic helper
    private List<String> sortedFruitsWithFilter(Predicate<String> filter) throws InvalidDataException {
        return sortedWithFilter(fruits, filter, String::compareTo);
    }

    // Utility method for safe integer operations
    private OptionalDouble safeAverage(Collection<Integer> numbers) {
        return numbers.stream()
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .average();
    }
}