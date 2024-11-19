package view;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import poll.AdminAuthentication;
import model.Admin;
import model.Candidate;
import model.Voter;
import model.ElectoralArea;
import model.ValidationCheck;
import controller.VoxPopuliSystem;

public class VoxPopuliManagement extends ValidationCheck{
       
       protected Scanner input = new Scanner(System.in);
       private VoxPopuliSystem voxPopuliSystem = VoxPopuliSystem.getVoxPopuliInstance();
       private AdminAuthentication adminAuthentication = AdminAuthentication.getAdminAuthenticationInstance();
       
       //admin login
       protected boolean loginAdmin(){
             System.out.println("-------------------------------");
             System.out.println("Enter Your UserName:");
             String userName = input.nextLine();
             System.out.println("Enter Your Password:");
             String password = input.nextLine();
             System.out.println("-------------------------------");
             boolean checkAdmin = voxPopuliSystem.adminCheck(userName, password);
             if(checkAdmin)
                    System.out.println("Welcome:" + userName);
             else{
                   System.out.println("Invalid login credentials.Please Ensure your userName and password before Login:");
                   adminAuthentication.checking();
                   return false;
             }
             return true;             
       }
       
       //authenticate root admin
       protected boolean checkRootAdmin(){
             System.out.println("-------------------------------");
             System.out.println("Enter Your UserName:");
             String userName = input.nextLine();
             System.out.println("Enter Your Password:");
             String password = input.nextLine();
             System.out.println("-------------------------------");
             boolean checkRootAdmin  =  voxPopuliSystem.authorizeAdmin(userName,password);
             if(checkRootAdmin)
                    System.out.println("Welcome:" + userName);
             else{
                   System.out.println("Invalid login credentials.Please Ensure your userName and password before Login:");
                   return false;
             }
             return true;                          
       }
       
       //add admin
       protected void addAdmin(){
            System.out.printf("Enter details for the given fields.%n" +
                  "All names should contain a minimum of 4 and a maximum of 30 characters.%n" +
                  "The name should only contain characters A-Z/a-z.%n" +
                  "The password must start with alphabets, may or may not contain numbers, " +
                  "but must contain at least one special character.%n");
            System.out.println("--------------------");
            
	    String adminName  = getValidatedInput("Enter the Admin Name:");
	    String adminUserName = getValidatedInput("Enter the userName of the Admin:");
	    String password = getValidatedPassword("Enter the password:");
	
	    Admin admin = new Admin(adminName,adminUserName,password);
	    voxPopuliSystem.addAdmin(admin);
	    System.out.println("-------------------------");
      }
                                     
      //Name validation        
      private  String getValidatedInput(String prompt) {
            System.out.println(prompt);
            String inputString = input.nextLine();
            while (!validateName(inputString)) {
                System.out.println("Invalid input.please  correctly " + prompt);
                inputString = input.nextLine();
            }
            return inputString;
       }
       
       //password validation
       private String getValidatedPassword(String prompt){
           System.out.println(prompt);
           String password = input.nextLine();
           while(!validatePassword(password)){
                System.out.println("Invalid password.please  correctly" + prompt);
                password = input.nextLine();
           }
           return password;
      }    
      
      //voterId validation
      private String getValidatedVoterId(String prompt){
           System.out.println(prompt);
           String voterId = input.nextLine();
           while(!validateVoterId(voterId)){
               System.out.println("Invalid Voter Id.Please correctly"+prompt);
               voterId = input.nextLine();
           }
           return voterId;    
      }
      
      //Gender validation
      private String getValidatedGender(String prompt){
            System.out.println(prompt);
            String gender = input.nextLine();
            while(!validateGender(gender)){
                System.out.println("Invalid Gender.Please correctly"+prompt);
                gender = input.nextLine();
           }
           return gender;
      }          
      
      //add area
      protected void addElectoralArea(){
           System.out.println("-----------------------");
           System.out.println("Enter the Electoral Area ID:");
           short electoralAreaId = input.nextShort();
           String areaName = getValidatedInput("Enter the Electoral Area Name:");
           System.out.println("Enter the total Male voters in that Area:");
           int totalMales = input.nextInt();
           System.out.println("Enter the total Female voters in that Area:");
           int totalFemales = input.nextInt();
           System.out.println("Enter the total TransGender voters in that Area:");      
           int totalTransgender = input.nextInt();
           
           ElectoralArea electoralArea = new ElectoralArea(electoralAreaId,areaName,totalMales,totalFemales,totalTransgender);
           voxPopuliSystem.addElectoralArea(electoralArea);
           System.out.println("-----------------------");
      }
      
