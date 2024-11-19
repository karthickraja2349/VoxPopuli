package poll;

import model.Candidate;
import model.Voter;
import model.ElectoralArea;
import view.VoxPopuliManagement;
import java.io.PrintWriter;


public class VoxPopuli extends VoxPopuliManagement {

    private PrintWriter writer;
    private VoxPopuliMenu voxPopuliMenu = VoxPopuliMenu.getVoxPopuliMenuInstance();


    public static void main(String[] args) {
        VoxPopuli voxPopuli = new VoxPopuli();
        voxPopuli.initialize();
    }

    public void initialize() {
        voxPopuliMenu.noteMenu();
        writer = new PrintWriter(System.out,true); 
        byte choice;
        while (true) {
            displayMenu();
            
            if (input.hasNextByte()) {
                choice = input.nextByte(); 
            }
             else {
                System.out.println("Invalid input. Please enter a number.");
                input.next(); 
                continue; 
            }

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                     candidateMenu();
                    break;
                case 3:
                     exitMenu();
                    break;
                default:
                    System.out.println("Invalid choice, Please select a valid choice as per the list. You entered: " + choice);
            }
        }
    }

    private void displayMenu() {
        writer.println("Welcome to VoxPopuli!");
        writer.println("-----------------------------------");
        writer.printf("| %-6s | %-22s |\n", "Option", "Main Menu");
        writer.println("|--------+------------------------|");
        writer.printf("| %-6d | %-22s |\n", 1, "Admin");
        writer.printf("| %-6d | %-22s |\n", 2, "Candidate");
        writer.printf("| %-6d | %-22s |\n", 3, "Exit");
        writer.println("-----------------------------------");
        writer.println("Enter Your choice: (Press the Option number)");
        writer.flush(); 
    }
    
  private void adminMenu() {
        byte adminChoice;

        while (true) {
           writer.println("-----------------------------------");
           writer.printf("| %-6s | %-22s |\n", "Option", "Admin Menu");
           writer.println("|--------+------------------------|");
           writer.printf("| %-6d | %-22s |\n", 1, "Login Admin");
           writer.printf("| %-6d | %-22s |\n", 2, "Add Admin");
           writer.printf("| %-6d | %-22s |\n", 3, "Back to Main Menu");
           writer.println("-----------------------------------");
           writer.println("Enter Your choice: (Press the Option number)");
           writer.flush();

           if (input.hasNextByte()) {
              adminChoice = input.nextByte(); 
          } 
          else {
             System.out.println("Invalid input. Please enter a number.");
             input.next(); 
             continue; 
          }

          switch (adminChoice) {
             case 1:
                 input.nextLine();
                 if (loginAdmin())
                    adminSubMenu();
                  break; 
              case 2 :
                   input.nextLine();
                   if(checkRootAdmin())
                       addAdmin();
                   break;        
             case 3:
                 return; 
             default:
                 System.out.println("Invalid choice, Please Select Valid choice: " + adminChoice);
          }  
      }
   }  
    
   private void candidateMenu(){
        byte candidateChoice;

        while (true) {
          writer.println("-----------------------------------");
          writer.printf("| %-6s | %-22s |\n", "Option", "Candidate Menu");
          writer.println("|--------+------------------------|");
          writer.printf("| %-6d | %-22s |\n", 1, "Candidate Login");
          writer.printf("| %-6d | %-22s |\n", 2, "Back to Main Menu");
          writer.println("-----------------------------------");
          writer.println("Enter Your choice: (Press the Option number)");
          writer.flush();   
          
          if (input.hasNextByte()) {
              candidateChoice = input.nextByte(); 
          } 
          else {
             System.out.println("Invalid input. Please enter a number.");
             input.next(); 
             continue; 
          }    
          
          switch(candidateChoice){
             case 1 :
                  //candidateLogin();      
                  break;
              case 2:
                  return ;    
             default :
                  System.out.println("Invalid choice, Please Select Valid choice: " + candidateChoice);;
         }                        
      }
  }
  
  private void exitMenu(){
        byte exitChoice;
        
        while(true){
            writer.println("---------------------------------------------");
            writer.printf("| %-6s | %-32s |\n", "Option", "Exit Menu");
            writer.println("|--------+----------------------------------|");
            writer.printf("| %-6d | %-32s |\n", 1, "Are you Sure?(If it, press 1)");
            writer.printf("| %-6d | %-32s |\n", 2, "Stay Here");
            writer.println("---------------------------------------------");
            writer.println("Enter Your choice: (Press the Option number)");
            writer.flush();
            
            if(input.hasNextByte()){
                 exitChoice = input.nextByte();
            }
            else{
                System.out.println("Invalid input. Please enter a number.");
                input.next();
                continue;
           }
           
           switch(exitChoice){
                case 1:
                    System.out.println("Thank You Visit Again! Be a Citizen , Be Responsible");
                    System.exit(0);
                case 2:
                     return;
           }
       }
   }
   
   private void adminSubMenu(){
         byte adminChoice;
         while(true){
              writer.println("--------------------------------------------------------");
              writer.printf("| %-6s | %-42s |\n", "Option", "ExitMenu");
              writer.println("|--------+--------------------------------------------|");      
              writer.printf("| %-6s | %-42s |\n", 1,"Add Electoral Area");   
              writer.printf("| %-6s | %-42s |\n", 2,"Add Voter");
              writer.printf("| %-6s | %-42s |\n", 3,"Add Candidate");
              writer.printf("| %-6s | %-42s |\n", 4,"View Candidates");
              writer.printf("| %-6s | %-42s |\n", 5,"Find Winner");
              writer.printf("| %-6s | %-42s |\n", 6,"View Particular Candidate");   
              writer.printf("| %-6s | %-42s |\n", 7,"Find Weaker");
              writer.printf("| %-6s | %-42s |\n", 8,"Find Popular Party");
              writer.printf("| %-6s | %-42s |\n", 9,"Who votes for Whom");
              writer.printf("| %-6s | %-42s |\n", 10,"Votes Acquired By Candidate");
              writer.printf("| %-6s | %-42s |\n", 11,"Find Non voters");
              writer.printf("| %-6s | %-42s |\n", 12,"Back to Admin Menu");
              writer.println("--------------------------------------------------------");
              writer.println("Enter Your choice: (Press the Option number)");        
              writer.flush();
                            
              if (input.hasNextByte()) {
                 adminChoice = input.nextByte(); 
              } 
              else {
                 System.out.println("Invalid input. Please enter a number.");
                 input.next(); 
                 continue; 
            }                           
            
            switch(adminChoice){
                 case 1 :
                      input.nextLine();
                      addElectoralArea();
                      break;
                 case 2 :
                      input.nextLine();
                      addVoter();
                      break;
                 case 3 :
                      input.nextLine();
                      addCandidate();
                      break;     
                 case 4 :
                      input.nextLine();
                      viewCandidates();
                      break;
                  case 5 :
                      input.nextLine();
                      findWinner();
                      break;
                  case 6 :
                      input.nextLine();
                      viewParticularCandidate();
                      break;
                  case 7 :
                      input.nextLine();
                      findWeaker();
                      break;
                  case 8 :
                      input.nextLine();
                      findPopularParty();
                      break;    
                   case 9 :
                       input.nextLine();
                       voxPopuliMenu.getLegalNoticeMenu();
                       findAllVotes();
                       break;
                  case 10 :
                      input.nextLine();
                      getVotesOfTheCandidate();
                      break;
                  case 11 :
                      input.nextLine();
                      findNonVoters();
                      break;    
                  case 12 :
                      return ;
                  default :
                      System.out.println("Invalid choice, Please Select Valid choice: " + adminChoice);
            }
         }
     }
     
                                                          
                    
                                          
        
//java -cp .:mysql-connector-j-9.0.0.jar poll.VoxPopuli


}

