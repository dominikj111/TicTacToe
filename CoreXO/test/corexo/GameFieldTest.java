/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package corexo;

import java.util.ArrayList;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import structures.FieldSize;
import structures.Location;

/**
 *
 * @author domino
 */
public class GameFieldTest {
    
    int         maximValue    = 20;
    
    Random      rnd           = new Random();
    symboly[]   symbols       = symboly.values();
    symboly[][] pole          = null;
    GameField   herniPole     = null;
    
    public GameFieldTest() {
        this.pole          = generujNahodnePole(new FieldSize(rnd.nextInt(maximValue) + 1, rnd.nextInt(maximValue) + 1));
        this.herniPole     = new GameField(pole);
    }
    
    private symboly[][] generujNahodnePole(FieldSize fieldSize) {
        symboly[][] nahodnePole = new symboly[fieldSize.rows][fieldSize.columns];
        
        /* Potrebujem, aby alespon jedno policko bylo prazdne. */
        int pocetPrazdnychPolicek = 0;
        
        for (int radek = 0; radek < fieldSize.rows; radek++) {
            for (int sloupec = 0; sloupec < fieldSize.columns; sloupec++) {
                nahodnePole[radek][sloupec] = symbols[rnd.nextInt(symbols.length)];
                
                
                if(nahodnePole[radek][sloupec] == symboly.e){
                    pocetPrazdnychPolicek++;
                }
                
            }
            
            if(pocetPrazdnychPolicek == 0){
                nahodnePole[rnd.nextInt(fieldSize.rows)][rnd.nextInt(fieldSize.columns)] = symboly.e;
            }
        }
        
        return nahodnePole;
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getCurrentField method, of class GameField.
     */
    @Test
    public void testGetCurrentField() {
        symboly[][] result = this.herniPole.getCurrentField();
        assertArrayEquals(this.pole, result);
    }

    /**
     * Test of hraciPoleJePlne method, of class GameField.
     */
    @Test
    public void testHraciPoleJePlne() {
        
        symboly[][] fullFi = new symboly[][]{
            {symboly.o, symboly.x, symboly.o, symboly.x},
            {symboly.x, symboly.x, symboly.o, symboly.x},
            {symboly.o, symboly.x, symboly.x, symboly.x}
        };
        
        GameField   herniPoleFull = new GameField(fullFi);
        
        boolean fullFieldResusl   = herniPoleFull.hraciPoleJePlne();
        boolean NoFullFieldResult = this.herniPole.hraciPoleJePlne();

        assertTrue(fullFieldResusl);
        assertFalse(NoFullFieldResult);
    }

    /**
     * Test of TestPolicekVeVsechSmerech method, of class GameField.
     */
    @Test
    public void testTestPolicekVeVsechSmerech() {
        
        symboly[][] gameField1 = new symboly[][]{
            {symboly.x, symboly.x, symboly.x}
        };
        
        ArrayList<Location> seznamPolicekOcekavany = new ArrayList<>();
        seznamPolicekOcekavany.add(new Location(0, 0));
        seznamPolicekOcekavany.add(new Location(0, 1));
        seznamPolicekOcekavany.add(new Location(0, 2));       
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(0, 2));
        seznamPolicekOcekavany.add(new Location(0, 1));
        seznamPolicekOcekavany.add(new Location(0, 0));        
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(0, 1));
        seznamPolicekOcekavany.add(new Location(0, 0));
        seznamPolicekOcekavany.add(new Location(0, 2));        
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
                        
