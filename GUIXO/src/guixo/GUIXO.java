
package guixo;

import structures.Location;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import structures.FieldSize;

/**
 *
 * @author domino
 */
public class GUIXO extends JFrame
{   
    private Policko[][] herniPole   = null;
    private FieldSize   rozmerPole  = null;
    private ArrayList<userInputInterface> userInputListeners = new ArrayList<>();
        
    private KeyAdapter reakceNaStiskKlavesy = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                System.exit(0);                
            }
            
            if (!userInputListeners.isEmpty()) {
                for (userInputInterface list : userInputListeners) {
                    list.stiskKlavesy(e);
                }
            }
        }
    };
    
    public GUIXO(FieldSize rozmerHracihoPole, boolean viditelnostOkna){
        this.setVisible(viditelnostOkna);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addKeyListener(reakceNaStiskKlavesy);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);        
        
        panel.setPreferredSize(new Dimension(
                symboly.E.getImageSize().width * rozmerHracihoPole.columns, 
                symboly.E.getImageSize().height * rozmerHracihoPole.rows
                ));
                    
        this.herniPole   = inicializujHerniPole(rozmerHracihoPole, panel);
        this.rozmerPole = rozmerHracihoPole;
        
        this.getContentPane().add(panel);
        this.pack();                
    }
        
    private Policko[][] inicializujHerniPole(FieldSize rozmer, JPanel panel){
        Policko[][] pole = new Policko[rozmer.rows][rozmer.columns];
        
        for (int radek = 0; radek < pole.length; radek++) {
            for (int sloupec = 0; sloupec < pole[radek].length; sloupec++) {
                pole[radek][sloupec] = new Policko(new Location(radek, sloupec));
                panel.add(pole[radek][sloupec]);
            }
        }
        
        return pole;
    }   

    
        
    public void updateField(symboly[][] herniPole, boolean repaint) throws Exception {
        
        if(neniStejnyRozmer(this.herniPole, herniPole)){
            throw new Exception("Rozmer predaneho pole neodpovida hernimu poli.\n"
                              + "Rozmer herniho pole je : " + this.getRozmerPole());
        }
        
        for (int radek = 0; radek < herniPole.length ; radek++) {
            for (int sloupec = 0; sloupec < herniPole[radek].length; sloupec++) {
                
                if(herniPole[radek][sloupec] == null){
                    this.herniPole[radek][sloupec].setSymbol(symboly.E);
                }
                else{                                    
                    this.herniPole[radek][sloupec].setSymbol(herniPole[radek][sloupec]);
                }
                
                if(repaint){this.herniPole[radek][sloupec].repaint();}
            }
        }
    }
    
    public void updateSquare(Location pozice, symboly sym){
        this.herniPole[pozice.row][pozice.column].setSymbol(sym);
        this.herniPole[pozice.row][pozice.column].repaint();
    }
    
    public void updateSquare(Location pozice){
        this.herniPole[pozice.row][pozice.column].repaint();
    }
    
    public void resetGui(){
        try{ 
            this.updateField(new symboly[this.getRozmerPole().rows][this.getRozmerPole().columns], true);
        }catch(Exception ex){}
    }
    
    public symboly[][] getCurrentField(){
        symboly[][] retField = new symboly[this.getRozmerPole().rows][this.getRozmerPole().columns];
        
        for (int radek = 0; radek < retField.length; radek++) {
            for (int sloupec = 0; sloupec < retField[radek].length; sloupec++) {
                retField[radek][sloupec] = this.herniPole[radek][sloupec].getSymbol();
            }
        }
        
        return retField;
    }
    
    public BufferedImage getCurrentGameImage(){
        
        BufferedImage img = new BufferedImage(symboly.E.getImageSize().width * this.getRozmerPole().columns,
                                              symboly.E.getImageSize().height * this.getRozmerPole().rows,
                                              BufferedImage.TYPE_INT_ARGB);
        
        Graphics g = img.getGraphics();
        
        for (int radek = 0; radek < this.getRozmerPole().rows; radek++) {
            for (int sloupec = 0; sloupec < this.getRozmerPole().columns; sloupec++) {
                g.drawImage(this.herniPole[radek][sloupec].getSymbol().getImage(), 
                            this.herniPole[radek][sloupec].calcXPosition(), 
                            this.herniPole[radek][sloupec].calcYPosition(), 
                            null);
            }
        }
        
        return img;
    }
    
    public void addUserInputListener(userInputInterface listener){
        if(!userInputListeners.contains(listener)){
            this.userInputListeners.add(listener);
        }
    }
    
    public void removeUserInputListener(userInputInterface listener){
        userInputListeners.remove(listener);
    }
    
    public FieldSize getRozmerPole(){
        return this.rozmerPole;
    }

    
    
    private <T> boolean neniStejnyRozmer(T[][] pole1, T[][] pole2) {
        
        if(pole1.length != pole2.length){return true;}
        
        for (int radek = 0; radek < pole1.length; radek++) {
                if(pole1[radek].length != pole2[radek].length){ return true;}
        }
        
        return false;
    }

    
    
    
    
    private void kliknutiNaPolicko(Location souradnicePolicka, symboly aktualniSymbol){
        if (!userInputListeners.isEmpty()) {
                for (userInputInterface list : userInputListeners) {
                    list.kliknutiNaPolicko(souradnicePolicka, aktualniSymbol);
                }
        }
    }
    
    private class Policko extends JPanel{
        
        private symboly    symbolPolicka = null;
        private Location   souradnicePol = null;                
        
        public Policko(Location pozice){
            this.symbolPolicka = symboly.E;
            this.souradnicePol = pozice;
            
            this.setSize(this.symbolPolicka.getImageSize());
            this.setLocation(calcXPosition(), calcYPosition());
            
            this.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    
                    kliknutiNaPolicko(souradnicePol, symbolPolicka);
                }                
            });
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(this.symbolPolicka.getImage(), 0, 0, null);
        }                
        
        private void setSymbol(symboly sym){
            this.symbolPolicka = sym;
        }
        
        private symboly getSymbol(){
            return this.symbolPolicka;
        }
        
        private int calcXPosition(){
            return this.getWidth() * this.souradnicePol.column;
        }
        
        private int calcYPosition(){
            return this.getHeight() * this.souradnicePol.row;
        }
    }
                
    
    /**
     * Metoda testuje funkènost grafického uživatelského rozhraní.
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception{
        GUIXO okno = new GUIXO(new FieldSize(15, 15),true);
        
        okno.addUserInputListener(new userInputInterface() {

            @Override
            public void kliknutiNaPolicko(Location pozice, symboly aktualniSymbol) {
                System.out.println(pozice + " " + aktualniSymbol);
            }

            @Override
            public void stiskKlavesy(KeyEvent key) {
                System.out.println("Klavesa: " + key.getKeyCode());
            }
        });
        
        okno.updateField(getRandomField(okno.getRozmerPole()), true);        
//        testNahodnehoZobrazeni(okno, 10, 1000);
//        testPrekresleniNahodnehoPolicka(okno);
//        testReset(okno, 1500);
//        testGetImage(okno);
//        testGetCurrentField(okno);
    }
        
    private static void testNahodnehoZobrazeni(GUIXO okno, int pocetOpakovani, int delayMs) throws Exception {                
        
        while(--pocetOpakovani > 0){
            
            okno.updateField(getRandomField(okno.getRozmerPole()), true);
            
            try {Thread.sleep(delayMs);} catch (InterruptedException ex) {}
        }
        
        okno.updateField(getRandomField(okno.getRozmerPole()), true);
    }
    
    private static symboly[][] getRandomField(FieldSize rozmer){
        Random rnd = new Random();
        symboly[][] pole = new symboly[rozmer.rows][rozmer.columns];
        
        for (int radek = 0; radek < pole.length; radek++) {
            for (int sloupec = 0; sloupec < pole[radek].length; sloupec++) {
                pole[radek][sloupec] = symboly.values()[rnd.nextInt(symboly.values().length)];
            }
        }        
        
        return pole;
    }
    
    private static void testPrekresleniNahodnehoPolicka(final GUIXO okno) throws Exception{
        okno.updateField(getRandomField(okno.getRozmerPole()), true);
        
        okno.addUserInputListener(new userInputAdapter() {

            @Override
            public void kliknutiNaPolicko(Location pozice, symboly aktualniSymbol) {
                super.kliknutiNaPolicko(pozice, aktualniSymbol);
                
                okno.updateSquare(pozice, 
                                  symboly.values()[new Random().nextInt(symboly.values().length)]);
            }
            
        });
    }
    
    private static void testReset(GUIXO okno, int delayMS) throws Exception{
        okno.updateField(getRandomField(okno.getRozmerPole()), true);
        
        try{Thread.sleep(delayMS);}catch(InterruptedException in){}
        
        okno.resetGui();
    }
    
    private static void testGetImage(GUIXO okno) throws Exception{
        
        File output = new File("aktualniOkno.png");        
        if(output.exists()){output.delete();}
        
        okno.updateField(getRandomField(okno.getRozmerPole()), true);
        
        ImageIO.write(okno.getCurrentGameImage(), "png", output);
    }
    
    private static void testGetCurrentField(GUIXO okno) throws Exception{
        okno.updateField(getRandomField(okno.getRozmerPole()), true);
        symboly[][] currField = okno.getCurrentField();
        
        for (int radek = 0; radek < currField.length; radek++) {
            for (int sloupec = 0; sloupec < currField[radek].length; sloupec++) {
                System.out.print("[" + currField[radek][sloupec] + "]");
            }
            System.out.println("");
        }
    }
}
