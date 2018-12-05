package view;

import controller.CanvasController;
import javafx.scene.paint.Color;
import model.Graph;
import model.Vertex;

import java.util.ArrayList;

//TODO: Set bounds for the graph and add label inside the bounds top left corner. Bounds should be given from outside
//TODO: Update Bounds method on vertex drag...
public class GraphView {
    private int id;
    private ArrayList<VertexView> vertices;
    private ArrayList<EdgeView> edges;
    private Graph<Integer> graph;

    public GraphView(Graph<Integer> graph){
        this.graph= graph;
        this.vertices = new ArrayList<VertexView>();
        this.edges = new ArrayList<EdgeView>();
        int radius = 200;
        int centerx = 720;
        int centery = 450;
        int numberOfVertices = graph.getVertices().size();

        for (int i = 0; i < numberOfVertices; i++) {
             this.vertices.add(new VertexView(centerx+radius*Math.cos(Math.PI/numberOfVertices+(2*Math.PI/numberOfVertices)*i),
                     centery+radius*Math.sin(Math.PI/numberOfVertices+(2*Math.PI/numberOfVertices)*i),20,this.graph.getVertices().get(i)));
        }
//        this.graph.getVertices().forEach((v)->{
//            this.vertices.add(new VertexView(centerx+radius*Math.cos(),Math.random()*800+20,20,v));
//        });

        for (int i = 0; i < this.vertices.size(); i++) {
            VertexView v1 = this.vertices.get(i);
            for (int j = i+1; j < this.vertices.size(); j++) {
                VertexView v2 = this.vertices.get(j);
                if(v1.getVertex().getNeighbours().contains(v2.getVertex())){
                    this.edges.add(new EdgeView(v1,v2));
                }
            }
        }

        this.edges.forEach(e->CanvasController.pane.getChildren().add(e.getLine()));
        this.vertices.forEach(v->{
            CanvasController.pane.getChildren().add(v.getCircle());
        });
    }

    public void addVertex(double x, double y, Vertex<Integer> v){
        VertexView newV = new VertexView(x,y,20,v);
        this.vertices.add(newV);
        CanvasController.pane.getChildren().add(newV.getCircle());
    }

    public void removeVertices(ArrayList<VertexView> vertices){
        vertices.forEach(v->{
            this.graph.removeVertex(v.getVertex());
            CanvasController.pane.getChildren().remove(v.getCircle());
        });

        this.edges.forEach(e->{
            if (vertices.contains(e.getStart()) || vertices.contains(e.getEnd())) {
                this.edges.remove(e);
                CanvasController.pane.getChildren().remove(e.getLine());
            }
        });

        this.vertices.removeAll(vertices);
        vertices.forEach(v->v=null);
    }

    public void addEdge(VertexView vFrom, VertexView vTo){
        EdgeView newE = new EdgeView(vFrom,vTo);
        this.edges.add(newE);
        CanvasController.pane.getChildren().add(newE.getLine());
        newE.getLine().toBack();
    }

    public VertexView returnContainedVertex(double x, double y){
        VertexView v;
        for (int i = 0; i < this.vertices.size(); i++) {
            v = this.vertices.get(i);
            if (v.checkCoordinatesConatins(x,y)) {
                return v;
            }
        }
        return null;
    }

    public ArrayList<VertexView> getVertices() {
        return vertices;
    }

    public ArrayList<EdgeView> getEdges() {
        return edges;
    }

    public Graph<Integer> getGraph(){return this.graph;}
}
