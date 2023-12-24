package org.aoc.day23;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int row;
    public int col;
    public List<List<Integer>> edges;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.edges = new ArrayList<>();
    }

    public String toString() {
        return "(" + row + ", " + col + ") -> " + (edges.isEmpty() ? "none" : edges);
    }
}
