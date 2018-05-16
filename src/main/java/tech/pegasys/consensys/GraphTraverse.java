package tech.pegasys.consensys;

import java.util.ArrayList;
import java.lang.Comparable;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public class GraphTraverse extends Thread {

    GraphNode graph;
    VertexRepository vertexRepo;

    /**
     * Repository for storing visited node references
     *
     */
    public static class VertexRepository {
        private volatile ConcurrentHashMap<GraphNode,Boolean> visitedMap = new ConcurrentHashMap<>(32);

        /**
         * Mark a node as visited
         * @param node
         * @return True if the node was previously unvisited and now has been marked as visited
         *         False if the node has already been visited
         */
        public synchronized boolean visitNode( GraphNode node) {
            boolean first_time_visit = false;
            if( !visitedMap.containsKey(node)){
                visitedMap.put(node, new Boolean(true));
                first_time_visit = true;
            }
            return first_time_visit;
        }
    }

    /**
     * Simple class to represent a vertex in a graph
     */
    private static class GraphNode implements Comparable<GraphNode> {
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

    /**
     * Method to build the example graph provided in the challenge.  Build from top-left to bottom-right
     * @return
     */
    private static GraphNode BuildChallengeGraph() {

        //Sample graph had nine nodes;
        GraphNode root = new GraphNode(1);
        GraphNode two = new GraphNode(2);
        GraphNode three = new GraphNode(3);
        GraphNode four = new GraphNode(4);
        GraphNode five = new GraphNode(5);
        GraphNode six = new GraphNode(6);
        GraphNode seven = new GraphNode(7);
        GraphNode eight = new GraphNode(8);
        GraphNode nine = new GraphNode(9);


        //Left-most node is the root, had two directed connections
        root.children.add(two);
        root.children.add(four);

        //second left-most connected to third left-most
        two.children.add(three);
        two.children.add(seven);

        //fourth left-most connected to fifth, sixth and ninth nodes
        four.children.add(five);
        four.children.add(six);
        four.children.add(nine);

        //Fifth left-most connected to eighth and ninth
        five.children.add(eight);
        five.children.add(nine);

        //Sixth connected only to seventh
        six.children.add(seven);

        //Seventh connected to eighth.
        seven.children.add(eight);

        return root;
    }

    /**
     * Constructor -- initializes member instances
     * @param gh - initial value for member instance graph
     * @param vr - initial value for member instance vertexRepo
     */
    GraphTraverse(GraphNode gh, VertexRepository vr) {
        vertexRepo = vr;
        graph = gh;
    }

    /**
     * If a node has not already been visited, visit this node and recursively all related nodes.
     * @param parent
     */
    public void visitNodesInGraph( GraphNode parent ) {

        if (vertexRepo.visitNode(parent)) {
            System.out.println("Node " + parent.id + " visited! ");
            for (GraphNode children : parent.children) {
                visitNodesInGraph(children);
            }
        }
    }


    public void run() {
        try {
            visitNodesInGraph(graph);
        } catch (Exception e) {
            System.out.println("Graph visit interrupted before completion.  Reason: " + e.getMessage());
        }
    }

    public static void main( String[] args ){

        //Build the graph detailed in question 5
        GraphNode root = BuildChallengeGraph();

        //Initialize repository
        VertexRepository vr = new VertexRepository();

        //Visit the root
        vr.visitNode(root);
        System.out.println("Node " + root.id + " visited! ");

        //Concurrently visit the rest of the nodes in the graph
        for(GraphNode child: root.children){
            GraphTraverse gt = new GraphTraverse(child, vr);
            gt.start();
        }
    }

}
