package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfs(-1, 0);
    }

    // Helper methods go here
    /** My own version */
    private int dfs(int p, int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                int cycle = dfs(v, w);
                if(cycle != -1) {
                    edgeTo[w] = v;
                    announce();
                    if (v != cycle) {
                        return cycle;
                    }
                    return -1;
                } else {
                    return -1;
                }
            } else if (w != p) {
                // find the cycle!
                edgeTo[w] = v;
                announce();
                return w; // return where the cycle is detected
            }
        }
        return -1;
    }

    /** PKUFlyingPig's version */
    // Helper methods go here
    private boolean isFound = false;
    private int[] nodeTo;
    private void dfs_(int u, int v) {
        marked[v] = true;
        announce();
        for (int w : maze.adj(v)) {
            if (!marked[w]) {
                nodeTo[w] = v;
                dfs_(v, w);
            } else if (w != u) {
                // finding the cycle
                edgeTo[w] = v;
                announce();
                for (int x = v; x != w; x = nodeTo[x]) {
                    edgeTo[x] = nodeTo[x];
                    announce();
                }
                isFound = true;
            }
            if (isFound) return;
        }
    }
}
