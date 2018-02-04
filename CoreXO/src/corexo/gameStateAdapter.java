/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corexo;

import java.util.ArrayList;
import structures.Location;

/**
 *
 * @author domino
 */
public abstract class gameStateAdapter implements gameStateInterface{

    @Override
    public void startGame(Player player) {
        
    }

    @Override
    public void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
        
    }

    @Override
    public void changeState(symboly[][] gameField) {
        
    }
}
