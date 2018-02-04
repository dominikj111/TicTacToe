/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corexo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import structures.FieldSize;
import structures.Location;

/**
 *
 * @author domino
 */
public class Game {

    private final Player[]    players;
    private final GameField   gameField;
    private       int         playerIndex, vyherniPocet, shootDelayMS = 0;
    
    private ArrayList<gameStateInterface> listeners = new ArrayList<>();
    
    
    
    private playerShootedReaction reaction = new playerShootedReaction(){

        @Override
        public boolean playerShooted(Player player, Location location) {                                         
            
            if(location == null){return true;}
            
            /* Testovani poradi hrace. */
            if(!player.equals(players[playerIndex])){return false;}
            
            /* Zapis symbolu hrace do pole. */
            if(!gameField.setSymbol(location,player.getSymbol())){return false;}
            fireEventChangeGame();
            
            /* Zvyseni playerIndexu a zavolani metody dalsiho hrace run(this.gameField). */            
            playerIndex = (++playerIndex >= players.length) ? 0 : playerIndex;
            
            /* Overeni konce hry a vyvolani udalosti. */
            ArrayList<Location> seznam = null;
            if ((seznam = gameField.TestPolicekVeVsechSmerech(location, vyherniPocet)) != null) {
                
                fireEventEndGame(player, players, seznam);
                
                return true;
            }
            
            /* Pokud neni detekovana vyhra, testujeme hraci pole na remizu. */
            if (gameField.hraciPoleJePlne()) {
                
                fireEventEndGame(null, players, null);
                
                return true;
            }
                        
            /* Zpozdeni pred dalsim tahem. */
            delay();
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    players[playerIndex].run(gameField.getCurrentField());
                }
            }).start();
            
            /* Pokud je vse v poradku, vracime true. */
            return true;
        }                          
        
        private void delay(){
            if(shootDelayMS > 0){
                try {
                    Thread.sleep(shootDelayMS);
                } catch (InterruptedException ex) {}
            }
        }
    };
    
    
        
    public Game(Player[] players, FieldSize fieldSize, int vyherniPocet) {
        this.players      = players;
        this.gameField    = new GameField(getNewGameField(fieldSize));
        this.playerIndex  = new Random().nextInt(this.players.length);        
        this.vyherniPocet = vyherniPocet;
    }
    
    private symboly[][] getNewGameField(FieldSize size){
        symboly[][] retSym = new symboly[size.rows][size.columns];
        
        for (int radek = 0; radek < size.rows; radek++) {
            for (int sloupec = 0; sloupec < size.columns; sloupec++) {
                retSym[radek][sloupec] = symboly.e;
            }
        }
        
        return retSym;
    }
    
    public void start() throws Exception {
        
        Queue<symboly> frontaSymbolu = getQueueSymbols();
        
        for (int i = 0; i < this.players.length; i++) {
            
            if(frontaSymbolu.isEmpty()){
                throw new Exception("Pro dany pocet hracu neni dostatek symbolu.");
            }
            
            this.players[i].setPlayerShootedReaction(reaction);        
            this.players[i].initPlayer(frontaSymbolu.poll());
        }
        
        fireEventStartGame();
        this.players[playerIndex].run(this.gameField.getCurrentField());
    }

    public void userMouseInput(Location loc){
        if(players[playerIndex].isNoStrategyImplement()){
            if(this.reaction.playerShooted(players[playerIndex], loc)){
                System.out.println(loc);
            } else {
                System.out.println("Chyba tahu, hrajte znovu!");
            }
        }
    }
    
    private LinkedList<symboly> getQueueSymbols(){
        
        LinkedList<symboly> symbs = new LinkedList<>();
        
        symboly[] poleSymbolu = symboly.values();
        
        for (int i = 0; i < poleSymbolu.length; i++) {
            if(poleSymbolu[i] != symboly.e){
                symbs.add(poleSymbolu[i]);
            }
        }
        
        return symbs;
        
    }
    
    public void setShutDelay(int delayMS){
        this.shootDelayMS = delayMS;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Work with listeners">
    public void addGameStateListener(gameStateInterface listener) {
        if(listener != null && !this.listeners.contains(listener)){
            this.listeners.add(listener);
        }
    }
    
    public void removeGameStateListener(gameStateInterface listener) {
        this.listeners.remove(listener);
    }
    
    public boolean isContainsGameStateListener(gameStateInterface listener){
        return this.listeners.contains(listener);
    }
    
    private void fireEventStartGame(){
        if(!this.listeners.isEmpty()){
            for(gameStateInterface list : listeners){
                list.startGame(this.players[playerIndex]);
            }
        }
    }
    
    private void fireEventEndGame(Player winner, Player[] opponents, ArrayList<Location> winListSquares){
        if(!this.listeners.isEmpty()){
            for(gameStateInterface list : listeners){
                list.endGame(winner, opponents, this.gameField.getCurrentField(), winListSquares);
            }
        }
    }
    
    private void fireEventChangeGame(){
        if(!this.listeners.isEmpty()){
            for(gameStateInterface list : listeners){
                list.changeState(this.gameField.getCurrentField());
            }
        }
    }
    //</editor-fold>

    @Override
    public Object clone(){
        
        Game retGame = new Game(this.players, this.gameField.getFieldSize().getClone(), this.vyherniPocet);
        
        ArrayList<gameStateInterface> listerCopy = new ArrayList<>();
        
        for (int i = 0; i < this.listeners.size(); i++) {
            listerCopy.add(this.listeners.get(i));
        }
        
        retGame.listeners = listerCopy;
        
        return retGame;
    }
    
    
}