        gameField1 = new symboly[][]{
            {symboly.x, symboly.o, symboly.o, symboly.x},
            {symboly.o, symboly.x, symboly.x, symboly.o},
            {symboly.o, symboly.x, symboly.x, symboly.o},
            {symboly.x, symboly.o, symboly.x, symboly.o}
        };
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(2, 1));
        seznamPolicekOcekavany.add(new Location(3, 0));
        seznamPolicekOcekavany.add(new Location(1, 2));
        seznamPolicekOcekavany.add(new Location(0, 3));
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(1, 2));
        seznamPolicekOcekavany.add(new Location(2, 1));
        seznamPolicekOcekavany.add(new Location(3, 0));
        seznamPolicekOcekavany.add(new Location(0, 3));
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(0, 3));
        seznamPolicekOcekavany.add(new Location(1, 2));
        seznamPolicekOcekavany.add(new Location(2, 1));
        seznamPolicekOcekavany.add(new Location(3, 0));
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        gameField1 = new symboly[][]{
            {symboly.x, symboly.o, symboly.o, symboly.x},
            {symboly.o, symboly.x, symboly.o, symboly.o},
            {symboly.o, symboly.x, symboly.o, symboly.x},
            {symboly.x, symboly.o, symboly.o, symboly.x},
            {symboly.x, symboly.o, symboly.o, symboly.o},
            {symboly.x, symboly.o, symboly.x, symboly.x},
            {symboly.x, symboly.o, symboly.o, symboly.o}
        };
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(4, 2));
        seznamPolicekOcekavany.add(new Location(3, 2));
        seznamPolicekOcekavany.add(new Location(2, 2));
        seznamPolicekOcekavany.add(new Location(1, 2));
        seznamPolicekOcekavany.add(new Location(0, 2));
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(2, 2));
        seznamPolicekOcekavany.add(new Location(1, 2));
        seznamPolicekOcekavany.add(new Location(0, 2));
        seznamPolicekOcekavany.add(new Location(3, 2));
        seznamPolicekOcekavany.add(new Location(4, 2));
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
        
        seznamPolicekOcekavany.clear();
        seznamPolicekOcekavany.add(new Location(4, 0));
        seznamPolicekOcekavany.add(new Location(3, 0));
        seznamPolicekOcekavany.add(new Location(5, 0));
        seznamPolicekOcekavany.add(new Location(6, 0));
        testListOfWonLocations(gameField1, seznamPolicekOcekavany);
    }
    
    private void testListOfWonLocations(symboly[][] field, ArrayList<Location> expectedList){
        
        GameField gameF = new GameField(field);
        ArrayList<Location> testList = gameF.TestPolicekVeVsechSmerech(expectedList.get(0), expectedList.size());
        
        assertEquals(expectedList, testList);
    }

    /**
     * Test of setSymbol method, of class GameField.
     */
    @Test
    public void testSetSymbol() {
        
        symboly  nahSymbol    = (rnd.nextBoolean()) ? symboly.o : symboly.x;
        Location eSymb        = findSymbol(symboly.e);
        Location randSymb     = findSymbol(nahSymbol);
        
        boolean mimoPole = herniPole.setSymbol(new Location(this.pole.length + 10, 0), nahSymbol);
        boolean noFree   = herniPole.setSymbol(randSymb, nahSymbol);
        boolean Free     = herniPole.setSymbol(eSymb, nahSymbol);
        
        assertFalse(mimoPole);
        assertFalse(noFree);
        assertTrue(Free);
        
        
        this.pole[eSymb.row][eSymb.column] = nahSymbol;
        assertEquals(this.pole, herniPole.getCurrentField());
    }

    private Location findSymbol(symboly symb) {
        int radek, sloupec;
	int pocetPolicekPole = this.pole.length * this.pole[0].length;
        
	int nahodUmisteni = rnd.nextInt(pocetPolicekPole);
	do{
            radek = nahodUmisteni / this.pole[0].length;
            sloupec = nahodUmisteni % this.pole[0].length;
            
            nahodUmisteni = (++nahodUmisteni < pocetPolicekPole) ? nahodUmisteni : 0;
	}while(this.pole[radek][sloupec] != symb);
        
	return new Location(radek, sloupec);
    }        
    
    /**
     * Test of clone method, of class GameField.
     */
    @Test
    public void testClone() throws Exception {
        symboly[][] poleLocal = herniPole.getCurrentField();
        assertArrayEquals(pole, poleLocal);
        
        GameField herniPoleLocal = (GameField)herniPole.clone();
        assertArrayEquals(herniPole.getCurrentField(), herniPoleLocal.getCurrentField());
    }   
}
