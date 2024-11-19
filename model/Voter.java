package model;

public class Voter{
      private String voter_id;
      private String voter_name;
      private byte voter_age;
      private String gender;
      private short electoral_area_id;
      
      private Voter(){
      
      }
      
      public Voter(String voter_id, String voter_name , byte voter_age , String gender, short electoral_area_id){
            this.voter_id = voter_id;
            this.voter_name = voter_name;
            this.voter_age = voter_age;
            this.gender = gender;
            this.electoral_area_id = electoral_area_id;
      }
      
      public String getVoterId(){
            return voter_id;
      }
      
      public String getVoterName(){
            return voter_name;
      }
      
      public byte getVoterAge(){
           return voter_age;
      }
      
      public String getGender(){
           return gender;
      }
      
      public short getElectoralAreaId(){
           return   electoral_area_id;
      }
      
      public String toString(){
           StringBuilder voter = new StringBuilder();
           voter.append("------------------------------------------\n");
           voter.append(String.format("| %-20s : %-20s |\n","Voter Name is",getVoterName()));
           voter.append(String.format("| %-20s : %-20s |\n","Voter Id is",getVoterId()));
           voter.append(String.format("| %-20s : %-20s |\n","Voter Age is", getVoterAge()));
           voter.append(String.format("| %-20s : %-20s |\n","Voter Gender is", getGender()));
           voter.append(String.format("| %-20s : %-20s |\n","Voter's Electoral Area ID is", getElectoralAreaId()));
           voter.append("------------------------------------------\n");
           return voter.toString();
      }
}                                                     
