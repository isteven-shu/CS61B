package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Queue<Integer> queue;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        queue = new ArrayDeque<>();
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        marked[s] = true;
        announce();

        queue.add(s);
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int neighbor : maze.adj(cur)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    distTo[neighbor] = distTo[cur] + 1;
                    edgeTo[neighbor] = cur;
                    announce();
                    if (neighbor == t) {
                        targetFound = true;
                        return;
                    }
                    queue.add(neighbor);
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

