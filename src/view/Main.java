package view;

import controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tests.Test6Speed;

public class Main extends Application {

//    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Graphz");
        Scene sc = new Scene(root,1920,1080);
        primaryStage.setScene(sc);
        primaryStage.setMaximized(true);
        primaryStage.show();
        SceneController controller = new SceneController(sc);
//        Test6Speed test = new Test6Speed();
//        test.execute();
//        ToggleButton maikati = ((ToggleButton)(sc.lookup("#select")));
//        Canvas cnvs = (Canvas)sc.lookup("#the-canvas");
//        Pane pane = (Pane)sc.lookup("#pane");
//        cnvs.setWidth(pane.getWidth());
//        cnvs.setHeight(pane.getHeight());
//        cnvs.setOnMouseClicked((e)->{
//            System.out.println("x: "+e.getX()+"y: "+e.getY());
//            System.out.println("width: "+pane.getWidth()+"height: "+pane.getHeight());
//        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
