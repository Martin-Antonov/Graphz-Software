package model;

import java.util.ArrayList;
import java.util.Collection;

public class Edge<T> {
    private ArrayList<Vertex<T>> vertices;
    private EdgeType type;
    private double weight;

    public Edge(Vertex<T> v1, Vertex<T> v2) {
        this(v1, v2, EdgeType.UNDIRECTED);
    }

    public Edge(Vertex<T> v1, Vertex<T> v2, EdgeType type) {
        this.generateEdge(v1, v2, type, 0);
    }

    public Edge(Vertex<T> v1, Vertex<T> v2, EdgeType type, double weight) {
        this.generateEdge(v1, v2, type, weight);
    }

    public Edge(Collection<Vertex<T>> vertices) {
        this(vertices, EdgeType.UNDIRECTED);
    }

    public Edge(Collection<Vertex<T>> vertices, EdgeType type) {
        this(vertices, type, 0);
    }

    public Edge(Collection<Vertex<T>> vertices, EdgeType type, double weight) {
        ArrayList<Vertex<T>> verticesAsList = new ArrayList<Vertex<T>>(vertices);
        if (vertices.size() == 0) {
            throw new IllegalArgumentException("Number of vertices passed to edge is 0!");
        } else if (vertices.size() == 2) {
            this.generateEdge(verticesAsList.get(0), verticesAsList.get(1), type, weight);
        } else {
            this.type = EdgeType.HYPER;
            this.weight = weight;
            this.vertices = new ArrayList<Vertex<T>>(vertices);

            if (this.vertices.size() > 1) {
                for (int i = 0; i < this.vertices.size(); i++) {
                    Vertex<T> v1 = this.vertices.get(i);
                    for (int j = i + 1; j < this.vertices.size(); j++) {
                        Vertex<T> v2 = this.vertices.get(j);
                        v1.getNeighbours().add(v2);
                        v2.getNeighbours().add(v1);
                        v1.setDeg(v1.getDeg() + 1);
                        v1.setInDeg(v1.getInDeg() + 1);
                        v1.setOutDeg(v1.getOutDeg() + 1);
                        v2.setDeg(v2.getDeg() + 1);
                        v2.setInDeg(v2.getInDeg() + 1);
                        v2.setOutDeg(v2.getOutDeg() + 1);
                    }
                }
            }

        }
    }

    private void generateEdge(Vertex<T> v1, Vertex<T> v2, EdgeType type, double weight) {
        if (v1 == v2) {
            this.type = EdgeType.LOOP;
        } else {
            this.type = type;
        }

        if (this.type == EdgeType.UNDIRECTED || this.type == EdgeType.HYPER) {
            v1.getNeighbours().add(v2);
            v2.getNeighbours().add(v1);
            v1.setDeg(v1.getDeg() + 1);
            v1.setInDeg(v1.getInDeg() + 1);
            v1.setOutDeg(v1.getOutDeg() + 1);
            v2.setDeg(v2.getDeg() + 1);
            v2.setInDeg(v2.getInDeg() + 1);
            v2.setOutDeg(v2.getOutDeg() + 1);

        } else if (this.type == EdgeType.DIRECTED) {
            v1.getNeighbours().add(v2);
            v1.setOutDeg(v1.getOutDeg() + 1);
            v2.setInDeg(v2.getInDeg() + 1);
        } else if (this.type == EdgeType.LOOP) {
            v1.getNeighbours().add(v2);
            v1.setDeg(v1.getDeg() + 1);
            v1.setInDeg(v1.getInDeg() + 1);
            v1.setOutDeg(v1.getOutDeg() + 1);
        }

        this.weight = weight;
        this.vertices = new ArrayList<>();
        this.vertices.add(v1);
        this.vertices.add(v2);
    }

    public ArrayList<Vertex<T>> getVertices() {
        return vertices;
    }

    public EdgeType getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
