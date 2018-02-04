/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package structures;

import java.awt.Point;

/**
 *
 * @author domino
 */
public class Location {
    public int row;
    public int column;
    
    public Location(int row, int column){
        this.row = row;
        this.column = column;
    }
    
    public Point toPoint(){
        return new Point(this.row, this.column);
    }

    @Override
    public String toString() {
        return Location.class.getName() + " [" + this.row + "," + this.column + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.row;
        hash = 29 * hash + this.column;
        return hash;
    }       
}
