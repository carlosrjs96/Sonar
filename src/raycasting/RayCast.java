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
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;


public class RayCast {
    public static final double EPSILON = 0.000001;
    public static final Random random = new Random();
    public static final double DIST_MAX_RAYO = 500;
    public static double DIST_TOTAL = 10;

    public static double crossProduct(Vector a, Vector b) {
        return a.x * b.y - b.x * a.y;
    }

    public static double distance(Point a,Point b){
        double distance = Math.sqrt(Math.pow(b.x-a.x,2)+Math.pow(b.y-a.y,2));
        //System.out.println("Distancia: "+distance);
        return distance;
    }

    // Find intersection of RAY & SEGMENT
    // returns null if no intersection found
    public static Point getIntersection(LineSegment ray, LineSegment segment){
        Vector r = new Vector(ray.B.x - ray.A.x,ray.B.y-ray.A.y);
        Vector s = new Vector(segment.B.x - segment.A.x,segment.B.y-segment.A.y);
        double rxs = crossProduct(r,s);

        Vector qp = new Vector(segment.A.x - ray.A.x,segment.A.y - ray.A.y);
        double qpxr = crossProduct(qp,r);

        // If r x s = 0 and (q - p) x r = 0, then the two lines are collinear.
        if (rxs < EPSILON && qpxr < EPSILON)
        {
            // 1. If either  0 <= (q - p) * r <= r * r or 0 <= (p - q) * s <= * s
            // then the two lines are overlapping,
            /*if (considerCollinearOverlapAsIntersect)
                if ((0 <= (q - p)*r && (q - p)*r <= r*r) || (0 <= (p - q)*s && (p - q)*s <= s*s))
                    return true;*/

            // 2. If neither 0 <= (q - p) * r = r * r nor 0 <= (p - q) * s <= s * s
            // then the two lines are collinear but disjoint.
            // No need to implement this expression, as it follows from the expression above.
            return null;
        }

        // 3. If r x s = 0 and (q - p) x r != 0, then the two lines are parallel and non-intersecting.
        if (rxs < EPSILON && qpxr >= EPSILON)
            return null;

        // t = (q - p) x s / (r x s)
        //var t = (q - p).Cross(s)/rxs;
        double t = crossProduct(qp,s)/rxs;
        // u = (q - p) x r / (r x s)
        //var u = (q - p).Cross(r)/rxs;
        double u = crossProduct(qp,r)/rxs;
        // 4. If r x s != 0 and 0 <= t <= 1 and 0 <= u <= 1
        // the two line segments meet at the point p + t r = q + u s.
        if (rxs >= EPSILON && (0 <= t && t <= 1) && (0 <= u && u <= 1))
        {
            // We can calculate the intersection point using either t or u.
            //intersection = p + t*r;

            return new Point((int)Math.floor(ray.A.x + t*r.x),(int)Math.floor(ray.A.y + t*r.y));

            // An intersection was found.
            //return true;
        }

        // 5. Otherwise, the two line segments are not parallel but do not intersect.
        return null;
    }

