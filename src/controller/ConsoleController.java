package controller;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import model.Algorithms.GraphPropertiesAlgorithms;
import model.GraphGenerator;
import model.GraphProperty;
import view.GraphView;

import java.util.ArrayList;

public class ConsoleController {
    private ArrayList<GraphView> graphs;
    private GraphGenerator graphGenerator;
    private RightMenuController rightMenuController;

    private TextArea console;

    public ConsoleController(Scene sc, ArrayList<GraphView> graphs, GraphGenerator graphGenerator, RightMenuController rightMenuController) {
        this.graphs = graphs;
        this.graphGenerator = graphGenerator;
        this.rightMenuController = rightMenuController;
        this.console = (TextArea) sc.lookup("#console");
        this.console.setText("type \"help\" for a list of commands\n\n");

        this.console.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                String[] allText = this.console.getText().split("\\n");
                String command = allText[allText.length - 1].toLowerCase();
                this.handleCommand(command);
            }
            else if(e.getCode()==KeyCode.ESCAPE){
                this.console.getParent().requestFocus();
            }
        });
    }

    private void handleCommand(String command) {
        String[] commands = command.split(" ");
        switch (commands[0]) {
            case "help":
                this.helpCommand();
                break;
            case "clear":
                this.clearCommand();
                this.console.appendText("\n--Graph Cleared--\n");
                break;
            case "clearconsole":
                this.console.setText("");
                break;
            case "complement":
                complementCommand(commands);
                this.console.appendText("\n--Graph Generated--\n");
                break;
            case "generate":
                generateCommand(commands);
                this.console.appendText("\n--Graph Generated--\n");
                break;
            case "vertices":this.handlePropertyCommand(0);break;
            case "edges":this.handlePropertyCommand(1);break;
            case "deg":this.handlePropertyCommand(2);break;
            case "components":this.handlePropertyCommand(3);break;
            case "radius":this.handlePropertyCommand(4);break;
            case "diameter":this.handlePropertyCommand(5);break;
            case "girth":this.handlePropertyCommand(6);break;
            case "type":this.handlePropertyCommand(7);break;
            case "adjm":this.handleSpecPropertyCommand(0);break;
            case "degm":this.handleSpecPropertyCommand(1);break;
            case "lapm":this.handleSpecPropertyCommand(2);break;
            case "adjmeigen":this.handleSpecPropertyCommand(3);break;
            case "lapmeigen":this.handleSpecPropertyCommand(4);break;
        }
    }

    private void clearCommand() {
        String[] commands = {"generate", "empty(1)"};
        this.generateCommand(commands);
    }

    private void complementCommand(String[] commands) {
        if (commands.length == 1) {
            CanvasController.pane.getChildren().clear();
            this.graphs.set(0, new GraphView(this.graphGenerator.complement(this.graphs.get(0).getGraph())));
            this.rightMenuController.showGraphProperties(this.graphs.get(0));
        }
        if (commands.length > 1) {
            String[] newCommands = {"generate","!"+commands[1]};
            this.generateCommand(newCommands);
        }
    }

    private void generateCommand(String[] commands) {
        if (this.graphs.size() == 0) {
            this.graphs.add(new GraphView(this.graphGenerator.empty(1)));
        }
        if (commands.length > 1) {
            CanvasController.pane.getChildren().clear();
            this.graphs.set(0, new GraphView(this.graphGenerator.generateByKey(commands[1])));
            this.rightMenuController.showGraphProperties(this.graphs.get(0));
        }
    }

    private void helpCommand(){
        StringBuilder text = new StringBuilder();
        text.append("\n--clear-- Clears the current Graph");
        text.append("\n--clearConsole-- Clears the Console");
        text.append("\n--complement-- Generates the complement of the current graph on screen");
        text.append("\n--generate \"graph name\"-- Generates a graph by key. Possible keys are listed below:");
        text.append("\n  --empty(n)-- Generates an empty graph on n vertices");
        text.append("\n  --k(n)-- Generates a complete graph on n vertices");
        text.append("\n  --k(n,m)-- Generates a complete bipartite graph with n x m vertices");
        text.append("\n  --k(n1,n2,...,n_m)-- Generates a complete m-partite graph with m sets of independent vertices");
        text.append("\n  --c(n)-- Generates a cycle on n vertices");
        text.append("\n  --p(n)-- Generates a path on n vertices");
        text.append("\n  --star(n)-- Generates a star graph on n vertices");
        text.append("\n  --wheel(n)-- Generates a wheel graph on n vertices");
        text.append("\n  --bull(n)-- Generates a bull graph on n vertices");
        text.append("\n  --prism(n,k)-- Generates a prism graph on n,k vertices");
        text.append("\n  --petersen(n,k)-- Generates a generalised petersen graph on n,k vertices");
        text.append("\n  --random(n,p)-- Generates a random graph on n vertices with connectivity 0<=p<=1");
        text.append("\n  --random(n,p)-- Generates a random graph on n vertices with connectivity 0<=p<=1");
        text.append("\n  --!-- Generates the complement of a graph. Should be placed before \"graph name\"");
        text.append("\n  --+-- Generates the union of 2 or more graphs");
        text.append("\n  --EXAMPLE-- \"generate !k(3)+k(2)\"");
        text.append("\n--vertices-- Gives the number of the vertices of the graph on screen");
        text.append("\n--edges-- Gives the number of edges of the graph on screen");
        text.append("\n--deg-- Gives the degree sequence of the graph on screen");
        text.append("\n--radius-- Gives the radius of the graph on screen");
        text.append("\n--diameter-- Gives the diameter of the graph on screen");
        text.append("\n--girth-- Gives the girth of the graph on screen");
        text.append("\n--type-- Gives the list of types of the graph on screen");
        text.append("\n--adjM-- Gives the adjacent matrix of the current graph on screen");
        text.append("\n--degM-- Gives the degree matrix of the current graph on screen");
        text.append("\n--lapM-- Gives the laplacian matrix of the current graph on screen");
        text.append("\n--adjMeigen-- Gives the eigenvalues of the adjacent matrix");
        text.append("\n--lapMeigen-- Gives the eigenvalues of the laplacian matrix");
        this.console.appendText(text.toString());
    }

    private void handlePropertyCommand(int index){
        GraphProperty prop = (GraphProperty)rightMenuController.getPropTable().getItems().get(index);
        this.console.appendText("\n"+prop.getProperty()+": "+prop.getValue().toString());
    }

    private void handleSpecPropertyCommand(int index){
        GraphProperty prop = (GraphProperty)rightMenuController.getSpecTable().getItems().get(index);
        this.console.appendText("\n"+prop.getProperty()+": \n"+prop.getValue().toString());
    }


}
