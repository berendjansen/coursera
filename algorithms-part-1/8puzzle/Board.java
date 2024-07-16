import edu.princeton.cs.algs4.Stack;

public class Board {
    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }

    }

    // string representation of this board
    public String toString() {
        StringBuilder out = new StringBuilder(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                out.append(" ").append(tiles[i][j]);
                if (j == n - 1) out.append("\n");
            }
        }
        return out.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int outOfPlace = 0;
        int c = 1;
        if (tiles[n-1][n-1] != 0) outOfPlace++;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i == n - 1) && (j == n - 1)) break;
                if (tiles[i][j] != c && tiles[i][j] != 0) {
                    outOfPlace++;
                }
                c++;
            }
        }
        return outOfPlace;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanSum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i][j];
                if (tile == 0) continue;
                int trueX = (tile - 1) % n;
                int trueY = (tile - 1) / n;
                int xDistance = Math.abs(trueX - j);
                int yDistance = Math.abs(trueY - i);
                manhattanSum += (xDistance + yDistance);
            }
        }
        return manhattanSum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int c = 1;
        if (tiles[n-1][n-1] != 0) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == n-1 && j == n-1) break;
                if (tiles[i][j] != c) {
                    return false;
                }
                c++;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;

        if (y.getClass() != this.getClass()) {
            return false;
        }

        if (dimension() != ((Board) y).dimension()) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                 if (tiles[i][j] != ((Board) y).tiles[i][j]) {
                     return false;
                 }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> stack = new Stack<>();
        int zeroX = 0;
        int zeroY = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    zeroY = i;
                    zeroX = j;
                }
            }
        }

        if (zeroY != 0) {
            Board cloneUp = copy();
            cloneUp.exchange(zeroY, zeroX, zeroY - 1, zeroX);
            stack.push(cloneUp);
        }
        if (zeroY != n - 1) {
            Board cloneDown = copy();
            cloneDown.exchange(zeroY, zeroX, zeroY + 1, zeroX);
            stack.push(cloneDown);
        }
        if (zeroX != 0) {
            Board cloneLeft = copy();
            cloneLeft.exchange(zeroY, zeroX, zeroY, zeroX - 1);
            stack.push(cloneLeft);
        }
        if (zeroX != n-1) {
            Board cloneRight = copy();
            cloneRight.exchange(zeroY, zeroX, zeroY, zeroX + 1);
            stack.push(cloneRight);
        }
        return stack;
    }

    private Board copy() {
        int[][] newTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                newTiles[i][j] = tiles[i][j];
            }
        }
        return new Board(newTiles);
    }

    private void exchange(int ay, int ax, int by, int bx) {
        int old = tiles[ay][ax];
        tiles[ay][ax] = tiles[by][bx];
        tiles[by][bx] = old;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        boolean exchanged = false;
        Board twin = copy();
        for (int i = 0; i < n && !exchanged; i++) {
            if (twin.tiles[i][0] != 0 && twin.tiles[i][1] != 0) {
                twin.exchange(i, 0, i, 1);
                exchanged = true;
            }
        }
        return twin;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] a = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                a[i][j] = (i * 3) + j + 1;
            }
        }
        a[2][2] = 0;
        Board board = new Board(a);
        Board board2 = new Board(a);
//        System.out.println(board);
        System.out.println(board.isGoal());
        System.out.println(board.twin().isGoal());
        System.out.println(board.twin());
        System.out.println(board.twin().hamming());
        System.out.println(board.twin().manhattan());

        System.out.println(board.equals(board2));
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
    }

}