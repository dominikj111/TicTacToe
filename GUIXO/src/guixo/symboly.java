/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package guixo;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author domino
 */
public enum symboly {

    X, X_off, 
    O, O_off, 
    D, D_off, 
    E, E_off,
    P, P_off;
    
    
    protected BufferedImage getImage() {
        initSymWork();
        return SymWork.slovnik.get(this);
    }

    protected Dimension getImageSize() {
        initSymWork();
        return SymWork.getSize();
    }

    private void initSymWork() {
        SymWork.checkMap();
    }

    private static class SymWork {

        private static String X_PNGName      = "x.png";
        private static String X_Off_PNGName  = "x_off.png";
        private static String O_PNGName      = "o.png";        
        private static String O_Off_PNGName  = "o_off.png";
        private static String D_PNGName      = "d.png";
        private static String D_Off_PNGName  = "d_off.png";
        private static String E_PNGName      = "e.png";
        private static String E_Off_PNGName  = "e_off.png";
        private static String P_PNGName      = "p.png";
        private static String P_Off_PNGName  = "p_off.png";
        private static Dimension size        = null;
        
        private static Map<symboly, BufferedImage> slovnik = new HashMap<>();
        
        private static void checkMap() {            
            if(slovnik.isEmpty()){
                initMap();
            }
        }
        
        private static void initMap() {
            slovnik.put(symboly.X, getBufferedImage(X_PNGName));
            slovnik.put(symboly.X_off, getBufferedImage(X_Off_PNGName));
            slovnik.put(symboly.O, getBufferedImage(O_PNGName));
            slovnik.put(symboly.O_off, getBufferedImage(O_Off_PNGName));
            slovnik.put(symboly.D, getBufferedImage(D_PNGName));
            slovnik.put(symboly.D_off, getBufferedImage(D_Off_PNGName));
            slovnik.put(symboly.E, getBufferedImage(E_PNGName));
            slovnik.put(symboly.E_off, getBufferedImage(E_Off_PNGName));
            slovnik.put(symboly.P, getBufferedImage(P_PNGName));
            slovnik.put(symboly.P_off, getBufferedImage(P_Off_PNGName));
        }

        private static BufferedImage getBufferedImage(String pngName) {
            
//            URL str = SymWork.class.getResource("PNGSymb\\" + pngName); // windows path
            URL str = SymWork.class.getResource("PNGSymb/" + pngName); // mac path
           

            
            BufferedImage img = null;
            try {
                    img = ImageIO.read(str);
            } catch (IOException ex) {
                    System.out.println(ex.getMessage());
            }

            return img;
        }
        
        private static Dimension getSize() {
            if (size == null) {
                size = new Dimension(slovnik.get(symboly.X).getWidth(), slovnik.get(symboly.X).getHeight());
            }

            return size;
        }
    }
}
