import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;

public class TicTacToe {
    enum GameState {nextPlayer, Xwins, Owins, tie}
    enum Player {X, O}
    static Scanner inputParser;
    static String[] board;
    static Player currentPlayer;

    public static void main(String[] args) {

        inputParser = new Scanner(System.in);
        board = new String[9];
        currentPlayer = Player.X;
        initializeEmptyBoard();

        System.out.println("Welcome to a new TicTacToe game! Hope you enjoy.");
        printBoard();
        System.out.println("Player X plays first. Enter the corresponding field number from 1-9 to place your mark.");
        GameState currentState = GameState.nextPlayer;
        
        while (currentState == GameState.nextPlayer) {
            int parsedInput;
            try {
                parsedInput = inputParser.nextInt();
                if (!(parsedInput > 0 && parsedInput <= 9)) {
                    System.out.println("Use numbers from 1-9, not outside that range. Try again:");
                    continue;
                }
            } catch (InputMismatchException e) {
                System.out.println("Input not valid. Use numbers from 1-9:");
                continue;
            }
            if (board[parsedInput-1].equals(String.valueOf(parsedInput))) {
                board[parsedInput-1] = currentPlayer.name();
                if(currentPlayer == Player.X){
                    currentPlayer = Player.O;
                } else {
                    currentPlayer = Player.X;
                }
                printBoard();
                currentState = getCurrentGameState();
            } else {
                System.out.println(parsedInput + "Already taken. Try again:");
            }
        }
        switch (currentState){
            case tie:
                System.out.println("It's a tie!");
                break;
            case Xwins:
                System.out.println("X wins!");
                break;
            case Owins:
                System.out.println("O wins!");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + currentState);
        }
    }

    static GameState getCurrentGameState() {
        for (int winPossibility = 0; winPossibility < 8; winPossibility++) {
            String line;
            if (winPossibility == 0) {
                line = board[0] + board[1] + board[2];
            } else if (winPossibility == 1) {
                line = board[3] + board[4] + board[5];
            } else if (winPossibility == 2) {
                line = board[6] + board[7] + board[8];
            } else if (winPossibility == 3) {
                line = board[0] + board[3] + board[6];
            } else if (winPossibility == 4) {
                line = board[1] + board[4] + board[7];
            } else if (winPossibility == 5) {
                line = board[2] + board[5] + board[8];
            } else if (winPossibility == 6) {
                line = board[0] + board[4] + board[8];
            } else {
                line = board[2] + board[4] + board[6];
            }
            if (line.equals("XXX")) {
                return GameState.Xwins;
            } else if (line.equals("OOO")) {
                return GameState.Owins;
            }
        }

        if(Arrays.stream(board).anyMatch(field -> field.matches("11|2|3|4|5|6|7|8|9"))){
            System.out.println(currentPlayer.name() + "'s turn; enter in which field to place " + currentPlayer.name() + ":");
            return GameState.nextPlayer;
        }
        return GameState.tie;
    }

    static void printBoard() {
        System.out.println("|" + board[0] + "|" + board[1] + "|" + board[2] + "|");
        System.out.println("|" + board[3] + "|" + board[4] + "|" + board[5] + "|");
        System.out.println("|" + board[6] + "|" + board[7] + "|" + board[8] + "|");
    }

    static void initializeEmptyBoard() {
        int a = 0;
        while (a < 9) {
            board[a] = String.valueOf(a+1);
            a++;
        }
    }
}