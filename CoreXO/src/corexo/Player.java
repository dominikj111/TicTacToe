/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corexo;

import playersXO.HumanPlayer;
import structures.Location;

/**
 *
 * @author domino
 */
public abstract class Player {

    private String  playersName = "[NOINIT]";
    private symboly symb        = null;
    private int     ID          = -1;
    private boolean noStrategyImplement = false;
    
    private playerShootedReaction reactionAfterStrategy;
        
    public void initPlayer(symboly symbol){
        try{
            this.playersName = getNameAI();
        } catch (UnsupportedOperationException un){
            this.playersName = "Player.getNameAI -> UnsupportedOperationException";
            this.noStrategyImplement = true;
        }
        
        this.symb = symbol;
    }        
        
    @Override
    public String toString() {
        return Player.class.getName() + " " + this.getName();
    }

    public String getName() {
        return this.playersName + "[" + this.ID + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.symb != null ? this.symb.hashCode() : 0);
        hash = 53 * hash + this.ID;
        return hash;
    }
        
    boolean isNoStrategyImplement(){
        return this.noStrategyImplement;
    }
    
    void setPlayerShootedReaction(playerShootedReaction reaction) {
        this.reactionAfterStrategy = reaction;
    }
    
    boolean isPlayerShootedReactionNull(){
        return this.reactionAfterStrategy == null;
    }

    protected void run(symboly[][] gameField) {
        Location shot = null;
        try{
            shot = runStrategy(gameField, this.ID, this.symb, this.playersName);
            
            if(this.noStrategyImplement){this.noStrategyImplement = false;}
        } catch (UnsupportedOperationException un) {
            System.out.println("Player.runStrategy -> UnsupportedOperationException");
            if(!this.noStrategyImplement){this.noStrategyImplement = true;}
        }
        
        fireReactionAfterStrategy(shot);
    }

    public symboly getSymbol() {
        return this.symb;
    }
    
    public String getPlayersName(){
        return this.playersName;
    }    
    
    public int getID(){
        return this.ID;
    }
    
    public void setID(int value){
        this.ID = value;
    }
    
    protected void fireReactionAfterStrategy(Location shot){
        this.reactionAfterStrategy.playerShooted(this, shot);
    }
    
    protected abstract Location runStrategy(symboly[][] gameField, int playerID, symboly playerSymb, String playerName);
    
    protected abstract String getNameAI();
}
