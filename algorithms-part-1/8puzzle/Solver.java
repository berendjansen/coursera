import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private final boolean solvable;
    private final int moves;
    private final Stack<Board> solution;

    private static class SearchNode {
        Board board;
        int moves;
        SearchNode previous;
        int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
        }

        public static class ByPriority implements Comparator<SearchNode> {
            public int compare(SearchNode a, SearchNode b) {
                return a.priority - b.priority;
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board cannot be null");
        }
        
        MinPQ<SearchNode> initialPQ = new MinPQ<>(new SearchNode.ByPriority());
        SearchNode currentNode = new SearchNode(initial, 0, null);
        initialPQ.insert(currentNode);

        MinPQ<SearchNode> twinPQ = new MinPQ<>(new SearchNode.ByPriority());
        SearchNode currentTwinNode = new SearchNode(initial.twin(), 0, null);
        twinPQ.insert(currentTwinNode);

        while (!currentNode.board.isGoal() && !currentTwinNode.board.isGoal()) {
            currentNode = initialPQ.delMin();
            currentTwinNode = twinPQ.delMin();
            iteration(initialPQ, currentNode);
            iteration(twinPQ, currentTwinNode);
        }

        this.solvable = !currentTwinNode.board.isGoal();
        this.moves = currentNode.moves;
        this.solution = new Stack<>();
        while (currentNode != null) {
            solution.push(currentNode.board);
            currentNode = currentNode.previous;
        }

    }

    private void iteration(MinPQ<SearchNode> pq, SearchNode node) {
        for (Board b : node.board.neighbors()) {
            SearchNode newNode = new SearchNode(b, node.moves + 1, node);
            if (node.previous == null || !newNode.board.equals(node.previous.board)) {
                pq.insert(newNode);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) return moves;
        return -1;
    }

    // sequence of boards in the shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (solvable) {
            return this.solution;
        } else {
            return null;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}