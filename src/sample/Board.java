package sample;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import static sample.Game.game;

public class Board {
    public final static int cellWidth = 60;
    public final static int cellHeight = 60;
    public final static int numOfCnR = 9;
    public final static int baseOffset = 1;
    public final static int remove = numOfCnR*5;

    public void setActivesolving(int[] activesolving) {
        this.activesolving = activesolving;
    }

    private int[] activesolving = {-1, -1};
    private int[] active = {-1,-1};
    public int[][] getModifiedCellList() {
        return modifiedCellList;
    }
    private int[][] initBoard = new int[numOfCnR][numOfCnR];

    public int[][] getInitBoard() {
        return initBoard;
    }

    public void setInitBoard(int[][] initBoard) {
        this.initBoard = initBoard;
    }

    public void setModifiedCellList(int[][] cellList) {
        this.modifiedCellList = cellList;
    }

    private int[][] modifiedCellList = new int[numOfCnR][numOfCnR];

    public void drawBoard(GraphicsContext gc){
        int row = 0,col = 0;
        for (int[] v: modifiedCellList)
        {
            col = 0;
            for(int c: v){
                //draw cells with shade
                gc.setFill(Color.GRAY);
                gc.fillRect(col*cellWidth, row*cellHeight, cellWidth, cellHeight);
                col++;
            }
            row++;

        }
        //draw black lines between 3x3 squares
        gc.setFill(Color.BLACK);
        gc.fillRect(3*cellWidth-baseOffset, 0, baseOffset*2, cellHeight*numOfCnR);
        gc.fillRect(6*cellWidth-baseOffset, 0, baseOffset*2, cellHeight*numOfCnR);
        gc.fillRect(0, 3*cellHeight-baseOffset,cellWidth*numOfCnR, baseOffset*2);
        gc.fillRect(0, 6*cellHeight-baseOffset,cellWidth*numOfCnR, baseOffset*2);
        drawContent(gc);
    }
    public void drawContent(GraphicsContext gc){
        int row = 0,col = 0;
        for (int[] v: initBoard)
        {
            col = 0;
            for(int c: v){
                int offset[] = {baseOffset, baseOffset, baseOffset, baseOffset}; //left, right, top bottom
                if(row==0){
                    offset[2] += baseOffset;
                }
                if(col==0){
                    offset[0] += baseOffset;
                }
                if(col==(numOfCnR-1)){
                    offset[1] += baseOffset;
                }
                if(row==(numOfCnR-1)){
                    offset[3] += baseOffset;
                }
                gc.setFill(Color.WHITE);
                gc.fillRect(col*cellWidth+offset[0], row*cellHeight+offset[2], cellWidth-(offset[0]+offset[1]), cellHeight-(offset[2]+offset[3]));
                //fill in numbers
                if (c!=0) {
                    gc.setFill(Color.BLACK);
                    gc.setFont(Font.font("Times New Roman", 32));
                    gc.fillText(String.valueOf(c), col*cellWidth+cellWidth/3.0, row*cellHeight+cellHeight/1.5);
                }
                col++;
            }
            row++;
        }
        // draw actively solving
        if(game.isSolving()){
            if(activesolving[0]!=-1 || activesolving[1]!=-1){
                gc.setFill(Color.GREEN);
                gc.fillRect(activesolving[1]*cellWidth,  activesolving[0]*cellHeight, cellWidth, cellHeight);
                gc.setFill(Color.WHITE);
                gc.fillRect(activesolving[1]*cellWidth+baseOffset, activesolving[0]*cellHeight+baseOffset, cellWidth-(2*baseOffset), cellHeight-(2*baseOffset));
            }
        }
        // draw active
        else{
            if(active[0]!=-1 || active[1]!=-1){
                gc.setFill(Color.RED);
                gc.fillRect(active[1]*cellWidth,  active[0]*cellHeight, cellWidth, cellHeight);
                gc.setFill(Color.WHITE);
                gc.fillRect(active[1]*cellWidth+baseOffset, active[0]*cellHeight+baseOffset, cellWidth-(2*baseOffset), cellHeight-(2*baseOffset));
            }
        }
        // draw filled
        row = 0;
        for (int[] ints : modifiedCellList) {
            col=0;
            for (int e : ints) {
                if (e != 0 && initBoard[row][col]==0) {
                    gc.setFill(Color.GREY);
                    gc.setFont(Font.font("Times New Roman", 32));
                    gc.fillText(String.valueOf(e), col*cellWidth+cellWidth/3.0, row*cellHeight+cellHeight/1.5);
                }
                col++;
            }
            row++;
        }
        try{
            Thread.sleep(5);
        }catch(InterruptedException e){

        }
    }

    public int[] getActive() {
        return active;
    }

    public void setActive(int[] active) {
        this.active = active;
    }

    public void setValueForActive(int i) {
        modifiedCellList[active[0]][active[1]]=i;
        // check if its full
        if(Algorythm.isFullB(modifiedCellList)){
            game.result(game.checkForEnding());
        }
    }

    public void acceptValue() {
        // here's a room for value checker that would check if after entering a certain value sudo would
        // still be solvable. Then there would be 2 states: when value is simply typed, and when value is accepted
        // Thats why there is an ambigious node in fxml file with id wrongs. After entering wrong value I would add some
        // red X to that box so that user would know how many times he/she entered wrong value.
    }

    public void moveActive(String dir) {
        switch (dir){
            case "UP":
                if(active[0]!=0)
                    if(initBoard[--active[0]][active[1]]!=0)
                        active[0]++;
                break;
            case "DOWN":
                if (active[0]!=numOfCnR-1)
                    if(initBoard[++active[0]][active[1]]!=0)
                        active[0]--;
                break;
            case "LEFT":
                if(active[1]!=0)
                    if(initBoard[active[0]][--active[1]]!=0)
                        active[1]++;
                break;
            case "RIGHT":
                if (active[1]!=numOfCnR-1)
                    if(initBoard[active[0]][++active[1]]!=0)
                    active[1]--;
                break;
        }
    }
}
