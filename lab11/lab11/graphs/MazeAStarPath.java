package lab11.graphs;

import java.util.Comparator;
import edu.princeton.cs.algs4.IndexMinPQ;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private IndexMinPQ<Integer> pq;
    private Maze maze;

    private class Node implements Comparable<Node> {
        private int v;
        private int priority;

        public Node(int v) {
            this.v = v;
            this.priority = distTo[v] + h(v);
        }

        @Override
        public int compareTo(Node n) {
            return priority - n.priority;
        }
    }

    /*
    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return n1.priority - n2.priority;
        }
    } */

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        int sourceX = maze.toX(v);
        int sourceY = maze.toY(v);
        int targetX = maze.toX(t);
        int targetY = maze.toY(t);
        return Math.abs(sourceX - targetX) + Math.abs(sourceY - targetY);
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return pq.minIndex();
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        pq = new IndexMinPQ<>(maze.V());
        pq.insert(s, distTo[s] + h(s));

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            marked[v] = true;
            announce();
            if (v == t) return;
            for (int w : maze.adj(v)) {
                relax(v, w);
            }
        }
    }

    private void relax(int begin, int end) {
        if (distTo[begin] + 1 < distTo[end]) {
            distTo[end] = distTo[begin] + 1;
            edgeTo[end] = begin;
            if (pq.contains(end))   pq.changeKey(end, distTo[end] + h(end));
            else pq.insert(end, distTo[end] + h(end));
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

