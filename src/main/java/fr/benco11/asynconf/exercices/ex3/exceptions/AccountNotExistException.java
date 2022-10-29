package fr.benco11.asynconf.exercices.ex3.exceptions;

public class AccountNotExistException extends RuntimeException {
    public AccountNotExistException(String accountName) {
        super("Le compte '" + accountName + "' n'existe pas !");
    }
}
