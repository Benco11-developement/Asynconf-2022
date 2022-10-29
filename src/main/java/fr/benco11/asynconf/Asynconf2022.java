package fr.benco11.asynconf;

import fr.benco11.asynconf.exercices.*;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static fr.benco11.asynconf.utils.InputUtils.asInt;
import static fr.benco11.asynconf.utils.InputUtils.getOrRetry;

public class Asynconf2022 {
    public static final Scanner SCANNER = new Scanner(System.in, StandardCharsets.UTF_8);

    public static void main(String[] args) {
        main();
    }

    public static void main() {
        System.out.println("\n--------------------");
        Ex ex = getOrRetry(() -> {
            System.out.println("Veuillez entrer le numéro de l'exercice :");
            int id = asInt(SCANNER.nextLine()).orElse(-1);
            return switch(id) {
                case 1 -> new Ex1();
                case 2 -> new Ex2();
                case 3 -> new Ex3();
                case 4 -> new Ex4();
                case 5 -> new Ex5();
                default -> null;
            };
        }, "Exercice invalide !");
        System.out.println("\n--- Exercice " + ex.id() + " ---\n");
        ex.launch();
    }
}
