/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package suxo;

import corexo.symboly;
import java.util.ArrayList;
import structures.Location;

/**
 *
 * @author domino
 */
public abstract class supervisorAdapter implements supervisorListener {

    @Override
    public void startGame(Player[] players, Player whoStart) {
        
    }

    @Override
    public void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
        
    }

    @Override
    public void startGames(Player[] players) {
        
    }

    @Override
    public void endGames(Player[] players) {
        
    }

    @Override
    public void changeGameField(symboly[][] gameField) {
        
    }
    
    
}
