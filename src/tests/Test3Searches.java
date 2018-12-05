package tests;

import model.Algorithms.SearchAlgorithms;
import model.Graph;
import model.GraphGenerator;

public class Test3Searches {

    public void testDFS(){
        GraphGenerator generator = new GraphGenerator();
        SearchAlgorithms search = new SearchAlgorithms();

        Graph<Integer> g = generator.generateByKey("K(3)+K(3)");
        search.DFS(g);
    }
    public void testBFS(){
        GraphGenerator generator = new GraphGenerator();
        SearchAlgorithms search = new SearchAlgorithms();

        Graph<Integer> g = generator.completeBinaryTree(2);

        search.BFS(g);
    }
}
