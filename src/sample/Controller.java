package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.time.Instant;

import static sample.Game.game;

public class Controller {
    @FXML
    public Canvas graphicBoard;
    @FXML
    public HBox wrongs;
    @FXML
    public Label timeLabel;
    public Button button_solve_for_me;
    public Button button_generate_again;
    public BorderPane container;
    public Label endLabel;

    @FXML
    public void initialize(){
        graphicBoard.setHeight(Board.cellHeight*Board.numOfCnR);
        graphicBoard.setWidth(Board.cellWidth*Board.numOfCnR);
        game.setEndLabel(endLabel);
        game.setTimerLabel(timeLabel);
        container.setOnKeyReleased(e->{
            if(game.isSolving())
                return;
            if(game.gameBoard.getActive()[0] == -1)
                return;
            if(game.gameBoard.getInitBoard()[game.gameBoard.getActive()[0]][game.gameBoard.getActive()[1]]==0 ){
                switch(e.getCode()){
                    case ENTER:
                        game.gameBoard.acceptValue();
                        break;
                    case DIGIT0:
                        game.gameBoard.setValueForActive(0);
                        break;
                    case DIGIT1:
                        game.gameBoard.setValueForActive(1);
                        break;
                    case DIGIT2:
                        game.gameBoard.setValueForActive(2);
                        break;
                    case DIGIT3:
                        game.gameBoard.setValueForActive(3);
                        break;
                    case DIGIT4:
                        game.gameBoard.setValueForActive(4);
                        break;
                    case DIGIT5:
                        game.gameBoard.setValueForActive(5);
                        break;
                    case DIGIT6:
                        game.gameBoard.setValueForActive(6);
                        break;
                    case DIGIT7:
                        game.gameBoard.setValueForActive(7);
                        break;
                    case DIGIT8:
                        game.gameBoard.setValueForActive(8);
                        break;
                    case DIGIT9:
                        game.gameBoard.setValueForActive(9);
                        break;
                }
            }
            switch (e.getCode()){
                case UP:
                    game.gameBoard.moveActive("UP");
                    break;
                case DOWN:
                    game.gameBoard.moveActive("DOWN");
                    break;
                case LEFT:
                    game.gameBoard.moveActive("LEFT");
                    break;
                case RIGHT:
                    game.gameBoard.moveActive("RIGHT");
                    break;
            }
            game.drawGameBoard();

        });
    }

    public void guiButton(ActionEvent actionEvent) {
        if(game.isSolving())
            return;
        if(actionEvent.getSource().equals(button_solve_for_me)){
            new Thread(()->game.solve()).start();

        }
        else if(actionEvent.getSource().equals(button_generate_again)){
            game.gameBoard = Algorythm.generateBoard();
            game.setIsSolved(false);
            game.initTimer();
            game.drawGameBoard();
        }
    }

    public void boardClick(MouseEvent mouseEvent) {
        if(game.isSolving())
            return;
        if((int)mouseEvent.getSceneY() < Board.numOfCnR*Board.cellHeight && (int)mouseEvent.getSceneX() < Board.numOfCnR*Board.cellWidth){
            int row = (int)mouseEvent.getSceneY()/Board.cellHeight;
            int col = (int)mouseEvent.getSceneX()/Board.cellWidth;
            if(game.gameBoard.getInitBoard()[row][col]==0){
                game.gameBoard.setActive(new int[]{row, col});
                game.drawGameBoard();
            }
        }
    }
}
