package fr.benco11.asynconf.exercices.ex3.exceptions;

public class TaskNotExistException extends RuntimeException {
    public TaskNotExistException(String taskName) {
        super("La tâche '" + taskName + "' n'existe pas !");
    }
}
