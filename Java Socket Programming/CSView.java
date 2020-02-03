// Century Skies - Model
// Big Nuclear Button Studios
// Build 1
// 16/01/18
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import sun.audio.*;

public class CSView implements ActionListener{
  //Properties
  JFrame frame;
  CSViewPanel panel;
  //Buttons - play, server, client, credits, quit, back
  //Play menu - connect to server, connect to client, start game
  JButton play;
  JButton connserver;
  JButton connclient;
  JButton entergame;
  JButton startgame;
  JLabel labeluser;
  JLabel labelip;
  JLabel labelmessage;
  JTextField username;
  JTextField ipaddress;
  String strUsername;
  String strIP = "127.0.0.1";
  boolean blnServer;
  boolean blnClient;
  boolean blnStart = false;
  JButton instructions;
  JButton credits;
  JButton quit;
  JButton back;
  Timer timer;
  //Chat menu - activated after pressing play
  JTextField chat;
  JTextArea chatwindow;
  JScrollPane scroll;
  Timer countdown;
  int intCountdownSecs = 20;
  SuperSocketMaster ssm;
  
  //Methods
  public void actionPerformed(ActionEvent evt){
    if(evt.getSource() == play){
      //--> Goes to play menu
      //Invisible buttons: play, instructions, credits, quit
      //Visible buttons: connserver, connclient, back
      panel.blnPlay = true;
      panel.blnMenu = false;
      play.setVisible(false);
      connserver.setVisible(true);
      connclient.setVisible(true);
      instructions.setVisible(false);
      credits.setVisible(false);
      quit.setVisible(false);
      back.setVisible(true);
    }else if(evt.getSource() == connclient){
      //User can enter a username, IP address, and start game button appears
      blnServer = false;
      blnClient = true;
      username.setVisible(true);
      ipaddress.setVisible(true);
      labeluser.setVisible(true);
      labelip.setVisible(true);
      labelmessage.setVisible(false);
      entergame.setVisible(true);
      ssm = new SuperSocketMaster(strIP, 6112, this);
    }else if(evt.getSource() == connserver){
      //User cannot enter username or IP address, but start game button appears
      blnServer = true;
      blnClient = false;
      username.setVisible(false);
      ipaddress.setVisible(false);
      labeluser.setVisible(false);
      labelip.setVisible(false);
      labelmessage.setVisible(false);
      entergame.setVisible(true);
      ssm = new SuperSocketMaster(6112, this);
    }else if(evt.getSource() == instructions){
      //--> Goes to instructions menu
      //Invisible buttons: play, instructions, credits, quit
      //Visible buttons: back
      panel.blnMenu = false;
      panel.blnInstructions = true;
      play.setVisible(false);
      instructions.setVisible(false);
      credits.setVisible(false);
      quit.setVisible(false);
      back.setVisible(true);
    }else if(evt.getSource() == credits){
      //--> Goes to credits menu
      //Invisible buttons: play, instructions, credits, quit
      //Visible buttons: back
      panel.blnMenu = false;
      panel.blnCredits = true;
      play.setVisible(false);
      instructions.setVisible(false);
      credits.setVisible(false);
      quit.setVisible(false);
      back.setVisible(true);
    }else if(evt.getSource() == quit){
      System.exit(0);
    }else if(evt.getSource() == back){
      //Goes back to main menu - everything except menu buttons and menu are set true
      panel.blnMenu = true;
      panel.blnPlay = false;
      panel.blnInstructions = false;
      panel.blnCredits = false;
      play.setVisible(true);
      instructions.setVisible(true);
      credits.setVisible(true);
      quit.setVisible(true);
      connserver.setVisible(false);
      connclient.setVisible(false);
      username.setVisible(false);
      ipaddress.setVisible(false);
      labeluser.setVisible(false);
      labelip.setVisible(false);
      labelmessage.setVisible(false);
      entergame.setVisible(false);
      back.setVisible(false);
    }else if(evt.getSource() == entergame){
      if(blnServer == true){
        strUsername = "Server";
      }else{
        strUsername = username.getText();
        strIP = ipaddress.getText();
      }
      if((strUsername.equals("") || strIP.equals(""))){
        labelmessage.setText("Please make sure you have entered in all the fields.");
        labelmessage.setVisible(true);
      }else{
        ssm.connect();
        if(blnClient == true){
          ssm.sendText("hello");
        }
        connserver.setVisible(false);
        connclient.setVisible(false);
        entergame.setVisible(false);
        username.setVisible(false);
        ipaddress.setVisible(false);
        labeluser.setVisible(false);
        labelip.setVisible(false);
        back.setEnabled(false);
        chat = new JTextField();
        chat.setSize(800, 100);
        chat.setLocation(240, 465);
        chat.setFont(new Font("DIALOG", Font.PLAIN, 20));
        chat.addActionListener(this);
        panel.add(chat);
        startgame = new JButton("START GAME");
        startgame.setSize(200, 100);
        startgame.setLocation(840, 600);
        startgame.setFont(new Font("DIALOG", Font.BOLD, 20));
        startgame.addActionListener(this);
        panel.add(startgame);
        chatwindow = new JTextArea();
        chatwindow.setFont(new Font("DIALOG", Font.PLAIN, 20));
        chatwindow.append("The IP address for this server is: "+ssm.getMyAddress() + "\n");
        scroll = new JScrollPane(chatwindow);
        scroll.setSize(800, 300);
        scroll.setLocation(240, 160);
        panel.add(scroll);
      }
    }else if(evt.getSource() == chat){
      String strText = chat.getText();
      strText = strUsername+": "+strText;
      ssm.sendText(strText);
      chatwindow.append(strText + "\n");
      chat.setText("");
    }else if(evt.getSource() == ssm){
      chatwindow.append(ssm.readText() + "\n");
      JScrollBar vertical = scroll.getVerticalScrollBar();
      vertical.setValue(vertical.getMaximum());
    }else if(evt.getSource() == startgame){
      labelmessage.setText("Please go to the CSGameplayMain.java file.");
      labelmessage.setVisible(true);
    }else if(evt.getSource() == timer){
      panel.repaint();
    }
  }
  //Constructor
  public CSView(){
    PlayMusic playmusic = new PlayMusic();
    playmusic.music();
    
    panel = new CSViewPanel();
    panel.setPreferredSize(new Dimension(1280, 720));
    panel.setLayout(null);
    
    play = new JButton("PLAY");
    play.setSize(400, 75);
    play.setLocation(800, 180);
    play.setFocusPainted(false);
    play.setFont(new Font("DIALOG", Font.BOLD, 24));
    play.addActionListener(this);
    panel.add(play);
    
    connserver = new JButton("Server");
    connserver.setSize(565, 75);
    connserver.setLocation(50, 150);
    connserver.setFont(new Font("DIALOG", Font.BOLD, 24));
    connserver.setVisible(false);
    connserver.addActionListener(this);
    panel.add(connserver);
    
    connclient = new JButton("Client");
    connclient.setSize(565, 75);
    connclient.setLocation(665, 150);
    connclient.setFont(new Font("DIALOG", Font.BOLD, 24));
    connclient.addActionListener(this);
    connclient.setVisible(false);
    panel.add(connclient);
    
    entergame = new JButton("ENTER GAME");
    entergame.setSize(1180, 75);
    entergame.setLocation(50, 500);
    entergame.setFocusPainted(false);
    entergame.setFont(new Font("DIALOG", Font.BOLD, 24));
    entergame.addActionListener(this);
    entergame.setVisible(false);
    panel.add(entergame);
    
    labeluser = new JLabel("USERNAME:");
    labeluser.setSize(190, 100);
    labeluser.setLocation(50, 250);
    labeluser.setFont(new Font("DIALOG", Font.BOLD, 24));
    labeluser.setVisible(false);
    panel.add(labeluser);
    
    labelip = new JLabel("IP ADDRESS:");
    labelip.setSize(190, 100);
    labelip.setLocation(50, 375);
    labelip.setFont(new Font("DIALOG", Font.BOLD, 24));
    labelip.setVisible(false);
    panel.add(labelip);
    
    username = new JTextField();
    username.setSize(980, 100);
    username.setLocation(240, 250);
    username.setFont(new Font("DIALOG", Font.PLAIN, 24));
    username.setVisible(false);
    panel.add(username);
    
    ipaddress = new JTextField();
    ipaddress.setSize(980, 100);
    ipaddress.setLocation(240, 375);
    ipaddress.setFont(new Font("DIALOG", Font.PLAIN, 24));
    ipaddress.setVisible(false);
    panel.add(ipaddress);
    
    labelmessage = new JLabel();
    labelmessage.setSize(600, 50);
    labelmessage.setLocation(240, 620);
    labelmessage.setFont(new Font("DIALOG", Font.BOLD, 24));
    labelmessage.setBackground(Color.BLACK);
    labelmessage.setForeground(Color.WHITE);
    labelmessage.setVisible(false);
    panel.add(labelmessage);
    
    instructions = new JButton("INSTRUCTIONS");
    instructions.setSize(400, 75);
    instructions.setLocation(800, 275);
    instructions.setFocusPainted(false);
    instructions.setFont(new Font("DIALOG", Font.BOLD, 24));
    instructions.addActionListener(this);
    panel.add(instructions);
    
    credits = new JButton("CREDITS");
    credits.setSize(400, 75);
    credits.setLocation(800, 370);
    credits.setFocusPainted(false);
    credits.setFont(new Font("DIALOG", Font.BOLD, 24));
    credits.addActionListener(this);
    panel.add(credits);
    
    quit = new JButton("QUIT");
    quit.setSize(400, 75);
    quit.setLocation(800, 465);
    quit.setFocusPainted(false);
    quit.setFont(new Font("DIALOG", Font.BOLD, 24));
    quit.addActionListener(this);
    panel.add(quit);
    
    back = new JButton("BACK");
    back.setSize(150, 50);
    back.setLocation(50, 620);
    back.setFocusPainted(false);
    back.setFont(new Font("DIALOG", Font.BOLD, 24));
    back.addActionListener(this);
    back.setVisible(false);
    panel.add(back);
    
    frame = new JFrame("CENTURY SKIES");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setContentPane(panel);
    frame.pack();
    frame.setVisible(true);
    
    timer = new Timer(1000/60, this);
    timer.start();
  }
  
  //Main method
  public static void main(String[] args){
    new CSView();
  }
}