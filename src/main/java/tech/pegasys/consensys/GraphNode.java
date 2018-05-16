package tech.pegasys.consensys;

import java.util.ArrayList;

public class GraphNode implements Comparable<GraphNode> {
    private int id;
    ArrayList<GraphNode> children = new ArrayList<>();

    GraphNode(int info) {
        id = info;
    }

    public int getId() {
        return id;
    }
    public int compareTo(GraphNode b) {
        return this.id - b.id;
    }

}
