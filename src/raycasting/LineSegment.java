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
public class LineSegment {
    public Point A;
    public Point B;
    public Vector dir;

    public LineSegment(Point A,Point B){
        this.A = A;
        this.B = B;
        dir = new Vector(B.x-A.x,B.y-A.y);
    }

    public LineSegment(Point A,Vector dir){
        this.A = A;
        this.dir = dir;
        this.B = new Point(A.x + dir.x,A.y + dir.y);
    }
    
    public void actualizarDir(Point A,Point B){
        this.A = A;
        this.B = B;
        this.dir.x = B.x-A.x;
        this.dir.y = B.y-A.y;
    }

    @Override
    public String toString() {
        return "(" + A.toString() + " -> " + B.toString() + ")";
    }
}
