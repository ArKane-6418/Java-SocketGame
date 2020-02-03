// Century Skies - Multiplayer Gameplay Panel
// by: Big Nuclear Button Studios
// Build 4
// 23/01/18

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class CSGameplayPanel extends JPanel{
  // Properties
  int intCount; // counter for image array creation
  String strImage; // image identifier
  int intMap1 = 0; // x-coordinate for first half of map
  int intMap2 = 1280; // x-coordinate for second half of map
  BufferedImage image[] = new BufferedImage[10]; // image array
  boolean blnWin = false; // win condition
  boolean blnLose = false; // lose condition
  
  // Declare all aircraft
  FighterBuild3Plus player = new FighterBuild3Plus("camel");
  FighterBuild3Plus player2 = new FighterBuild3Plus("dr1");
  BomberBuild3Plus enemy1 = new BomberBuild3Plus("bomber1");
  BomberBuild3Plus enemy2 = new BomberBuild3Plus("bomber2");
  BomberBuild3Plus enemy3 = new BomberBuild3Plus("bomber3");
  
  // Declare all projectiles
  ProjectileBuild3Plus playproj = new ProjectileBuild3Plus(0, -10, -5, 0);
  ProjectileBuild3Plus playproj2 = new ProjectileBuild3Plus(0, -10, -5, 0);
  ProjectileBuild3Plus proj1 = new ProjectileBuild3Plus(1, 1000, 125, 20);
  ProjectileBuild3Plus proj2 = new ProjectileBuild3Plus(1, 800, 325, 20);
  ProjectileBuild3Plus proj3 = new ProjectileBuild3Plus(1, 1100, 525, 20);
  
  //Methods
  public void paintComponent(Graphics g){
    //Draws scrolling background
    g.drawImage(image[4], intMap1, 0, null);
    g.drawImage(image[5], intMap2, 0, null);
    
    //Temporary - draws the hitbox of the player and enemies
    g.setColor(Color.RED);
    
    //Draws player entities
    if(player.intHP!=0){
      g.drawImage(image[playproj.intIndex], playproj.intX, playproj.intY, null);
      g.drawImage(image[player.intIndex], player.intX, player.intY, null);
    }
    //Draws player2 entities
    if(player2.intHP!=0){
      g.drawImage(image[playproj2.intIndex], playproj2.intX, playproj2.intY, null);
      g.drawImage(image[player2.intIndex], player2.intX, player2.intY, null);
    }
    //Draws enemy 1 entities
    if(enemy1.intHP!=0){
      g.drawImage(image[proj1.intIndex], proj1.intX, proj1.intY, null);
      g.drawImage(image[enemy1.intIndex], enemy1.intX, enemy1.intY, null);
    }
    //Draws enemy2 entities
    if(enemy2.intHP!=0){
      g.drawImage(image[proj2.intIndex], proj2.intX, proj2.intY, null);
      g.drawImage(image[enemy2.intIndex], enemy2.intX, enemy2.intY, null);
    }
    //Draws enemy3 entities
    if(enemy3.intHP!=0){
      g.drawImage(image[proj3.intIndex], proj3.intX, proj3.intY, null);
      g.drawImage(image[enemy3.intIndex], enemy3.intX, enemy3.intY, null);
    }
    
    //Draws win/lose screen
    if(blnWin == true){
      g.drawImage(image[8], 0, 0, null);
    }else if(blnLose == true){
      g.drawImage(image[9], 0, 0, null);
    }
  }
  // Constructors
  public CSGameplayPanel(){
    super();
    // Loads all the images
    for(intCount=0; intCount<10; intCount++){
      try {
        if(intCount==0){
          strImage = "projpl.png";
        }else if(intCount==1){
          strImage = "projen.png";
        }else if(intCount==2){
          strImage = "SopwithCamelBW.png";
        }else if(intCount==3){
          strImage = "GothaGIVBW.png";
        }else if(intCount==4){
          strImage = "GameBackground.png";
        }else if(intCount==5){
          strImage = "GameBackground2.png";
        }else if(intCount==6){
          strImage = "FokkerDr1BW.png";
        }else if(intCount==7){
          strImage = "SPADSXIIIBW.png";
        }else if(intCount == 8){
          strImage = "Win.png";
        }else{
          strImage = "Lose.png";
        }
        image[intCount] = ImageIO.read(new File(strImage));
      } catch (IOException e) {
      }
    }
  }
}