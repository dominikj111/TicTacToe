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
public interface gameStateInterface {
    void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares);
    void startGame(Player player);
    void changeState(symboly[][] gameField);
}