    public static Point getClosestIntersection(LineSegment ray,ArrayList<LineSegment> segments){
        Point closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        for(LineSegment l : segments){
            Point intersect = getIntersection(ray,l);
            if(intersect == null) continue;
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>10)){
                

                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>10)){
                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        return closestIntersect;
    }
    
    public static LineSegment getIntersection2(LineSegment ray, LineSegment segment){
        Vector r = new Vector(ray.B.x - ray.A.x,ray.B.y-ray.A.y);
        Vector s = new Vector(segment.B.x - segment.A.x,segment.B.y-segment.A.y);
        double rxs = crossProduct(r,s);

        Vector qp = new Vector(segment.A.x - ray.A.x,segment.A.y - ray.A.y);
        double qpxr = crossProduct(qp,r);

        if (rxs < EPSILON && qpxr < EPSILON)
        {
            return null;
        }

        if (rxs < EPSILON && qpxr >= EPSILON)
            return null;

        // t = (q - p) x s / (r x s)
        //var t = (q - p).Cross(s)/rxs;
        double t = crossProduct(qp,s)/rxs;
        // u = (q - p) x r / (r x s)
        //var u = (q - p).Cross(r)/rxs;
        double u = crossProduct(qp,r)/rxs;
        // 4. If r x s != 0 and 0 <= t <= 1 and 0 <= u <= 1
        // the two line segments meet at the point p + t r = q + u s.
        if (rxs >= EPSILON && (0 <= t && t <= 1) && (0 <= u && u <= 1))
        {
            // We can calculate the intersection point using either t or u.
            //intersection = p + t*r;
            return segment;
            //return new Point((int)Math.floor(ray.A.x + t*r.x),(int)Math.floor(ray.A.y + t*r.y));

            // An intersection was found.
            //return true;
        }

        // 5. Otherwise, the two line segments are not parallel but do not intersect.
        return null;
    }

    public static double getAngulo(ArrayList<LineSegment> lineSegments, LineSegment rayo ){
        LineSegment interseco = getSegmentIntersection(rayo, lineSegments);
        //System.out.println("Interseco: "+interseco.A.toString()+" ** "+interseco.B.toString());
        return calcularAngulo(interseco.dir, rayo.dir);
    }
    
    
    public static LineSegment getSegmentIntersection(LineSegment ray,ArrayList<LineSegment> segments){
        Point closestIntersect = null;
        LineSegment lineCercana = null;
        double closestDistance = Double.MAX_VALUE;
        for(LineSegment l : segments){
            Point intersect = getIntersection(ray,l);
            if(intersect == null) continue;
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>10)){
                closestIntersect = intersect;
                lineCercana = l;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>10)){
                closestIntersect = intersect;
                lineCercana = l;
                closestDistance = distance(ray.A,intersect);
            }
        }
        return lineCercana;
    }
    
    public static Point rotarPuntos(double angle, Point pCentral, Point pARotar){
        double angle_div = Math.toRadians(angle);
        double dist = RayCast.distance(pCentral, pARotar);
        Point point = new Point((pCentral.x+Math.cos(angle_div)*dist),(pCentral.y+Math.sin(angle_div)*dist)); 
        /*System.out.println(">> Angulo: "+ angle);
        System.out.println(">> Distancia: "+ dist);
        System.out.println(">> Punto: "+ point.toString());*/
        return point;
    }
    
    public static Point calcularPunto(double angle, Point pCentral,double dist){
        double angle_div = Math.toRadians(angle);
        Point point = new Point((pCentral.x+Math.cos(angle_div)*dist),(pCentral.y+Math.sin(angle_div)*dist)); 
        return point;
    }
    
    public static double getDistRayoSecundario(double dist,double anguloRayoP,double anguloRayoS){
        //dist = dist/4;
        if(anguloRayoP>=anguloRayoS){
            return dist * anguloRayoS / anguloRayoP;
        }else{
            double diferencia = anguloRayoP - anguloRayoS;
            anguloRayoS = anguloRayoP + diferencia;
            return dist * anguloRayoS / anguloRayoP;
        }
    }
    
    public static double getDistRayoSecundario2(double dist,Point src,double anguloRayoP,double anguloRayoS){
        //dist = dist/4;
//        System.out.println("--------------------------------------");
//        System.out.println("dist : "+ dist);
//        System.out.println("Point Principal : "+ src.toString());
//        System.out.println("anguloRayoP : "+ anguloRayoP);
//        System.out.println("anguloRayoS : "+ anguloRayoS);
        Point rayoP = RayCast.calcularPunto(anguloRayoP, src, 100);
//        System.out.println("Point Principal : "+rayoP.toString());
        Point rayoS = RayCast.calcularPunto(anguloRayoS, src, 100);
//        System.out.println("Point Secundario : "+rayoS.toString());
        double diferenciaRayos = RayCast.calcularAngulo(src, rayoP, rayoS);
        //if(diferenciaRayos>90){diferenciaRayos=diferenciaRayos-90;}
//        System.out.println("Diferencia : "+diferenciaRayos);
        double result = dist * (180- diferenciaRayos) / 180; 
//        System.out.println("Dist : "+result);
//        System.out.println("--------------------------------------");
        return result;
    }
    
    
    
    
    public static double normalEuclidea(Vector a){
        return Math.sqrt(Math.pow(a.x, 2) + Math.pow(a.y, 2));
    }
    
    public static Vector rectaDirigida(Point a, Point b){
        return new Vector( b.x - a.x , b.y - a.y);
    }
    
    public static double productoPunto(Vector a, Vector b){
        return (a.x * b.x) + (a.y * b.y);
    }
    
      public static double calcularAngulo (Point vertice, Point princ, Point sec){
        Vector vect1 = new Vector (vertice, princ);
        Vector vect2 = new Vector (vertice, sec);
        return Math.toDegrees(calcularAngulo(vect1, vect2));
    }
    
    public static double calcularAngulo(Vector a, Vector b){
        return Math.acos( productoPunto(a,b) / ( normalEuclidea(a) * normalEuclidea(b) ) );
    }
    
    public static double calcularAngulo2(Vector a, Vector b){ 
        return Math.atan2(b.y - a.y, b.x - b.y) * 180 / Math.PI;
    }
    
    public static double calculateAngle(Point P1,Point P2,Point P3){ //calcula el angulo a partir de 3 puntos (P1 como origen)
        double P1X = P1.x; double P1Y = P1.y; 
        double P2X = P2.x; double P2Y = P2.y;
        double P3X = P3.x; double P3Y = P3.y;
        double numerator = P2Y*(P1X-P3X) + P1Y*(P3X-P2X) + P3Y*(P2X-P1X);
        double denominator = (P2X-P1X)*(P1X-P3X) + (P2Y-P1Y)*(P1Y-P3Y);
        double ratio = numerator/denominator;

        double angleRad = Math.atan(ratio);
        double angleDeg = (angleRad*180)/Math.PI;

        if(angleDeg<0){
            angleDeg = 180+angleDeg;
        }
        return angleDeg;
    }
    
    public static double calculateAngle2(Point P1,Point P2,Point P3){ //calcula el angulo a partir de 3 puntos (P1 como origen)
        double result = Math.atan2(P3.y - P1.y, P3.x - P1.x) - Math.atan2(P2.y - P1.y, P2.x - P1.x);
        return Math.abs(Math.toDegrees(result));
    }

    public static double calculateAngle(Point origen, Point destino){//Calcula el angulo de una linea a partir de un punto de origen
        double x1 = destino.x; 
        double y1 = destino.y; 
        double x2 = origen.x; 
        double y2 = origen.y;
        double hypothenus=(double)Math.sqrt(Math.pow(x1-x2,2)+Math.pow(y1-y2,2));
        double angle=(double)Math.toDegrees(Math.acos( (x1-x2)/hypothenus ));
        if(y1<y2) angle=360-angle;
        return angle;
    }
    
    public static Point getRayReflectedTip(LineSegment line, LineSegment ray ){
        double normalY = line.B.x - line.A.x;
       // System.out.println("normalY = "+normalY);
        double normalX = line.A.y - line.B.y;
       // System.out.println("normalX = "+normalX);
        
        double normalLength = Math.sqrt(normalX * normalX + normalY * normalY);
       // System.out.println("normalLength = "+normalLength);
        
        normalX = normalX / normalLength;
        normalY = normalY / normalLength;
       // System.out.println("> normalY = "+normalY);
       // System.out.println("> normalX = "+normalX);
        
        Point intersection = RayCast.getIntersection(ray, line);
       // System.out.println("Intersection : "+intersection.toString());
       if (intersection == null){
           intersection = RayCast.getIntersection( line, ray);
       }
        
        double rayX = ray.B.x - intersection.x; // ray.B.x - ray.A.x;
       // System.out.println("rayX = "+rayX);
        
        double rayY = ray.B.y - intersection.y; //ray.B.y - ray.A.y;
       // System.out.println("rayY = "+rayY);
        
        double dotProduct = (rayX * normalX) + (rayY * normalY);
       // System.out.println("dotProduct = "+rayY);   
        
        double dotNormalX = dotProduct * normalX;
        double dotNormalY = dotProduct * normalY;
      //  System.out.println("dotNormalX = "+dotNormalX);
       // System.out.println("dotNormalY = "+dotNormalY);
        
        double reflectedRayTipX = ray.B.x - (dotNormalX * 2);
        double reflectedRayTipY = ray.B.y - (dotNormalY * 2);
        /*System.out.println("reflectedRayTipX = " + reflectedRayTipX);
        System.out.println("reflectedRayTipY = " + reflectedRayTipY);
        */  
        
        Point reflectedRayTip = new Point(reflectedRayTipX,reflectedRayTipY);
        //System.out.println("*** ANGULO DEL RAYO REFLETADO :"+RayCast.calculateAngle(ray.B,reflectedRayTip));
        //System.out.println("inter : " + intersection.toString());
        return reflectedRayTip;
    }
    
    public static Color getColor(int intensidad){
        int rgb = ((intensidad * 255) / 100);        
//        System.out.println(">>intensidad : "+  intensidad);
//        System.out.println("COLOR : "+  rgb);
        rgb = Math.abs(rgb % 255);
        
        return  new Color(rgb, rgb, rgb);
    }

    public static double in180(double ang){
        if (ang>= 90){
            return Math.abs(180-ang);
        }
        return ang;
    }

    public static double in360 (double ang) {
        return ang % 360;
    }
    
    public static Vector normaliza (Vector vector) {
        double d = normalEuclidea(vector);
        return new Vector (vector.x/d, vector.y/d);
    }
    public static Point getReflected (LineSegment segmento, LineSegment ray, Point src, Point target ) {
        Vector normal = normaliza (segmento.dir);

        Point interseccion = getIntersection (ray, segmento);
        if (interseccion == null){
            interseccion = getIntersection(segmento, ray);
        }
        
        
        System.out.println("interseccion " + interseccion.toString());
        Vector tipRay = new Vector (target.x-interseccion.x, target.y-interseccion.y);
        System.out.println("largo tip " + normalEuclidea(tipRay));

        double productoPunto = productoPunto(tipRay, normal);

        Point dotNormal = new Point (productoPunto * normal.x, productoPunto * normal.y);

        Point reflectedPoint = new Point (target.x - (dotNormal.x * 2), target.y - (dotNormal.y * 2));
        Vector v = new Vector(reflectedPoint.x-interseccion.x, reflectedPoint.y-interseccion.y);
        System.out.println("largo v " + normalEuclidea(v));
        

        return reflectedPoint;
   }
}
