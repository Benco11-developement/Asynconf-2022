package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.exceptions.AccountNotExistException;
import fr.benco11.asynconf.exercices.ex3.exceptions.InvalidCommandException;
import fr.benco11.asynconf.exercices.ex3.services.AccountService;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Commande "connecter"
 */
public final class ConnectCommand implements Command {
    public static final String ACCOUNT_NAME_ARGUMENT = "name";

    private final AccountService accountService;

    public ConnectCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CommandId id() {
        return CommandId.CONNECT;
    }

    @Override
    public Map<String, Argument> arguments() {
        return Map.of(ACCOUNT_NAME_ARGUMENT, new Argument(ACCOUNT_NAME_ARGUMENT, "Nom d'utilisateur :", ""));
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> {
            // Vérifie que le compte existe puis se connecte
            String userName = c.arguments()
                               .get(ACCOUNT_NAME_ARGUMENT)
                               .value();
            if(userName.isBlank()) throw new InvalidInputException("Le nom d'utilisateur est vide !");
            User user = accountService.accountByName(userName)
                                      .orElseThrow(() -> new AccountNotExistException(userName));
            c.terminalSystem()
             .login(user);
        };
    }
}
