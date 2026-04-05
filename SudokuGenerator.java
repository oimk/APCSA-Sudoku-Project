import java.util.ArrayList;

public class SudokuGenerator {
    private static int[][] board = new int[9][9];
    
    
    public static void main(String[] args){
        fillDiagonal(0,0);
        fillDiagonal(3,3);
        fillDiagonal(6,6);

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

    public static void fillGrid(int row, int col){
        if (col == 9){
            return;
        }

        if (row == 9){
            fillGrid(0, col+1);
        }

        if (board[row][col] == 0){
            fillGrid(row+1, col);
        } else {

        }
    }

    public static void fillElement(int row, int col){
        board[row][col] = findValid(row, col);
    }

    //outputs a vaild number
    public static int findValid(int row, int col){
        ArrayList<Integer> buffer = new ArrayList<>();

        //check row
        for (int i=0; i<board.length; i++){
            int num = board[i][col];
            if (num != 0){
                buffer.add(num);
            }
        }

        //if all rows are filled, try a different number in the p
        if (buffer.size() == 9){
            
        }

        //check col
        for (int j=0; j<board[0].length; j++){
            int num = board[row][j];
            if (num != 0 && num != col && !buffer.contains(num)){
                buffer.add(num);
            }
        }

        if (buffer.size() == 9){
            return -1;
        }

        // check grid
        int rowOrgin = (int)(row/3) * 3;
        int colOrgin = (int)(col/3) * 3;
        
        for (int r=rowOrgin; r<rowOrgin+3; r++){
            if (r==row){
                continue;
            } else {
                for (int c=colOrgin; c<colOrgin+3; c++){
                    if (c==col){
                        continue;
                    } else{
                        int num = board[r][c];
                        if (num != 0 && !buffer.contains(num)){
                            buffer.add(num);
                        }
                    }
                }
            }
        }




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