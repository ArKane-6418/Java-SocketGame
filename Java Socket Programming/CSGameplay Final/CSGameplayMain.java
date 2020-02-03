// Century Skies - Multiplayer Gameplay Main Program
// by: Big Nuclear Button Studios
// Build 4
// 23/01/18

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class CSGameplayMain implements ActionListener, KeyListener{
  
  // Before starting gameplay, press either [p] (for server) or [o] (for client)
  // For this to work, please press [p] first to set server before pressing [o] on the second instance to set as a client
  
  // Properties
  int intSSM; // decides the ssm type (server/client)
  JFrame frame; // frames the panel
  CSGameplayPanel panel; // draws the graphics
  JLabel plhealth; // shows player 1 health
  JLabel p2health; // shows player 2 health
  JLabel en1health; // shows enemy 1 health
  JLabel en2health; // shows enemy 2 health
  JLabel en3health; // shows enemy 3 health
  boolean blnW = false; // toggles 'w' key input
  boolean blnA = false; // toggles 'a' key input
  boolean blnS = false; // toggles 's' key input
  boolean blnD = false; // toggles 'd' key input
  boolean blnSpace = false; // toggles spacebar input
  boolean blnStart = false; // starts animation and battle sequence when 'true'
  boolean blnToggleSC = true; // spam control for server/client choice
  String strClient; // string data from client
  String strServer; // string data from server
  String strSend = ""; // string package for player and bomber movement
  Timer timer; // frame rate timer
  Timer projtimer; // projectile rate of fire timer
  SuperSocketMaster ssm; // getting ready for transition
  
  //// ---Synced Data--- ////
  // The server performs all calculations for important features in the game and sends relevant data back to client
  // This ensures that the client and server gameplay are in total sync
  
  public void actionPerformed(ActionEvent evt){
    if(intSSM==1){
      ////// -----Server Mode----- //////
      if(evt.getSource() == timer && blnStart == true){
        // Win/Lose calculation
        if(panel.enemy1.intHP==0 && panel.enemy2.intHP==0 && panel.enemy3.intHP==0){
          panel.blnWin = true;
          ssm.sendText("winner01");
        }else if(panel.player.intHP==0 && panel.player2.intHP==0){
          panel.blnLose = true;
          ssm.sendText("Loser012");
        }
        //// ---Object Calculations--- ////
        if(panel.enemy1.intHP!=0){
          Rectangle2D.Double screen = new Rectangle2D.Double(0, 0, 1280, 720);
          Rectangle2D.Double projen1 = new Rectangle2D.Double(panel.proj1.intX, panel.proj1.intY, 10, 5);
          Rectangle2D.Double enemy1 = new Rectangle2D.Double(panel.enemy1.intX, panel.enemy1.intY, 250, 73);
          Rectangle2D.Double playproj = new Rectangle2D.Double(panel.playproj.intX, panel.playproj.intY, 1, 5);
          Rectangle2D.Double playproj2 = new Rectangle2D.Double(panel.playproj2.intX, panel.playproj2.intY, 1, 5);
          panel.enemy1.intY += panel.enemy1.intDeltaY;
          panel.proj1.intX -= panel.proj1.intDelta;
          //If enemy hits border, enemy changes direction
          if(panel.enemy1.intY >= 630 || panel.enemy1.intY <= 0){
            panel.enemy1.intDeltaY *= -1;
          }
          if(playproj.intersects(enemy1)){
            panel.enemy1.intHP-=1;
            panel.playproj.intY = 800;
          }
          if(playproj2.intersects(enemy1)){
            panel.enemy1.intHP-=1;
            panel.playproj2.intY = 800;
          }
        }
        if(panel.enemy2.intHP!=0){
          Rectangle2D.Double screen = new Rectangle2D.Double(0, 0, 1280, 720);
          Rectangle2D.Double projen2 = new Rectangle2D.Double(panel.proj2.intX, panel.proj2.intY, 10, 5);
          Rectangle2D.Double enemy2 = new Rectangle2D.Double(panel.enemy2.intX, panel.enemy2.intY, 250, 73);
          Rectangle2D.Double playproj = new Rectangle2D.Double(panel.playproj.intX, panel.playproj.intY, 1, 5);
          Rectangle2D.Double playproj2 = new Rectangle2D.Double(panel.playproj2.intX, panel.playproj2.intY, 1, 5);
          panel.enemy2.intY -= panel.enemy2.intDeltaY;
          panel.proj2.intX -= panel.proj2.intDelta;
          //If enemy hits border, enemy changes direction
          if(panel.enemy2.intY >= 630 || panel.enemy2.intY <= 0){
            panel.enemy2.intDeltaY *= -1;
          }
          if(playproj.intersects(enemy2)){
            panel.enemy2.intHP-=1;
            panel.playproj.intY = 800;
          }
          if(playproj2.intersects(enemy2)){
            panel.enemy2.intHP-=1;
            panel.playproj2.intY = 800;
          }
        }
        if(panel.enemy3.intHP!=0){
          Rectangle2D.Double screen = new Rectangle2D.Double(0, 0, 1280, 720);
          Rectangle2D.Double projen3 = new Rectangle2D.Double(panel.proj3.intX, panel.proj3.intY, 10, 5);
          Rectangle2D.Double enemy3 = new Rectangle2D.Double(panel.enemy3.intX, panel.enemy3.intY, 250, 73);
          Rectangle2D.Double playproj = new Rectangle2D.Double(panel.playproj.intX, panel.playproj.intY, 1, 5);
          Rectangle2D.Double playproj2 = new Rectangle2D.Double(panel.playproj2.intX, panel.playproj2.intY, 1, 5);
          panel.enemy3.intY += panel.enemy3.intDeltaY;
          panel.proj3.intX -= panel.proj3.intDelta;
          //If enemy hits border, enemy changes direction
          if(panel.enemy3.intY >= 630 || panel.enemy3.intY <= 0){
            panel.enemy3.intDeltaY *= -1;
          }
          if(playproj.intersects(enemy3)){
            panel.enemy3.intHP-=1;
            panel.playproj.intY = 800;
          }
          if(playproj2.intersects(enemy3)){
            panel.enemy3.intHP-=1;
            panel.playproj2.intY = 800;
          }
        }
        if(panel.player.intHP!=0){
          Rectangle2D.Double screen = new Rectangle2D.Double(0, 0, 1280, 720);
          Rectangle2D.Double hitboxFront = new Rectangle2D.Double(panel.player.intX, panel.player.intY, 150, 75);
          Rectangle2D.Double playproj = new Rectangle2D.Double(panel.playproj.intX, panel.playproj.intY, 1, 5);
          Rectangle2D.Double projen1 = new Rectangle2D.Double(panel.proj1.intX, panel.proj1.intY, 10, 5);
          Rectangle2D.Double projen2 = new Rectangle2D.Double(panel.proj2.intX, panel.proj2.intY, 10, 5);
          Rectangle2D.Double projen3 = new Rectangle2D.Double(panel.proj3.intX, panel.proj3.intY, 10, 5);
          if(panel.playproj.intX < 1280){
            panel.playproj.intX += panel.playproj.intDelta;
          }
          if(hitboxFront.intersects(projen1) && panel.enemy1.intHP!=0){
            panel.player.intHP-=1;
            panel.proj1.intY = 800;
          }else if(hitboxFront.intersects(projen2) && panel.enemy2.intHP!=0){
            panel.player.intHP-=1;
            panel.proj2.intY = 800;
          }else if(hitboxFront.intersects(projen3) && panel.enemy3.intHP!=0){
            panel.player.intHP-=1;
            panel.proj3.intY = 800;
          }
        }
        if(panel.player2.intHP!=0){
          Rectangle2D.Double screen = new Rectangle2D.Double(0, 0, 1280, 720);
          Rectangle2D.Double hitboxFront = new Rectangle2D.Double(panel.player2.intX, panel.player2.intY, 150, 75);
          Rectangle2D.Double projen1 = new Rectangle2D.Double(panel.proj1.intX, panel.proj1.intY, 10, 5);
          Rectangle2D.Double projen2 = new Rectangle2D.Double(panel.proj2.intX, panel.proj2.intY, 10, 5);
          Rectangle2D.Double projen3 = new Rectangle2D.Double(panel.proj3.intX, panel.proj3.intY, 10, 5);
          if(!screen.contains(hitboxFront.getBounds2D())){
            if(blnW){
              panel.player2.intY+=panel.player2.intDelta;
            }
            if(blnA){
              panel.player2.intX+=panel.player2.intDelta;
            } 
            if(blnS){
              panel.player2.intY-=panel.player2.intDelta;
            } 
            if(blnD){
              panel.player2.intX-=panel.player2.intDelta;
            }
          }
          if(panel.playproj2.intX < 1280){
            panel.playproj2.intX += panel.playproj2.intDelta;
          }
          if(hitboxFront.intersects(projen1) && panel.enemy1.intHP!=0){
            panel.player2.intHP-=1;
            panel.proj1.intY = 800;
          }else if(hitboxFront.intersects(projen2) && panel.enemy2.intHP!=0){
            panel.player2.intHP-=1;
            panel.proj2.intY = 800;
          }else if(hitboxFront.intersects(projen3) && panel.enemy3.intHP!=0){
            panel.player2.intHP-=1;
            panel.proj3.intY = 800;
          }
        }
        //// ------ ////
        //// ---Server sending Data to Client--- ////
        strSend = "enemy1hp"+panel.enemy1.intHP;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "enemy2hp"+panel.enemy2.intHP;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "enemy3hp"+panel.enemy3.intHP;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "player1h"+panel.player.intHP;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "player2h"+panel.player2.intHP;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projp1x"+panel.playproj.intX;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projp1y"+panel.playproj.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projp2x"+panel.playproj2.intX;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projp2y"+panel.playproj2.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        // Basic bomber movement *Note, server exclusive function
        // How Server sends Data to Client:
        // packages the data (strSend) in format enemy-coordinate, then sends package
        strSend = "enemy1y"+panel.enemy1.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "enemy2y"+panel.enemy2.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "enemy3y"+panel.enemy3.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projc1x"+panel.proj1.intX;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projc1y"+panel.proj1.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projc2x"+panel.proj2.intX;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projc2y"+panel.proj2.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projc3x"+panel.proj3.intX;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        strSend = "projc3y"+panel.proj3.intY;
        if(!strSend.equals(null)){
          ssm.sendText(strSend);
        }
        if(blnSpace){
          //Projectile speed is changed (from 0)
          panel.playproj.intDelta = 20;
          //Player projectile begins from front of player's plane
          panel.playproj.intX = panel.player.intX + 100;
          panel.playproj.intY = panel.player.intY + 35;
          ssm.sendText("32");
        }
        // Basic plane movement controls
        // How Server sends Data to Client:
        // packages the data (strSend) in format player-keypress-coordinate, then sends package
        if(blnW){
          panel.player.intY-=panel.player.intDelta;
          strSend = "player1w"+panel.player.intY;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        }
        if(blnA){
          panel.player.intX-=panel.player.intDelta;
          strSend = "player1a"+panel.player.intX;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        } 
        if(blnS){
          panel.player.intY+=panel.player.intDelta;
          strSend = "player1s"+panel.player.intY;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        } 
        if(blnD){
          panel.player.intX+=panel.player.intDelta;
          strSend = "player1d"+panel.player.intX;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        }
        panel.repaint();
        //// ------ ////
      }
      if(evt.getSource() == projtimer && blnStart == true){
        if(projtimer.getDelay() == 1000){
          // Sets the projectile bullets' X and Y coordinates to be proportional to the enemy's movement
          // Basic plane movement controls
          // How Server sends Data to Client:
          // packages the data (strSend) in format projectile-axis-coordinate, then sends package
          panel.proj1.intX = panel.enemy1.intX - 20;
          panel.proj1.intY = panel.enemy1.intY + 50;
          panel.proj2.intX = panel.enemy2.intX - 20;
          panel.proj2.intY = panel.enemy2.intY + 50;
          panel.proj3.intX = panel.enemy3.intX - 20;
          panel.proj3.intY = panel.enemy3.intY + 50;
        }
      }
      if(evt.getSource() == ssm){
        //// ---Server Receiving Data from Client--- ////
        strClient = ssm.readText();
        // server/client connection check
        if(strClient.equals("hello")){
          blnStart = true;
        }
        // receiving shooting data
        if(strClient.equals("32")){
          //Projectile speed is changed (from 0)
          panel.playproj2.intDelta = 20;
          //Player projectile begins from front of player's plane
          panel.playproj2.intX = panel.player2.intX + 100;
          panel.playproj2.intY = panel.player2.intY + 35;
        }
        // How Server Receives Plane Movement Data from Client:
        // 1. Checks validity of package (strClient) by checking length of player-keypress
        // 2. Grabs key press (w,a,s,d)
        // 3. Grabs Integer from the package and syncs data
        if(strClient.length() > 7){
          if(strClient.substring(7,8).equals("d")){
            panel.player2.intX = Integer.parseInt(strClient.substring(8,strClient.length()));
          }
          if(strClient.substring(7,8).equals("a")){
            panel.player2.intX = Integer.parseInt(strClient.substring(8,strClient.length()));
          }
          if(strClient.substring(7,8).equals("s")){
            panel.player2.intY = Integer.parseInt(strClient.substring(8,strClient.length()));
          }
          if(strClient.substring(7,8).equals("w")){
            panel.player2.intY = Integer.parseInt(strClient.substring(8,strClient.length()));
          }
          //// ------ ////
        }
      }
    }else if(intSSM==2){
      ////// -----Client Mode----- //////
      if(evt.getSource() == timer && blnStart == true){
        //// ---Client sending Data to Server--- ////
        if(blnSpace){
          ssm.sendText("32");
        }
        // Basic plane movement controls
        // How Client sends Data to Server:
        // packages the data (strSend) in format player-keypress-coordinate, then sends package
        Rectangle2D.Double screen = new Rectangle2D.Double(0, 0, 1280, 720);
        Rectangle2D.Double hitboxFront = new Rectangle2D.Double(panel.player2.intX, panel.player2.intY, 150, 75);
        if(blnW){
          panel.player2.intY-=panel.player2.intDelta;
          strSend = "player2w"+panel.player2.intY;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        }
        if(blnA){
          panel.player2.intX-=panel.player2.intDelta;
          strSend = "player2a"+panel.player2.intX;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        } 
        if(blnS){
          panel.player2.intY+=panel.player2.intDelta;
          strSend = "player2s"+panel.player2.intY;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        } 
        if(blnD){
          panel.player2.intX+=panel.player2.intDelta;
          strSend = "player2d"+panel.player2.intX;
          if(!strSend.equals(null)){
            ssm.sendText(strSend);
          }
        }
        panel.repaint();
      }
      //// ------ ////
      if(evt.getSource() == ssm){
        //// ---Client Receiving Data from Server--- ////
        strServer = ssm.readText();
        // 3. Grabs Integer from the package and syncs data
        // How Client Receives Plane Movement Data from Server:
        // 1. Checks validity of package (strClient) by checking length of player-keypress
        // 2. Grabs key press type (w,a,s,d)
        // 3. Grabs Integer from the package and syncs data
        // How Client Receives Other Data from Server:
        // 1. Checks validity of package (strClient) by checking length of identifier
        // 2. Grabs integer data
        // 3. Syncs data with local data
        if(strServer.length() >7){
          if(strServer.substring(7,8).equals("d")){
            panel.player.intX = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(7,8).equals("a")){
            panel.player.intX = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(7,8).equals("s")){
            panel.player.intY = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(7,8).equals("w")){
            panel.player.intY = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(0,7).equals("enemy1y")){
            panel.enemy1.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("enemy2y")){
            panel.enemy2.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("enemy3y")){
            panel.enemy3.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projc1x")){
            panel.proj1.intX = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projc1y")){
            panel.proj1.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projc2x")){
            panel.proj2.intX = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projc2y")){
            panel.proj2.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projc3x")){
            panel.proj3.intX = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projc3y")){
            panel.proj3.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,8).equals("enemy1hp")){
            panel.enemy1.intHP = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(0,8).equals("enemy2hp")){
            panel.enemy2.intHP = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(0,8).equals("enemy3hp")){
            panel.enemy3.intHP = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(0,8).equals("player1h")){
            panel.player.intHP = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(0,8).equals("player2h")){
            panel.player2.intHP = Integer.parseInt(strServer.substring(8,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projp1x")){
            panel.playproj.intX = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projp1y")){
            panel.playproj.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projp2x")){
            panel.playproj2.intX = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.substring(0,7).equals("projp2y")){
            panel.playproj2.intY = Integer.parseInt(strServer.substring(7,strServer.length()));
          }
          if(strServer.equals("winner01")){
            panel.blnWin = true;
          }
          if(strServer.equals("Loser012")){
            panel.blnLose = true;
          }
          //// ------ ////
        }
      }
    }
    
    ////// ---------- //////
    
    if(evt.getSource()==timer){
      //// ---Local calculations--- ////
      
      // Map Scrolling
      panel.intMap1-=5;
      panel.intMap2-=5;
      if(panel.intMap1==-1280){
        panel.intMap1 = 1280;
      }
      if(panel.intMap2==-1280){
        panel.intMap2 = 1280;
      }
      
      // Update all plane statistics
      en1health.setText("ENEMY 1 HP: "+panel.enemy1.intHP);
      en2health.setText("ENEMY 2 HP: "+panel.enemy2.intHP);
      en3health.setText("ENEMY 3 HP: "+panel.enemy3.intHP);
      plhealth.setText("PLAYER 1 HP: "+panel.player.intHP);
      p2health.setText("PLAYER 2 HP: "+panel.player2.intHP);
    }
    
    //// ------ ////
    
  }
  
  public void keyReleased(KeyEvent evt){
    if(evt.getKeyCode() == 32){
      blnSpace = false;
    }if(evt.getKeyChar() == 'w'){
      blnW = false;
    }if(evt.getKeyChar() == 'a'){
      blnA = false;
    }if(evt.getKeyChar() == 's'){
      blnS = false;
    }if(evt.getKeyChar() == 'd'){
      blnD = false;
    }
  }
  public void keyPressed(KeyEvent evt){
    if(evt.getKeyCode() == 32){
      blnSpace = true;
    }if(evt.getKeyChar() == 'w'){
      blnW = true;
    }if(evt.getKeyChar() == 'a'){
      blnA = true;
    }if(evt.getKeyChar() == 's'){
      blnS = true;
    }if(evt.getKeyChar() == 'd'){
      blnD = true;
    }
  }
  public void keyTyped(KeyEvent evt){
    if(evt.getKeyChar() == 'p' && blnToggleSC== true){
      blnToggleSC = false;
      ssm = new SuperSocketMaster(6112,this);
      ssm.connect();
      System.out.println(ssm.getMyAddress());
      intSSM = 1;
    }
    if(evt.getKeyChar() == 'o' && blnToggleSC== true){
      blnToggleSC = false;
      ssm = new SuperSocketMaster("127.0.0.1",6112,this);
      ssm.connect();
      intSSM = 2;
      blnStart=true;
      ssm.sendText("hello");
    }
  }
  
  public CSGameplayMain(){
    
    panel = new CSGameplayPanel();
    panel.setPreferredSize(new Dimension(1280,720));
    panel.setLayout(null);
    
    panel.player.readCSV();
    panel.player2.readCSV();
    panel.enemy1.readCSV();
    panel.enemy2.readCSV();
    panel.enemy3.readCSV();
    
    plhealth = new JLabel("PLAYER 1 HP: "+panel.player.intHP);
    plhealth.setSize(200, 100);
    plhealth.setLocation(10, 450);
    plhealth.setForeground(Color.WHITE);
    panel.add(plhealth);
    
    p2health = new JLabel("PLAYER 2 HP: "+panel.player2.intHP);
    p2health.setSize(200, 100);
    p2health.setLocation(10, 500);
    p2health.setForeground(Color.WHITE);
    panel.add(p2health);
    
    en1health = new JLabel("ENEMY 1 HP: "+panel.enemy1.intHP);
    en1health.setSize(200, 100);
    en1health.setLocation(10, 550);
    en1health.setForeground(Color.WHITE);
    panel.add(en1health);
    
    en2health = new JLabel("ENEMY 2 HP: "+panel.enemy2.intHP);
    en2health.setSize(200, 100);
    en2health.setLocation(10, 600);
    en2health.setForeground(Color.WHITE);
    panel.add(en2health);
    
    en3health = new JLabel("ENEMY 3 HP: "+panel.enemy3.intHP);
    en3health.setSize(200, 100);
    en3health.setLocation(10, 650);
    en3health.setForeground(Color.WHITE);
    panel.add(en3health);
    
    frame = new JFrame("CENTURY SKIES - Multiplayer Gameplay - press [p] for server and [o] for client");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(panel);
    frame.pack();
    frame.setVisible(true);
    frame.addKeyListener(this);
    
    timer = new Timer(1000/60, this);
    timer.start();
    
    projtimer = new Timer(1000, this);
    projtimer.start();
  }
  
  public static void main(String[]args){
    new CSGameplayMain();
  }
}