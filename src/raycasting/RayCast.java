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
import java.util.ArrayList;
import javax.swing.text.Segment;

public class RayCast {
    public static final double EPSILON = 0.000001;

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
            if(closestIntersect == null || distance(ray.A,intersect) < closestDistance){
                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || distance(ray.A,intersect) < closestDistance){
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
        LineSegment interseco = null;
        for (LineSegment lineSegment : lineSegments) {
            interseco = getIntersection2(rayo, lineSegment);
            System.out.println("-->> "+ lineSegment.toString());
            if(lineSegment != null){
                System.out.println("si entra");
                break;
            }
        }
        //System.out.println("Interseco: "+interseco.A.toString()+" ** "+interseco.B.toString());
        return calcularAngulo(interseco.dir, rayo.dir);
    }
    
    /*
    public static LineSegment getClosestIntersection2(LineSegment ray,ArrayList<LineSegment> segments){
        LineSegment closestIntersect = null;
        double closestDistance = Double.MAX_VALUE;
        for(LineSegment l : segments){
            Point intersect = getIntersection(ray,l);
            LineSegment intersect2 = 
            if(intersect == null) continue;
            if(closestIntersect == null || distance(ray.A,intersect) < closestDistance){
                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        for(LineSegment l : segments){
            Point intersect = getIntersection(l,ray);
            if(intersect == null) continue;
            if(closestIntersect == null || distance(ray.A,intersect) < closestDistance){
                closestIntersect = intersect;
                closestDistance = distance(ray.A,intersect);
            }
        }
        return closestIntersect;
    }*/
    
    
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
    
}
