package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.exceptions.AccountAlreadyExistsException;
import fr.benco11.asynconf.exercices.ex3.exceptions.InvalidGroupException;
import fr.benco11.asynconf.exercices.ex3.permissions.PermissionsGroup;
import fr.benco11.asynconf.exercices.ex3.services.AccountService;
import fr.benco11.asynconf.exercices.ex3.services.PermissionService;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static fr.benco11.asynconf.utils.CollectionUtils.linkedHashMap;
import static java.util.Map.entry;

/**
 * Commande "ajouter-compte"
 */
public final class RegisterAccountCommand implements Command {
    public static final String NAME_ARGUMENT = "name";
    public static final String PERMISSION_GROUP_ARGUMENT = "permission_group";
    private final AccountService accountService;
    private final PermissionService permissionService;

    public RegisterAccountCommand(AccountService accountService,
            PermissionService permissionService) {
        this.accountService = accountService;
        this.permissionService = permissionService;
    }

    @Override
    public CommandId id() {
        return CommandId.REGISTER_USER;
    }

    @Override
    public Map<String, Argument> arguments() {
        // Utilise CollectionUtils.linkedHashMap() pour garder l'ordre des arguments
        return linkedHashMap(List.of(entry(NAME_ARGUMENT, new Argument(NAME_ARGUMENT, "Nom de l'utilisateur :", "")),
                entry(PERMISSION_GROUP_ARGUMENT, new Argument(PERMISSION_GROUP_ARGUMENT, "Groupe de permissions de l'utilisateur ("
                        + permissionService.permissionGroupsHelp() + ")", ""))));
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> {
            // Récupère le nom du compte et son groupe de permissions
            String name = c.arguments()
                           .get(NAME_ARGUMENT)
                           .value();
            if(name.isBlank()) throw new InvalidInputException("Le nom du compte est vide !");

            String permissionGroupId = c.arguments()
                                        .get(PERMISSION_GROUP_ARGUMENT)
                                        .value();
            if(permissionGroupId.isBlank()) throw new InvalidInputException("Le nom du groupe de permissions est vide");
            PermissionsGroup permissionsGroup = permissionService.groupById(permissionGroupId)
                                                                 .orElseThrow(() -> new InvalidGroupException(permissionGroupId));

            // Enregistre le compte s'il n'existe pas
            User account = new User(name, permissionsGroup);
            if(accountService.isRegistered(account))
                throw new AccountAlreadyExistsException(account);

            accountService.saveAccount(account);
        };
    }
}
