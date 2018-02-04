/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suxo;

import corexo.symboly;
import structures.Location;

/**
 *
 * @author domino
 */
public abstract class Player extends corexo.Player{
    
    private int wins = 0;
    private int tie  = 0;
    
    public void resetStatistics(){
        this.wins = 0;
        this.tie  = 0;
    }

    public void oneMoreWin() {
        this.wins++;
    }
    
    public void oneMoreTie(){
        this.tie++;
    }    

    public int getNumberOfWins() {
        return this.wins;
    }
    
    public int getNumberOfTies() {
        return this.tie;
    }
    
    /*
     * Pokud neni implementovana metoda runStrategy, tak hraje na policko null.
     * Timto pak algoritmus konci a zaroven indikuje hru cloveka.
     * 
     * V tomto projektu prepisujeme metodu prave proto, aby pri neimplementaci metody program vyvolal vyjimku.
     * Nepocitame s hrou cloveka proti UI.
     */
    @Override
    protected void run(symboly[][] gameField) {
        Location shot = runStrategy(gameField, super.getID(), super.getSymbol(), super.getPlayersName());
        super.fireReactionAfterStrategy(shot);
    }
}
