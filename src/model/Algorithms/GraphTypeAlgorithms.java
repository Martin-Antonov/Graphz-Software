package model.Algorithms;

import model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GraphTypeAlgorithms<T> {
    Graph<T> graph;

    public GraphTypeAlgorithms(Graph<T> graph){
        this.graph = graph;
    }

    public void checkAllTypes(){
        this.checkDirected();
        this.checkUndirected();
        this.checkHyper();
        this.checkMixed();
        this.checkConnected();
        this.checkEulerian();
        this.checkRegular();
        this.checkTree();
        this.checkSimple();
        this.checkCycle();
        this.checkComplete();
    }

    public boolean checkDirected(){
        for (int i = 0; i < this.graph.getEdges().size(); i++) {
             if(this.graph.getEdges().get(i).getType()!= EdgeType.DIRECTED){
                return false;
             }
        }

        this.graph.addType(GraphType.DIRECTED);

        return true;
    }

    public boolean checkUndirected(){
        for (int i = 0; i < this.graph.getEdges().size(); i++) {
            if(this.graph.getEdges().get(i).getType()!= EdgeType.UNDIRECTED){
                return false;
            }
        }

        this.graph.addType(GraphType.UNDIRECTED);

        return true;
    }

    public boolean checkHyper(){
        for (int i = 0; i < this.graph.getEdges().size(); i++) {
            if(this.graph.getEdges().get(i).getType()!= EdgeType.HYPER){
                return false;
            }
        }

        this.graph.addType(GraphType.HYPER);

        return true;
    }

    public boolean checkMixed(){
        int undirectedCount = 0;
        int directedCount = 0;
        for (int i = 0; i < this.graph.getEdges().size(); i++) {
            if(this.graph.getEdges().get(i).getType()== EdgeType.DIRECTED){
                directedCount++;
            } else if(this.graph.getEdges().get(i).getType()== EdgeType.UNDIRECTED){
                undirectedCount++;
            }
        }

        if(undirectedCount!=0 && directedCount!=0){
            this.graph.addType(GraphType.MIXED);
            return true;
        }

        return false;
    }

    public boolean checkConnected() {
        if(this.graph.algorithms.getConnectedComponents() == 1){
            this.graph.addType(GraphType.CONNECTED);
            return true;
        }

        return false;
    }

    public boolean checkEulerian(){
        if(this.graph.algorithms.getConnectedComponents()!=1){
            return false;
        }
        int[] degreeSequence = this.graph.algorithms.getDegreeSequence();

        for (int i = 0; i < degreeSequence.length; i++) {
            if(degreeSequence[i]%2!=0){
                return false;
            }
        }
        this.graph.addType(GraphType.EULERIAN);
        return true;
    }

    public boolean checkRegular(){
        int[] degreeSequence = this.graph.algorithms.getDegreeSequence();
        boolean areAllTheSame = Arrays.stream(degreeSequence).allMatch(x-> x==degreeSequence[0]);
        if(areAllTheSame){
            this.graph.addType(GraphType.REGULAR);
            return true;
        }
        return false;
    }

    public boolean checkTree(){
        if(this.checkConnected() && (this.graph.getEdges().size()+1==this.graph.getVertices().size())){
            this.graph.addType(GraphType.TREE);
            return true;
        }
        return false;
    }

    public boolean checkSimple(){
        HashSet<Edge<T>> edgesAsSet = new HashSet<Edge<T>>(this.graph.getEdges());

        if(this.graph.getEdges().size()==edgesAsSet.size()){
            this.graph.addType(GraphType.SIMPLE);
            return true;
        }

        return false;
    }

    public boolean checkCycle(){
        if(this.checkRegular() && this.checkConnected() && this.graph.getVertices().get(0).getDeg()==2){
            this.graph.addType(GraphType.CYCLE);
            return true;
        }

        return false;
    }

    public boolean checkComplete(){
        if(this.checkRegular() && this.checkConnected() && this.graph.getVertices().get(0).getDeg()==this.graph.getVertices().size()-1){
            this.graph.addType(GraphType.COMPLETE);
            return true;
        }
        return false;
    }

    //TODO: Bipartite with colours
    //TODO: Hamiltonian somehow
}