      //add voter
      protected void addVoter(){
          System.out.println("-----------------------");
          String voterId = getValidatedVoterId("Enter the Voter's voter id:");
          String voterName = getValidatedInput("Enter the Voter's  Name:");
          System.out.println("Enter the Voter's Age:");
          byte voterAge = input.nextByte();
          input.nextLine();
          String gender = getValidatedGender("Enter the gender of the Voter:");
          System.out.println("Enter the Electoral area ID:");
          short electoralAreaId = input.nextShort();
          
          Voter voter = new Voter(voterId, voterName, voterAge, gender, electoralAreaId);
          voxPopuliSystem.addVoter(voter);
          System.out.println("-----------------------");
      }  
      
      //add candidate
      protected void addCandidate(){
          System.out.println("-------------------------");
          String candidateName = getValidatedInput("Enter the Candidate's Name:");
          String gender = getValidatedGender("Enter the gender of the Candidate:");
          System.out.println("Enter the Party Name of the Candidate:");
          String party = input.nextLine();
          System.out.println("Enter the Candidate ID:");
          int candidateId = input.nextInt();
          System.out.println("Enter the Candidate's Age:");
          byte candidateAge = input.nextByte();
          System.out.println("Enter the Electoral area ID:");
          short electoralAreaId = input.nextShort();
          
          Candidate candidate = new Candidate(candidateId,candidateName, candidateAge, gender , party, electoralAreaId);
          voxPopuliSystem.addCandidate(candidate); 
      
      }
      
      //view particular candidate
      protected void viewParticularCandidate(){
          System.out.println("-------------------------");
          System.out.println("Enter the Candidate ID:");
          int candidateId = input.nextInt();
          System.out.println("Enter the Electoral Area ID of the Candidate:");
          short electoralAreaId = input.nextShort();
         
          System.out.println("Enter the Party of the Candidate:");
          System.out.println();
          input.nextLine();
          String party = input.nextLine();
     
          List<Candidate> candidateList = voxPopuliSystem.viewCandidate(candidateId,electoralAreaId,party);
          if(candidateList.isEmpty()){
                 System.out.println("There is NO candidate based on the given Details:");
          }
          else{
                System.out.println("--------------------------------");
                for(Candidate candidate : candidateList){
                       System.out.println(candidate);
                }
                System.out.println("--------------------------------");      
         }        
    }
    
    //view All candidates
    protected void viewCandidates(){
          List<Candidate> candidateList = voxPopuliSystem.viewCandidate();
          if(candidateList.isEmpty()){
                 System.out.println("There are NO candidates Yet :");
          }  
          else{
                 System.out.println("-----------------------------------");
                 for(Candidate candidate : candidateList){
                         System.out.println(candidate);
                 }
                 System.out.println("------------------------------------");
         }
    }  
    
    //find result(winner/loser)
    private void findResult(int electoralAreaId, byte WinnerOrLoser){
         List<String> candidateList = null;  
         if(WinnerOrLoser==1){
             candidateList = voxPopuliSystem.findWinner(electoralAreaId);
         }     
         else{
             candidateList = voxPopuliSystem.findWeaker(electoralAreaId);   
         }    
         System.out.println("-------------------------------------");
         for (String candidateDetail : candidateList) { 
                    System.out.println(candidateDetail);
         }
         System.out.println("-------------------------------------");    
   }      
             
    //find winner
    protected void findWinner() {
         System.out.println("Enter the Electoral Area ID:");
         int electoralAreaId = input.nextInt(); 
         findResult(electoralAreaId,(byte)1);

    }
    
    //find loser
    protected void findWeaker(){
         System.out.println("Enter the Electoral Area ID:");
         int electoralAreaId = input.nextInt();
         findResult(electoralAreaId,(byte)0);
    }     
    
    //find popular party in an area
    protected void findPopularParty(){
         List<String> partyList = voxPopuliSystem.findPopularParties();
         System.out.println("-------------------------------------");
         for(String popularPartyList : partyList){
              System.out.println(popularPartyList);
         }     
         System.out.println("----------------------------------");  
     }        
     
     //who votes for whom
     protected void findAllVotes(){
         List<String> findAllVotes = voxPopuliSystem.whoVotesForWhom();
         System.out.println("-------------------------------------------------------------------------------------------");
         for(String pollList : findAllVotes){
              System.out.println(pollList);
         }
         System.out.println("-------------------------------------------------------------------------------------------");
    }                  
    
    //votes acquired by the candidates of the particular party 
    protected void getVotesOfTheCandidate(){
        System.out.println("Enter The Party Name:");
        String partyName = input.nextLine();
        List<String> candidatesList = voxPopuliSystem.getVotesOfTheCandidate(partyName);
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        for(String candidates : candidatesList){
             System.out.println(candidates);
        }
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
   }   
   
   //find nonVoters
   protected void findNonVoters(){
        List<String> nonVotersList =  voxPopuliSystem.findNonVoters();
        System.out.println("--------------------------------------------------------------");
        for(String nonVoters : nonVotersList){
             System.out.println(nonVoters);
        }
        System.out.println("---------------------------------------------------------------");
  }                 
               
      
        

      
          
      
       
}       
