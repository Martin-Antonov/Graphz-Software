package model.Algorithms;

import model.Graph;
import model.Vertex;
import org.apache.commons.math3.linear.*;

import java.util.HashMap;
import java.util.Map;


public class SpectralAlgorithms<T> {
    private Graph<T> graph;
    private double[][] adjacencyMatrix;
    private double[][] degreeMatrix;
    private double[][] laplacianMatrix;
    private double[] aEigenvalues;
    private double[] lEigenvalues;
    private Map<String,Object> spectralProperties;

    public SpectralAlgorithms(Graph<T> graph){
        this.graph = graph;
        this.spectralProperties = new HashMap<String,Object>();
    }

    public void calculateMatrices(){
        this.spectralProperties.put("adj(G)",this.calculateAdjacencyMatrix());
        this.spectralProperties.put("deg(G)",this.calculateDegreeMatrix());
        this.spectralProperties.put("lap(G)",this.calculateLaplacianMatrix());

        RealMatrix A = MatrixUtils.createRealMatrix(this.adjacencyMatrix);
        RealMatrix L = MatrixUtils.createRealMatrix(this.laplacianMatrix);

        EigenDecomposition eigenA = new EigenDecomposition(A);
        EigenDecomposition eigenL = new EigenDecomposition(L);

        this.aEigenvalues = eigenA.getRealEigenvalues();
        this.lEigenvalues = eigenL.getRealEigenvalues();

        this.spectralProperties.put("eigen adj(G)",this.aEigenvalues);
        this.spectralProperties.put("eigen lap(G)",this.lEigenvalues);

        this.graph.setSpectralProperties(this.spectralProperties);

    }

    private double[][] calculateAdjacencyMatrix(){
        int n = this.graph.getVertices().size();

        this.adjacencyMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            Vertex<T> v1 = this.graph.getVertices().get(i);
            for (int j = i+1; j < n; j++) {
                Vertex<T> v2 = this.graph.getVertices().get(j);
                if(v1.getNeighbours().contains(v2)){
                    getAdjacencyMatrix()[i][j]=1;
                }
                if(v2.getNeighbours().contains(v1)){
                    getAdjacencyMatrix()[j][i]=1;
                }
            }
        }
        return this.adjacencyMatrix;
    }

    private double[][] calculateDegreeMatrix(){
        int n = this.graph.getVertices().size();

        this.degreeMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            this.getDegreeMatrix()[i][i] = this.graph.getVertices().get(i).getDeg();
        }

        return this.degreeMatrix;
    }

    private double[][] calculateLaplacianMatrix() {
        int n = this.graph.getVertices().size();

        this.laplacianMatrix = new double[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.getLaplacianMatrix()[i][j] =this.getDegreeMatrix()[i][j]-this.getAdjacencyMatrix()[i][j];
            }
        }

        return this.laplacianMatrix;
    }

    public double[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public double[][] getDegreeMatrix() {
        return degreeMatrix;
    }

    public double[][] getLaplacianMatrix() {
        return laplacianMatrix;
    }
}
