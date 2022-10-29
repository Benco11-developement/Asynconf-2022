package fr.benco11.asynconf.exercices.ex3.commands;

// Argument avec son id, le texte à afficher lorsque le programme demande l'argument à l'utilisateur et la valeur de l'argument
public record Argument(String id, String askClient, String value) {
}
