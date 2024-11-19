package model;

public class ElectoralArea{
        private short electoral_area_id;
        private String electoral_area_name;
        private int totalMales;
        private int totalFemales;
        private int totalTransgenders;
        
        
        
        private ElectoralArea(){
        
        }
        
        public ElectoralArea(short electoral_area_id, String electoral_area_name, int totalMales, int totalFemales, int totalTransgenders){
                 this.electoral_area_id = electoral_area_id;
                 this.electoral_area_name = electoral_area_name;
                 this.totalMales = totalMales;
                 this.totalFemales = totalFemales;
                 this.totalTransgenders = totalTransgenders;
               
        }
        
              
        
        public short getElectoralAreaId(){
                return  electoral_area_id;
        }
        
        public String getElectoralAreaName(){
                return  electoral_area_name;
        }
        
        public int getTotalMales(){
                return  totalMales;
        }
        
        public int getTotalFemales(){
               return  totalFemales;
        }
        
        public int getTotalTransgenders(){
              return  totalTransgenders;
        }
        
      
        
        public String toString(){
             StringBuilder electoralArea = new StringBuilder();
             electoralArea.append("------------------------------------------\n");
             electoralArea.append(String.format("| %-20s : %-20s |\n","Electoral Area Name is",getElectoralAreaName()));
             electoralArea.append(String.format("| %-20s : %-20s |\n","Electoral Area Id is",getElectoralAreaId()));
             electoralArea.append(String.format("| %-20s : %-20s |\n","Total Number of  Male  is", getTotalMales()));
             electoralArea.append(String.format("| %-20s : %-20s |\n","Total Number of  Female  is", getTotalFemales()));
             electoralArea.append(String.format("| %-20s : %-20s |\n","Total Number of  TransGender  is", getTotalTransgenders()));
             electoralArea.append("------------------------------------------\n");    
             return electoralArea.toString();
        }
}                                                          
                 
             
