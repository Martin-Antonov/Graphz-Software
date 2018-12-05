package model;

import com.sun.javaws.exceptions.InvalidArgumentException;
import model.Algorithms.AlgorithmsController;
import model.Algorithms.GraphPropertiesAlgorithms;

import java.util.*;

public class Graph<T> {
    private ArrayList<Vertex<T>> vertices;
    private ArrayList<Edge<T>> edges;
    public AlgorithmsController<T> algorithms;
    private Map<String,Object> properties;
    private Map<String,Object> spectralProperties;
    private HashSet<GraphType> types;

    public Graph() {
        this(new ArrayList<Vertex<T>>(),new ArrayList<Edge<T>>());
    }

    public Graph(Collection<Vertex<T>> vertices, Collection<Edge<T>> edges) {
        this.vertices = new ArrayList<Vertex<T>>(vertices);
        this.edges = new ArrayList<Edge<T>>(edges);
        this.algorithms = new AlgorithmsController<T>(this);
        this.properties = new HashMap<String,Object>();
        this.spectralProperties = new HashMap<String,Object>();
        this.types = new HashSet<GraphType>();

    }

    public void addVertex(T value) {
        this.addVertex(new Vertex<T>(value));
    }

    public void addVertex(Vertex<T> vertex) {
        for (Vertex<T> v : this.vertices) {
            if (v.getValue().equals(vertex.getValue())) {
                throw new IllegalArgumentException("Cannot add vertices with the same value");
            }
        }
        this.vertices.add(vertex);
    }

    public void addVertices(Collection<T> values) {
        for (T value : values) {
            this.addVertex(value);
        }
    }

    public void addVertices(Iterable<Vertex<T>> vertices) {
        for (Vertex<T> vertex : vertices) {
            this.addVertex(vertex);
        }
    }

    public void addEdge(Edge<T> edge) {
        this.edges.add(edge);
    }

    public void addEdge(T value1, T value2) {
        this.addEdge(value1, value2, EdgeType.UNDIRECTED);
    }

    public void addEdge(Vertex<T> v1, Vertex<T> v2) {
        this.addEdge(v1, v2, EdgeType.UNDIRECTED);
    }

    public void addEdge(T value1, T value2, EdgeType type) {
        this.addEdge(value1, value2, type, 0);
    }

    public void addEdge(Vertex<T> v1, Vertex<T> v2, EdgeType type) {
        this.addEdge(v1, v2, type, 0);
    }

    public void addEdge(T value1, T value2, EdgeType type, double weight) {
        Vertex<T> v1 = this.getVertex(value1);
        Vertex<T> v2 = this.getVertex(value2);
        this.addEdge(v1, v2, type, weight);
    }

    public void addEdge(Vertex<T> v1, Vertex<T> v2, EdgeType type, double weight) {
        this.edges.add(new Edge<T>(v1, v2, type, weight));
    }

    public void addHyperEdge(Iterable<T> values){
        this.addHyperEdge(values,0);
    }

    public void addHyperEdge(Iterable<T> values, int weight){
        ArrayList<Vertex<T>> vertices = new ArrayList<Vertex<T>>();

        for (T value : values) {
            vertices.add(this.getVertex(value));
        }

        this.addHyperEdge(vertices,weight);
    }

    public void addHyperEdge(Collection<Vertex<T>> vertices) {
        this.addHyperEdge(vertices, 0);
    }

    public void addHyperEdge(Collection<Vertex<T>> vertices, double weight) {
        this.edges.add(new Edge<T>(vertices, EdgeType.HYPER, weight));
    }

    public Vertex<T> removeVertex(T value) {
        Vertex<T> vertex = this.getVertex(value);
        this.removeVertex(vertex);
        return vertex;
    }

    public Vertex<T> removeVertex(Vertex<T> vertex) {
        if (this.containsVertex(vertex)) {
            this.vertices.remove(vertex);
            for (Vertex<T> v : this.vertices) {
                v.getNeighbours().remove(vertex);
            }

            for (Edge<T> edge : this.edges) {
                if (edge.getType() == EdgeType.HYPER) {
                    edge.getVertices().remove(vertex);
                } else {
                    this.removeEdge(edge);
                }
            }
            return vertex;
        } else {
            return null;
        }
    }

    public Edge<T> removeEdge(Edge<T> edge) {
        if (this.edges.contains(edge)) {
            this.edges.remove(edge);
            return edge;
        } else {
            return null;
        }
    }

    public Graph<T> clone() {
        Graph<T> cloned = new Graph<T>();
        for (Vertex<T> vertex : this.vertices) {
            cloned.addVertex(new Vertex<T>(vertex.getValue()));
        }

        for (Edge<T> edge : this.edges) {
            ArrayList<Vertex<T>> clonedEdgeVertices = new ArrayList<Vertex<T>>();
            for (Vertex<T> vertex : edge.getVertices()) {
                clonedEdgeVertices.add(cloned.getVertex(vertex.getValue()));
            }
            //create the edge and add it to the new graph.
            Edge<T> clonedEdge = new Edge<T>(clonedEdgeVertices, edge.getType(), edge.getWeight());
            cloned.getEdges().add(clonedEdge);
            //create all connections between the vertices from the edges (to avoid recursion)
        }
        return cloned;
    }

    public Vertex<T> getVertex(Vertex<T> v) {
        return this.getVertex(v.getValue());
    }

    public Vertex<T> getVertex(T value) {
        if (this.containsVertex(value)) {
            return this.vertices.stream().filter(v -> v.getValue().equals(value)).findAny().get();
        } else {
        }
        return null;
    }

    public boolean containsVertex(Vertex<T> v) {
        return this.containsVertex(v.getValue());
    }

    public boolean containsVertex(T value) {
        return this.vertices.stream().filter(v -> v.getValue().equals(value)).count() == 1;
    }

    public HashSet<GraphType> getTypes(){return this.types;}

    public void setTypes(HashSet<GraphType> types){this.types=types;}

    public void addType(GraphType type){this.types.add(type);}

    public void removeType(GraphType type){this.types.remove(type);}

    //TODO: Implement this because it will be important at some point!
    public Graph<Integer> convertToIntegerGraph(){
        Graph<Integer> graph = new Graph<Integer>();
        return graph;
    }

    public Map<String,Object> getProperties(){
        return this.properties;
    }

    public void setProperties(Map<String,Object> properties){this.properties=properties;}

    public Map<String,Object> getSpectralProperties(){return this.spectralProperties;}

    public void setSpectralProperties(Map<String,Object> spectralProperties){this.spectralProperties = spectralProperties;}

    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
    }

    public ArrayList<Edge<T>> getEdges() {
        return edges;
    }

    public String toString() {
        int tab = 4;
        StringBuilder result = new StringBuilder();
        result.append("Vertices:\n");
        for (Vertex<T> vertex : this.vertices) {
            result.append(new String(new char[tab]).replace('\0', ' ') + vertex.getValue() + "-> ");
            for (Vertex<T> neighbour : vertex.getNeighbours()) {
                result.append(neighbour.getValue()+" ");
            }
            result.append("\n");
        }
        result.append("Edges:\n");
        for (int i = 0; i < this.edges.size(); i++) {
            Edge<T> edge = this.edges.get(i);
            result.append(new String(new char[tab]).replace('\0', ' ') + "e" + i + "={");
            for (Vertex<T> vertex : edge.getVertices()) {
                result.append(vertex.getValue() + ", ");
            }
            result.replace(result.length() - 2, result.length(), "");
            result.append("}   type: " + edge.getType() + "\n");
        }
        return result.toString();
    }
}