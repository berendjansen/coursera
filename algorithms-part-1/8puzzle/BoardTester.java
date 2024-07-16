import edu.princeton.cs.algs4.In;

public class BoardTester {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board board = new Board(tiles);

        System.out.printf("Manhattan: %d\n", board.manhattan());
        System.out.println(board);

        Board board1 = new Board(new int[][] {{1, 2, 0}, {7, 5, 4}, {8, 6, 3}});
        Board board2 = new Board(new int[][] {{1, 2, 0}, {7, 5, 4}, {8, 6, 3}});
        System.out.println(board1.equals(board2));

        for (Board b: board1.neighbors()) {
            System.out.println(b);
        }

        Board board3 = new Board(new int[][] {{1, 6, 5}, {4, 2, 3}, {8, 7, 0}});
        System.out.println(board3.isGoal());
    }
}
