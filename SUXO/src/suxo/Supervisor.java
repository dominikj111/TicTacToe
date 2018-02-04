/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suxo;

import corexo.Game;
import corexo.gameStateAdapter;
import corexo.gameStateInterface;
import corexo.symboly;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.FieldSize;
import structures.Location;

/**
 *
 * @author domino
 */
public class Supervisor {

    private boolean         needInit     = true;
    private GameEnvironment enviGame     = null;
    private Player[]        playersArray = null;
    
    private int zpozdeniDalsihoHrace, zpozdeniDalsihoKola;
    private boolean slowMode;
        
    public Supervisor(int zpozdeniDalsihoHrace, int zpozdeniDalsihoKola, boolean slowMode){
        this.zpozdeniDalsihoHrace = zpozdeniDalsihoHrace;
        this.zpozdeniDalsihoKola  = zpozdeniDalsihoKola;
        this.slowMode = slowMode;
    }
    
    public void initGames(Player[] players, FieldSize size, 
                          int vyherniPocet, int pocetHracuNaHru){
        this.needInit = false;
        this.playersArray = players;
        
        resetPlayersStatistics(this.playersArray);
        
        this.enviGame = makeGamesEnvironment(this.playersArray, size, vyherniPocet, pocetHracuNaHru, 
                                             this.slowMode, this.zpozdeniDalsihoHrace, this.zpozdeniDalsihoKola);
    }
    
    public void startGames(int pocetHer) throws Exception{
        if(this.needInit){throw new Exception("Hry nejsou inicializovany, zavolejte nejdriv metodu initGames.");}
                
        this.enviGame.runGames(pocetHer);
    }
        
    private void resetPlayersStatistics(Player[] players) {
        for (int i = 0; i < players.length; i++) {
            players[i].resetStatistics();
        }
    }

    private GameEnvironment makeGamesEnvironment(Player[] players, FieldSize size, int vyherniPocet, int pocetHracuNaHru, 
                                                 boolean slowMode, int zpozdeniDalsihoHrace, int zpozdeniDalsihoKola) {
        
        final Player[][]   kombinaceHracu   = makePlayersCombination(players, pocetHracuNaHru);
              Game[]       games            = new Game[kombinaceHracu.length];
        
        for (int i = 0; i < kombinaceHracu.length; i++) {
            final int index = i;
            
            games[index] = new Game(kombinaceHracu[index], size, vyherniPocet);
            
            if(slowMode && zpozdeniDalsihoHrace > 0){
                games[index].setShutDelay(zpozdeniDalsihoHrace);
            }
            
            games[index].addGameStateListener(new gameStateAdapter() {
                @Override
                public void startGame(corexo.Player player) {
                    super.startGame(player);
                    fireSuperVisorListenerBeforeStartGame(kombinaceHracu[index], (Player)player);
                }

                @Override
                public void endGame(corexo.Player winner, corexo.Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
                    super.endGame(winner, opponents, gameField, winListSquares);
                    
                    Player[] playersGame = new Player[opponents.length];
                    for (int j = 0; j < opponents.length; j++) {playersGame[j] = (Player)opponents[j];}
                    
                    if(!(winner == null && winListSquares == null)){
                        ((Player)winner).oneMoreWin();
                    } else {
                        for (int j = 0; j < playersGame.length; j++) {
                            playersGame[j].oneMoreTie();
                        }
                    }
                                        
                    fireSuperVisorListenerEndGame((Player)winner, playersGame, gameField, winListSquares);
                }
            });
        }
                
        return new GameEnvironment(games, slowMode, zpozdeniDalsihoKola);
    }

    private Player[][] makePlayersCombination(Player[] players, int pocetHracuNaHru) {
        int[] ukazatele = new int[pocetHracuNaHru]; //musi platit, ze ukazatele.length <= hraci.length
        for (int i = 0; i < ukazatele.length; i++) {ukazatele[i] = i;}
        
        ArrayList<Player[]> skupinyHracu = new ArrayList<>();
        Player[] skupinaHracu;
        do{
            skupinaHracu = new Player[pocetHracuNaHru];
            
            for (int hracIndex = 0; hracIndex < pocetHracuNaHru; hracIndex++) {
                skupinaHracu[hracIndex] = players[ukazatele[hracIndex]];
            }
            
            skupinyHracu.add(skupinaHracu);
        }while(posunIndexy(ukazatele, ukazatele.length - 1, players.length - 1));
        
        return skupinyHracu.toArray(new Player[skupinyHracu.size()][]);
    }
    
    private static boolean posunIndexy(int[] ukazatele, int posledniUkazatel, int maxValue) {
        if(posledniUkazatel < 0){return false;}
        if(ukazatele[posledniUkazatel] != maxValue){
            ukazatele[posledniUkazatel]++;
            return true;
        } else {
            boolean temp = posunIndexy(ukazatele, posledniUkazatel - 1, maxValue - 1);
            
            if(posledniUkazatel >= 1){
                ukazatele[posledniUkazatel] = ukazatele[posledniUkazatel - 1] + 1;
            }
            
            return temp;
        }
    }
    
