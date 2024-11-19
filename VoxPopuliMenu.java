package poll;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.IOException;

public class VoxPopuliMenu{
      
      private static VoxPopuliMenu voxPopuliMenu;

      private VoxPopuliMenu(){
      
      }
      
      public static VoxPopuliMenu getVoxPopuliMenuInstance(){
            if(voxPopuliMenu==null)
                voxPopuliMenu = new VoxPopuliMenu();
            return voxPopuliMenu;
      }
      
    public void noteMenu(){
           readFromFile("/home/zs-gsch24/poll/VoxPopuli.txt");      
    }
     
    public void getLegalNoticeMenu(){
          readFromFile("/home/zs-gsch24/poll/LegalNote.txt");
    }
    
    private void readFromFile(String FileName){
            FileInputStream fileInputStream = null;
            BufferedInputStream bufferedInputStream = null;
            
            try{
                fileInputStream = new FileInputStream(FileName);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                
                int line = 0;
                while((line=bufferedInputStream.read())!=-1){
                     char characterAtLine = (char)line;
                     System.out.print(characterAtLine);
                }
          }
          catch(IOException ioException){
                System.err.println("The Content cannot be open at that time !, Sorry for the Inconvenience");
          }
          finally{
                try{
                   if (bufferedInputStream != null) {
                       bufferedInputStream.close();
                  }
               }
               catch(IOException ioException){
                   ioException.getMessage();
             }   
        }     
    }
       
    
         
     
}                    
                                 
      
