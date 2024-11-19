package controller;

import java.sql.SQLException;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Admin;
import model.Voter;
import model.Candidate;
import model.ElectoralArea;
import database.DatabaseConnection;
import database.SQLQueries;

public class VoxPopuliSystem{
      
      private static VoxPopuliSystem voxPopuliSystem;
      private Connection connection = DatabaseConnection.getConnection();
      
     //For singleton purpose 
      private VoxPopuliSystem(){
      
      }
      
      public static VoxPopuliSystem getVoxPopuliInstance(){
             if(voxPopuliSystem==null)
                   voxPopuliSystem = new VoxPopuliSystem();
             return voxPopuliSystem;
      }          
      
       //authenticate admin
       public boolean adminCheck(String userName, String password){
             try(PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.ADMIN_CHECK)){
                       preparedStatement.setString(1,userName);
                       try(ResultSet resultSet = preparedStatement.executeQuery()){
                              while(resultSet.next()){
                                     String user_Name = resultSet.getString("admin_userName");
                                     String passWord = resultSet.getString("admin_password");
                                      if(userName.equals(user_Name) && password.equals(passWord))
                                              return true;
                              }
                       }
            }
            catch(SQLException sqlException){
                   sqlException.getMessage();
            }
            return false;
      }    
      
      //authorize admin
      public boolean authorizeAdmin(String userName, String password){
            if(userName.equals(Admin.getRootUserName()) && password.equals(Admin.getRootPassword()))
                    return true;
            return false;        
      }
      
      //check presence of records      
      private boolean recordExists(String query, String param) throws SQLException {
          PreparedStatement existenceStatement = DatabaseConnection.getPreparedStatement(query);
          existenceStatement.setString(1, param);
          ResultSet resultSet = existenceStatement.executeQuery();
          resultSet.next();
         return resultSet.getInt(1) > 0;
     }
      
      //add admin     
      public boolean addAdmin(Admin admin){
            try{
                  if(recordExists(SQLQueries.CHECK_ADMIN_EXISTENCE,admin.getAdminUserName())){
                        System.out.println("Admin userName  already exists. Please Enter Different UserName:.");
                        return false;     
                  }                             
              
              PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.ADD_ADMIN);
              preparedStatement.setString(1,admin.getAdminName());
              preparedStatement.setString(2,admin.getAdminUserName());
              preparedStatement.setString(3,admin.getAdminPassword());
              preparedStatement.executeUpdate();
              
              System.out.println("ADMIN ADDED SUCCESSFULLY");
              return true;
          }
          catch(SQLException sqlException){
                System.err.println("There was an issue in Add Admin. Verify the Query/Ensure the Database connectivity");
          }  
          return false;             
      }                                     

      //add area
      public boolean addElectoralArea(ElectoralArea electoralArea){
            try{
                 if(recordExists(SQLQueries.CHECK_AREA_EXISTENCE,electoralArea.getElectoralAreaName())){
                        System.out.println("Electoral Area already exists. Please choose a different Area Name.");
                        return false;
               }
               
               PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.ADD_ELECTORAL_AREA);
               preparedStatement.setShort(1,electoralArea.getElectoralAreaId());
               preparedStatement.setString(2,electoralArea.getElectoralAreaName());
               preparedStatement.setInt(3,electoralArea.getTotalMales());
               preparedStatement.setInt(4,electoralArea.getTotalFemales());
               preparedStatement.setInt(5,electoralArea.getTotalTransgenders());
               preparedStatement.executeUpdate();
               
               System.out.println("ELECTORAL AREA ADDED SUCCESSFULLY");
               return true;
          }
          catch(SQLException sqlException){
                System.err.println("There was an issue in Add Electoral Area. Verify the Query/Ensure the Database connectivity");
          }  
          return false;             
      }           
      
      //add voter
      public boolean addVoter(Voter voter){
           try{
                 if(recordExists(SQLQueries.CHECK_VOTER_EXISTENCE,voter.getVoterId())){
                        System.out.println("Voter  already exists. Please Enter Valid Voter Details.");
                        return false;                                     
              }
              
              PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.ADD_VOTER);
              preparedStatement.setString(1, voter.getVoterId());
              preparedStatement.setString(2, voter.getVoterName());
              preparedStatement.setInt(3,(int)voter.getVoterAge());
              preparedStatement.setString(4,voter.getGender());
              preparedStatement.setInt(5,(int)voter.getElectoralAreaId());
              preparedStatement.executeUpdate();
              
              System.out.println("VOTER ADDED SUCCESSFULLY");
              return true;
          }
          catch(SQLException sqlException){
                 System.err.println("There was an issue in Add Voter . Verify the Query/Ensure the Database connectivity");           
          }
          return false;         
     }
     
     //add candidate
     public boolean addCandidate(Candidate candidate){
          try{
              Integer candiateId = candidate.getCandidateId();
              String candidateIdInString  = candiateId.toString();
              if(recordExists(SQLQueries.CHECK_CANDIDATE_EXISTENCE,candidateIdInString)){
                     System.out.println("Candidate ID already exists. Please Enter Valid candidate Details."); 
                     return false;
              }       
              
              PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.ADD_CANDIDATE);
              preparedStatement.setInt(1,candidate.getCandidateId());
              preparedStatement.setString(2,candidate.getCandidateName());
              preparedStatement.setString(3,candidate.getCandidateParty());
              preparedStatement.setInt(4,(int)candidate.getElectoralAreaId());
              preparedStatement.setInt(5,(int)candidate.getCandidateAge());
              preparedStatement.setString(6,candidate.getGender());
              preparedStatement.executeUpdate();
              
              System.out.println("CANDIDATE ADDED SUCCESSFULLY");
              return true;
          }
          catch(SQLException sqlException){
                   System.err.println("There was an issue in Add Candidate . Verify the Query/Ensure the Database connectivity"); 
          }
          return false;         
     }
     
     //view particular candidate
     public List<Candidate> viewCandidate(int candidateId , short electoralAreId , String party){
          List<Candidate> candidateList = new LinkedList();
          try(PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.VIEW_CANDIDATE)){
                preparedStatement.setInt(1,candidateId);
                preparedStatement.setInt(2,(int)electoralAreId);
                preparedStatement.setString(3,party);
               
                try(ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                            candidateList.add(new Candidate(
                                            resultSet.getInt("candidate_id"),
                                            resultSet.getString("candidate_name"),
                                            resultSet.getByte("candidate_age"),
                                            resultSet.getString("party"),
                                            resultSet.getString("gender"),
                                            resultSet.getShort("electoral_area_id")));
                   }
               }
         }
         catch(SQLException sqlException){
                 System.err.println("Sorry! There was an unExpected error while finding the Candidate, Try Again!");
         }                                             
         return candidateList;
    }     
    
    //view candidates
     public List<Candidate> viewCandidate(){
           List<Candidate> candidateList = new ArrayList();
           try(PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.VIEW_ALL_CANDIDATES); 
                 ResultSet resultSet = preparedStatement.executeQuery()){
                    while(resultSet.next()){
                            candidateList.add(new Candidate(
                                            resultSet.getInt("candidate_id"),
                                            resultSet.getString("candidate_name"),
                                            resultSet.getByte("candidate_age"),
                                            resultSet.getString("gender"),
                                            resultSet.getString("party"),
                                            resultSet.getShort("electoral_area_id")));
                   } 
          }
          catch(SQLException sqlException){
                 System.err.println("Sorry! There was an unexpected error while finding the Candidate, Try Again!");
          }
          return candidateList;
     }
     
     //find result
     private List<String> findResult(int electoralAreaId, String sqlQuery, String noVotesMessage) {
         List<String> candidateList = new LinkedList();
         try (PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(sqlQuery)) {
               preparedStatement.setInt(1, electoralAreaId); 
               try (ResultSet resultSet = preparedStatement.executeQuery()) {
                  if (resultSet.next()) {
                    int candidateId = resultSet.getInt("candidate_id");
                    String candidateName = resultSet.getString("candidate_name");
                    String party = resultSet.getString("party");
                    int voteCount = resultSet.getInt("vote_count");

                    candidateList.add("Candidate ID: " + candidateId);
                    candidateList.add("Candidate Name: " + candidateName);
                    candidateList.add("Party: " + party);
                    candidateList.add("Vote Count: " + voteCount);
                 } 
                 else {
                   candidateList.add(noVotesMessage + electoralAreaId);
                 }
              }
          } 
          catch (SQLException e) {
                candidateList.add("Error retrieving candidate details: " + e.getMessage());
          }
          return candidateList;
     }
     
     //find winner
     public List<String> findWinner(int electoralAreaId) {
         return findResult(electoralAreaId, SQLQueries.FIND_WINNER_IN_AN_AREA, 
                         "No votes recorded for the electoral area ID: ");
     }
     
     //find loser 
     public List<String> findWeaker(int electoralAreaId) {
         return findResult(electoralAreaId, SQLQueries.FIND_WEAKER_IN_AN_AREA, 
                         "No votes recorded for the electoral area ID: ");
     }
     
     //find popular parties
     public List<String> findPopularParties() {
        List<String> popularParties = new LinkedList<>();
        try (PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.FIND_POPULAR_PARTY);
               ResultSet resultSet = preparedStatement.executeQuery()) {
 
               popularParties.add(String.format("| %-20s | %-15s |", "Party Name", "Total Votes"));
               popularParties.add("|----------------------|-----------------|");

              while (resultSet.next()) {
                  String partyName = resultSet.getString("party");
                  long totalVotes = resultSet.getInt("total_votes");
                 popularParties.add(String.format("| %-20s | %-15d |", partyName, totalVotes));
             }
            popularParties.add("|----------------------|-----------------|");
       } 
       catch (SQLException sqlException) {
           System.err.println("There was an issue while retrieving the popular parties. Please try again later.");
       }
       return popularParties;
    }

     //who votes for whom
     public List<String> whoVotesForWhom(){
         List<String> findAllVotes = new ArrayList();
         try(PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.WHO_VOTES_FOR_WHOM);
               ResultSet resultSet = preparedStatement.executeQuery()){
               
               findAllVotes.add(String.format("%-20s | %-20s | %-20s | %-20s|", "Voter_name","Voter_id","Candidate_Name","Party_Name"));
               findAllVotes.add("-------------------------------------------------------------------------------------------|");
               
               while(resultSet.next()){
                    String voterName = resultSet.getString("voter_name");
                    String voterId = resultSet.getString("voter_id");
                    String candidateName = resultSet.getString("candidate_name");
                    String partyName = resultSet.getString("party");
                    
                    findAllVotes.add(String.format("%-20s | %-20s | %-20s | %-20s|",voterName,voterId,candidateName,partyName));
               }     
               findAllVotes.add("|-------------------------------------------------------------------------------------------|");
        }       
        catch(SQLException sqlException){
              System.err.println("Sorry! The current method Invocation is Failed due to some Issues. CALL MAIN ADMIN for further purposes");
        }
        return  findAllVotes;
    } 
    
    //get votes of the candidates of the particular party
    public List<String> getVotesOfTheCandidate(String partyName){
          List<String> candidatesList = new LinkedList();
          try(PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.VIEW_CANDIDATES_OF_THE_PARTY)){
            preparedStatement.setString(1,partyName);
               
            try(ResultSet resultSet = preparedStatement.executeQuery()){
              candidatesList.add(String.format("%-25s|%-25s|%-25s|%-25s|%-25s|","candidate_id","candidate_name","party","Area_Name","Total_Votes"));               
              candidatesList.add("-------------------------------------------------------------------------------------------------------------------------------|");                  
                    while(resultSet.next()){
                        int candidateId = resultSet.getInt("candidate_id");
                        String candidateName = resultSet.getString("candidate_name");
                        String party = resultSet.getString("party");
                        String areaName = resultSet.getString("electoral_area_name");
                        int totalVotes = resultSet.getInt("votes_received");
                        
                      candidatesList.add(String.format("%-25s|%-25s|%-25s|%-25s|%-25s|",candidateId,candidateName,party,areaName,totalVotes));
                   }
               candidatesList.add("|--------------------------------------------------------------------------------------------------------------------------------|");                  
           }
        }
        catch(SQLException sqlException){
              System.err.println("Sorry! This Option is Currently UnavailAble");
        }
        return  candidatesList;
   }          
   
   //find non voters
   public List<String> findNonVoters(){
       List<String> nonVotersList = new ArrayList();
       try(PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement(SQLQueries.VOTERS_DOESNOT_VOTE);
              ResultSet resultSet = preparedStatement.executeQuery()){
                
                nonVotersList.add(String.format("%-20s|%-20s|%-20s|","voter_id","voter_name","Area_id"));
                nonVotersList.add("--------------------------------------------------------------|");
                
                while(resultSet.next()){
                     String voterId = resultSet.getString("voter_id");
                     String voterName = resultSet.getString("voter_name");
                     int AreaId = resultSet.getInt("electoral_area_id");
                     
                     nonVotersList.add(String.format("%-20s|%-20s|%-20s|",voterId,voterName,AreaId));
              }
              
      }
      catch(SQLException sqlException){
            System.err.println("Sorry! Due to some internal issues , This page is get interrupted. Try later.");
      }
      return nonVotersList;
  }          
                     
                   
                                                                                                                                                                                                                           
          
    
    
    
            
                  
                       
                       
                       
                       
                                                                                         
                                                                                  
      
                            
             
  
}             
