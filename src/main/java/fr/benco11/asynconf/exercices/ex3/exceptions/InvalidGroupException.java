package fr.benco11.asynconf.exercices.ex3.exceptions;

public class InvalidGroupException extends RuntimeException {
    public InvalidGroupException(String groupId) {
        super("Le groupe '" + groupId + "' n'existe pas !");
    }
}
