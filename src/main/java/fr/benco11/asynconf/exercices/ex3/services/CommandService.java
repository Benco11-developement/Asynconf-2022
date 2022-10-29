package fr.benco11.asynconf.exercices.ex3.services;

import fr.benco11.asynconf.exercices.ex3.commands.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service qui gère les commandes
 */
public class CommandService {
    private final Map<Command.CommandId, Command> commands;

    public CommandService(AccountService accountService, TaskService taskService,
            PermissionService permissionService) {
        commands = new EnumMap<>(Command.CommandId.class);
        addCommand(new AddTaskCommand(accountService, taskService));
        addCommand(new ClearTaskCommand(taskService));
        addCommand(new CompleteTaskCommand(taskService));
        addCommand(new DeleteAccountCommand(accountService));
        addCommand(new ListTaskCommand(taskService, permissionService));
        addCommand(new RegisterAccountCommand(accountService, permissionService));
        addCommand(new RemoveTaskCommand(taskService));
        addCommand(new ConnectCommand(accountService));
    }

    public void addCommand(Command command) {
        commands.put(command.id(), command);
    }

    public Optional<Command> commandById(Command.CommandId id) {
        return (commands.containsKey(id)) ? Optional.of(commands.get(id)) : Optional.empty();
    }
}
