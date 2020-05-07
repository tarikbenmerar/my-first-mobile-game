
/*
 * Level.java
 *
 * Created on 8 octobre 2007, 17:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Sniper Ninja
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.*;
public class Level {
    private int NumWin;
    private int NumMax;
    private int[][] Level;
    private int NbrLigne,NbrColonne; 
    private int InitPosx,InitPosy;
    private DataInputStream LevelC;
    public class Type{
        final static int   Vide=1;
        final static int    Box=2;
        final static int    Dot=3;
        final static int BoxDot=4;
        final static int  Block=5;
    }
    void DecWin(){NumWin--;}
    void IncWin(){NumWin++;}
    /** Créer une instance de Level */
    public Level(int NumeroNiveau)  {
    //initialisation de NumWin et NumMax
    NumWin=0;
    NumMax=0;
//----création du niveau----//
    try{
    //ouverture du fichier//
    InputStream in = new Object().getClass().getResourceAsStream("/Level/"+NumeroNiveau+".lvl");
    LevelC=new DataInputStream(in);
    LevelC.skip(3);
    //-------------------//
    NbrLigne=LevelC.read()-48;
    NbrLigne=NbrLigne*10+LevelC.read()-48;
    NbrColonne=LevelC.read()-48;
    NbrColonne=NbrColonne*10+LevelC.read()-48;
    InitPosx=LevelC.read()-48;
    InitPosx=InitPosx*10+LevelC.read()-48;
    InitPosy=LevelC.read()-48;
    InitPosy=InitPosy*10+LevelC.read()-48;
    Level=new int[NbrLigne][NbrColonne]; 
    for(int i=0;i<NbrLigne;i++)
      for(int j=0;j<NbrColonne;j++)
      { 
        Level[i][j]=LevelC.read()-48;
        if(Level[i][j]==Type.Dot)NumMax++;
        if(Level[i][j]==Type.BoxDot){NumMax++;NumWin++;}      
     
        }
    }catch(IOException e){
        e.printStackTrace();}  
  try{
    LevelC.close();
    }catch(IOException e){
        e.printStackTrace();} 
   }
    /*-------------------Accesseurs---------------*/
    public int ReturnInitX(){return  InitPosx;}
    public int ReturnInitY(){return  InitPosy;}
    public int ReturnNbrLigne(){return  NbrLigne;}
    public int ReturnNbrColonne(){return  NbrColonne;}
    public int ReturnVal(int Posx,int Posy)
    { 
      if( Posx >= 0  && Posy >= 0 && Posx <= NbrLigne-1 && Posy <= NbrColonne-1 )
       return Level[Posx][Posy]; 
      return Type.Block ;
    }
    /*---------------------------------------------*/
    public void AssignVal(int Posx,int Posy,int T){Level[Posx][Posy]=T; }
    public boolean LevelWon(){return NumMax==NumWin ;}
    
}
