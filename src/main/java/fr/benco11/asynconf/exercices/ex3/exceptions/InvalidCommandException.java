package fr.benco11.asynconf.exercices.ex3.exceptions;

public class InvalidCommandException extends RuntimeException {
    public InvalidCommandException(String commandString) {
        super("La commande '" + commandString + "' n'existe pas !");
    }
}
