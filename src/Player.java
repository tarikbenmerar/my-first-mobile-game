/*
 * Player.java
 *
 * Created on 6 octobre 2007, 23:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author Sniper Ninja
 */

public class Player {
    private int Posx;
    private int Posy;
    public class Direction {
        final static int    Up=0;
        final static int  Down=1;
        final static int Right=2;
        final static int  Left=3;
                       }
    /*---Accesseurs---------*/
    public int ReturnX(){return Posx;}
    public int ReturnY(){return Posy;}        
    /*----------------------*/
    /** Créer une nouvelle instance de Player a une position donnée  */
    public Player(int InitPosx,int InitPosy) {
        Posx=InitPosx;  
        Posy=InitPosy;
       }
    
    /*--Fonction de Movement---*/
    public void Moveto(int Dir,Level Lvl)
    { int Decalx=0,Decaly=0,DecalBx=0,DecalBy=0;
      int ValD=Level.Type.Block,ValB=Level.Type.Vide ;
      /*Initialisation des 4 var ci-dessus*/ 
      switch (Dir)
       { case Direction.Right: Decalx=Posx;
                               Decaly=Posy+1;                
                               DecalBx=Posx; 
                               DecalBy=Posy+2;        
                                 break;           
         case Direction.Down: Decalx=Posx+1 ;  
                              Decaly=Posy;
                              DecalBx=Posx+2;
                              DecalBy=Posy;        
                                break; 
      
         case Direction.Left: Decalx=Posx;
                              Decaly=Posy-1;
                              DecalBx=Posx;
                              DecalBy=Posy-2;        
                                break;                        
         case Direction.Up:  Decalx=Posx-1;
                             Decaly=Posy;
                             DecalBx=Posx-2;
                             DecalBy=Posy;
                                break;              
      }
    /*---------------------------------------*/
    /*--Modification de la Position du Jouer*/
   ValD=Lvl.ReturnVal(Decalx,Decaly);
   ValB=Lvl.ReturnVal(DecalBx,DecalBy);        
   
    if(ValD==Level.Type.Vide || ValD==Level.Type.Dot){
       Posx=Decalx;
       Posy=Decaly;    
    }else{
          if(ValD!=Level.Type.Block &&(ValB==Level.Type.Vide || ValB==Level.Type.Dot)){
           Lvl.AssignVal(Decalx,Decaly,ValD-1);
           Lvl.AssignVal(DecalBx,DecalBy,ValB+1);
           if(ValD==Level.Type.BoxDot) Lvl.DecWin();
           if(ValB==Level.Type.Dot) Lvl.IncWin();
           Posx=Decalx;
           Posy=Decaly;
          }     
    
    
    }
   /*---------------------------------------------*/ 
    }
   /*----------Fin De La Fonction Moveto----------*/  
    

}
