package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.entity.Task;
import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.services.PermissionService;
import fr.benco11.asynconf.exercices.ex3.services.TaskService;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Commande "liste"
 */
public final class ListTaskCommand implements Command {
    private final TaskService taskService;
    private final PermissionService permissionService;

    public ListTaskCommand(TaskService taskService, PermissionService permissionService) {
        this.taskService = taskService;
        this.permissionService = permissionService;
    }

    @Override
    public CommandId id() {
        return CommandId.LIST_TASKS;
    }

    @Override
    public Map<String, Argument> arguments() {
        return Collections.emptyMap();
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> {
            // Récupère toutes les tâches si l'utilisateur est admin, sinon seules les tâches où il est assigné
            User user = c.user();

            List<Task> taskList = taskService.tasks()
                                             .stream()
                                             .filter(t -> permissionService.isAdmin(user) || t.getAssignedUsers()
                                                                                              .contains(user))
                                             .toList();
            System.out.println("Liste des tâches :\n\n" + taskList.stream()
                                                                  .map(Task::toString)
                                                                  .collect(Collectors.joining("\n\n")));
        };
    }
}
