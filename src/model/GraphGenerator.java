package model;

//Reference here for more types: http://doc.sagemath.org/html/en/reference/graphs/sage/graphs/graph_generators.html

import java.util.*;
import java.util.regex.Pattern;

//add by key
public class GraphGenerator {
    //TODO: Add graph types and chromatic numbers for known graphs

    public Graph<Integer> generateByKey(String key) {
        String keyToLower = key.toLowerCase();
        if (keyToLower.startsWith("!")) {
            Graph<Integer> graph = this.generateByKey(keyToLower.substring(1,keyToLower.length()));
            return this.complement(graph);
        }
        else if(keyToLower.contains("+")){
            String[] graphKeys = keyToLower.split("\\+");
            Graph<Integer> graph = this.generateByKey(graphKeys[0]);

            for (int i = 1; i < graphKeys.length; i++) {
                graph = this.merge(graph,this.generateByKey(graphKeys[i]));
            }
            return graph;
        }
        else{
            int openingBracketIndex = keyToLower.indexOf("(");
            int closingBracketIndex = keyToLower.indexOf(")");
            String graphTitle = openingBracketIndex!=-1?keyToLower.substring(0,openingBracketIndex):keyToLower;
            String[] params = openingBracketIndex!=-1?
                    keyToLower.substring(openingBracketIndex+1,closingBracketIndex).split(","):
                    null;
            if(graphTitle.equals("k")){
                ArrayList<Integer> paramsInt = new ArrayList<Integer>();

                for (String param : params) {
                    paramsInt.add(Integer.parseInt(param));
                }
                return this.complete(paramsInt);
            } else if(graphTitle.equals("c")){
                return this.cycle(Integer.parseInt(params[0]));
            }else if(graphTitle.equals("p")){
                return this.path(Integer.parseInt(params[0]));
            }else if(graphTitle.equals("empty")){
                return this.empty(Integer.parseInt(params[0]));
            }else if(graphTitle.equals("star")){
                return this.star(Integer.parseInt(params[0]));
            }else if(graphTitle.equals("wheel")){
                return this.wheel(Integer.parseInt(params[0]));
            }else if(graphTitle.equals("bull")){
                return this.bull();
            }else if(graphTitle.equals("prism")){
                return this.prism(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
            }else if(graphTitle.equals("petersen")){
                return this.petersen(Integer.parseInt(params[0]),Integer.parseInt(params[1]));
            }else if(graphTitle.equals("random")){
                return this.random(Integer.parseInt(params[0]),Double.parseDouble(params[1]));
            }else{
                System.out.print("error");
            }
        }
        return this.empty(1);
    }

    public Graph<Integer> empty(int n) {
        Graph<Integer> graph = new Graph<Integer>();
        for (int i = 0; i < n; i++) {
            graph.addVertex(i);
        }

        return graph;
    }

    public Graph<Integer> cycle(int n) {
        if (n < 3) {
            throw new IllegalArgumentException("A Cycle should have at least 3 vertices!");
        }
        Graph<Integer> graph = this.empty(n);

        for (int i = 0; i < n; i++) {
            graph.addEdge(i, (i + 1) % n);
        }

        return graph;
    }

    public Graph<Integer> path(int n) {
        Graph<Integer> graph = this.empty(n);

        for (int i = 0; i < n-1; i++) {
            graph.addEdge(i, i + 1);
        }
        return graph;
    }

    public Graph<Integer> complete(int n) {
        Graph<Integer> graph = this.empty(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                graph.addEdge(i, j);
            }
        }

        return graph;
    }

    public Graph<Integer> complete(Integer[] indSets) {
        ArrayList<Integer> indSetsList = new ArrayList<Integer>(Arrays.asList(indSets));

        return complete(indSetsList);
    }

    //(2,3,1,4) - > (2,5,6,10)
    //Example of the algorithm: Take K(2,3,2). Transform (2,3,2) into (2,5,7) by adding.
    //Then subtract 1 from each to get (1,4,6). This gives us the end points of the 3 groups of independent sets
    //From there add all edges.
    public Graph<Integer> complete(Collection<Integer> indSets) {
        ArrayList<Integer> indSetsList = new ArrayList<Integer>(indSets);
        if (indSetsList.size() == 1) {
            return this.complete(indSetsList.get(0));
        } else if (indSetsList.size() < 0) {
            throw new IllegalArgumentException("Cannot have sets with negative length");
        }
        int sum = indSetsList.stream().mapToInt(i -> i).sum();

        Graph<Integer> graph = this.empty(sum);

        int iteratorSum = indSetsList.get(0);
        for (int i = 1; i < indSetsList.size(); i++) {
            indSetsList.set(i, indSetsList.get(i) + indSetsList.get(i - 1));
        }
        for (int i = 0; i < indSetsList.size(); i++) {
            indSetsList.set(i, indSetsList.get(i) - 1);
        }

        for (int i = 0; i < indSetsList.size(); i++) {
            int toGroup1 = indSetsList.get(i);
            for (int j = i + 1; j < indSetsList.size(); j++) {
                int toGroup2 = indSetsList.get(j);
                int fromGroup1 = (i == 0) ? 0 : indSetsList.get(i - 1) + 1; // If it is the first element we have to start from zero;
                int fromGroup2 = indSetsList.get(j - 1) + 1; //otherwise we start from the previous element minus 1;

                for (int k = fromGroup1; k <= toGroup1; k++) {
                    for (int l = fromGroup2; l <= toGroup2; l++) {
                        graph.addEdge(k, l);
                    }
                }
            }

        }
        System.out.print(graph.toString());
        return graph;
    }

