package controller;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import model.Graph;
import model.GraphGenerator;
import view.EdgeView;
import view.GraphView;

public class CanvasController {
    public static Pane pane;
    private Canvas canvas;
    private GraphicsContext context;


    public CanvasController(Scene sc) {
        this.pane = (Pane)sc.lookup("#pane");

    }
}
