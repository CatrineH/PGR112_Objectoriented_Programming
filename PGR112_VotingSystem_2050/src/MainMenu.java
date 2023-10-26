import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;
    private final VoteSystem voteSystem;

    public MainMenu(Scanner scanner, VoteSystem voteSystem) {
        this.scanner = scanner;
        this.voteSystem = voteSystem;


    }

    public void showMainMenu() throws SQLException {
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Sign in");
            System.out.println("2. Change your comment");
            System.out.println("3. See the current top Nominee");
            System.out.println("4. See the votes for all Nominees");
            System.out.println("5. Return to main menu");
            System.out.println("6. Exit");
            System.out.print("-> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    signIn();
                    break;
                case 2:
                    changeComment();
                    break;
                case 3:
                    showTopNomineeWithMostVotes();
                    break;
                case 4:
                    printAllVotes();
                    break;
                case 5:
                    showMainMenu();
                    break;
                case 6:
                    exit();
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    private void signIn() throws SQLException {
        System.out.print("Student id: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();

        if (voteSystem.isValidStudentId(studentId)) {
            Nominees nominees = new Nominees(voteSystem, scanner, studentId);
            nominees.showOptions();
        } else {
            System.out.println("Invalid student ID. Please try again.");
        }
    }

    private void changeComment() {
        System.out.print("Enter the Nominee ID for whom you want to change the comment: ");
        int nomineeId = scanner.nextInt();
        scanner.nextLine();

        String nomineeName = voteSystem.getNomineeNameById(nomineeId);
        if (nomineeName != null) {
            System.out.print("Enter the new comment: ");
            String newComment = scanner.nextLine();
            voteSystem.addComment(nomineeId, newComment);
            System.out.println("Comment updated successfully.");
        } else {
            System.out.println("Nominee not found.");
        }
    }

    private void showTopNomineeWithMostVotes() {
        System.out.println("Nominee with the highest number of votes:");
        List<VoteResult> results = voteSystem.printAllVotes();

        if (!results.isEmpty()) {
            VoteResult topResult = null;
            int maxVotes = -1;

            for (VoteResult result : results) {
                if (result.getVotes() > maxVotes) {
                    maxVotes = result.getVotes();
                    topResult = result;
                    result.setComment(result.comments());
                }
            }

            if (topResult != null) {
                System.out.println("Name: " + topResult.getName());
                System.out.println("Votes: " + topResult.getVotes());
                System.out.println("Comment: " + topResult.getComments());

            } else {
                System.out.println("No top nominee with votes found.");
            }
        } else {
            System.out.println("No nominees found.");
        }
    }

    private void printAllVotes() {
        System.out.println("Votes for all Nominees:");
        List<VoteResult> results = voteSystem.printAllVotes();

        if (!results.isEmpty()) {
            for (VoteResult result : results) {
                System.out.println("Name: " + result.getName());
                System.out.println("Votes: " + result.getVotes());
                System.out.println("Comment: " + result.getComments());
                System.out.println();
            }
        } else {
            System.out.println("No nominees found.");
        }
    }

    private void exit() {
        System.out.println("Exiting the program. Goodbye!");
        System.exit(0);
    }
}


