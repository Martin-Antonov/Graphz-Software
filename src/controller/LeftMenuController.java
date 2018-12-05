package controller;

import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;

import java.util.ArrayList;


public class LeftMenuController {
    public static String currentButtonID;
    private ToggleButton selectButton;
    private ToggleButton vertexButton;
    private ToggleButton edgeButton;
    private ToggleButton dirEdgeButton;
    private ToggleButton hyperEdgeButton;
    private ToggleButton joinButton;
    private ToggleButton splitButton;
    private ToggleButton weightButton;
    private ArrayList<ToggleButton> allButtons;


    public LeftMenuController(Scene sc) {
        this.selectButton = (ToggleButton) SceneController.scene.lookup("#select");
        this.vertexButton = (ToggleButton) SceneController.scene.lookup("#vertex");
        this.edgeButton = (ToggleButton) SceneController.scene.lookup("#edge");
        this.dirEdgeButton = (ToggleButton) SceneController.scene.lookup("#dir-edge");
        this.hyperEdgeButton = (ToggleButton) SceneController.scene.lookup("#hyper-edge");
        this.splitButton = (ToggleButton) SceneController.scene.lookup("#separate");
        this.joinButton = (ToggleButton) SceneController.scene.lookup("#join");
        this.weightButton = (ToggleButton) SceneController.scene.lookup("#weight");

        this.allButtons = new ArrayList<ToggleButton>();
        this.allButtons.add(this.selectButton);
        this.allButtons.add(this.vertexButton);
        this.allButtons.add(this.edgeButton);
        this.allButtons.add(this.dirEdgeButton);
        this.allButtons.add(this.hyperEdgeButton);
        this.allButtons.add(this.splitButton);
        this.allButtons.add(this.joinButton);
        this.allButtons.add(this.weightButton);

        this.allButtons.forEach((b) -> {
            b.setOnAction((e) -> {
                this.allButtons.forEach((button) -> {
                    button.setSelected(false);
                });
                b.setSelected(true);
                this.currentButtonID = b.getId();
            });
        });

        sc.setOnKeyPressed(e->{
            switch(e.getCode()){
                case M:this.selectButton.fire();break;
                case V:this.vertexButton.fire();break;
                case E:this.edgeButton.fire();break;
                case D:this.dirEdgeButton.fire();break;
                case H:this.hyperEdgeButton.fire();break;
                case S:this.splitButton.fire();break;
                case J:this.joinButton.fire();break;
                case W:this.weightButton.fire();break;
            }
        });

        this.selectButton.fire();
    }
}

