<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Acknowledgment</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <h1>Thank You for Voting!</h1>

        <%
            String url = "jdbc:mysql://localhost:3306/Tenkasi"; 
            String user = "main"; 
            String password = "zoho"; 

            Connection connection = null;
            PreparedStatement selectStmt = null;
            PreparedStatement insertStmt = null;
            PreparedStatement checkVoteStmt = null;
            ResultSet resultSet = null;

            String voterId = request.getParameter("voter_id");
            String candidateId = request.getParameter("candidate_id");
            String electoralAreaId = request.getParameter("electoral_area_id");
            String reason = request.getParameter("reason");

            try {
               
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);

                // Check if the voter has already voted
                String sqlCheckVote = "SELECT 1 FROM poll WHERE voter_id = ?";
                checkVoteStmt = connection.prepareStatement(sqlCheckVote);
                checkVoteStmt.setString(1, voterId);
                ResultSet checkVoteResult = checkVoteStmt.executeQuery();

                if (checkVoteResult.next()) {
                    // Voter has already voted
                    out.println("<p>You have already voted. Only one vote is allowed per person.</p>");
                } 
                else {
                    // Proceed to get candidate information and insert the vote
                    String sqlSelect = "SELECT c.candidate_name, c.party " +
                                       "FROM candidate c " +
                                       "WHERE c.candidate_id = ?";
                
                    selectStmt = connection.prepareStatement(sqlSelect);
                    selectStmt.setInt(1, Integer.parseInt(candidateId));
                    resultSet = selectStmt.executeQuery();

                    if (resultSet.next()) {
                        String candidateName = resultSet.getString("candidate_name");
                        String party = resultSet.getString("party");

                        // Display candidate and voter details
        %>
                        <p><strong>Voter ID:</strong> <%= voterId %></p>
                        <p><strong>Candidate ID:</strong> <%= candidateId %></p>
                        <p><strong>Candidate Name:</strong> <%= candidateName %></p>
                        <p><strong>Electoral Area ID:</strong> <%= electoralAreaId %></p>
                        <p><strong>Party:</strong> <%= party %></p>
                        <h2>Thank You for Your Vote!</h2>
        <%      
                        if (reason == null || reason.trim().isEmpty()) {
                            reason = "No reason provided";
                        }

                        String sqlInsert = "INSERT INTO poll (voter_id, candidate_id, electoral_area_id, reason_for_vote) VALUES (?, ?, ?, ?)";
                        insertStmt = connection.prepareStatement(sqlInsert);
                        insertStmt.setString(1, voterId);
                        insertStmt.setInt(2, Integer.parseInt(candidateId));
                        insertStmt.setInt(3, Integer.parseInt(electoralAreaId));
                        insertStmt.setString(4, reason);
                        int rowsAffected = insertStmt.executeUpdate();

                        if (rowsAffected > 0) {
                            out.println("<p>Your vote has been recorded successfully.</p>");
                        } else {
                            out.println("<p>There was an issue recording your vote. Please try again.</p>");
                        }
                    } else {
                        out.println("<p>No candidate found with the given Candidate ID.</p>");
                    }
                }
            } 
            catch (Exception e) {
                out.println("<p>But, your data is not in the Voter List of this constituency. Please contact the Election Commission of India.</p>");
            } 
            finally {
                if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (checkVoteStmt != null) try { checkVoteStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (selectStmt != null) try { selectStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (insertStmt != null) try { insertStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
                if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
            }
        %>
    </div>
</body>
</html>

