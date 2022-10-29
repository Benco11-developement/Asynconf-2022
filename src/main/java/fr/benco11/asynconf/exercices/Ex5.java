package fr.benco11.asynconf.exercices;

import fr.benco11.asynconf.Asynconf2022;
import fr.benco11.asynconf.utils.InvalidInputException;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import static fr.benco11.asynconf.exercices.Ex5.Board.*;

public class Ex5 implements Ex {
    @Override
    public int id() {
        return 5;
    }

    @Override
    public void launch() {
        // Récupère le plan
        System.out.println("Veuillez entrer le plan :");
        List<String> lines = new ArrayList<>();
        String l;
        while(!(l = Asynconf2022.SCANNER.nextLine()).isEmpty()) lines.add(l);

        // Parse l'input en un Board
        Board board = boardFromInput(lines);

        // En considérant le plan comme un labyrinthe, on utilise l'algorithme de Lee (https://en.wikipedia.org/wiki/Lee_algorithm)
        // On génère alors le tableau de distance par rapport à la sortie
        board.distanceBoard = generateDistanceBoard(board);

        // On récupère ensuite le chemin et on affiche les cases qui le composent
        System.out.println(getPath(board).stream()
                                         .map(Coord::toString)
                                         .collect(Collectors.joining(";")));

        Asynconf2022.main();
    }

    /**
     * Transforme un plan de plusieurs lignes en un Board
     *
     * @param lines le plan
     * @return le Board
     */
    public Board boardFromInput(List<String> lines) {
        int ordinate = lines.size();

        // Vérifie qu'il y ait au moins une ligne, que toutes les lignes aient la même taille et qu'aucune ligne ne fasse plus de 26 caractères de long
        if(lines.isEmpty() || lines.stream()
                                   .anyMatch(l -> l.length() != lines.get(0)
                                                                     .length()) || lines.get(0)
                                                                                        .length() > 26)
            throw new InvalidInputException();

        int abscissa = lines.get(0)
                            .length();

        Board board = new Board(new byte[abscissa][ordinate], new int[abscissa][ordinate]);

        // Remplis chaque point du tableau 2d représentant le labyrinthe en fonction du caractère dans le plan d'entrée et définis l'arrivée et le départ
        for(int x = 0; x < abscissa; x++) {
            for(int y = 0; y < ordinate; y++) {
                board.mazeBoard[x][y] = switch(lines.get(y)
                                                    .charAt(x)) {
                    case 'O' -> OBSTACLE;
                    case '_' -> NAVIGABLE;
                    case 'X' -> {
                        board.setStart(new Coord(x, y));
                        yield NAVIGABLE;
                    }
                    case 'V' -> {
                        board.setEnd(new Coord(x, y));
                        yield NAVIGABLE;
                    }
                    default -> throw new InvalidInputException();
                };
            }
        }

        return board;
    }

