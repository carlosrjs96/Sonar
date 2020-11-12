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
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        LineSegment line = new LineSegment(new Point(300, 100),new Point(500, 300));
        LineSegment ray = new LineSegment(new Point(400, 400),new Point(400, 100));
        
        System.out.println("Resutado: " + RayCast.getRayReflectedTip(line, ray));
        //LineSegment ray = new LineSegment(new Point(400, 400),new Point(400, 100));
   
        //System.out.println("ANGULO 3 puntos :"+RayCast.calculateAngle(ray.B, line.A , ray.A));
        
        //System.out.println("ANGULO 2 puntos :"+RayCast.calculateAngle(ray.B, line.B));
   
        //LineSegment line = new LineSegment(new Point(2, 6),new Point(4, 4));
        //LineSegment ray = new LineSegment(new Point(3, 3),new Point(3, 6));
//       
//        double normalY = line.B.x - line.A.x;
//        System.out.println("normalY = "+normalY);
//        double normalX = line.A.y - line.B.y;
//        System.out.println("normalX = "+normalX);
//        
//        double normalLength = Math.sqrt(normalX * normalX + normalY * normalY);
//        System.out.println("normalLength = "+normalLength);
//        
//        normalX = normalX / normalLength;
//        normalY = normalY / normalLength;
//        System.out.println("> normalY = "+normalY);
//        System.out.println("> normalX = "+normalX);
//        
//        Point intersection = RayCast.getIntersection(ray, line);
//        System.out.println("Intersection : "+intersection.toString());
//        
//        double rayX = ray.B.x - intersection.x; // ray.B.x - ray.A.x;
//        System.out.println("rayX = "+rayX);
//        
//        double rayY = ray.B.y - intersection.x; //ray.B.y - ray.A.y;
//        System.out.println("rayY = "+rayY);
//        
//        double dotProduct = (rayX * normalX) + (rayY * normalY);
//        System.out.println("dotProduct = "+rayY);   
//        
//        double dotNormalX = dotProduct * normalX;
//        double dotNormalY = dotProduct * normalY;
//        System.out.println("dotNormalX = "+dotNormalX);
//        System.out.println("dotNormalY = "+dotNormalY);
//        
//        double reflectedRayTipX = ray.B.x - (dotNormalX * 2);
//        double reflectedRayTipY = ray.B.y - (dotNormalY * 2);
//        System.out.println("reflectedRayTipX = " + reflectedRayTipX);
//        System.out.println("reflectedRayTipY = " + reflectedRayTipY);
//        Point reflectedRayTip = new Point(reflectedRayTipX,reflectedRayTipY);
//        System.out.println("*** ANGULO DEL RAYO REFLETADO :"+RayCast.calculateAngle(ray.B,reflectedRayTip));
    }
     
}

