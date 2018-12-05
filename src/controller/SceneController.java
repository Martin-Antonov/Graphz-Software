package controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.EdgeType;
import model.GraphGenerator;
import view.EdgeView;
import view.GraphView;
import view.VertexView;
import java.util.ArrayList;

public class SceneController {
    private LeftMenuController leftMenuController;
    private CanvasController canvasController;
    private RightMenuController rightMenuController;
    private ConsoleController consoleController;

    public static Scene scene;

    private ArrayList<GraphView> graphs;
    private GraphGenerator graphGenerator;
    private VertexView vFrom;
    private VertexView vTo;
    private ArrayList<VertexView> selectedVertices;
    private Line edgeLine;

    public SceneController(Scene sc){
        this.scene = sc;
        this.graphs = new ArrayList<GraphView>();
        this.selectedVertices = new ArrayList<VertexView>();
        this.graphGenerator = new GraphGenerator();

        this.canvasController = new CanvasController(sc);
        this.leftMenuController = new LeftMenuController(sc);
        this.rightMenuController = new RightMenuController(sc);
        this.consoleController = new ConsoleController(sc,this.graphs,this.graphGenerator,this.rightMenuController);

        //TODO: Fix delete
        CanvasController.pane.setOnMouseClicked(e->{
            switch(LeftMenuController.currentButtonID){
//                case "select":this.mouseClickSelect(e);break;
                case "vertex":this.mouseClickVertex(e);break;
            }
        });

        CanvasController.pane.setOnMousePressed(e->{
            switch(LeftMenuController.currentButtonID){
                case "edge":this.mouseClickEdgeIn(e);break;
            }
        });

        CanvasController.pane.setOnMouseReleased(e->{
            switch(LeftMenuController.currentButtonID){
                case "edge":this.mouseClickEdgeOut(e);break;
            }
        });
        CanvasController.pane.setOnMouseDragged(e->{

            switch(LeftMenuController.currentButtonID){
                //TODO: Select graph
                case "select":this.graphs.get(0).getEdges().forEach(EdgeView::updateLine);break;
                case "edge":this.mouseMovedEdge(e);break;
            }
        });

        //TODO: Fix delete
        this.scene.addEventHandler(KeyEvent.KEY_PRESSED,(e)->{
            switch(LeftMenuController.currentButtonID){
                case "select": this.keyobardPressedSelect(e);break;
            }
        });
    }

    private void mouseClickVertex(MouseEvent e){
        if(this.graphs.size()==0){
            this.graphs.add(new GraphView(this.graphGenerator.empty(0)));
        }

        //TODO: Select clicked graph
        GraphView currentGraphView = this.graphs.get(0);
        currentGraphView.getGraph().addVertex(currentGraphView.getVertices().size());
        currentGraphView.addVertex(e.getX(),e.getY(),currentGraphView.getGraph().getVertex(currentGraphView.getVertices().size()));
        this.rightMenuController.showGraphProperties(this.graphs.get(0));

    }
    private void mouseClickEdgeIn(MouseEvent e){
        //TODO: Select clicked graph
        GraphView currentGraphView = this.graphs.get(0);

       this.vFrom = currentGraphView.returnContainedVertex(e.getX(),e.getY());

        if(this.vFrom!=null){
            this.edgeLine = new Line(this.vFrom.getCircle().getCenterX(),this.vFrom.getCircle().getCenterY(),this.vFrom.getCircle().getCenterX(),this.vFrom.getCircle().getCenterY());
            this.edgeLine.setStrokeWidth(2);
            this.edgeLine.setStroke(Color.web("#32477A"));
            this.edgeLine.getStrokeDashArray().addAll(10d);
            CanvasController.pane.getChildren().add(this.edgeLine);
            this.edgeLine.toBack();

        }
    }

    private void mouseClickEdgeOut(MouseEvent e){
        //TODO: Select clicked graph
        GraphView currentGraphView = this.graphs.get(0);

        this.vTo = currentGraphView.returnContainedVertex(e.getX(),e.getY());

        if(this.vFrom!=null && this.vTo!=null && this.vFrom!=this.vTo){
            currentGraphView.getGraph().addEdge(this.vFrom.getVertex(),this.vTo.getVertex(), EdgeType.UNDIRECTED);
            currentGraphView.addEdge(this.vFrom,this.vTo);
        }
        this.vFrom = null;
        this.vTo = null;
        CanvasController.pane.getChildren().remove(edgeLine);
        this.edgeLine = null;

        this.rightMenuController.showGraphProperties(this.graphs.get(0));
    }
    private void mouseMovedEdge(MouseEvent e){
        if(this.edgeLine!=null) {
            this.edgeLine.setEndX(e.getX());
            this.edgeLine.setEndY(e.getY());
        }
    }

    private void mouseClickSelect(MouseEvent e){
        GraphView currentGraphView = this.graphs.get(0);
        VertexView vToSelect = currentGraphView.returnContainedVertex(e.getX(),e.getY());

        if(vToSelect!=null){
            if(this.selectedVertices.contains(vToSelect)){
                vToSelect.getCircle().setFill(Color.web("#303030"));
                this.selectedVertices.remove(vToSelect);
            }else{
                vToSelect.getCircle().setFill(Color.web("#32477A"));
                this.selectedVertices.add(vToSelect);
            }
        }
    }

    private void keyobardPressedSelect(KeyEvent e){
        if(e.getCode()== KeyCode.DELETE){
            this.graphs.get(0).removeVertices(this.selectedVertices);
            this.selectedVertices.clear();
        }
    }
}
