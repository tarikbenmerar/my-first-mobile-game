/*
 * GameManager.java
 *
 * Created on 19 octobre 2007, 12:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Sniper Ninja
 */
import java.io.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.*;
public class GameManager extends GameCanvas implements Runnable,CommandListener {
           private JavaSokoban Game ;
	   private Image PersoImg;
           private Sprite PersoSprite;
           private LayerManager SpriteManager=null;
           private Image BlocksImg;
           private TiledLayer Blocks;
           private Level Level; 
           private Player Joueur  ;
           private boolean FirstTime ;
           private int CornerX,CornerY;
           
      //variables de commande
           private Command RetourC; 
  
    /** Creates a new instance of GameManager */
    public GameManager(JavaSokoban G) {
        super(true);
        FirstTime=true ; 
        Game=G;
        RetourC=new Command("Retour.",Command.EXIT,0);  
        this.addCommand(RetourC);
        this.setCommandListener(this);
    }
/*----------- créer Le graphisme du niveau-----------*/
 private void CreerNiveau() throws IOException {
 //création des sprites  
 try{
 BlocksImg = Image.createImage("/Graphics/Blocks.jpg"); 
 }catch(IOException ioex) { System.err.println(ioex);}
 Blocks = new TiledLayer(Level.ReturnNbrColonne(),Level.ReturnNbrLigne(), BlocksImg, 16, 15);

  
  }
  /*------Méthode de synchronisation de létat affiché avec l'état réelle--*/
  void Synchroniser(){
     //remplissage du niveau
   for (int i = 0; i < Level.ReturnNbrLigne(); i++) 
    for (int j = 0; j < Level.ReturnNbrColonne(); j++)
   { try{
       Blocks.setCell(j,i , Level.ReturnVal(i,j)); 
     } catch(IndexOutOfBoundsException e){
         e.printStackTrace();
     }
     
    }
      PersoSprite.setRefPixelPosition(CornerY+Joueur.ReturnY()*16, CornerX+Joueur.ReturnX()*15);
  }
 
 
  /*----------------------------------------------------------------------*/
  /*-----------------------------------------------------*/ 
   
     public void start(boolean FirstTime,int NumeroNiveau)  {
      /*si c'est la premiére fois on crée le niveau*/  
        this.FirstTime=FirstTime ;
         if(FirstTime==true)
        {
            Level= new Level(NumeroNiveau);
            Joueur=new Player(Level.ReturnInitX(),Level.ReturnInitY());
              
       // Création des images                 
	try {
          PersoImg = Image.createImage("/Graphics/Perso.jpg");
	  // creation du niveau
          CreerNiveau(); 
         } catch(IOException ioex) { System.err.println(ioex);}
       // creation du SpriteManager et l'ajout des sprites
          SpriteManager = new LayerManager();
          PersoSprite = new Sprite(PersoImg, 16 , 15);
          SpriteManager.append(PersoSprite);
          SpriteManager.append(Blocks);                                                                                                   
          CornerX=(getHeight()-16*Level.ReturnNbrLigne())/2;
          CornerY=(getWidth()-16*Level.ReturnNbrColonne())/2;
          Blocks.setPosition(CornerY,CornerX); 
        }    
         
          Thread runner = new Thread(this);     
          runner.start();
          

	}
    
     private void VerifierGagnant() {
	if(Level.LevelWon())
        {   try {
                Game.IncLevel();
                FirstTime = true;
                Game.ActiverMenuPrincipal(true);
                ViderMemoire();
                Thread.currentThread().join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
     }
/*----Procedure permettant la gestion des touches-------*/
  private void GererTouche() {

	  // Lire le type de touche appuyée
	  int Touche = getKeyStates();
switch ( Touche )
    { case GameCanvas.RIGHT_PRESSED: Joueur.Moveto(Player.Direction.Right,Level);       
                                 break;           
      case GameCanvas.DOWN_PRESSED : Joueur.Moveto(Player.Direction.Down,Level);       
                                 break;      
      
      case GameCanvas.LEFT_PRESSED:  Joueur.Moveto(Player.Direction.Left,Level);       
                                 break;                      
      case GameCanvas.UP_PRESSED  :  Joueur.Moveto(Player.Direction.Up,Level);       
                                 break;              
      }
  }

/*-------------------------------------------------------*/
    
     public void run() {
       while(true) { // Boucle infinie

    	
 		// Gérer les touches
  		GererTouche();
              try {
                  // update screen
                 updateGameScreen(getGraphics());
               } catch (IOException ex) {
                ex.printStackTrace();
            }

		
                // Gérer la vitesse du rafaraichissement
		try {
		  Thread.currentThread().sleep(250);
		} catch(Exception e) {}
                // verifier si le niveau est terminé
   		VerifierGagnant();
	  
       }

	}
   
   private void updateGameScreen(Graphics g) throws IOException {
             Synchroniser();
         // the manager paints all the layers
             SpriteManager.paint(g, 0, 0);             
	  // this call paints off screen buffer to screen
	  flushGraphics();

     }

    public void commandAction(Command arg0, Displayable GameDisplay) {
      FirstTime=false ; 
      Game.ActiverMenuPrincipal(FirstTime);
      
    }

   public void ViderMemoire()
   {   
       PersoImg=null;
       BlocksImg=null;
       
    }


}
