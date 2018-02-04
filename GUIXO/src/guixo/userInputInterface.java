/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package guixo;

import structures.Location;
import java.awt.event.KeyEvent;

/**
 *
 * @author domino
 */
public interface userInputInterface {
    void kliknutiNaPolicko(Location pozice, symboly aktualniSymbol);
    void stiskKlavesy(KeyEvent key);
}