    public Graph<Integer> completeBipartite(int n, int m) {
        Integer[] values = {n, m};
        return this.complete(values);
    }

    public Graph<Integer> star(int n) {
        return completeBipartite(1, n);
    }

    public Graph<Integer> wheel(int n) {
        Graph<Integer> graph = this.cycle(n - 1);
        graph.addVertex(n - 1);
        for (int i = 0; i < n - 1; i++) {
            graph.addEdge(i, n - 1);
        }

        return graph;
    }

    public Graph<Integer> bull() {
        Graph<Integer> graph = this.path(5);
        graph.addEdge(1, 3);

        return graph;
    }

    //TODO: Test when you can
    public Graph<Integer> prism(int n, int m) {
        if (m == 1) {
            return this.cycle(n);
        }

        Graph<Integer> graph = this.cycle(n);
        for (int i = 0; i < m - 1; i++) {
            graph = this.merge(graph, this.cycle(n));
        }

        for (int i = 0; i < n * (m - 1); i++) {
            graph.addEdge(i, i + n);
        }

        return graph;
    }

    //ref: https://en.wikipedia.org/wiki/Generalized_Petersen_graph
    //TODO: Test!
    public Graph<Integer> petersen(int n, int k) {
        if (k >= (n + 1) / 2) {
            throw new IllegalArgumentException("In Petersen graphs k should be less than n/2");
        }
        Graph<Integer> graph = this.empty(2 * n);
        for (int i = 0; i < n; i++) {
            graph.addEdge(i, (i + 1) % n);
            graph.addEdge(i, (i + n));
            graph.addEdge((i + n), ((i + k) % n + n));

        }
        return graph;
    }

    public Graph<Integer> merge(Graph<Integer> g1, Graph<Integer> g2) {
        ArrayList<Vertex<Integer>> vertices = new ArrayList<Vertex<Integer>>(g1.getVertices());
        vertices.addAll(g2.getVertices());
        ArrayList<Edge<Integer>> edges = new ArrayList<Edge<Integer>>(g1.getEdges());
        edges.addAll(g2.getEdges());

        Graph<Integer> merged = new Graph<Integer>(vertices, edges);

        //Check for duplicates and relabel in necessary.
        ArrayList<Integer> allValues = new ArrayList<Integer>();
        for (Vertex<Integer> vertex : merged.getVertices()) {
            allValues.add(vertex.getValue());
        }

        Set<Integer> uniqueValues = new HashSet<Integer>(allValues);
        if (uniqueValues.size() < allValues.size()) {
            for (int i = 0; i < vertices.size(); i++) {
                merged.getVertices().get(i).setValue(i);
            }
        }

        return merged;
    }

    public Graph<Integer> completeBinaryTree(int levels){
        Graph<Integer> result = this.empty((int)Math.round(Math.pow(2,levels+1)-1));
        int lastStep = (int)Math.round(Math.pow(2,levels)-1);
        for (int i = 0; i < lastStep; i++) {
            result.addEdge(i,2*i+1);
            result.addEdge(i,2*i+2);
        }
        return result;
    }

    public Graph<Integer> complement(Graph<Integer> graph) {
        Graph<Integer> complement = this.empty(graph.getVertices().size());

        for (int i = 0; i < graph.getVertices().size(); i++) {
            Vertex<Integer> v1 = graph.getVertices().get(i);
            for (int j = i + 1; j < graph.getVertices().size(); j++) {
                Vertex<Integer> v2 = graph.getVertices().get(j);
                if (!v1.getNeighbours().contains(v2)) {
                    complement.addEdge(i, j);
                }
            }
        }

        return complement;
    }

    //ref: Erdős–Rényi model
    public Graph<Integer> random(int n, double p) {
        if (p < 0 || p > 1) {
            throw new IllegalArgumentException("p denotes probability and should between 0 and 1");
        }
        if (n < 1) {
            throw new IllegalArgumentException("n denotes number of vertices and should be bigger than 0");
        }

        Graph<Integer> graph = this.empty(n);

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (p > Math.random()) {
                    graph.addEdge(i, j);
                }
            }
        }
        return graph;
    }
}
