package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.services.TaskService;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Commande "vider"
 */
public final class ClearTaskCommand implements Command {

    private final TaskService taskService;

    public ClearTaskCommand(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public CommandId id() {
        return CommandId.CLEAR_TASKS;
    }

    @Override
    public Map<String, Argument> arguments() {
        return Collections.emptyMap();
    }

    @Override
    public Consumer<CommandExecution> execute() {
        return c -> taskService.clear();
    }
}