    /**
     * En utilisant l'algorithme de Lee, calcule un plan de distance qui représente pour chaque cellule sa distance avec l'arrivée en nombre de mouvements
     *
     * @param board le Board (labyrinthe)
     * @return le plan de distance
     */
    public int[][] generateDistanceBoard(Board board) {
        int abscissa = board.abscissa();
        int ordinate = board.ordinate();

        int[][] distanceBoard = board.distanceBoard;

        // Met toutes les cases navigables à Integer.MAX_VALUE et les murs à Integer.MAX_VALUE-1 dans le plan de distance pour ne pas visiter les murs
        for(int x = 0; x < abscissa; x++) {
            for(int y = 0; y < ordinate; y++) {
                distanceBoard[x][y] = Integer.MAX_VALUE - ((board.mazeBoard[x][y] == OBSTACLE) ? 1
                                                                                               : 0);
            }
        }

        // Récupère le début et la fin et met en place une file représentant les cellules dont les voisines doivent être visitées
        Coord end = board.getEnd();
        Coord start = board.getStart();

        Deque<Coord> toVisit = new ArrayDeque<>();
        Deque<Coord> toVisitTemp = new ArrayDeque<>();

        distanceBoard[end.x][end.y] = 0;
        toVisit.add(end);

        int distance = 1;
        boolean startFound = false;

        while(!startFound) {
            // Tant que toutes les voisines des cases de toVisit n'ont pas été visitées :
            while(!toVisit.isEmpty()) {
                // On récupère la case et si c'est le départ, BINGO, c'est inutile de continuer d'itérer
                Coord p = toVisit.pop();
                if(p.equals(start)) {
                    startFound = true;
                    break;
                }
                // On visite chaque voisin grâce aux différents mouvements dans MOVEMENTS
                for(byte[] move : MOVEMENTS) {
                    int x = p.x + move[0];
                    int y = p.y + move[1];
                    // On vérifie que le voisin est bien dans le plan, et qu'il n'a pas été visité auparavant (et que ce n'est pas un mur)
                    if(x < 0 || y < 0 || x >= abscissa || y >= ordinate || distanceBoard[x][y] != Integer.MAX_VALUE)
                        continue;
                    distanceBoard[x][y] = distance;
                    toVisitTemp.add(new Coord(x, y));
                }
            }
            // Une fois que toutes les cases à visiter sont visitées, on incrémente la distance puisqu'à la prochaine itération, ce seront les voisins des dernières cases qui verront leurs cellules voisines explorées
            distance++;
            toVisit.addAll(toVisitTemp);
            toVisitTemp.clear();
        }
        return distanceBoard;
    }

    /**
     * Récupère le chemin une fois que le plan de distance est calculé en suivant à chaque fois les cases avec la plus petite distance avec l'arrivée
     *
     * @param board le Board avec le plan de distance
     * @return la liste de points constituant le chemin
     */
    public List<Coord> getPath(Board board) {
        List<Coord> path = new ArrayList<>();

        int abscissa = board.abscissa();
        int ordinate = board.ordinate();

        Coord start = board.start;
        Coord end = board.getEnd();
        Coord now = start;

        path.add(start);

        // Tant que l'arrivée n'a pas été rejointe, on continue de se déplacer
        while(!end.equals(now)) {
            Coord min = null;
            int minDistance = Integer.MAX_VALUE;
            // Essaye tous les mouvements à partir du point courant et ne retient que celui avec la plus petite distance
            for(byte[] move : MOVEMENTS) {
                int x = now.x + move[0];
                int y = now.y + move[1];
                if(x < 0 || y < 0 || x >= abscissa || y >= ordinate) continue;
                int moveDistance = board.distanceBoard[x][y];
                if(moveDistance < minDistance) {
                    min = new Coord(x, y);
                    minDistance = moveDistance;
                }
            }
            // Ajoute le mouvement dans le chemin et le définis comme le nouveau point courant
            path.add(min);
            now = min;
        }
        return path;
    }

    /**
     * Représente un plateau avec le plan de distance, le plan du labyrinthe, le début et la fin
     */
    static class Board {
        public static final byte OBSTACLE = 0;
        public static final byte NAVIGABLE = 1;
        protected static final byte[][] MOVEMENTS = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        protected byte[][] mazeBoard;
        protected int[][] distanceBoard;
        private Coord start;

        private Coord end;

        public Board(byte[][] mazeBoard, int[][] distanceBoard) {
            this.mazeBoard = mazeBoard;
            this.distanceBoard = distanceBoard;
        }

        public Coord getStart() {
            return start;
        }

        public void setStart(Coord start) {
            this.start = start;
        }

        public Coord getEnd() {
            return end;
        }

        public void setEnd(Coord end) {
            this.end = end;
        }

        public int ordinate() {
            return mazeBoard[0].length;
        }

        public int abscissa() {
            return mazeBoard.length;
        }
    }

    public record Coord(int x, int y) {
        @Override
        public String toString() {
            return Character.toString(65 + x) + (y + 1);
        }
    }
}
