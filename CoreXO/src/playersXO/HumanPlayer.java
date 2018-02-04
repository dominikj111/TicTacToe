/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package playersXO;

import corexo.Player;
import corexo.symboly;
import structures.Location;

/**
 *
 * @author domino
 */
public class HumanPlayer extends Player{

    @Override
    protected Location runStrategy(symboly[][] gameField, int playerID, symboly playerSymb, String playerName) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected String getNameAI() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
