import java.util.*;
import edu.princeton.cs.algs4.*;

public class Solver {

    private final Node goalNode;

    private static class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node parent;
        private final int priority;

        public Node(Board board, int moves, Node parent) {
            this.board = board;
            this.moves = moves;
            this.parent = parent;
            this.priority = moves + board.manhattanPriority; // A* priority
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.priority, other.priority);
        }
    }

    public Solver(Board initial) {
        if (initial == null || !initial.isSolvable())
            throw new IllegalArgumentException("Board cannot be solved!");

        MinPQ<Node> pq = new MinPQ<>();
        HashSet<Board> visited = new HashSet<>();
        pq.insert(new Node(initial, 0, null));

        Node current = null;
        while (!pq.isEmpty()) {
            current = pq.delMin();

            if (current.board.isGoal()) break; // reached goal

            visited.add(current.board);

            for (Board neighbor : current.board.neighbors()) {
                if (current.parent != null && neighbor.equals(current.parent.board)) continue; // skip back move
                if (!visited.contains(neighbor)) {
                    pq.insert(new Node(neighbor, current.moves + 1, current));
                }
            }
        }

        goalNode = current;
    }

    public int moves() {
        if (goalNode == null) return -1;
        return goalNode.moves;
    }

    public Iterable<Board> solution() {
        if (goalNode == null) return null;
        ArrayList<Board> path = new ArrayList<>();
        Node node = goalNode;
        while (node != null) {
            path.add(node.board);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}