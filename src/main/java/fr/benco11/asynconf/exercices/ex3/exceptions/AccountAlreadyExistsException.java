package fr.benco11.asynconf.exercices.ex3.exceptions;

import fr.benco11.asynconf.exercices.ex3.entity.User;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(User account) {
        super("Le compte avec le nom '" + account.name() + "' existe déjà !");
    }
}
