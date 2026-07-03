package com.payflow.account.utils;

public class EntityUtils {

    // Private constructor to prevent instantiation (standard for util classes)
    private EntityUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Checks if a Long object is null or empty (0 or less).
     */
    public static boolean isEmpty(Long userId) {
        return userId == null || userId <= 0L;
    }

    /**
     * Checks if a primitive long is empty (0 or less).
     */
    public static boolean isEmpty(long userId) {
        return userId <= 0L;
    }

    /**
     * Optional: The inverse check, for when you want to ensure the ID is valid.
     */
    public static boolean isNotEmpty(Long userId) {
        return !isEmpty(userId);
    }
}