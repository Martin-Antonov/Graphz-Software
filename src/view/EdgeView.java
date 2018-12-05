package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class EdgeView {

    private Line line;
    private VertexView start;
    private VertexView end;

    public EdgeView(VertexView start, VertexView end){
        this.start=start;
        this.end = end;
        this.line = new Line(this.start.getCircle().getCenterX(),this.start.getCircle().getCenterY(),this.end.getCircle().getCenterX(),this.end.getCircle().getCenterY());
        this.line.setStrokeWidth(3);
        this.line.setStroke(Color.web("#303030"));
        this.line.toBack();
    }

    public void updateLine(){
        this.line.setStartX(this.start.getCircle().getCenterX());
        this.line.setStartY(this.start.getCircle().getCenterY());
        this.line.setEndX(this.end.getCircle().getCenterX());
        this.line.setEndY(this.end.getCircle().getCenterY());
    }

    public Line getLine() {
        return line;
    }
    public VertexView getStart(){return this.start;}
    public VertexView getEnd(){return this.end;}
}
