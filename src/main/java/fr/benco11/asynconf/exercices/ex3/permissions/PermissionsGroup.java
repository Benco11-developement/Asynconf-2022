package fr.benco11.asynconf.exercices.ex3.permissions;

import fr.benco11.asynconf.exercices.ex3.commands.Command;

import java.util.Arrays;
import java.util.List;

import static fr.benco11.asynconf.exercices.ex3.commands.Command.CommandId.*;

/**
 * Permet d'autoriser certaines commandes ou non
 */
public interface PermissionsGroup {
    String id();

    /**
     * Renvoie vrai si le groupe de permissions permet l'utilisation d'une commande
     *
     * @param command l'id de la commande
     */
    boolean preAuthorize(Command.CommandId command);

    enum GlobalPermissionsGroup implements PermissionsGroup {
        // Autorise toutes les commandes à ADMIN
        ADMIN(Arrays.asList(Command.CommandId.values()), "admin"), REGULAR(Arrays.asList(ADD_TASK, COMPLETE_TASK, LIST_TASKS, CONNECT), "regular");

        private final List<Command.CommandId> authorizedCommands;
        private final String id;

        GlobalPermissionsGroup(List<Command.CommandId> authorizedCommands, String id) {
            this.authorizedCommands = authorizedCommands;
            this.id = id;
        }

        @Override
        public String id() {
            return id;
        }

        @Override
        public boolean preAuthorize(Command.CommandId command) {
            return authorizedCommands.contains(command);
        }
    }
}
