/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package suxo;

import corexo.symboly;
import java.util.Random;
import playersXO.RandomPlayer;
import structures.Location;

/**
 *
 * @author domino
 */
public class PlayerRandomBot extends Player{

    @Override
    protected Location runStrategy(symboly[][] gameField, int playerID, symboly playerSymb, String playerName) {                        
        
        Location retLoc = findSymbol(symboly.e, gameField);
        
        return retLoc;
    }

    @Override
    protected String getNameAI() {
        return RandomPlayer.class.getName();
    }

    private Location findSymbol(symboly symb, symboly[][] pole) {
        
        Random rnd = new Random();
        
        int radek, sloupec;
	int pocetPolicekPole = pole.length * pole[0].length;
        
	int nahodUmisteni = rnd.nextInt(pocetPolicekPole);
	do{
            radek = nahodUmisteni / pole[0].length;
            sloupec = nahodUmisteni % pole[0].length;
            
            nahodUmisteni = (++nahodUmisteni < pocetPolicekPole) ? nahodUmisteni : 0;
	}while(pole[radek][sloupec] != symb);
        
	return new Location(radek, sloupec);
    }

}
