package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.Instant;

public class Game {
    public static Game game = new Game();

    public boolean isSolving() {
        return isSolving;
    }

    private boolean isSolving = false;
    private boolean isSolved = false;
    private TimerService timerService;

    public void setIsSolved(boolean isSolved){
        game.isSolved=isSolved;
    }

    private Instant startTime;
    Label endLabel;

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    Label timerLabel;
    public Board gameBoard = Algorythm.generateBoard();
    public Canvas graphicBoard;
    public void initTimer(){
        game.startTime = Instant.now();
        game.timerService = new TimerService();
        game.timerService.setPeriod(Duration.seconds(0.1));
        game.timerService.setOnSucceeded((e) -> {
            if(game.isSolved)
                game.timerService.cancel();
            game.timerLabel.setText(String.format("Time : %5.3f", java.time.Duration.between(game.startTime, Instant.now()).toMillis() / 1000.0));
        });
        game.timerService.start();
    }
    public void start(Stage stage) {
        game.graphicBoard = (Canvas) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
        drawGameBoard();
        initTimer();
    }

    public void drawGameBoard() {
        game.gameBoard.drawBoard(game.graphicBoard.getGraphicsContext2D());
    }

    public void setEndLabel(Label endLabel) {
        game.endLabel = endLabel;
    }

    public boolean checkForEnding() {
        int row = 0, col;
        for (int i = 0; i < Board.numOfCnR; i++) {
            col = 0;
            for (int j = 0; j < Board.numOfCnR; j++) {
                if (!Algorythm.isSafe(row, col, game.gameBoard.getModifiedCellList()[row][col], game.gameBoard.getModifiedCellList())) {
                    return false;
                }
                col++;
            }
            row++;
        }
        timerService.cancel();
        return true;
    }

    public void result(boolean r) {
        if (r) {
            endLabel.setText("Congratulations, this grid is correct" + String.valueOf(java.time.Duration.between(startTime, Instant.now()).toMillis() / 1000.0));
        } else {
            endLabel.setText("Sorry, but you got this one wrong." + String.valueOf(java.time.Duration.between(startTime, Instant.now()).toMillis() / 1000.0));
        }
    }

    public void solve() {
        game.isSolving=true;
        game.gameBoard.setActive(new int[]{-1, -1});
        Algorythm.backtrackSolutionWithVisual(game.gameBoard.getModifiedCellList(), graphicBoard.getGraphicsContext2D());
        game.isSolved=true;
        game.gameBoard.setActivesolving(new int[]{-1, -1});
        game.gameBoard.drawBoard(graphicBoard.getGraphicsContext2D());
        game.isSolving=false;
    }
}
