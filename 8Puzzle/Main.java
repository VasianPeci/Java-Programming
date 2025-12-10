import java.util.ArrayList;
import edu.princeton.cs.algs4.*;

public static void main(String[] args) {
    for (String filename : args) {
        In in = new In(filename);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }

        Board initial = new Board(tiles);
        Solver solver = new Solver(initial);
        System.out.println("Total moves: " + solver.moves());
        System.out.println("Length and boards for each move: ");
        for (Board b : solver.solution()) {
            System.out.print(b.toString());
        }
    }
}