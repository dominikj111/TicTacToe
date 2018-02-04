/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package corexo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import structures.Location;

/**
 *
 * @author domino
 */
public enum symboly {
    x, o, d, p, e;
    
    public static class convertSymbolsClass{
        
        final static Map<corexo.symboly, guixo.symboly> symbolConverterOn  = new HashMap<>();
        final static Map<corexo.symboly, guixo.symboly> symbolConverterOff = new HashMap<>();
                
        static{
            symbolConverterOn.put(x, guixo.symboly.X);
            symbolConverterOn.put(o, guixo.symboly.O);
            symbolConverterOn.put(d, guixo.symboly.D);
            symbolConverterOn.put(e, guixo.symboly.E);
            symbolConverterOn.put(p, guixo.symboly.P);
            
            symbolConverterOff.put(x, guixo.symboly.X_off);
            symbolConverterOff.put(o, guixo.symboly.O_off);
            symbolConverterOff.put(d, guixo.symboly.D_off);
            symbolConverterOff.put(e, guixo.symboly.E_off);
            symbolConverterOff.put(p, guixo.symboly.P_off);
        }
        
        public static guixo.symboly[][] convertSymbolsField(corexo.symboly[][] gameField) {
            guixo.symboly[][] returnField = new guixo.symboly[gameField.length][gameField[0].length];

            for (int radek = 0; radek < gameField.length; radek++) {
                for (int sloupec = 0; sloupec < gameField[radek].length; sloupec++) {
                    returnField[radek][sloupec] = symbolConverterOn.get(gameField[radek][sloupec]);
                }
            }

            return returnField;
        }

        public static guixo.symboly[][] convertToFinishShow(corexo.symboly[][] gameField, ArrayList<Location> winListSquares) {
            guixo.symboly[][] returnField = new guixo.symboly[gameField.length][gameField[0].length];

            for (int radek = 0; radek < gameField.length; radek++) {
                for (int sloupec = 0; sloupec < gameField[radek].length; sloupec++) {
                    if (winListSquares.contains(new Location(radek, sloupec))) {
                        returnField[radek][sloupec] = symbolConverterOn.get(gameField[radek][sloupec]);
                        continue;
                    }
                    
                    returnField[radek][sloupec] = symbolConverterOff.get(gameField[radek][sloupec]);
                }
            }

            return returnField;
        }
    }
}
