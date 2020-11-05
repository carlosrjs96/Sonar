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
import javax.swing.text.Segment;

public class RayCast {
    public static final double EPSILON = 0.000001;
    public static final Random random = new Random();
    public static final double DIST_MAX_RAYO = 1000;

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
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>2)){
                

                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>2)){
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
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>2)){
                closestIntersect = intersect;
                lineCercana = l;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || (distance(ray.A,intersect) < closestDistance&&distance(ray.A,intersect)>2)){
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
    
    
    
    public static double normalEuclidea(Vector a){
        return Math.sqrt(Math.pow(a.x, 2) + Math.pow(a.y, 2));
    }
    
    public static Vector rectaDirigida(Point a, Point b){
        return new Vector( b.x - a.x , b.y - a.y);
    }
    
    public static double productoPunto(Vector a, Vector b){
        return (a.x * b.x) + (a.y * b.y);
    }
    
    public static double calcularAngulo(Vector a, Vector b){
        return Math.acos( productoPunto(a,b) / ( normalEuclidea(a) * normalEuclidea(b) ) );
    }
    
    public static double calcularAngulo2(Vector a, Vector b){ 
        return Math.atan2(b.y - a.y, b.x - b.y) * 180 / Math.PI;
    }
    
    public static double calcRotationAngle(Point A, Point B) {
        double angle = Math.toDegrees(Math.atan2(B.x - A.x, B.y - A.y));
        // Keep angle between 0 and 360
        angle = angle + Math.ceil(-angle / 360) * 360;
        return angle;
    }
    
    public static double calcRotationAngle2(Point firstPoint, Point secondPoint) {

        if ((secondPoint.x > firstPoint.x)) {//above 0 to 180 degrees

            return (Math.atan2((secondPoint.x - firstPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

        } else if ((secondPoint.x < firstPoint.x)) {//above 180 degrees to 360/0

            return 360 - (Math.atan2((firstPoint.x - secondPoint.x), (firstPoint.y - secondPoint.y)) * 180 / Math.PI);

        }//End if((secondPoint.x > firstPoint.x) && (secondPoint.y <= firstPoint.y))

        return Math.atan2(0, 0);

    }//End public float getAngleFromPoint(Point firstPoint, Point secondPoint)
    
    public static Color getColor(int intensidad){
        int rgb = ((intensidad * 255) / 100);
        return  new Color(rgb, rgb, rgb);
    }
    
}
