
package suxo;

import corexo.symboly;
import guixo.GUIXO;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.FieldSize;
import structures.Location;

/**
 *
 * @author domino
 */
public class SUXOMain2
{
    public  static int       def_pocetHer              = 1;
    public  static int       def_zpozdeniMeziHrami     = 3000;
    public  static int       def_zpozdeniMeziTahyHracu = 100;
    public  static int       def_vyherniPocet          = 5;
    public  static int       def_pocetHracuNaJednuHru  = 2;
    public  static FieldSize def_velikostPole          = new FieldSize(15, 15); 
    public  static boolean   def_ukazatGui             = true;
    
    private static Class[] playerClasses = new Class[]{
        PlayerRandomBot.class,
        PlayerRandomBot.class,
        PlayerRandomBot.class,
        PlayerRandomBot.class
    };
    
    
    private static supervisorListener suListener = new supervisorAdapter() {

        @Override
        public void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
            super.endGame(winner, opponents, gameField, winListSquares);
            
            if(winner == null){
                String out = "REMIZA:\n";
                
                for (int i = 0; i < opponents.length; i++) {
                    out += opponents[i] + "\n";
                }
                
                
                System.out.println(out);
            } else {
                System.out.println("Vyhral hrac: " + winner + 
                                   ", pocet vyher: " + winner.getNumberOfWins() +
                                   ", pocet remiz: " + winner.getNumberOfTies());
            }
        }

        @Override
        public void endGames(Player[] players) {
            super.endGames(players);
            
            System.out.println("************************");
            
            for (int i = 0; i < players.length; i++) {
                System.out.println("Pocet vyher: " + players[i].getNumberOfWins() + 
                                   ", pocet remiz: " + players[i].getNumberOfTies() +
                                   ", jmeno hrace: " + players[i].getName());
            }
                
            System.out.println("************************");
        }
    };
    
    private static Supervisor supervisor = null;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        main2(playerClasses, 
              def_zpozdeniMeziTahyHracu, def_zpozdeniMeziHrami, def_vyherniPocet, def_pocetHracuNaJednuHru, 
              def_velikostPole,
              def_ukazatGui);
               
        addSUListener(suListener);
        
        startoGames(def_pocetHer);
    }
    
    public static void main2(Class[] playerClassArray, 
                             int zpozdeniDalsihoHrace, int zpozdeniDalsihoKola, int vyherniPocet, int pocetHracuNaHru, 
                             FieldSize velikostPole, 
                             boolean ukazatGUI){
        
        supervisor = new Supervisor(zpozdeniDalsihoHrace, zpozdeniDalsihoKola, ukazatGUI);
        
              Player[] players = makePlayerObjets(playerClassArray);
        final GUIXO    gui     = new GUIXO(velikostPole, ukazatGUI);
        
        supervisor.initGames(players, velikostPole, vyherniPocet, pocetHracuNaHru);
        
        supervisor.addSuperVisorListener(new supervisorAdapter() {
            @Override
            public void changeGameField(symboly[][] gameField) {
                super.changeGameField(gameField);
                
                repaint(corexo.symboly.convertSymbolsClass.convertSymbolsField(gameField));
            }

            @Override
            public void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
                super.endGame(winner, opponents, gameField, winListSquares);
                
                repaint(corexo.symboly.convertSymbolsClass.convertToFinishShow(gameField, winListSquares));
            }
            
            private void repaint(guixo.symboly[][] gameField){
                try {
                    gui.updateField(gameField, true);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
    }

    public static void addSUListener(supervisorListener suList) throws Exception{
        if(supervisor == null){throw new Exception("Supervisor neni inicializovan, zavolejte funkci main2.");}
        
        supervisor.addSuperVisorListener(suList);
    }
    
    public static void startoGames(int pocetOpakovaniHer) throws Exception{
        supervisor.startGames(pocetOpakovaniHer);
    }
    
    public static void startoGames() throws Exception{
        supervisor.startGames(def_pocetHer);
    }

    private static Player[] makePlayerObjets(Class[] playerClassArray) {
        
        Player[] retPlayers = new Player[playerClassArray.length];
        
        try{
            for (int i = 0; i < playerClassArray.length; i++) {
                retPlayers[i] = (Player)playerClassArray[i].newInstance();
                ((corexo.Player)retPlayers[i]).setID(i);
            }
            
        } catch(InstantiationException | IllegalAccessException ex){
            System.out.println(ex.getMessage());
        }
        
        return retPlayers;
    }
}
