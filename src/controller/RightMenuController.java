package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Graph;
import model.GraphProperty;
import model.GraphType;
import view.GraphView;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashSet;

public class RightMenuController {


    private TableView propTable;
    private TableColumn<GraphProperty,String> propColumn;
    private TableColumn<GraphProperty,Object> valueColumn;

    private TableView specTable;
    private TableColumn<GraphProperty,String> specPropColumn;
    private TableColumn<GraphProperty,Object> specValueColumn;
    RightMenuController(Scene sc){
        this.propTable = (TableView)sc.lookup("#propTable");
        this.propTable.getColumns().clear();
        this.propColumn = new TableColumn<>("Property");
        this.propColumn.setCellValueFactory(new PropertyValueFactory<GraphProperty, String>("property"));
        this.valueColumn = new TableColumn<>("Value");
        this.valueColumn.setCellValueFactory(new PropertyValueFactory<GraphProperty, Object>("value"));
        this.propTable.getColumns().addAll(this.propColumn,this.valueColumn);

        this.specTable = (TableView)sc.lookup("#specTable");
        this.specTable.getColumns().clear();
        this.specPropColumn = new TableColumn<>("Property");
        this.specPropColumn.setCellValueFactory(new PropertyValueFactory<GraphProperty, String>("property"));
        this.specValueColumn = new TableColumn<>("Value");
        this.specValueColumn.setCellValueFactory(new PropertyValueFactory<GraphProperty, Object>("value"));
        this.specTable.getColumns().addAll(this.specPropColumn,this.specValueColumn);


    }

    public void showGraphProperties(GraphView graph){

        this.propTable.setItems(this.getGraphProperties(graph));
        this.specTable.setItems(this.getSpectralGraphProperties(graph));
    }

    private ObservableList<GraphProperty> getGraphProperties(GraphView graph){
        ObservableList<GraphProperty> properties = FXCollections.observableArrayList();
        Graph<Integer> currentGraph = graph.getGraph();
        currentGraph.algorithms.calculateTypes();
        currentGraph.algorithms.calculateProperties();
        properties.add(new GraphProperty("Vertices",currentGraph.getProperties().get("vertices")));
        properties.add(new GraphProperty("Edges",currentGraph.getProperties().get("edges")));
        int[] degreeSequence = (int[])currentGraph.getProperties().get("degree sequence");
        properties.add(new GraphProperty("Deg Sequence", Arrays.toString(degreeSequence)));
        properties.add(new GraphProperty("Conn. Components",currentGraph.getProperties().get("components")));
        properties.add(new GraphProperty("Radius",currentGraph.getProperties().get("radius")));
        properties.add(new GraphProperty("Diameter",currentGraph.getProperties().get("diameter")));
        properties.add(new GraphProperty("Girth",currentGraph.getProperties().get("girth")));
        properties.add(new GraphProperty("Types",this.convertGraphTypes(currentGraph.getTypes())));
        return properties;
    }

    private ObservableList<GraphProperty> getSpectralGraphProperties(GraphView graph){

        ObservableList<GraphProperty> properties = FXCollections.observableArrayList();
        Graph<Integer> currentGraph = graph.getGraph();
        currentGraph.algorithms.calculateSpectrum();
        properties.add(new GraphProperty("Adjacency(G)",this.convertMatrixToString(currentGraph.getSpectralProperties().get("adj(G)"))));
        properties.add(new GraphProperty("Degree(G)",this.convertMatrixToString(currentGraph.getSpectralProperties().get("deg(G)"))));
        properties.add(new GraphProperty("Laplacian(G)",this.convertMatrixToString(currentGraph.getSpectralProperties().get("lap(G)"))));
        properties.add(new GraphProperty("Eigenvalues A(G))",this.convertEigenValuesToString(currentGraph.getSpectralProperties().get("eigen adj(G)"))));
        properties.add(new GraphProperty("Eigenvalues L(G)",this.convertEigenValuesToString(currentGraph.getSpectralProperties().get("eigen lap(G)"))));

        return properties;
    }

    private String convertMatrixToString(Object matrixObject){
        StringBuilder result = new StringBuilder();
        double[][] matrix = (double[][])matrixObject;
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.CEILING);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result.append(df.format(matrix[i][j])+" ");
            }
            result.append("\n");
        }

        return result.toString();
    }

    private String convertEigenValuesToString(Object arrayObject){
        StringBuilder result = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        double[] array = (double[])arrayObject;

        for (int i = 0; i < array.length; i++) {
            result.append(df.format(array[i])+" ");
        }

        return result.toString();
    }

    private String convertGraphTypes(Object typesAsObject){
        StringBuilder result = new StringBuilder();
        HashSet<GraphType> types = (HashSet<GraphType>)typesAsObject;
        int index = 0;

        for (GraphType t : types) {
            result.append(t.toString().toLowerCase()+" ");
            index++;
            if(index%2==0){
                result.append("\n");
            }
        }
        return result.toString();
    }
    public TableView getPropTable() {
        return propTable;
    }

    public TableView getSpecTable() {
        return specTable;
    }
}
