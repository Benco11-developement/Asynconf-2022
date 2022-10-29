package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.entity.Task;
import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.exceptions.AccountNotExistException;
import fr.benco11.asynconf.exercices.ex3.exceptions.TaskAlreadyExistsException;
import fr.benco11.asynconf.exercices.ex3.services.AccountService;
import fr.benco11.asynconf.exercices.ex3.services.TaskService;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static fr.benco11.asynconf.utils.CollectionUtils.linkedHashMap;
import static java.util.Map.entry;

/**
 * Commande "ajouter"
 */
public final class AddTaskCommand implements Command {
    public static final String TASK_NAME_ARGUMENT = "name";
    public static final String TASK_DESCRIPTION_ARGUMENT = "description";
    public static final String TASK_ASSIGNED_ARGUMENT = "assigned";
    private final AccountService accountService;
    private final TaskService taskService;


    public AddTaskCommand(AccountService accountService, TaskService taskService) {
        this.accountService = accountService;
        this.taskService = taskService;
    }

    @Override
    public CommandId id() {
        return CommandId.ADD_TASK;
    }

    @Override
    public Map<String, Argument> arguments() {
        // Utilise CollectionUtils.linkedHashMap() pour garder l'ordre des arguments
        return linkedHashMap(List.of(entry(TASK_NAME_ARGUMENT, new Argument(TASK_NAME_ARGUMENT, "Nom de la tâche :", "")),
                entry(TASK_DESCRIPTION_ARGUMENT, new Argument(TASK_DESCRIPTION_ARGUMENT, "Description de la tâche :", "")),
                entry(TASK_ASSIGNED_ARGUMENT, new Argument(TASK_ASSIGNED_ARGUMENT, "Utilisateurs assignés (séparés par '/')", ""))));
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> {
            // Récupère les différents arguments
            String name = c.arguments()
                           .get(TASK_NAME_ARGUMENT)
                           .value();
            if(name.isBlank()) throw new InvalidInputException("Le nom de la tâche est vide !");

            String description = c.arguments()
                                  .get(TASK_DESCRIPTION_ARGUMENT)
                                  .value();
            if(description.isBlank()) throw new InvalidInputException("Le nom de la tâche est vide !");

            List<String> assigned = Arrays.asList(c.arguments()
                                                   .get(TASK_ASSIGNED_ARGUMENT)
                                                   .value()
                                                   .split("/"));
            if(c.arguments().get(TASK_ASSIGNED_ARGUMENT).value().isBlank())
                throw new InvalidInputException("Aucun utilisateur n'a été assigné !");

            // Récupère les comptes des utilisateurs assignés (en s'assurant qu'ils existent)
            List<User> assignedUsers = assigned.stream()
                                               .map(u -> accountService.accountByName(u)
                                                                       .orElseThrow(() -> new AccountNotExistException(u)))
                                               .toList();
            Task task = new Task(name, description, assignedUsers);
            if(taskService.isRegistered(task)) throw new TaskAlreadyExistsException(task);

            taskService.addTask(task);
        };
    }
}
