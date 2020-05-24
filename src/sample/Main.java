package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Simple Sudoku Solver");
        primaryStage.setScene(new Scene(root, Board.cellWidth*Board.numOfCnR, 100+Board.cellHeight*Board.numOfCnR));
        primaryStage.show();
        root.requestFocus();
        Game g = new Game();
        g.start(primaryStage);

    }
    public static void main(String[] args) {
        launch(args);
    }
}
