package sample;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

public class Game {
    public static Game game = new Game();
    public static Board gameBoard = Algorythm.generateBoard();
    private Stage stage;
    public Canvas graphicBoard;
    public void start(Stage stage){
        this.graphicBoard = (Canvas)stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        drawGameBoard();
    }
    public void drawGameBoard(){
        if(graphicBoard==null)
            this.graphicBoard = (Canvas)stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        try{

            gameBoard.drawBoard(graphicBoard.getGraphicsContext2D());
        }catch(NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
