package view;

import controller.CanvasController;
import controller.LeftMenuController;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Vertex;

public class VertexView {
    private Circle circle;
    private Vertex<Integer> vertex;
    private Tooltip vertexInfo;

    public VertexView(double x, double y, double radius, Vertex<Integer> vertex) {
        this.circle = new Circle(x, y, radius);
        this.circle.setFill(Color.web("#303030"));
        this.circle.setOnMouseDragged((e) -> {
            if (LeftMenuController.currentButtonID.equals("select")) {
                double xM = e.getX();
                double yM = e.getY();
                if (xM > radius && xM < CanvasController.pane.getWidth() - radius && yM > radius && yM < CanvasController.pane.getHeight() - radius) {
                    this.circle.setCenterX(xM);
                    this.circle.setCenterY(yM);
                }
            }
        });


        this.vertex = vertex;
        this.vertexInfo = new Tooltip();
        this.vertexInfo.setX(this.circle.getCenterX()+this.circle.getRadius()*3);
        this.vertexInfo.setY(this.circle.getCenterY()+this.circle.getRadius()*3);
        Tooltip.install(this.circle, this.vertexInfo);
        this.vertexInfo.setOnShown(e -> this.updateToolTip());
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public Vertex<Integer> getVertex() {
        return vertex;
    }

    public void setVertex(Vertex<Integer> vertex) {
        this.vertex = vertex;
    }

    public boolean checkCoordinatesConatins(double x, double y) {
        boolean containsX = Math.abs(this.circle.getCenterX() - x) < this.circle.getRadius();
        boolean containsY = Math.abs(this.circle.getCenterY() - y) < this.circle.getRadius();
        return containsX && containsY;
    }

    private void updateToolTip(){
        StringBuilder information = new StringBuilder();
        information.append("Degree: "+this.vertex.getDeg());
        information.append("\nIn Degree: "+this.vertex.getInDeg());
        information.append("\nOut Degree: "+this.vertex.getOutDeg());
        information.append("\nEccentricity: "+this.vertex.getEccentricity());
        information.append("\nIs Leaf: "+this.vertex.isLeaf());
        information.append("\nIs Isolated: "+this.vertex.isIsolated());
        information.append("\nIs Sink: "+this.vertex.isSink());
        information.append("\nIs Source: "+this.vertex.isSource());
        information.append("\nIs Simplicial: "+this.vertex.isSimplicial());
//        information.append("\nIs Universal: "+this.vertex.isUniversal());

        this.vertexInfo.setText(information.toString());
    }
}
