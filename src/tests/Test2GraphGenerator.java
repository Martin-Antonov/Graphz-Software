package tests;

import model.Graph;
import model.GraphGenerator;

public class Test2GraphGenerator {
    public void testComplement(){
        GraphGenerator g = new GraphGenerator();
        Graph<Integer> C4 = g.cycle(4);
        Graph<Integer> complement = g.complement(C4);

        System.out.print(complement.toString());
        System.out.print(C4);
    }

    public void testCommandGenerator(){
        GraphGenerator g = new GraphGenerator();
        Graph<Integer> graph = g.generateByKey("K(3)+K(4)+Star(5)");
        System.out.print(graph.toString());
    }
}
