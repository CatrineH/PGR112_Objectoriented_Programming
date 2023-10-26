import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        VoteSystem voteSystem = VoteSystem.getInstance();
        MainMenu mainMenu = new MainMenu(scanner, voteSystem);

        Runnable printVoteResults = () -> voteSystem.printVoteResults();

        System.out.println("Welcome! Here are your options:");
        mainMenu.showMainMenu();
        printVoteResults.run();
    }
}

