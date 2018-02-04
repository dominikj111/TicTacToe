/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package corexo;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import structures.FieldSize;
import structures.Location;
import structures.Vector2D;

/**
 *
 * @author dominikjelinek
 */
public class GameField {

    private symboly[][] herniPole;
    
    
    /*
     
     |--------|--------|--------|
     | -1, -1 | -1,  0 | -1,  1 |
     |--------|--------|--------|
     |  0, -1 |  0,  0 |  0,  1 |
     |--------|--------|--------|
     |  1, -1 |  1,  0 |  1,  1 |
     |--------|--------|--------|
     
     */
    
    private static Vector2D[][] smerProchazeni = new Vector2D[][]{
        {new Vector2D(-1, -1), new Vector2D( 1,  1)}, 
        {new Vector2D( 1, -1), new Vector2D(-1,  1)},
        {new Vector2D( 0, -1), new Vector2D( 0,  1)},
        {new Vector2D(-1,  0), new Vector2D( 1,  0)}
    };
    
    protected GameField(symboly[][] symbolyPole) {
        this.herniPole = symbolyPole;
    }
   
    
    protected symboly[][] getCurrentField() {
        return (symboly[][])this.herniPole.clone();
    }
    
    protected FieldSize getFieldSize(){
        return new FieldSize(this.herniPole.length, this.herniPole[0].length);
    }
    
    protected boolean hraciPoleJePlne(){
            
        for (int radek = 0; radek < herniPole.length; radek++) {
            for (int sloupec = 0; sloupec < herniPole[radek].length; sloupec++) {
                if(polickoJePrazdne(new Location(radek, sloupec))){
                    return false;
                }
            }
        }

        return true;
    }
    
    
    protected ArrayList<Location> TestPolicekVeVsechSmerech(Location souradnice, int vyherniPocet){
                
        ArrayList<Location> vyherniPoziceRetu = new ArrayList<>();
        ArrayList<Location> vyherniPoziceTemp = new ArrayList<>();
        
        for(Vector2D[] vects : smerProchazeni){
                                    
            vyherniPoziceTemp.add(souradnice);
            
            for (int i = 0; i < vects.length; i++) {
                hledejSousedy(souradnice, vects[i], vyherniPoziceTemp);
            } 
            
            if(vyherniPoziceTemp.size() >= vyherniPocet){
                
                if(vyherniPoziceRetu.contains(souradnice)){
                    vyherniPoziceTemp.remove(souradnice);
                }
                
                vyherniPoziceRetu.addAll(vyherniPoziceTemp);
            }
            vyherniPoziceTemp.clear();
        }
        
        if(!vyherniPoziceRetu.isEmpty()){return vyherniPoziceRetu;}
        
        return null;
    }    

    protected boolean setSymbol(Location location, symboly symbol) {
        if(chybaPriZapisu(location)){return false;}
         
        this.herniPole[location.row][location.column] = symbol;
        return true;
    }

    private boolean chybaPriZapisu(Location hracemZvolenePolicko) {

        if (souradniceMimoPole(hracemZvolenePolicko)) {
            return true;
        }
        if (!polickoJePrazdne(hracemZvolenePolicko)) {
            return true;
        }

        return false;
    }
    
    private boolean souradniceMimoPole(Location souradnice) {
        
        return souradnice.row < 0 || souradnice.row >= this.herniPole.length ||
               souradnice.column < 0 || souradnice.column >= this.herniPole[0].length;
    }

    private boolean polickoJePrazdne(Location souradnice) {        
        return this.herniPole[souradnice.row][souradnice.column] == symboly.e;
    }
    
    @Override
    protected Object clone(){
        
        symboly[][] retSyms = new symboly[this.herniPole.length][];
        
        for (int radek = 0; radek < this.herniPole.length; radek++) {
            
            retSyms[radek] = new symboly[this.herniPole[radek].length];
            
            for (int sloupec = 0; sloupec < this.herniPole[radek].length; sloupec++) {
                retSyms[radek][sloupec] = this.herniPole[radek][sloupec];
            }
        }
        
        return new GameField(retSyms);
    }
   
    
    
    private void hledejSousedy(Location aktualniPolicko, Vector2D vektor, ArrayList<Location> vyherniSeznam) {

        Location sousedniPolicko = new Location(
                aktualniPolicko.row + vektor.a,
                aktualniPolicko.column + vektor.b);

        if (souradniceMimoPole(sousedniPolicko)) {
            return;
        }

        if (polickoJePrazdne(sousedniPolicko)) {
            return;
        }

        if (symbolyJsouStejne(aktualniPolicko, sousedniPolicko)) {
            vyherniSeznam.add(sousedniPolicko);
            hledejSousedy(sousedniPolicko, vektor, vyherniSeznam);
        }
    }

    private boolean symbolyJsouStejne(Location souradnice1, Location souradnice2) {                        
        return this.herniPole[souradnice1.row][souradnice1.column] == this.herniPole[souradnice2.row][souradnice2.column];
    }
}
