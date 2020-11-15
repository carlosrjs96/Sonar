/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raycasting;

/**
 *
 * @author Carlos
 */
public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Vector (Point a, Point b){
        this.x = a.x -b.x;
        this.y = a.y-b.y;
    }

    @Override
    public String toString() {
        return "(" + x + "|" + y + ")";
    }
}
