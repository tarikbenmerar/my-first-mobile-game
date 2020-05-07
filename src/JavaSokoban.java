/*
 * JavaSokoban.java
 *
 * Created on 9 octobre 2007, 00:15
 */

import javax.microedition.lcdui.game.* ;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 *
 * @author  Sniper Ninja
 * @version
 */
public class JavaSokoban extends MIDlet implements CommandListener {
    private   Display   GameDisplay ;
    protected List      MainMenu;
    private   Ticker    GameTicker;
    private   Command   SelectC;
    private   Command   SelectL;
    private   Command   RetourL;
    private   Form      SelectLevel;
    private   TextField NumberInput ;
    private   StringItem  CurrentLevel; 
    private   GameManager Sokoban ;
    private int NumeroNiveau=1 ;
    private boolean FirstTime=true;
    public JavaSokoban(){
       GameDisplay=Display.getDisplay(this); 
       GameTicker=new Ticker("JavaSokoban.Un Jeu produit Par Sniper Ninja.All Right reserved 2007.");
       ActiverMenuPrincipal(true);
    }
    
    public void startApp() {
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
     // try{
       GameDisplay=null ;
       GameTicker=null;
       SelectC=null ; 
       if(Sokoban!=null)
       Sokoban.ViderMemoire(); 
    /*  // Sokoban=null;
      }catch(NullPointerException e){
          e.printStackTrace();
      }
*/      
      }

    public void commandAction(Command command, Displayable displayable) {
     if(GameDisplay.getCurrent().equals(MainMenu))
     {if (MainMenu.size()==3) 
      switch(MainMenu.getSelectedIndex())  
       {
         case 0 :  Sokoban=new GameManager(this);
                   Sokoban.start(true,NumeroNiveau);
                   GameDisplay.setCurrent(Sokoban);
                  break;       
         case  1:  
                 ActiverSelectionNiveau();
                  break;
         case  2:  destroyApp(true);
                   notifyDestroyed(); 
                  break;
      }
      else 
        switch(MainMenu.getSelectedIndex())  
       {
         case 0 : Sokoban.start(false,NumeroNiveau);
                  GameDisplay.setCurrent(Sokoban);
                   break; 
         case 1 : Sokoban=new GameManager(this);
                  Sokoban.start(true,NumeroNiveau);
                  GameDisplay.setCurrent(Sokoban);
                   break;                 
         case 2 : ActiverSelectionNiveau();
                   break;
         case 3 : destroyApp(true);
                  notifyDestroyed(); 
                   break;  
        }  
     
     }else{//si on est dans la selection de niveau
      if(command.equals(RetourL))
       ActiverMenuPrincipal(FirstTime);  
      else{ int Numero=0;
          try{
          Numero=Integer.valueOf(NumberInput.getString()).intValue();
          
          }catch(NumberFormatException e){
           Numero=0;
          }
          if(Numero>=1 && Numero<=40){
          NumeroNiveau=Numero;
          ActiverMenuPrincipal(true);
          }
         } 
         
     }
    }
    public void ActiverMenuPrincipal(boolean FirstTime) {
       MainMenu=new List("Menu Principale",List.IMPLICIT);
       MainMenu.setTicker(GameTicker);
       this.FirstTime=FirstTime ;
       if(!FirstTime)/* si on est dans le cas dune nouvelle
                      * partie alors il faut créer un nouveau
                      * niveau
                      */
          MainMenu.append("Reprendre Jeu",null);
          MainMenu.append("Nouveau Jeu",null);
          MainMenu.append("Selection Du Niveau",null);
          MainMenu.append("Quitter",null);
          SelectC=new Command("Select.",Command.EXIT,0);
          MainMenu.addCommand(SelectC);
          MainMenu.setSelectCommand(SelectC);
          MainMenu.setCommandListener(this);
          GameDisplay.setCurrent(MainMenu);
        
        
    }
  public void ActiverSelectionNiveau()
  { RetourL=new Command("Retour.",Command.EXIT,1);  
    SelectL=new Command("Select.",Command.OK,0);
    NumberInput=new TextField("Niveau(1-40):","",2,TextField.DECIMAL);
   
    CurrentLevel=new StringItem("","Niveau actuel="+NumeroNiveau);
    SelectLevel=new Form("Selection du Niveau:"); 
    SelectLevel.append(NumberInput);
    SelectLevel.append(CurrentLevel);
    SelectLevel.addCommand(SelectL);
    SelectLevel.addCommand(RetourL);
    SelectLevel.setCommandListener(this);
    GameDisplay.setCurrent(SelectLevel);
  }
  public void IncLevel(){
     if(NumeroNiveau<40)
      NumeroNiveau++;
  }

}

