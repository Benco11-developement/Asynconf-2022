package fr.benco11.asynconf.utils;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException() {
        super("Entrée invalide !");
    }

    public InvalidInputException(String details) {
        super("Entrée invalide ! " + details);
    }
}
