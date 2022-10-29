package fr.benco11.asynconf.exercices.ex3.commands;

import fr.benco11.asynconf.exercices.ex3.entity.User;
import fr.benco11.asynconf.exercices.ex3.terminal.TerminalSystem;

import java.util.Map;

/*
 * Détails de l'exécution d'une commande
 */
public record CommandExecution(Command command, User user, Map<String, Argument> arguments,
                               TerminalSystem terminalSystem) {
}
