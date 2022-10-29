package fr.benco11.asynconf.exercices;

import fr.benco11.asynconf.Asynconf2022;
import fr.benco11.asynconf.exercices.ex3.commands.CommandExecution;
import fr.benco11.asynconf.exercices.ex3.terminal.TerminalSystem;

import java.util.Optional;

public class Ex3 implements Ex {
    @Override
    public int id() {
        return 3;
    }

    @Override
    public void launch() {
        System.out.println("Aide : Les arguments vous sont demandés après avoir rentré la commande");
        // Instancie un nouveau terminal puis l'exécute jusqu'à l'utilisation de la commande 'exit'
        TerminalSystem system = new TerminalSystem();
        while(true) {
            try {
                // Récupère l'entrée de l'utilisateur et l'ignore si elle est vide
                String command = system.nextCommandString();
                if(command.equalsIgnoreCase("exit")) break;
                if(command.isBlank()) continue;

                // Récupère la commande associée à l'entrée et l'ignore si elle n'existe pas, si elle est invalide ou si l'utilisateur n'a pas les permissions
                Optional<CommandExecution> executionOptional = system.getCommand(command);
                if(executionOptional.isEmpty()) continue;

                // Exécute la commande avec les arguments
                CommandExecution execution = executionOptional.get();
                execution.command()
                         .execute()
                         .accept(execution);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        Asynconf2022.main();
    }

}
