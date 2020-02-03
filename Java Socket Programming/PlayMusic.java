import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class PlayMusic{
  public void music(){       
    AudioPlayer MGP = AudioPlayer.player;
    AudioStream BGM;
    AudioData MD;
    ContinuousAudioDataStream loop = null;
    try{
      InputStream test = new FileInputStream("Five_Card_Shuffle.wav");
      BGM = new AudioStream(test);
      AudioPlayer.player.start(BGM);
    }
    catch(FileNotFoundException e){
      System.out.println(e.toString());
    }
    catch(IOException e){
      System.out.println(e.toString());
    }
    MGP.start(loop);
  }
}