package model;

import java.util.ArrayList;

public class Vertex<T> {
    private T value;
    private ArrayList<Vertex<T>> neighbours;
    private int colour;
    private int deg;
    private int inDeg;
    private int outDeg;
    private int eccentricity;
    private boolean isLeaf;
    private boolean isIsolated;
    private boolean isSink;
    private boolean isSource;
    private boolean isSimplicial;
    private boolean isUniversal;

    public Vertex(T value) {
        this.value = value;
        this.neighbours = new ArrayList<Vertex<T>>();
        this.colour = 0;
        this.deg = 0;
        this.inDeg = 0;
        this.outDeg = 0;
    }

    public boolean isIsolated(){
        this.isIsolated = this.getDeg()==0 && this.getOutDeg()==0;

        return this.isIsolated;
    }

    public boolean isLeaf(){
        this.isLeaf = this.getDeg()==1 || this.getOutDeg()==1;
        return this.isLeaf;
    }

    public boolean isSource(){
        this.isSource = this.getInDeg()==0;
        return this.isSource;
    }

    public boolean isSink(){
        this.isSink = this.getOutDeg()==0;
        return this.isSink;
    }

    public boolean isSimplicial(){
        ArrayList<Vertex<T>> neighbours = this.getNeighbours();

        for (int i = 0; i < neighbours.size(); i++) {
            Vertex<T> neighbour1 = neighbours.get(i);
            for (int j = i+1; j < neighbours.size(); j++) {
                Vertex<T> neighbour2 = neighbours.get(j);
                if(!neighbour1.getNeighbours().contains(neighbour2) || neighbour2.getNeighbours().contains(neighbour1)){
                    this.isSimplicial =false;
                    return this.isSimplicial;
                }
            }
        }

        this.isSimplicial =true;
        return this.isSimplicial;
    }

    public boolean isUniversal(Graph<T> g){
        int numberOfVertices = g.getVertices().size();
        this.isUniversal = this.getNeighbours().size()==numberOfVertices-1;
        return this.isUniversal;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<Vertex<T>> getNeighbours() {
        return this.neighbours;
    }

    protected void setNeighbours(ArrayList<Vertex<T>> neighbours) {
        this.neighbours = neighbours;
    }

    public int getDeg() {
        return this.deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getInDeg() {
        return this.inDeg;
    }

    public void setInDeg(int inDeg) {
        this.inDeg = inDeg;
    }

    public int getOutDeg() {
        return this.outDeg;
    }

    public void setOutDeg(int outDeg) {
        this.outDeg = outDeg;
    }

    public int getColour() {
        return colour;
    }

    public void setColour(int colour) {
        this.colour = colour;
    }

    public int getEccentricity(){return this.eccentricity;}

    public void setEccentricity(int eccentricity){this.eccentricity = eccentricity;}

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("vertex: " + this.value + " deg:" + this.deg + " inDeg:" + this.inDeg + " outDeg:" + this.outDeg + " col:" + this.colour + " isLeaf:" + this.isLeaf + " isIsolated:" + this.isIsolated);
        return result.toString();
    }


}
