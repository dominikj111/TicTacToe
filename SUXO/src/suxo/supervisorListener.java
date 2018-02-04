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
public interface supervisorListener {
    void startGame(Player[] players, Player whoStart);
    void endGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares);
    void startGames(Player[] players);
    void endGames(Player[] players);
    void changeGameField(symboly[][] gameField);
}
