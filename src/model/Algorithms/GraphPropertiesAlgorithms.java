package model.Algorithms;

import model.Graph;
import model.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//Girth, diameter, radius, vertices, edges
// later: automorphisms, chromatic number
public class GraphPropertiesAlgorithms<T> {
    private Graph<T> graph;
    private Map<String,Object> properties;
    private int girth;

    public GraphPropertiesAlgorithms(Graph<T> g){
        this.graph=g;
        this.properties = new HashMap<String,Object>();
    }

    public void calculateProperties(){
        this.properties.put("vertices",this.getNumberOfVertices());
        this.properties.put("edges",this.getNumberOfEdges());
        this.properties.put("degree sequence", this.getDegreeSequence());
        this.properties.put("components",this.getConnectedComponents());
        this.calculateEccentricity();
        this.properties.put("radius",this.getRadius());
        this.properties.put("diameter",this.getDiameter());
        this.properties.put("girth",this.girth);
        this.graph.setProperties(this.properties);
    }

    public int getNumberOfVertices(){
        return this.graph.getVertices().size();
    }

    public int getNumberOfEdges(){
        return this.graph.getEdges().size();
    }

    public int[] getDegreeSequence(){
        int[] degreeSequence = new int[this.graph.getVertices().size()];
        for (int i = 0; i < degreeSequence.length; i++) {
            degreeSequence[i]=this.graph.getVertices().get(i).getNeighbours().size();
        }
        return degreeSequence;
    }

    public void calculateEccentricity(){
        this.girth = Integer.MAX_VALUE;

        for (int i = 0; i < this.graph.getVertices().size(); i++) {
            this.calculateEccentricityForAVertex(this.graph.getVertices().get(i));
        }
    }

    //based on my blue book on combinatorics
    public void calculateEccentricityForAVertex(Vertex<T> v){
        if(this.getConnectedComponents()!=1){
            return;
        }

        ArrayList<Integer> distances = new ArrayList<Integer>();
        int length = this.graph.getVertices().size();
        for (int i = 0; i < length; i++) {
            distances.add(-1);
        }
        distances.set(this.graph.getVertices().indexOf(v),0);

        int currentDistance = 0;

        while(distances.contains(-1)){
            for (int i = 0; i < distances.size(); i++) {
                 if(distances.get(i)==currentDistance){
                    Vertex<T> v1 = this.graph.getVertices().get(i);
                     for (int j = 0; j < v1.getNeighbours().size(); j++) {
                         Vertex<T> neighbour = v1.getNeighbours().get(j);
                         int indexOfNeighbour = this.graph.getVertices().indexOf(neighbour);
                         if(distances.get(indexOfNeighbour)==-1){
                             distances.set(indexOfNeighbour,currentDistance+1);
                         }
                         else{
                             int distancesFromVandNeighbour = distances.get(indexOfNeighbour)+distances.get(i)+1;
                             if(this.girth>distancesFromVandNeighbour && (distances.get(i)-distances.get(indexOfNeighbour)!=1)){
                                 this.girth = distancesFromVandNeighbour;
                             }
                         }
                     }
                 }
            }
            currentDistance++;
        }
        v.setEccentricity(Collections.max(distances));
    }

    public int getRadius(){
        return Collections.min(getEccentritiesArray());
    }

    public int getDiameter(){
        return Collections.max(this.getEccentritiesArray());
    }

    //this also calculates the girth.
    public ArrayList<Integer> getEccentritiesArray(){
        ArrayList<Integer> eccentricities = new ArrayList<Integer>();
        for (int i = 0; i < this.graph.getVertices().size(); i++) {
            eccentricities.add(this.graph.getVertices().get(i).getEccentricity());
        }
        return eccentricities;
    }
    //http://webcourse.cs.technion.ac.il/234247/Winter2003-2004/ho/WCFiles/Girth.pdf

    public int getConnectedComponents(){
        ArrayList<ArrayList<Vertex<T>>> dfsResult = this.graph.algorithms.DFS();
        return dfsResult.size();
    }

}
