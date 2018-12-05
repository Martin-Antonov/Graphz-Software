package model.Algorithms;

import model.Graph;
import model.GraphType;
import model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;

public class AlgorithmsController<T> {
    private Graph<T> graph;
    private GraphPropertiesAlgorithms<T> graphProperties;
    private GraphTypeAlgorithms<T> graphType;
    private SearchAlgorithms<T> search;
    private SpectralAlgorithms<T> spectral;

    public AlgorithmsController(Graph<T> g) {
        this.graph = g;
        this.search = new SearchAlgorithms<T>();
        this.graphProperties = new GraphPropertiesAlgorithms<T>(this.graph);
        this.graphType = new GraphTypeAlgorithms<T>(this.graph);
        this.spectral = new SpectralAlgorithms<T>(this.graph);
    }

    public ArrayList<ArrayList<Vertex<T>>> BFS() {
        return search.BFS(this.graph);
    }

    public ArrayList<ArrayList<Vertex<T>>> DFS() {
        return search.DFS(this.graph);
    }

    public void calculateProperties() {this.graphProperties.calculateProperties();}

    public int[] getDegreeSequence(){return this.graphProperties.getDegreeSequence();}

    public int getConnectedComponents(){return this.graphProperties.getConnectedComponents();}

    public void getTypes(){this.graphType.checkAllTypes();}

    public void calculateSpectrum(){ this.spectral.calculateMatrices();}

    public void calculateTypes(){
        this.graph.setTypes(new HashSet<GraphType>());
        this.graphType.checkAllTypes();
    }


}
