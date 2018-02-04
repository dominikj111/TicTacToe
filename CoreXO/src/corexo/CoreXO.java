
package corexo;

import guixo.GUIXO;
import guixo.userInputInterface;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import playersXO.HumanPlayer;
import playersXO.RandomPlayer;
import structures.FieldSize;
import structures.Location;

/**
 *
 * @author domino
 */
public class CoreXO
{
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        int       vyherniPoc = 4;
        FieldSize rozmerPole = new FieldSize(15, 15);
        
        Player[]  players    = new Player[]{
            new RandomPlayer(), new RandomPlayer(), 
            new RandomPlayer(), new RandomPlayer()
        };
//        Player[]  players    = new Player[]{
//            new HumanPlayer()
//        };
        
        
        final Game hra = new Game(players, rozmerPole, vyherniPoc);
        final GUIXO okno = new GUIXO(rozmerPole, true);
        
        hra.addGameStateListener(new gameStateAdapter(){

            @Override
            public void startGame(Player player) {
                System.out.println("Hra spustena! Zacina hrac " + player );
            }

            @Override
            public void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
                String out = "";
                
                if(winner == null && winListSquares == null){
                    out += "Remiza...";
                } else {
                    out += "Zvitezil hrac " + winner + "\nVitezna sekvence: ";
                    
                    for(Location loc : winListSquares){
                        out += "[" + loc.row + "," + loc.column + "]";
                    }
                    
                    
                    repaint(symboly.convertSymbolsClass.convertToFinishShow(gameField, winListSquares));
                }
                
                System.out.println(out);
            }
            
            @Override
            public void changeState(symboly[][] gameField) {
                repaint(symboly.convertSymbolsClass.convertSymbolsField(gameField));
            }
            
            private void repaint(guixo.symboly[][] pole){
                try {
                    okno.updateField(pole, true);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            
        });
                                
        okno.addUserInputListener(new userInputInterface() {

            @Override
            public void kliknutiNaPolicko(Location pozice, guixo.symboly aktualniSymbol) {
                hra.userMouseInput(pozice);
            }

            @Override
            public void stiskKlavesy(KeyEvent key) {
                if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
                    System.exit(0);
                }
            }
        });

        hra.start();
    }

}
