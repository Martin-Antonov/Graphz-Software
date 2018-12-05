package model.Algorithms;

import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SearchAlgorithms<T> {
    private static boolean[] visited;
    private ArrayList<Vertex<T>> dfsStep;
    private ArrayList<ArrayList<Vertex<T>>> bfsResult;

    private void DFSStep(Graph<T> g, Vertex<T> currentVertex){
        visited[g.getVertices().indexOf(currentVertex)]=true;
        ArrayList<Vertex<T>> neighbours = currentVertex.getNeighbours();
        for (int i = 0; i < neighbours.size(); i++) {
            Vertex<T> neighbour = neighbours.get(i);
            if(!visited[g.getVertices().indexOf(neighbour)]) {
                this.DFSStep(g, neighbour);
             }
        }
        dfsStep.add(currentVertex);
    }

    //returns a matrix (in terms of lists) of vertices -> each row is a connected component
    public ArrayList<ArrayList<Vertex<T>>> DFS(Graph<T> g){
        visited = new boolean[g.getVertices().size()];
        ArrayList<ArrayList<Vertex<T>>> dfsResult = new ArrayList<ArrayList<Vertex<T>>>();

        for (int i = 0; i < g.getVertices().size(); i++) {
            if(!visited[i]){
                dfsStep = new ArrayList<Vertex<T>>();
                DFSStep(g,g.getVertices().get(i));
                dfsResult.add(dfsStep);
            }
        }
//        for (ArrayList<Vertex<T>> currentList : dfsResult) {
//            for (Vertex<T> vertex : currentList) {
//                System.out.print(vertex.getValue() + " ");
//            }
//            System.out.println();
//        }

        return dfsResult;
    }

    private ArrayList<Vertex<T>> BFSStep(Graph<T> g, Vertex<T> currentVertex){
        ArrayList<Vertex<T>> result = new ArrayList<Vertex<T>>();
        Queue<Vertex<T>> bfsStep = new LinkedList<Vertex<T>>();
        bfsStep.add(currentVertex);
        visited[g.getVertices().indexOf(currentVertex)]=true;

        while(!bfsStep.isEmpty()){
            Vertex<T> v = bfsStep.remove();
            result.add(v);
            for (int i = 0; i < v.getNeighbours().size(); i++) {
                Vertex<T> neighbour = v.getNeighbours().get(i);
                int index = g.getVertices().indexOf(neighbour);
                if(!visited[index]) {
                    visited[index] = true;
                    bfsStep.add(neighbour);
                }
            }
        }
        return result;
    }

    public ArrayList<ArrayList<Vertex<T>>> BFS(Graph<T> g){
        visited = new boolean[g.getVertices().size()];
        ArrayList<ArrayList<Vertex<T>>> bfsResult = new ArrayList<ArrayList<Vertex<T>>>();

        for (int i = 0; i < g.getVertices().size(); i++) {
            if(!visited[i]){
                Vertex<T> currentVertex = g.getVertices().get(i);
                bfsResult.add(BFSStep(g,currentVertex));
            }
        }
//        for (ArrayList<Vertex<T>> currentList : bfsResult) {
//            for (Vertex<T> vertex : currentList) {
//                System.out.print(vertex.getValue() + " ");
//            }
//            System.out.println();
//        }

        return bfsResult;
    }
}
