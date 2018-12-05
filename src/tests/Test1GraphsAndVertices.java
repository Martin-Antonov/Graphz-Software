package tests;


import model.EdgeType;
import model.Graph;

import java.util.ArrayList;
import java.util.Arrays;

public class Test1GraphsAndVertices {
    public void createSomeSimpleGraph() {
        Graph<Integer> graph = new Graph<Integer>();
        ArrayList<Integer> vsAsList = new ArrayList<Integer>();
        vsAsList.add(1);
        vsAsList.add(2);
        vsAsList.add(3);
        vsAsList.add(4);
        vsAsList.add(5);
        vsAsList.add(6);

        graph.addVertices(vsAsList);

        graph.addEdge(1, 2);
        graph.addEdge(1, 1);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);
        graph.addEdge(4, 5);
        graph.addEdge(5, 6);

        System.out.print(graph.toString());

        for (int i = 0; i < graph.getVertices().size(); i++) {
            System.out.println(graph.getVertices().get(i).toString());
        }
    }

    public void createDirectedGraph() {
        Graph<Integer> graph = new Graph<Integer>();
        ArrayList<Integer> vsAsList = new ArrayList<Integer>();
        vsAsList.add(1);
        vsAsList.add(2);
        vsAsList.add(3);

        graph.addVertices(vsAsList);

        graph.addEdge(1,2, EdgeType.DIRECTED);
        graph.addEdge(1,3, EdgeType.DIRECTED);



        Graph<Integer> cloned = graph.clone();

        System.out.print(cloned.toString());

        for (int i = 0; i < cloned.getVertices().size(); i++) {
            System.out.println(cloned.getVertices().get(i).toString());
        }
    }

    public void createHyperGraph(){
        Graph<Integer> graph = new Graph<Integer>();
        ArrayList<Integer> vsAsList = new ArrayList<Integer>();
        vsAsList.add(1);
        vsAsList.add(2);
        vsAsList.add(3);
        vsAsList.add(4);

        graph.addVertices(vsAsList);

        ArrayList<Integer> edge1 = new ArrayList<Integer>();
        edge1.add(1);
        edge1.add(2);
        edge1.add(3);

        ArrayList<Integer> edge2 = new ArrayList<Integer>();
        edge2.add(2);
        edge2.add(3);
        edge2.add(4);

        graph.addHyperEdge(edge1);
        graph.addHyperEdge(edge2);

        System.out.println(graph.toString());

    }
}
