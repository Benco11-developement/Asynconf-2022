package fr.benco11.asynconf.exercices;

import fr.benco11.asynconf.Asynconf2022;

import java.util.ArrayList;
import java.util.List;

public class Ex1 implements Ex {
    @Override
    public int id() {
        return 1;
    }

    @Override
    public void launch() {
        // Récupère les planètes jusqu'à la ligne vide
        System.out.println("Veuillez entrer les planètes :");
        List<String> steps = new ArrayList<>();
        String line;
        while(!(line = Asynconf2022.SCANNER.nextLine()).isEmpty()) steps.add(line);

        System.out.println(missionNameBySteps(steps));
        Asynconf2022.main();
    }

    /**
     * Calcule le nom de la mission
     *
     * @param steps liste des planètes
     * @return nom de la mission
     */
    public String missionNameBySteps(List<String> steps) {
        StringBuilder result = new StringBuilder();
        for(String step : steps) {
            // On regarde pour chaque planète si elle est déjà présente dans le nom de la mission, si oui, on dévoile un caractère de plus (ex : M, Ma, Mar...) jusqu'à ce que l'étape ait un nom unique dans le code de mission
            int stepIdLength = 1;
            String stepId;
            while(result.indexOf((stepId = step.substring(0, stepIdLength))) > -1) stepIdLength++;

            // On ajoute le nom de l'étape (les caractères dévoilés suivis du nombre de caractères restants)
            result.append(stepId)
                  .append(step.length() - stepIdLength);
        }
        return result.toString();
    }
}
