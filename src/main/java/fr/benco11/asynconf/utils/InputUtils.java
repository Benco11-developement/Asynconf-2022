package fr.benco11.asynconf.utils;

import java.text.Normalizer;
import java.util.Optional;
import java.util.function.Supplier;

public class InputUtils {

    public static Optional<Integer> asInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return Optional.of(i);
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static Optional<Double> asDouble(String s) {
        try {
            double i = Double.parseDouble(s);
            return Optional.of(i);
        } catch(NumberFormatException e) {
            return Optional.empty();
        }
    }

    /**
     * Essaye de récupérer une valeur et en cas d'erreur, réessaye jusqu'à son obtention
     *
     * @param read  Supplier permettant de récupérer la valeur
     * @param error erreur à afficher en cas d'erreur
     * @param <T>   le type de la valeur
     * @return la valeur obtenue
     */
    public static <T> T getOrRetry(Supplier<T> read, String error) {
        T value = null;
        while(value == null) {
            try {
                value = read.get();
                if(value != null) break;
            } catch(Exception ignored) {
            }
            System.out.println(error);
        }
        return value;
    }

    public static String removeSpace(String s) {
        return s.replace(" ", "");
    }

    public static String withoutAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFKD);
    }

    private InputUtils() {
    }
}
