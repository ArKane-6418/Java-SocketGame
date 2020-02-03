// Century Skies - View
// Big Nuclear Button Studios
// Build 1
// 16/01/2018

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.*;

public class CSViewPanel extends JPanel{
  //Properties
  BufferedImage menubg = null;
  BufferedImage gameconn = null;
  BufferedImage credits = null;
  BufferedImage title = null;
  BufferedImage instructions = null;
  //Boolean variables for menu button selection
  boolean blnMenu = true;
  boolean blnPlay = false;
  boolean blnInstructions = false;
  boolean blnCredits = false;
  
  //Methods
  public void paintComponent(Graphics g){
    try{
      menubg = ImageIO.read(new File("Pictures/GameBackground.png"));
      gameconn = ImageIO.read(new File("Pictures/GameConnections.png"));
      credits = ImageIO.read(new File("Pictures/Credits.png"));
      title = ImageIO.read(new File("Pictures/Title.png"));
      instructions = ImageIO.read(new File("Pictures/Instructions.png"));
    }catch(IOException e){
    }
    if(blnMenu == true){
      g.drawImage(menubg, 0, 0, null);
      g.drawImage(title, 50, 0, null);
    }else if(blnInstructions == true){
      g.drawImage(instructions, 0, 0, null);
    }else if(blnPlay == true){
      g.drawImage(gameconn, 0, 0, null);
    }else if(blnCredits == true){
      g.drawImage(credits, 0, 0, null);
    }
  }
  
  //Main method
  public CSViewPanel(){
    super();
  }
}