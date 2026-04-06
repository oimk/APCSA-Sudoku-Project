import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class SudokuGenerator {
    private static int[][] board = new int[9][9];
    private static int[][] playerBoard = new int[9][9];
    private static int[][] zeroBoard = new int[9][9];
    private static int solutions = 0;
    private static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args){
        fillDiagonal(0, 0);
        fillDiagonal(3, 3);
        fillDiagonal(6,6);
        fillGrid(0,3);
        removeElements();
        printBoard(playerBoard);
        System.out.println("Solution: ");
        printBoard(board);
        
        boolean play = true;
        System.out.println();
        System.out.println("The board above is an unsolved Sudoku Puzzle. Your goal is to fill in the 0 in the board with a number from 1-9 that satisfies Sudoku's Rules.");
        System.out.println("If you don't know the rules, please look them up online now.");
        System.out.println("The first row and the first column (the ones with [] and |) are there numbers in a coordinate system to help you match a number to a position");
        System.out.println("If you wish to give up, please input -1 in the row, column inputs");
        System.out.println();
        while (play){
            System.out.println("Please enter the row you want to modify: ");
            int row = sc.nextInt();

            if (row == -1){
                System.out.println("You have given up!");
                play = false;
                continue;
            }
            System.out.println("Please enter the column you want to modify: ");
            int col = sc.nextInt();

            if (col == -1){
                System.out.println("You have given up!");
                play = false;
                continue;
            }

            if (zeroBoard[row][col] != 0){
                System.out.println("The current position is not a number you can modify. Please try agian!");
                continue;
            }
            System.out.println("Please enter the number you want to replace this position with: ");
            int guess = sc.nextInt();


            playerBoard[row][col] = guess;
            System.out.println("Output:");
            printBoard(playerBoard);

            if (isArrayEqual() == true){
                System.out.println("Congrats, you complete the Sudoku!");
                play = false;
            }
        }
        System.out.println();
        System.out.println("Solution: ");
        printBoard(board);

    }

    public static boolean isArrayEqual(){
        for (int r=0; r<board.length; r++){
            for (int c=0; c<board[0].length; c++){
                if (playerBoard[r][c] != board[r][c]){
                    return false;
                }
            }
        }
        return true;
    }

    public static void clone(int[][] input, int[][] output){
        for (int i=0; i<board.length; i++){
            for (int j=0; j<board[0].length; j++){
                output[i][j] = input[i][j];
            }
        }
    }

    public static void removeElements(){
        clone(board, playerBoard);
        for (int i=0; i<30; i++){
            int row = (int)(Math.random()*9);
            int col = (int)(Math.random()*9);
            if (playerBoard[row][col] == 0){
                i--;
                continue;
            }

            playerBoard[row][col] = 0;
            isUnqiue(0, 0);
            if (solutions > 1){
                playerBoard[row][col] = board[row][col];
                i--;
            }
            solutions = 0;
        }
        clone(playerBoard, zeroBoard);
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
            if(isVaild(row, col, n, board)){
                board[row][col] = n;
                if (fillGrid(row, col+1)){
                    return true;
                }
            }
            board[row][col] = 0;
        }
        return false;
    }

    public static void isUnqiue(int row, int col){
        if (row == 9){
            solutions++;
            return;
        }

        if (col == 9){
            isUnqiue(row+1, 0);
            return;
        }
        if (playerBoard[row][col] != 0){
            isUnqiue(row, col+1);
            return;
        }

        int[] list = {1,2,3,4,5,6,7,8,9};
        for (int n : list){
            if(isVaild(row, col, n, playerBoard)){
                playerBoard[row][col] = n;
                isUnqiue(row, col+1);
                playerBoard[row][col] = 0;
                if (solutions > 1) return;
            }
        }     
    }


    public static boolean isVaild(int row, int col, int val, int[][] input){
        //check col
        for (int i=0; i<input[0].length; i++){
            int num = input[row][i];
            if (num == val){
                return false;
            }
        }

        //check row
        for (int j=0; j<input.length; j++){
            int num = input[j][col];
            if (num == val){
                return false;
            }
        }

        int rowOrigin = (int)(row/3)*3;
        int colOrigin = (int)(col/3)*3;

        for (int r=rowOrigin; r<rowOrigin+3; r++){
            for (int c=colOrigin; c<colOrigin+3; c++){
                if (input[r][c] == val){
                    return false;
                }
            }
        }
        return true;
    }


    public static void printBoard(int[][] input){
        System.out.print("  [0 1 2 3 4 5 6 7 8]");
        System.out.println();
        for (int i=0; i<board.length; i++){
            System.out.print(i + "| ");
            for (int j=0; j<board[0].length; j++){
                System.out.print(input[i][j] + " ");
            }
            System.out.println();
        }

    }

}