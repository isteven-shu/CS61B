package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Solver {
    private class SearchNode implements Comparable<SearchNode>{
        WorldState worldState;
        int moves;
        SearchNode parent;

        SearchNode(WorldState w, int m, SearchNode p) {
            worldState = w;
            moves = m;
            parent = p;
        }

        @Override
        public int compareTo(SearchNode n) {
            return this.moves + cachedEstimate(worldState) -
                   (n.moves + cachedEstimate(n.worldState));    // 这里忘记写括号了，一个很蠢的bug
        }
    }
    private MinPQ<SearchNode> moveSequences;
    private HashSet<WorldState> marked;
    private HashMap<WorldState, Integer> estimated;
    private SearchNode target;

    /**
     * Second optimization: To avoid recomputing the estimatedDistanceToGoal() result
     * from scratch each time during various priority queue operations.
     */
    private int cachedEstimate(WorldState ws) {
        if (!estimated.containsKey(ws)) {
            estimated.put(ws, ws.estimatedDistanceToGoal());
        }
        return estimated.get(ws);
    }

    public Solver(WorldState initial) {
        // Initialization
        marked = new HashSet<>();
        moveSequences = new MinPQ<>();
        estimated = new HashMap<>();

        // A* algorithm
        SearchNode src = new SearchNode(initial, 0, null);
        moveSequences.insert(src);
        marked.add(initial);
        if (initial.isGoal()) {
            target = src;
            return;
        }

        while (!moveSequences.isEmpty()) {
            SearchNode bms = moveSequences.delMin();
            marked.add(bms.worldState);
            for (WorldState ws : bms.worldState.neighbors()) {
                if (!marked.contains(ws)) {
                    SearchNode temp = new SearchNode(ws, bms.moves + 1, bms);
                    moveSequences.insert(temp);
                    if (ws.isGoal()) {
                        target = temp;
                        return;
                    }
                }
            }
        }
    }

    public int moves() {
        return target.moves;
    }

    public Iterable<WorldState> solution() {
        LinkedList<WorldState> list = new LinkedList<>();
        SearchNode n = target;
        while (n != null) {
            list.addFirst(n.worldState);
            n = n.parent;
        }

        return list;
    }
}
