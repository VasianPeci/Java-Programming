import java.util.ArrayList;

public class Board implements Comparable<Board> {
    int[][] goalBoard;
    int[][] tiles;
    int length;
    private int zeroRow;
    private int zeroCol;
    public int manhattanPriority;

    public Board(int[][] tiles) {
        this.length = tiles.length;
        this.tiles = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }

        this.goalBoard = new int[length][length];
        int n = 1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                this.goalBoard[i][j] = n;
                n++;
            }
        }
        this.goalBoard[length-1][length-1] = 0;

        this.manhattanPriority = manhattan();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(length + "\n");
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                sb.append(tiles[i][j] + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int tileAt(int row, int col) {
        if (!(row >= 0 && row < length) || !(col >= 0 && col < length)) {
            throw new IllegalArgumentException("Row and Col must be within the length range!");
        }
        return tiles[row][col];
    }

    public int size() {
        return length * length;
    }

    public int hamming() {
        int count = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != goalBoard[i][j]) count++;
            }
        }
        return count;
    }

    private int manhattan() {
        int sum = 0;
        int row;
        int col;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] == 0) continue;
                row = tiles[i][j] % length == 0 ? -1 + tiles[i][j]/length : tiles[i][j]/length;
                col = tiles[i][j] % length == 0 ? length - 1 : tiles[i][j] % length - 1;
                sum += Math.abs(row - i) + Math.abs(col - j);
            }
        }

        return sum;
    }

    public boolean isGoal() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] != goalBoard[i][j]) return false;
            }
        }
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board y = (Board) o;

        if (size() != y.size()) return false;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (tiles[i][j] != y.tileAt(i, j)) return false;
            }
        }
        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> boards = new ArrayList<>();

        int[][] temp = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                temp[i][j] = tiles[i][j];
            }
        }

        // left neighbor
        if (zeroCol != 0) {
            temp[zeroRow][zeroCol] = temp[zeroRow][zeroCol-1];
            temp[zeroRow][zeroCol-1] = 0;
            boards.add(new Board(temp));
            temp[zeroRow][zeroCol-1] = temp[zeroRow][zeroCol];
            temp[zeroRow][zeroCol] = 0;
        }

        // right neighbor
        if (zeroCol != length-1) {
            temp[zeroRow][zeroCol] = temp[zeroRow][zeroCol+1];
            temp[zeroRow][zeroCol+1] = 0;
            boards.add(new Board(temp));
            temp[zeroRow][zeroCol+1] = temp[zeroRow][zeroCol];
            temp[zeroRow][zeroCol] = 0;
        }

        // top neighbor
        if (zeroRow != 0) {
            temp[zeroRow][zeroCol] = temp[zeroRow-1][zeroCol];
            temp[zeroRow-1][zeroCol] = 0;
            boards.add(new Board(temp));
            temp[zeroRow-1][zeroCol] = temp[zeroRow][zeroCol];
            temp[zeroRow][zeroCol] = 0;
        }

        // bottom neighbor
        if (zeroRow != length-1) {
            temp[zeroRow][zeroCol] = temp[zeroRow+1][zeroCol];
            temp[zeroRow+1][zeroCol] = 0;
            boards.add(new Board(temp));
            temp[zeroRow+1][zeroCol] = temp[zeroRow][zeroCol];
            temp[zeroRow][zeroCol] = 0;
        }

        return boards;
    }

    public boolean isSolvable() {
        int[] arr = new int[length * length];
        int index = 0;

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                arr[index++] = tiles[i][j];
            }
        }

        int inversions = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) continue;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] == 0) continue;
                if (arr[i] > arr[j]) inversions++;
            }
        }

        if (length % 2 != 0) {
            return inversions % 2 == 0;
        }
        return (inversions + zeroRow) % 2 != 0;
    }


    public int compareTo(Board other) {
        if (manhattanPriority == other.manhattanPriority) return 0;
        if (manhattanPriority < other.manhattanPriority) return -1;
        return 1;
    }
}