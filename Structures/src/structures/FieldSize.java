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
public class FieldSize {
    public int rows, columns;
    
    public FieldSize(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
    }

    public Point toPoint(){
        return new Point(this.rows, this.columns);
    }
    
    @Override
    public String toString() {
        return FieldSize.class.getName() + "[" + this.rows + "][" + this.columns + "]";
    }

    @Override
    public boolean equals(Object obj) {
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.rows;
        hash = 73 * hash + this.columns;
        return hash;
    } 

    @Override
    protected Object clone() {
        return new FieldSize(this.rows, this.columns);
    }
    
    public FieldSize getClone(){
        return (FieldSize)this.clone();
    }
}
