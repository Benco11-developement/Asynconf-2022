package fr.benco11.asynconf.exercices.ex3.commands;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static fr.benco11.asynconf.utils.InputUtils.withoutAccents;

// Représente une commande (utilise la preview java 17 du pattern matching et les sealed types)
public sealed interface Command permits AddTaskCommand, ClearTaskCommand, CompleteTaskCommand, ConnectCommand, DeleteAccountCommand, ListTaskCommand, RegisterAccountCommand, RemoveTaskCommand {
    CommandId id();

    /**
     * Map avec pour clé l'id de l'argument et en valeur l'Argument
     *
     * @return la Map
     */
    Map<String, Argument> arguments();


    /**
     * Vérifie que tous les arguments demandés ont bien été fournis
     *
     * @return un Predicate qui fait cette vérification
     */
    default Predicate<CommandExecution> isValid() {
        return c -> c.arguments()
                     .keySet()
                     .containsAll(arguments().keySet());
    }

    Consumer<CommandExecution> execute();


    enum CommandId {
        ADD_TASK("ajouter"), REMOVE_TASK("retirer"), COMPLETE_TASK("compléter"), LIST_TASKS("liste"), CLEAR_TASKS("vider"), REGISTER_USER("ajouter-compte"), DELETE_USER("supprimer-compte"), CONNECT("connecter");

        // Récupère un CommandId depuis un String sans tenir compte des accents ni des majuscules
        public static Optional<CommandId> getCommandIdById(String id) {
            Normalizer.normalize(id, Normalizer.Form.NFKD);
            return Arrays.stream(values())
                         .filter(c -> withoutAccents(c.id()).equalsIgnoreCase(withoutAccents(id)))
                         .findAny();
        }
        private final String id;

        CommandId(String id) {
            this.id = id;
        }

        public String id() {
            return id;
        }
    }
}
