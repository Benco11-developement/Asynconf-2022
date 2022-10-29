package fr.benco11.asynconf.exercices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.benco11.asynconf.Asynconf2022;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Ex4 implements Ex {

    public static final Gson GSON = new GsonBuilder().create();

    @Override
    public int id() {
        return 4;
    }

    @Override
    public void launch() {
        // Récupère le code secret
        System.out.println("Veuillez entrer le code secret :");
        String input = Asynconf2022.SCANNER.nextLine();

        // Décode l'input en base64 et parse le json en une liste de Planet
        List<Planet> planets = planetsListFromJson(decodeBase64(input));

        // Trie la liste de planètes grâce à l'algorithme Quicksort (sans une once de créativité)
        planets = quickSort(planets);

        // Affiche les planètes triées
        System.out.println("\n" + planets.stream()
                                  .map(Planet::toString)
                                  .collect(Collectors.joining("\n\n")));

        Asynconf2022.main();
    }

    /**
     * Décode l'entrée en base64 en utilisant java.util.Base64.Decoder
     *
     * @param input l'entrée
     * @return le message décodé
     */
    public String decodeBase64(String input) {
        return new String(Base64.getDecoder()
                                .decode(input), StandardCharsets.UTF_8);
    }

    /**
     * Parse le json en une liste de planètes grâce à la library Gson
     *
     * @param json le json d'entrée
     * @return la liste de planètes
     */
    public List<Planet> planetsListFromJson(String json) {
        return GSON.fromJson(json, new TypeToken<List<Planet>>() {
        }.getType());
    }

    /**
     * Appelle quicksort avec le début et la fin correspondant à la liste
     *
     * @param planets liste des planètes
     * @return la liste triée
     */
    public List<Planet> quickSort(List<Planet> planets) {
        return quickSort(planets, 0, planets.size() - 1);
    }

    /**
     * Implémentation de l'algorithme Quicksort, très classique, de manière instable (qui ne préserve pas l'ordre entre les planètes avec une distance identique) ce qui n'est pas dérangeant dans ce cas
     *
     * @param planets liste de planètes à trier
     * @return la liste triée
     */
    public List<Planet> quickSort(List<Planet> planets, int start, int end) {
        if(end > start) {
            // En prenant le dernier élément comme pivot, on fait le premier décalage sur l'ensemble des éléments puis on sépare les éléments déplacés du reste en deux partitions auxquelles on applique à nouveau quicksort
            int i = partition(planets, start, end);
            quickSort(planets, start, i - 1);
            quickSort(planets, i + 1, end);
        }
        return planets;
    }

    /**
     * Implémentation de partition (Quicksort)
     *
     * @param planets liste des planètes
     * @param begin   début de la partition (sous-ensemble)
     * @param end     fin de la partition
     * @return l'indice du nouveau pivot
     */
    private int partition(List<Planet> planets, int begin, int end) {
        // Choisit comme pivot la fin du sous-ensemble
        Planet pivot = planets.get(end);

        // i représente l'index du dernier élément déplacé
        int i = (begin - 1);

        // Déplace toutes les planètes avec une distance à l'étoile inférieure au pivot à l'index i puis on augmente i (= i, puis i+1, i+2,...) (on balaie par la gauche)
        for(int j = begin; j < end; j++) {
            if(planets.get(j).distanceToStar <= pivot.distanceToStar) {
                i++;
                Planet temp = planets.get(i);
                planets.set(i, planets.get(j));
                planets.set(j, temp);
            }
        }

        // On déplace le pivot juste après les planètes déplacées
        if(i + 1 < planets.size()) {
            Planet temp = planets.get(i + 1);
            planets.set(i + 1, pivot);
            planets.set(end, temp);
        }

        return i + 1;
    }

    /**
     * Représente une planète
     *
     * @param name           le nom de la planète
     * @param size           la taille (en km)
     * @param mass           la masse (en tonnes)
     * @param distanceToStar la distance à l'étoile (en km)
     */
    public record Planet(String name, long size, long mass, long distanceToStar) {
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Nom : ")
              .append(name)
              .append("\n");
            sb.append("Taille : ")
              .append(size)
              .append("km\n");
            sb.append("Masse : ")
              .append(mass)
              .append(" tonnes\n");
            sb.append("Distance à l'étoile : ")
              .append(distanceToStar)
              .append("km");
            return sb.toString();
        }
    }
}
