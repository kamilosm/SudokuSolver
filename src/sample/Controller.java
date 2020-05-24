package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class Controller {
    @FXML
    public Canvas graphicBoard;
    @FXML
    public HBox wrongs;
    @FXML
    public Label timeLabel;
    public Button button_solve_for_me;
    public Button button_generate_again;

    @FXML
    public void initialize(){
        graphicBoard.setHeight(Board.cellHeight*Board.numOfCnR);
        graphicBoard.setWidth(Board.cellWidth*Board.numOfCnR);
        graphicBoard.setOnKeyTyped(e->{
            if(Game.gameBoard.getInitBoard()[Game.gameBoard.getActive()[0]][Game.gameBoard.getActive()[1]]==0){
                switch(e.getCode()){
                    case ENTER:
                        Game.gameBoard.acceptValue();
                        break;
                    case SOFTKEY_0:
                        Game.gameBoard.setValueForActive(0);
                        break;
                    case SOFTKEY_1:
                        Game.gameBoard.setValueForActive(1);
                        break;
                    case SOFTKEY_2:
                        Game.gameBoard.setValueForActive(2);
                        break;
                    case SOFTKEY_3:
                        Game.gameBoard.setValueForActive(3);
                        break;
                    case SOFTKEY_4:
                        Game.gameBoard.setValueForActive(4);
                        break;
                    case SOFTKEY_5:
                        Game.gameBoard.setValueForActive(5);
                        break;
                    case SOFTKEY_6:
                        Game.gameBoard.setValueForActive(6);
                        break;
                    case SOFTKEY_7:
                        Game.gameBoard.setValueForActive(7);
                        break;
                    case SOFTKEY_8:
                        Game.gameBoard.setValueForActive(8);
                        break;
                    case SOFTKEY_9:
                        Game.gameBoard.setValueForActive(9);
                        break;
                }
            }
            switch (e.getCode()){
                case UP:
                    Game.gameBoard.moveActive("UP");
                    break;
                case DOWN:
                    Game.gameBoard.moveActive("DOWN");
                    break;
                case LEFT:
                    Game.gameBoard.moveActive("LEFT");
                    break;
                case RIGHT:
                    Game.gameBoard.moveActive("RIGHT");
                    break;
            }
            Game.game.drawGameBoard();

        });
    }

    public void guiButton(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(button_solve_for_me)){
            Game.gameBoard.setModifiedCellList(new int[Board.numOfCnR][Board.numOfCnR]);

        }
        else if(actionEvent.getSource().equals(button_generate_again)){
            Game.gameBoard = Algorythm.generateBoard();
            Game.game.drawGameBoard();
        }
    }

    public void boardClick(MouseEvent mouseEvent) {
        if((int)mouseEvent.getSceneY() < Board.numOfCnR*Board.cellHeight && (int)mouseEvent.getSceneX() < Board.numOfCnR*Board.cellWidth){
            int row = (int)mouseEvent.getSceneY()/Board.cellHeight;
            int col = (int)mouseEvent.getSceneX()/Board.cellWidth;
            if(Game.gameBoard.getInitBoard()[row][col]==0){
                Game.gameBoard.setActive(new int[]{row, col});
                Game.game.drawGameBoard();
            }
        }
    }
}
