package advent_of_code_2015;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.BiFunction;

public class Day19 {

    private static <E> E minElement(HashMap<E, Double> fScore,
                                    HashSet<E> openSet) {
        double currentMin = Double.POSITIVE_INFINITY;
        E currentMinEl = null;

        for(E el : openSet) {
            double fScoreVal = fScore.getOrDefault(el, Double.POSITIVE_INFINITY);
            if (fScoreVal < currentMin) {
                currentMinEl = el;
                currentMin = fScoreVal;
            }
        }

        return currentMinEl;
    }

    private static <E> ArrayList<E> reconstruct_path(HashMap<E, E> came_from,
                                                 E current) {
        ArrayList<E> total_path = new ArrayList<>();
        total_path.add(current);

        while (came_from.keySet().contains(current)) {
            current = came_from.get(current);
            total_path.add(current);
        }
        return total_path;
    }

    public static <E> ArrayList<E> aStarSearch(E start,
                                      E goal,
                                      Function<E, Iterable<E>> neighbours,
                                      BiFunction<E, E, Double> heuristic,
                                      BiFunction<E, E, Double> distance) {
        HashSet<E> closedSet = new HashSet<>();
        HashSet<E> openSet = new HashSet<>();
        HashMap<E, E> cameFrom = new HashMap<>();
        HashMap<E, Double> gScore = new HashMap<>();
        HashMap<E, Double> fScore = new HashMap<>();

        openSet.add(start);
        gScore.put(start, 0.0);
        fScore.put(start, heuristic.apply(start, goal));

        while (!openSet.isEmpty()) {
            E current = minElement(fScore, openSet);

            if (current == goal) {
                return reconstruct_path(cameFrom, current);
            }

            openSet.remove(current);
            closedSet.add(current);

            for(E neighbour : neighbours.apply(current)) {
                if (closedSet.contains(neighbour)) {
                    continue;
                }

                double gScoreCurrent = gScore.getOrDefault(current, Double.POSITIVE_INFINITY);
                double gScoreTentative = gScoreCurrent + distance.apply(current, neighbour);

                if (!openSet.contains(neighbour)) {
                    openSet.add(neighbour);
                } else if (gScoreTentative >= gScore.getOrDefault(neighbour, Double.POSITIVE_INFINITY)) {
                    continue;
                }

                cameFrom.put(neighbour, current);
                gScore.put(neighbour, gScoreTentative);
                fScore.put(neighbour, gScoreTentative + heuristic.apply(neighbour, goal));
            }
        }

        return null;
    }
}
