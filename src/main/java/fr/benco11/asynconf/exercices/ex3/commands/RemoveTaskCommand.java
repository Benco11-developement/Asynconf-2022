package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.exceptions.TaskNotExistException;
import fr.benco11.asynconf.exercices.ex3.services.TaskService;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Commande "retirer"
 */
public final class RemoveTaskCommand implements Command {
    public static final String TASK_NAME_ARGUMENT = "name";
    private final TaskService taskService;

    public RemoveTaskCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public CommandId id() {
        return CommandId.REMOVE_TASK;
    }

    @Override
    public Map<String, Argument> arguments() {
        return Map.of(TASK_NAME_ARGUMENT, new Argument(TASK_NAME_ARGUMENT, "Nom de la tâche :", ""));
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> {
            // Récupère la tâche et vérifie qu'elle existe avant de la supprimer
            String taskName = c.arguments()
                               .get(TASK_NAME_ARGUMENT)
                               .value();
            if(taskName.isBlank()) throw new InvalidInputException("Le nom de la tâche est vide !");
            if(!taskService.isRegistered(taskName)) throw new TaskNotExistException(taskName);

            taskService.delete(taskName);
        };
    }
}
