package sample;

import javafx.animation.AnimationTimer;

import static sample.Game.game;

public class Algorythm {
    private static int SNR = (int) Math.sqrt(Board.numOfCnR);
    public static int [][] test = {
        {5,3,0,0,7,0,0,0,0}, {6,0,0,1,9,5,0,0,0}, {0,9,8,0,0,0,0,6,0}, {8,0,0,0,6,0,0,0,3}, {4,0,0,8,0,3,0,0,1}, {7,0,0,0,2,0,0,0,6},
            {0,6,0,0,0,0,2,8,0}, {0,0,0,4,1,9,0,0,5}, {0,0,0,0,8,0,0,7,9}
    };
    public static Board generateBoard() {
        Board board = new Board();
        int[][] sudoku = new int[Board.numOfCnR][Board.numOfCnR];
        fillDiagonal(sudoku);
        fillRemaining(0, SNR, sudoku);
        remove(sudoku);
        board.setInitBoard(sudoku);
        int[][] mod = new int[Board.numOfCnR][Board.numOfCnR];
        for(int i=0;i<Board.numOfCnR;i++){
            for(int j=0;j<Board.numOfCnR;j++){
                mod[i][j]=sudoku[i][j];
            }
        }
        board.setModifiedCellList(mod);
        return board;
    }

    private static void fillDiagonal(int[][] sudoku) {
        for (int i = 0; i < Board.numOfCnR; i += SNR) {
            int val = 0;
            for (int r = 0; r < SNR; r++) {
                for (int c = 0; c < SNR; c++) {
                    do {
                        val = (int) Math.floor((Math.random() * Board.numOfCnR + 1));
                    } while (!isNotUsedInBox(i, i, val, sudoku));
                    sudoku[i + r][i + c] = val;
                }
            }
        }
    }

    private static boolean isNotUsedInBox(int row, int col, int val, int[][] sudoku) {
        row = row - row%3;
        col = col - col%3;
        for (int r = row; r < row + SNR; r++) {
            for (int c = col; c < col + SNR; c++) {
                if (sudoku[r][c] == val) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean fillRemaining(int row, int col, int[][] sudoku) {
        if (col >= Board.numOfCnR && row < Board.numOfCnR - 1) {
            row += 1;
            col = 0;
        }
        if (col >= Board.numOfCnR && row >= Board.numOfCnR)
            return true;
        if (row < SNR) {
            if (col < SNR)
                col = SNR;
        } else if (row < Board.numOfCnR - SNR) {
            if (col == (row / SNR * SNR))
                col += SNR;
        } else {
            if (col == Board.numOfCnR - SNR) {
                row++;
                col = 0;
                if (row >= Board.numOfCnR)
                    return true;
            }
        }
        for (int i = 1; i <= Board.numOfCnR; i++) {
            if (isSafe(row, col, i, sudoku)) {
                sudoku[row][col] = i;
                if (fillRemaining(row, col + 1, sudoku))
                    return true;
                sudoku[row][col] = 0;
            }
        }
        return false;
    }

    public static boolean isSafe(int row, int col, int i, int[][] sudoku) {
        boolean b1 = isNotInRow(row, i, sudoku);
        boolean b2 = isNotInCol(col, i, sudoku);
        boolean b3 = isNotUsedInBox(row, col, i, sudoku);
        return (b1 && b2 && b3);

    }

    private static boolean isNotInRow(int row, int i, int[][] sudoku) {
        for (int x = 0; x < Board.numOfCnR; x++)
            if (sudoku[row][x] == i)
                return false;
        return true;
    }

    private static boolean isNotInCol(int col, int i, int[][] sudoku) {
        for (int x = 0; x < Board.numOfCnR; x++)
            if (sudoku[x][col] == i)
                return false;
        return true;
    }
    private static void remove(int [][] sudoku){
        int i, j;
        for(int attemps=Board.remove;attemps>=0;attemps--){
            do
            {
                i = (int)(Math.random()*Board.numOfCnR);
                j = (int)(Math.random()*Board.numOfCnR);
            }while(sudoku[i][j]==0);
            sudoku[i][j]=0;
        }
    }
    public static int[] isFull(int[][] sudoku){
        for(int i=0;i<Board.numOfCnR;i++){
            for(int j=0;j<Board.numOfCnR;j++){
                if(sudoku[i][j]==0)
                    return new int[]{0,i,j};
            }
        }
        return new int[]{1};
    }
    public static boolean isFullB(int[][] sudoku){
        for(int i=0;i<Board.numOfCnR;i++){
            for(int j=0;j<Board.numOfCnR;j++){
                if(sudoku[i][j]==0)
                    return false;
            }
        }
        return true;
    }
    public static boolean backtrackSolutionWithVisual(int [][] sudoku){
        int row = 0;
        int col = 0;
        int [] e = isFull(sudoku);
        if (e[0]==0) {
            row = e[1];
            col = e[2];
        }
        else{
            return true;
        }
        for (int val = 1; val <= Board.numOfCnR; val++) {
            if (isSafe(row, col , val, sudoku)) {
                sudoku[row][col] = val;
                game.gameBoard.setActivesolving(new int[]{row, col});
                game.drawGameBoard();
                if (backtrackSolutionWithVisual(sudoku)) {
                    return true;
                }
                else {
                    sudoku[row][col] = 0;
                    game.drawGameBoard();
                }
            }
        }
        return false;
    }
}
