package tests;

import model.Graph;
import model.GraphGenerator;

public class Test4GraphProperties {
    public void basicTest(){
        GraphGenerator graphGenerator = new GraphGenerator();
        Graph<Integer> graph = graphGenerator.empty(6);

        graph.addEdge(0,1);
        graph.addEdge(0,4);
        graph.addEdge(4,3);
        graph.addEdge(4,1);
        graph.addEdge(3,2);
        graph.addEdge(3,5);
        graph.addEdge(2,1);

        graph.algorithms.calculateProperties();
    }


}
