package poll;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.Scanner;


public class AdminAuthentication {

  
  private static AdminAuthentication adminAuthentication = null;
  
  
  private AdminAuthentication(){
  
  }
  
  public static AdminAuthentication getAdminAuthenticationInstance(){
       if(adminAuthentication == null){
           adminAuthentication = new AdminAuthentication();
       }
       return adminAuthentication;
  }     
          

  
  public void checking() {
         System.out.println("Unauthorized access!");
          playSound("/home/zs-gsch24/poll/BEEP_Censorship 2 (ID 1311)_BSB.wav");
  
  }


   private  void playSound(String filename) {
    try {
        File file = new File(filename);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        clip.drain();
        clip.close();
    } 
    catch (Exception e){
        System.out.println("Error playing sound: " + e.getMessage());
    }
  }
}
