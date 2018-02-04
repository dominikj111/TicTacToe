/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.awt.Point;

/**
 *
 * @author dominikjelinek
 */
public class Vector2D {
    public int a, b;
    
    public Vector2D(int a, int b){
        this.a = a; this.b = b;
    }
    
    public Point toPoint(){
        return new Point(this.a, this.b);
    }
    
    @Override
    public String toString() {
        return Vector2D.class.getName() + "(" + this.a + "," + this.b + ")";
    }

    @Override
    public boolean equals(Object o) {
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.a;
        hash = 37 * hash + this.b;
        return hash;
    }
}
