package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.exceptions.AccountNotExistException;
import fr.benco11.asynconf.exercices.ex3.exceptions.InvalidCommandException;
import fr.benco11.asynconf.exercices.ex3.services.AccountService;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Commande "supprimer-compte"
 */
public final class DeleteAccountCommand implements Command {
    public static final String ACCOUNT_NAME_ARGUMENT = "name";
    private final AccountService accountService;

    public DeleteAccountCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandId id() {
        return CommandId.DELETE_USER;
    }

    @Override
    public Map<String, Argument> arguments() {
        return Map.of(ACCOUNT_NAME_ARGUMENT, new Argument(ACCOUNT_NAME_ARGUMENT, "Nom de l'utilisateur :", ""));
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> {
            // On vérifie que le compte existe puis on le supprime
            String name = c.arguments()
                           .get(ACCOUNT_NAME_ARGUMENT)
                           .value();
            if(name.isBlank()) throw new InvalidInputException("Le nom de compte est vide !");
            if(!accountService.isRegistered(name)) throw new AccountNotExistException(name);
            accountService.deleteAccount(name);
        };
    }
}
