package advent_of_code_2015;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.function.Function;

public class Day22 {
    public static <E> E breadthFirstSearch(E start,
                                           Function<E, Boolean> endTest,
                                           Function<E, Iterable<E>> neighbours) {
        HashSet<E> s = new HashSet<>();
        // HashMap<E, E> parents = new HashMap<>();
        ArrayDeque<E> q = new ArrayDeque<>();

        s.add(start);
        q.add(start);

        while (!q.isEmpty()) {
            E current = q.remove();

            if (endTest.apply(current)) {
                return current;
            }

            for (E n : neighbours.apply(current)) {
                if (!s.contains(n)) {
                    s.add(n);
                    // parents.put(n, current);
                    q.add(n);
                }
            }
        }

        return null;
    }
}
