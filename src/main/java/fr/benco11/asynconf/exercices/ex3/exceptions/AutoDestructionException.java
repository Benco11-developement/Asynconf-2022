package fr.benco11.asynconf.exercices.ex3.exceptions;

/**
 * bonsoir ?
 */
public class AutoDestructionException extends RuntimeException {
    public AutoDestructionException() {
        super("Tu m'as bien eu ! Autodestruction...\nrm -rf");
    }
}
