import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteSystem {
    static {
        try {
            DriverManager.registerDriver(new Driver());

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_vote?allowPublicKeyRetrieval=true&useSSL=false",
                    "censor",
                    "GetPasswordCensor"
            );

            String query = "CREATE TABLE IF NOT EXISTS student_nominees"
                    + "("
                    + "id INT AUTO_INCREMENT, "
                    + "name varchar(255) UNIQUE, "
                    + "votes INT DEFAULT 0, "
                    + "student_program varchar(255), "
                    + "PRIMARY KEY(id)"
                    + ");";

            Statement statement = VoteSystem.connection.createStatement();

            statement.execute(query);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final VoteSystem instance = new VoteSystem();
    private static final Connection connection;

    // Konstruktør
    private VoteSystem() {
    }


    // Metoder
    public boolean isValidStudentId(int studentId) {
        return studentId >= 1 && studentId <= 1000;
    }

    public boolean nomineeExistsInDatabase(String name) {
        String query = "SELECT * FROM student_nominees WHERE name = ?";

        try {
            PreparedStatement statement = VoteSystem.connection.prepareStatement(query);

            statement.setString(1, name);

            return statement.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addComment(int nomineeId, String commentText) {
        String query = "INSERT INTO comments (nominee_id, comment_text) VALUES (?, ?);";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, nomineeId);
            statement.setString(2, commentText);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNominee(String name, String studentProgram) {
        if (!this.nomineeExistsInDatabase(name)) {
            String query = "INSERT INTO student_nominees(name, student_program) VALUES(?, ?);";

            try {
                PreparedStatement statement = VoteSystem.connection.prepareStatement(query);
                statement.setString(1, name);
                statement.setString(2, studentProgram);
                statement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void incrementVotes(String name) {
        if (this.nomineeExistsInDatabase(name)) {
            String query = "UPDATE student_nominees SET votes = votes + 1 WHERE name = ?;";

            try {
                PreparedStatement statement = VoteSystem.connection.prepareStatement(query);
                statement.setString(1, name);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void printNomineeInformation() throws SQLException {
        String query = "SELECT sn.id, sn.name, sn.votes, sn.student_program, c.comment_text " +
                "FROM student_nominees sn " +
                "LEFT JOIN comments c ON sn.id = c.nominee_id;";

        ResultSet resultSet;

        try {
            PreparedStatement statement = VoteSystem.connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            int currentNomineeId = -1;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int votes = resultSet.getInt("votes");
                String studentProgram = resultSet.getString("student_program");
                String commentText = resultSet.getString("comment_text");

                if (id != currentNomineeId) {
                    if (currentNomineeId != -1) {
                        System.out.println();
                    }
                    System.out.println("Nominee ID: " + id);
                    System.out.println("Name: " + name);
                    System.out.println("Votes: " + votes);
                    System.out.println("Student Program: " + studentProgram);
                    currentNomineeId = id;
                }
                if (commentText != null) {
                    System.out.println("Comment: " + commentText);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<VoteResult> printAllVotes() {
        List<VoteResult> results = new ArrayList<>();

        String voteQuery = "SELECT id, name, student_program, votes FROM student_nominees;";
        String commentQuery = "SELECT nominee_id, comment_text FROM comments;";

        try {
            PreparedStatement voteStatement = VoteSystem.connection.prepareStatement(voteQuery);
            ResultSet voteResultSet = voteStatement.executeQuery();

            while (voteResultSet.next()) {
                int id = voteResultSet.getInt("id");
                String name = voteResultSet.getString("name");
                String studentProgram = voteResultSet.getString("student_program");
                int votes = voteResultSet.getInt("votes");

                results.add(new VoteResult(id, name, studentProgram, votes, "")); // Initialize comments as an empty string
            }

            PreparedStatement commentStatement = VoteSystem.connection.prepareStatement(commentQuery);
            ResultSet commentResultSet = commentStatement.executeQuery();

            HashMap<Integer, String> commentsMap = new HashMap<>();
            while (commentResultSet.next()) {
                int nomineeId = commentResultSet.getInt("nominee_id");
                String commentText = commentResultSet.getString("comment_text");
                commentsMap.put(nomineeId, commentText != null ? commentText : "No comment");
            }

            // Prøver å legger til kommentarer til VoteResult objektet
            for (VoteResult result : results) {
                String comment = commentsMap.get(result.getId()); // Hente kommentar for nominee ID
                result.setComment(comment); // sette kommentar for VoteResult object
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return results;
    }


    public String getNomineeNameById(int nomineeId) {
        String query = "SELECT name FROM student_nominees WHERE id = ?";

        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, nomineeId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null; // Returnerer null hvis nominee med gitt id ikke er funnet
    }

    private final HashMap<String, Integer> votes = new HashMap<>();

    String getWinner() {
        String winner = null;
        int maxVotes = -1;

        for (String candidate : votes.keySet()) {
            int candidateVotes = votes.get(candidate);
            if (candidateVotes > maxVotes) {
                maxVotes = candidateVotes;
                winner = candidate;
            }
        }

        return winner;
    }

    public void printVoteResults() {
        System.out.println("Vote results:");

        for (String candidate : votes.keySet()) {
            int candidateVotes = votes.get(candidate);
            System.out.printf("- %s: %d stemmer%n", candidate, candidateVotes);
        }

        String winner = getWinner();
        if (winner != null) {
            System.out.println("Winner: " + winner);
        } else {
            System.out.println("No winner has been chosen yet.");
        }
    }


    // Statiske metoder
    static VoteSystem getInstance() {
        return VoteSystem.instance;
    }
}
