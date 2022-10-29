package fr.benco11.asynconf.exercices;

import fr.benco11.asynconf.Asynconf2022;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static fr.benco11.asynconf.utils.InputUtils.*;
import static fr.benco11.asynconf.utils.MathUtils.daysToH;
import static fr.benco11.asynconf.utils.MathUtils.roundAndFormat;

public class Ex2 implements Ex {

    public static final String SPEED_PROPERTY = "speed";
    public static final String SPEED_UNIT = "km/h";
    public static final String NAME_PROPERTY = "name";
    public static final String PRICE_PROPERTY = "price";
    public static final String PRICE_UNIT = "/km";

    @Override
    public int id() {
        return 2;
    }

    @Override
    public void launch() {
        System.out.println("Veuillez entrer les propriétés du vaisseau :");

        // On récupère le premier input (les propriétés du vaisseau) et on le transforme en une map
        String propertiesString = Asynconf2022.SCANNER.nextLine();
        var starshipProperties = starshipProperties(propertiesString);

        // On vérifie que la map contient bien les trois attributs : vitesse, nom et prix donc que l'entrée soit valide
        if(!(starshipProperties.containsKey(SPEED_PROPERTY) && starshipProperties.containsKey(NAME_PROPERTY) && starshipProperties.containsKey(PRICE_PROPERTY)))
            throw new InvalidInputException();

        // On récupère les attributs de prix et de vitesse, puis on enlève tous les espaces comme ce sont des nombres et on tente de les convertir en double (flottants)
        String pricePerKm = starshipProperties.get(PRICE_PROPERTY);
        String speedKmPerH = starshipProperties.get(SPEED_PROPERTY);
        double price = asDouble(removeSpace(pricePerKm.replace(PRICE_UNIT, ""))).orElseThrow(InvalidInputException::new);
        double speed = asDouble(removeSpace(speedKmPerH.replace(SPEED_UNIT, ""))).orElseThrow(InvalidInputException::new);

        // On récupère le second input (la durée du voyage) et en cas d'erreur d'entrée, on la redemande jusqu'à ce qu'elle soit valide
        double durationDays = getOrRetry(() -> {
            System.out.println("Veuillez entrer la durée du voyage :");
            return asDouble(Asynconf2022.SCANNER.nextLine()).orElse(null);
        }, "Entrée invalide !");

        // On calcule la distance du voyage, avec laquelle on calcule le prix puis on arrondit au dixième le résultat et on le formate correctement
        System.out.println(roundAndFormat(price(distance(speed, durationDays), price), 1) + "€");
        Asynconf2022.main();
    }

    /**
     * Parse le premier input en une Map
     *
     * @param input propriétés du vaisseau
     * @return Map avec les propriétés
     */
    public Map<String, String> starshipProperties(String input) {
        // Si l'entrée ne contient aucun = ou ;, elle est invalide
        if(!(input.contains("=") && input.contains(";")))
            throw new InvalidInputException();
        return Arrays.stream(input.split(";"))
                     .map(property -> property.split("=", 2))
                     .collect(Collectors.toMap(property -> property[0], property -> property[1]));
    }

    /**
     * On convertit en heure 'time' puis on le multiplie par la vitesse pour obtenir la distance
     *
     * @param speed en km/h
     * @param time  en jours
     * @return la distance en km
     */
    public double distance(double speed, double time) {
        return daysToH(time)*speed;
    }

    /**
     * On multiplie la distance et le prix au km pour obtenir le prix
     *
     * @param distance en km
     * @param price    en €/km
     * @return le prix en €
     */
    public double price(double distance, double price) {
        return distance*price;
    }
}
