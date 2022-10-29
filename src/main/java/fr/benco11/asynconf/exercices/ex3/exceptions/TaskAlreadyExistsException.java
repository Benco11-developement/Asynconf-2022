package fr.benco11.asynconf.exercices.ex3.exceptions;

import fr.benco11.asynconf.exercices.ex3.entity.Task;

public class TaskAlreadyExistsException extends RuntimeException {
    public TaskAlreadyExistsException(Task task) {
        super("La tâche avec le nom '" + task.name() + "' existe déjà !");
    }
}
