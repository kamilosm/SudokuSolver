package sample;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.Instant;

public class Game {
    public static Game game = new Game();
    private boolean isSolving = false;
    private TimerService timerService;

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    private Instant startTime;
    Label endLabel;

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    Label timerLabel;
    public Board gameBoard = Algorythm.generateBoard();
    private Stage stage;
    public Canvas graphicBoard;
    public void start(Stage stage){
        game.graphicBoard = (Canvas)stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        drawGameBoard();
        startTime = Instant.now();
        timerService = new TimerService();
        timerService.setPeriod(Duration.seconds(0.1));
        timerService.setOnSucceeded((e)->
                game.timerLabel.setText("Time :" + String.valueOf(java.time.Duration.between(startTime, Instant.now()).toMillis()/1000.0)));
        timerService.start();
    }
    public void drawGameBoard(){
        game.gameBoard.drawBoard(game.graphicBoard.getGraphicsContext2D());
    }

    public void setEndLabel(Label endLabel) {
        game.endLabel = endLabel;
    }
    public boolean checkForEnding(){
        int row=0, col;
        for(int i=0;i<Board.numOfCnR;i++){
            col=0;
            for(int j=0;j<Board.numOfCnR;j++){
                if(!Algorythm.isSafe(row, col, game.gameBoard.getModifiedCellList()[row][col], game.gameBoard.getModifiedCellList())){
                    return false;
                }
                col++;
            }
            row++;
        }
        timerService.cancel();
        return true;
    }
    public void result(boolean r){
        if(r){
            endLabel.setText("Congratulations, this grid is correct" + String.valueOf(java.time.Duration.between(startTime, Instant.now()).toMillis()/1000.0));
        }
        else{
            endLabel.setText("Sorry, but you got this one wrong." + String.valueOf(java.time.Duration.between(startTime, Instant.now()).toMillis()/1000.0));
        }
    }

    public void solve() {
        isSolving=true;
        game.gameBoard.setActive(new int[]{-1,-1});
        Algorythm.backtrackSolutionWithVisual(gameBoard.getInitBoard());
        isSolving=false;
        game.gameBoard.setActivesolving(new int[]{-1, -1});
        game.gameBoard.drawBoard(graphicBoard.getGraphicsContext2D());
    }

    public boolean isSolving() {
        return isSolving;
    }

    public void setSolving(boolean solving) {
        isSolving = solving;
    }
}
