/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corexo;

import structures.Location;

/**
 *
 * @author domino
 */
public interface playerShootedReaction {
    
    /**
     * 
     * Metoda je zavolana po tahu hrace.
     * 
     * 
     * @param player   Hrac ktery prave odehral.
     * @param location Misto, ktere zvolil.
     * @return         Pokud je volba policka v poradku, vraci metoda true.<br>
     *                 Pokud nastala chyba a hrac by mel tahnout znovu, vrati metoda false.
     */
    boolean playerShooted(Player player, Location location);
}
