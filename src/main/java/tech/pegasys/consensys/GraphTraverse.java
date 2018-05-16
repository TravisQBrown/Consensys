package tech.pegasys.consensys;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GraphTraverse {
    private static final int MAX_THREADS = 5;
    static ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);

    GraphNode graph;
    GraphNodeVisitor vertexRepo;

    /**
     * If a node has not already been visited, visit this node and recursively all related nodes.
     * @param root
     */

    public void visitNode( GraphNode root) {
        //Set instance variables
        this.graph = root;
        vertexRepo =  new GraphNodeVisitor(this.graph);

        //Visit node in different thread
        pool.execute(vertexRepo);

        //If any children have not been visited, recursively visit.
        for(GraphNode child: root.children){
            GraphTraverse gt = new GraphTraverse();
            if( !vertexRepo.visited(child))
                gt.visitNode( child );
        }
    }

}
