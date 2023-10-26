import java.sql.SQLException;
import java.util.Scanner;


public class Nominees {
    private final VoteSystem voteSystem;
    private final Scanner scanner;
    private final int studentId;

    public Nominees(VoteSystem voteSystem, Scanner scanner, int studentId) {
        this.voteSystem = voteSystem;
        this.scanner = scanner;
        this.studentId = studentId;
    }

    public void showOptions() throws SQLException {
        boolean exit = false;

        while (!exit) {
            System.out.println("Here are your options:");
            System.out.println("----------------");
            System.out.println("1. See all Nominees");
            System.out.println("2. Propose new Nominee");
            System.out.println("3. Vote and add a comment for a Nominee");
            System.out.println("4. Return to main menu");
            System.out.print("-> ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    voteSystem.printNomineeInformation();
                    break;
                case 2:
                    proposeNewNominee();
                    break;
                case 3:
                    voteAndComment();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public void proposeNewNominee() {
        System.out.println("Proposing a new Nominee. Insert student id:");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter nominee name:");
        String nomineeName = scanner.nextLine();
        System.out.println("Enter nominee student program:");
        String nomineeProgram = scanner.nextLine();

        voteSystem.addNominee(nomineeName, nomineeProgram);

        System.out.println("You have nominated " + nomineeName);
    }

    private void voteAndComment() throws SQLException {
        System.out.println("Current Nominees are:");
        voteSystem.printNomineeInformation();

        System.out.print("Who do you wish to vote for? (select number): ");
        int nomineeId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Registered your vote for " + voteSystem.getNomineeNameById(nomineeId) + ". Would you like to add a comment (y/n): ");
        String addCommentChoice = scanner.nextLine();

        if (addCommentChoice.equalsIgnoreCase("y")) {
            System.out.print("Add your comment: ");
            String comment = scanner.nextLine();
            voteSystem.addComment(nomineeId, comment);
        }

        voteSystem.incrementVotes(voteSystem.getNomineeNameById(nomineeId));
        System.out.println("Vote and comment registered successfully.");
    }

}
