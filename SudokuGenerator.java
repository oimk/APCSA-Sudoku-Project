import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SudokuGenerator {
    private static int[][] board = new int[9][9];
    
    
    public static void main(String[] args){
        fillDiagonal(0, 0);
        fillDiagonal(3, 3);
        fillDiagonal(6,6);
        fillGrid(0,3);
        printBoard();

    }

    public static void fillDiagonal(int row, int col){
        ArrayList<Integer> buffer = new ArrayList<>();
        for (int i = col; i<col+3; i++){
            for (int j=row; j<row+3; j++){
                int num = (int)(Math.random()*9)+1;
                while(buffer.contains(num)){
                    num = (int)(Math.random()*9)+1;
                }
                buffer.add(num);
                board[i][j] = num; 
            }
        }
    }
    

    public static boolean fillGrid(int row, int col){
        if (row == 9){
            return true;
        }

        if (col == 9){
            return fillGrid(row+1, 0);
        }
        if (board[row][col] != 0){
            return fillGrid(row, col+1);
        }

        Integer[] list = {1,2,3,4,5,6,7,8,9};
        ArrayList<Integer> solutions = new ArrayList<>(Arrays.asList(list));
        Collections.shuffle(solutions);

        for (int n : solutions){
            if(isVaild(row, col, n)){
                board[row][col] = n;
                if (fillGrid(row, col+1)){
                    return true;
                }
            }
            board[row][col] = 0;
        }
        return false;
    }


    public static boolean isVaild(int row, int col, int val){
        //check col
        for (int i=0; i<board[0].length; i++){
            int num = board[row][i];
            if (num == val){
                return false;
            }
        }

        //check row
        for (int j=0; j<board.length; j++){
            int num = board[j][col];
            if (num == val){
                return false;
            }
        }

        int rowOrigin = (int)(row/3)*3;
        int colOrigin = (int)(col/3)*3;

        for (int r=rowOrigin; r<rowOrigin+3; r++){
            for (int c=colOrigin; c<colOrigin+3; c++){
                if (board[r][c] == val){
                    return false;
                }
            }
        }
        return true;
    }


    public static void printBoard(){
        for (int i=0; i<board.length; i++){
            for (int j=0; j<board[0].length; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

    }

}