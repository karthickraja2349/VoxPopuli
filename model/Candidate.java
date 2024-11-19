package model;

public class Candidate{
       private int candidate_id;
       private String candidate_name;
       private byte candidate_age;
       private String party;
       private String gender;
       private short electoral_area_id;
       

       
       
       public Candidate(int candidate_id, String candidate_name, byte candidate_age,String gender, String party, short electoral_area_id){
                  this.candidate_id = candidate_id;
                  this.candidate_name = candidate_name;
                  this.candidate_age = candidate_age;
                  this.party = party;
                  this.gender = gender;
                  this.electoral_area_id = electoral_area_id;
       }
       

       
       private Candidate(){
       
       }
       
       public int getCandidateId(){
              return candidate_id;
       }
       
       public String getCandidateName(){
             return candidate_name;
       }
       
       public byte getCandidateAge(){
            return candidate_age;
       }     
       
       public String getGender(){
            return gender;
       }     
       
       public String getCandidateParty(){
             return party;
       }
       
       public short getElectoralAreaId(){
            return electoral_area_id;
       }
       
       public String toString(){
            StringBuilder candidate = new StringBuilder();
            candidate.append("--------------------------------------------------------\n");
            candidate.append(String.format("| %-30s : %-20s |\n","Candidate Name is",getCandidateName()));
            candidate.append(String.format("| %-30s : %-20s |\n","Candidate Id is",getCandidateId()));
            candidate.append(String.format("| %-30s : %-20s |\n","Candidate Age is", getCandidateAge()));
            candidate.append(String.format("| %-30s : %-20s |\n","Candidate Gender is", getGender()));
            candidate.append(String.format("| %-30s : %-20s |\n","Candidate's Party is", getCandidateParty()));
            candidate.append(String.format("| %-30s : %-20s |\n","Candidate's Area Id is", getElectoralAreaId()));
            candidate.append("---------------------------------------------------------\n");
            return candidate.toString();
      }
}            
                      
       
       
