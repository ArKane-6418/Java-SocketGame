// Century Skies - Fighter Subclass
// by: Big Nuclear Button Studios
// Build 3+
// 10/01/21

import java.io.*;

public class FighterBuild3Plus extends PlaneBuild3Plus{
  // Properties
  int intIndex;
  int intX;
  int intY;
  int intHP;
  int intDelta;
  
  // Methods
  public void readCSV(){
    // Loads .csv files
    int intCount;
    int intStats[] = new int[6];
    String strStatsArray[] = new String[6];
    FileReader thefile;
    BufferedReader thefiledata;
    
    try{
      String comma = ",";
      thefile = new FileReader("planes.csv");
      thefiledata = new BufferedReader(thefile);
      String strStats;
      strStats = thefiledata.readLine();
      while(strStats!=null){
        strStatsArray = strStats.split(comma);
        if(strStatsArray[0].equals(strReference)){
          for(intCount=1; intCount<6; intCount++){
            intStats[intCount] = Integer.parseInt(strStatsArray[intCount]);
          }
        }
        strStats = thefiledata.readLine();
      }
      thefiledata.close();
      thefile.close();
    } catch (IOException e) {
    }
    intIndex = intStats[1];
    intX = intStats[2];
    intY = intStats[3];
    intHP = intStats[4];
    intDelta = intStats[5];
  }
  // Constructor
  public FighterBuild3Plus(String ref){
    super(ref);
  }
  
}