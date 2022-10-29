package fr.benco11.asynconf.exercices.ex3.terminal;

import fr.benco11.asynconf.Asynconf2022;
import fr.benco11.asynconf.exercices.ex3.commands.Argument;
import fr.benco11.asynconf.exercices.ex3.commands.Command;
import fr.benco11.asynconf.exercices.ex3.commands.CommandExecution;
import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.exceptions.AutoDestructionException;
import fr.benco11.asynconf.exercices.ex3.exceptions.InvalidCommandException;
import fr.benco11.asynconf.exercices.ex3.services.AccountService;
import fr.benco11.asynconf.exercices.ex3.services.CommandService;
import fr.benco11.asynconf.exercices.ex3.services.PermissionService;
import fr.benco11.asynconf.exercices.ex3.services.TaskService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Représente un système complet avec le terminal et les services
 */
public class TerminalSystem {
    private final AccountService accountService;
    private final PermissionService permissionService;
    private final TaskService taskService;
    private final CommandService commandService;
    private User currentUser;

    public TerminalSystem() {
        // Initialise les différents services et met l'utilisateur courant à 'Administrateur'
        taskService = new TaskService();
        accountService = new AccountService();
        permissionService = new PermissionService(taskService);
        commandService = new CommandService(accountService, taskService, permissionService);

        currentUser = accountService.accountByName(AccountService.ADMIN_NAME)
                                    .orElseThrow(AutoDestructionException::new);
    }

    /**
     * Change de compte
     *
     * @param user le nouveau compte
     */
    public void login(User user) {
        System.out.println("Connexion en tant que '" + user.name() + "'");
        currentUser = user;
    }

    /**
     * Donne le préfixe du terminal à partir de l'utilisateur courant
     *
     * @return le préfixe
     */
    public String prefix() {
        return currentUser.name() + " $ ";
    }

    /**
     * Affiche le préfixe et récupère le prochain input de l'utilisateur
     *
     * @return l'input de l'utilisateur
     */
    public String nextCommandString() {
        System.out.print("\n" + prefix());
        return Asynconf2022.SCANNER.nextLine()
                                   .trim();
    }

    /**
     * Récupère la commande depuis un String d'input, vérifie les permissions et demande les arguments à l'utilisateur puis crée un CommandExecution
     *
     * @param commandString l'input
     * @return un Optional contenant un CommandExecution (utilisateur, commande et arguments) si la commande est valide et que l'utilisateur a les permissions requises
     */
    public Optional<CommandExecution> getCommand(String commandString) {
        // Récupère la commande
        Command.CommandId commandId = Command.CommandId.getCommandIdById(commandString)
                                                       .orElseThrow(() -> new InvalidCommandException(commandString));
        Command command = commandService.commandById(commandId)
                                        .orElseThrow(() -> new InvalidCommandException(commandString));

        // Vérifie que la commande soit bien inscrite dans les commandes autorisées par le groupe de permissions de l'utilisateur
        if(!permissionService.preAuthorize(currentUser, command)) return notAuthorized();

        // Demande les arguments à l'utilisateur
        Map<String, Argument> arguments = new HashMap<>();
        command.arguments()
               .forEach((id, arg) -> {
                   System.out.println(arg.askClient());
                   String value = Asynconf2022.SCANNER.nextLine();
                   arguments.put(id, new Argument(arg.id(), arg.askClient(), value));
               });

        // Construit le CommandExecution
        CommandExecution execution = new CommandExecution(command, currentUser, arguments, this);

        // Vérifie que la commande soit valide (tous les arguments présents) et que l'utilisateur ait la permission (en prenant compte des arguments cette fois-ci)
        if(!command.isValid()
                   .test(execution)) return badUsage();
        if(!permissionService.authorize(execution)) return notAuthorized();
        return Optional.of(execution);
    }

    private Optional<CommandExecution> notAuthorized() {
        System.out.println("Vous n'avez pas la permission d'exécuter la commande !");
        return Optional.empty();
    }

    private Optional<CommandExecution> badUsage() {
        System.out.println("Mauvaise utilisation de la commande !");
        return Optional.empty();
    }
}
