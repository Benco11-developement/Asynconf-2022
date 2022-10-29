package fr.benco11.asynconf.exercices.ex3.services;

import fr.benco11.asynconf.exercices.ex3.commands.Command;
import fr.benco11.asynconf.exercices.ex3.commands.CommandExecution;
import fr.benco11.asynconf.exercices.ex3.commands.CompleteTaskCommand;
import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.exceptions.TaskNotExistException;
import fr.benco11.asynconf.exercices.ex3.permissions.PermissionsGroup;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service qui gère les permissions
 */
public class PermissionService {
    private final Map<String, PermissionsGroup> permissionGroups;
    private final TaskService taskService;

    public PermissionService(TaskService taskService) {
        this.taskService = taskService;

        permissionGroups = new HashMap<>();

        // Ajoute les groupes de permissions de GlobalPermissionsGroup
        Arrays.stream(PermissionsGroup.GlobalPermissionsGroup.values())
              .forEach(this::addGroup);
    }

    public void addGroup(PermissionsGroup group) {
        permissionGroups.put(group.id(), group);
    }

    /**
     * Donne la liste formatée des groupes de permissions
     */
    public String permissionGroupsHelp() {
        return String.join(", ", permissionGroups.keySet());
    }

    /**
     * Vérifie que le groupe de permissions de l'utilisateur l'autorise à utiliser une commande
     *
     * @param user    l'utilisateur
     * @param command la commande
     */
    public boolean preAuthorize(User user, Command command) {
        return user.group()
                   .preAuthorize(command.id());
    }

    /**
     * Autorise un utilisateur ou non à exécuter un CommandExecution
     *
     * @param execution le CommandExecution
     */
    public boolean authorize(CommandExecution execution) {
        User user = execution.user();
        return switch(execution.command()
                               .id()) {
            case COMPLETE_TASK -> {
                // Vérifie que l'utilisateur soit affecté à la tâche ou qu'il soit admin pour la compléter
                String taskName = execution.arguments()
                                           .get(CompleteTaskCommand.TASK_NAME_ARGUMENT)
                                           .value();
                yield taskService.getTask(taskName)
                                 .map(t -> t.getAssignedUsers()
                                            .contains(user))
                                 .orElseThrow(() -> new TaskNotExistException(taskName));
            }
            case REGISTER_USER, DELETE_USER, CLEAR_TASKS, REMOVE_TASK -> isAdmin(user);
            case ADD_TASK, CONNECT, LIST_TASKS -> true;
        };
    }

    public Optional<PermissionsGroup> groupById(String id) {
        return (permissionGroups.containsKey(id)) ? Optional.of(permissionGroups.get(id))
                                                  : Optional.empty();
    }

    public boolean isAdmin(User user) {
        return user.group()
                   .equals(PermissionsGroup.GlobalPermissionsGroup.ADMIN);
    }
}
