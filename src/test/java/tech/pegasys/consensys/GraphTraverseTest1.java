package tech.pegasys.consensys;

import org.junit.Test;

public class GraphTraverseTest1 {
    @Test
    public void testSomething() {
        GraphNode root = BuildChallengeGraph();
        GraphTraverse gt = new GraphTraverse();
        gt.visitNode(root);
        gt.pool.shutdown();
    }

    private  GraphNode BuildChallengeGraph() {

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
}