    private Player[] getSortedPlayers(){
        
        Player docasnyPrvek;

        for (int i = 0; i < playersArray.length - 1; i++) {
            if (playersArray[i].getNumberOfWins() < playersArray[i + 1].getNumberOfWins()) {
                docasnyPrvek = playersArray[i];
                playersArray[i] = playersArray[i + 1];
                playersArray[i + 1] = docasnyPrvek;

                i = -1;
            }
        }
        
        return this.playersArray;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="Work with Listeners">
    private ArrayList<supervisorListener> listeners = new ArrayList<>();
    
    
    //<editor-fold defaultstate="collapsed" desc="Private fire... Listener Methods">
    private void fireSuperVisorListenerBeforeStartGames(Player[] players){
        if(!this.listeners.isEmpty()){
            for(supervisorListener list : this.listeners){
                list.startGames(players);
            }
        }
    }
    
    private void fireSuperVisorListenerEndGames(Player[] players){
        if(!this.listeners.isEmpty()){
            for(supervisorListener list : this.listeners){
                list.endGames(players);
            }
        }
    }
    
    private void fireSuperVisorListenerBeforeStartGame(Player[] players, Player whoStart){
        if(!this.listeners.isEmpty()){
            for(supervisorListener list : this.listeners){
                list.startGame(players, whoStart);
            }
        }
    }
    
    private void fireSuperVisorListenerEndGame(Player winner, Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares){
        if(!this.listeners.isEmpty()){
            for(supervisorListener list : this.listeners){
                list.endGame(winner, opponents, gameField, winListSquares);
            }
        }
    }
    
    private void fireSuperVisorListenerChangeStateGameField(symboly[][] gameField){
        if(!this.listeners.isEmpty()){
            for(supervisorListener list : this.listeners){
                list.changeGameField(gameField);
            }
        }
    }
    //</editor-fold>
    
    
    public void addSuperVisorListener(supervisorListener suList) {
        if(suList != null && !listeners.contains(suList)){
            listeners.add(suList);
        }
    }
    
    public void removeSuperVisorListener(supervisorListener suList) {
        listeners.remove(suList);
    }
    
    public boolean containsSuperVisorListener(supervisorListener suList) {
        return listeners.contains(suList);
    }
    //</editor-fold>
    
    
    
    private class GameEnvironment {
    
        private Game[]       games;
        private Game[]       copyGames;
        private boolean      slowMode;
        private int          zpozdeniDalsihoKola;
        
        private int          indexHry;
        private int          indexKola;
        
        private gameStateInterface reakceNaKonecHry = new gameStateAdapter() {
            @Override
            public void endGame(corexo.Player winner, corexo.Player[] opponents, symboly[][] gameField, ArrayList<Location> winListSquares) {
                super.endGame(winner, opponents, gameField, winListSquares);
                
                //konec hry ->
                    //je index na konci?
                        //- posun index a spust hru, return
                        //+ je indexKola == 0? 
                            //- sniz indexKola, nastav index na 0, obnov hry za zalohy a spust hru, return
                            //+ nastav index na 0, obnov hry za zalohy a vyvolej udalost konce her, return
                
                if(slowMode){delay();}
                
                if(++indexHry < games.length){
                    spustHru(games[indexHry]);
                } else {
                    if(--indexKola != 0){
                        indexHry = 0;
                        restBckGames();
                        
                        spustHru(games[indexHry]);
                    } else {
                        restBckGames();
                        
                        fireSuperVisorListenerEndGames(getSortedPlayers());
                    }
                }
                
            }

            @Override
            public void changeState(symboly[][] gameField) {
                super.changeState(gameField);
                fireSuperVisorListenerChangeStateGameField(gameField);
            }
            
            private void delay(){
                try{
                    Thread.sleep(zpozdeniDalsihoKola);
                } catch (InterruptedException i){
                    
                }
            }
        };
                
        
        
        private GameEnvironment(Game[] games, boolean slowMode, int zpozdeniDalsihoKola){
            this.games = games;
            this.slowMode = slowMode;
            this.zpozdeniDalsihoKola = zpozdeniDalsihoKola;
            
            for (int i = 0; i < this.games.length; i++) {
                this.games[i].addGameStateListener(reakceNaKonecHry);
            }
        }
        
        private void runGames(final int pocetOpakovaniHer){
            this.indexHry  = 0;
            this.indexKola = pocetOpakovaniHer;
            
            bckGames();
            fireSuperVisorListenerBeforeStartGames(playersArray);
            
            spustHru(this.games[this.indexHry]);
        }
        
        private void spustHru(Game game){
            try {
                game.start();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        
        
        
        
        
        private void bckGames(){
            this.copyGames = getCopyGames();
        }
        
        private void restBckGames(){
            if(this.copyGames != null){
                this.games = this.copyGames;
                
                bckGames();
            }
        }
        
        private Game[] getCopyGames(){
            
            Game[] retGames = new Game[this.games.length];
            
            for (int i = 0; i < retGames.length; i++) {
                retGames[i] =  (Game)this.games[i].clone();
            }
            
            return retGames;
        }
    }        
}
