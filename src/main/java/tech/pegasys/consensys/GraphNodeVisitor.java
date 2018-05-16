package tech.pegasys.consensys;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 */
public class GraphNodeVisitor implements Runnable{

    private volatile static Collection<GraphNode> visitedMap = Collections.synchronizedSet(new HashSet<GraphNode>());
    private GraphNode node;

    GraphNodeVisitor( GraphNode node ){
        this.node = node;
    }

    @Override
    public void run() {
        synchronized (visitedMap) {
            if (!visitedMap.contains(node)) {
                System.out.println("Node " + node.getId() + " visited! ");
                visitedMap.add(node);
            }
        }
    }

    public boolean visited(GraphNode gn ) {
        return visitedMap.contains(gn);
    }
}
