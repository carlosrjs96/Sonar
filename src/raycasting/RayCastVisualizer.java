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
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;


public class RayCastVisualizer extends JPanel implements MouseMotionListener, KeyListener {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("RayCast Visualizer");
        window.setSize(640, 380);

        RayCastVisualizer rcv = new RayCastVisualizer();

        window.add(rcv);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public RayCastVisualizer() {
        this.setBackground(Color.BLACK);

        this.setLayout(null);

        initPolygons();
        initSegments();
        addMouseMotionListener(this);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
    }

    ArrayList<Polygon> activePolygons = new ArrayList<>();

    private void initPolygons() {

        // Border Polygon
        Polygon b = new Polygon();
        b.addPoint(0, 0);
        b.addPoint(640, 0);
        b.addPoint(640, 360);
        b.addPoint(0, 360);//0, 360
        activePolygons.add(b);

        Polygon p1 = new Polygon();
        p1.addPoint(100, 150);
        p1.addPoint(120, 50);
        p1.addPoint(200, 80);
        p1.addPoint(140, 210);
        activePolygons.add(p1);

        Polygon p2 = new Polygon();
        p2.addPoint(100, 200);
        p2.addPoint(120, 250);
        p2.addPoint(60, 300);
        activePolygons.add(p2);

        Polygon p3 = new Polygon();
        p3.addPoint(200, 260);
        p3.addPoint(220, 150);
        p3.addPoint(300, 200);
        p3.addPoint(350, 320);
        activePolygons.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint(340, 60);
        p4.addPoint(360, 40);
        p4.addPoint(370, 70);
        activePolygons.add(p4);

        Polygon p5 = new Polygon();
        p5.addPoint(450, 190);
        p5.addPoint(560, 170);
        p5.addPoint(540, 270);
        p5.addPoint(430, 290);
        activePolygons.add(p5);

        Polygon p6 = new Polygon();
        p6.addPoint(400, 95);
        p6.addPoint(580, 50);
        p6.addPoint(480, 150);
        p6.addPoint(400, 95);
        activePolygons.add(p6);
    }

    ArrayList<LineSegment> activeSegments = new ArrayList<>();

    private void initSegments() {
        for (Polygon p : activePolygons) {
            for (int i = 0; i < p.npoints; i++) {

                Point start = new Point(p.xpoints[i], p.ypoints[i]);
                Point end;
                if (i == p.npoints - 1) {
                    end = new Point(p.xpoints[0], p.ypoints[0]);
                } else {
                    end = new Point(p.xpoints[i + 1], p.ypoints[i + 1]);
                }
                activeSegments.add(new LineSegment(start, end));
                // System.out.println("new segment : " + start + " -> " + end);
            }
        }
    }

    // Point mousePos = new Point(1,1);

    @Override
    public void mouseMoved(MouseEvent e) {
        /*
         * mousePos = new Point(e.getX(),e.getY()); currentRays =
         * castRays(mousePos,50,100); repaint();
         */
    }

    ArrayList<Rayo> currentRays = new ArrayList<>();

    ArrayList<Pixel> currentPixels = new ArrayList<>();

    public void cast(ArrayList<Rayo> result, double angulo, Point src, int dist, int n) {
        //System.out.println("a : " + Math.toDegrees(angulo));
        angulo = angulo %360;
        if (dist > 0 && n != 0) {
            Point target = RayCast.calcularPunto(angulo, src, dist); // calcula el punto destino del rayo
            LineSegment ray = new LineSegment(src, target);// crea el segmento del rayo
            Point ci = RayCast.getClosestIntersection(ray, activeSegments);// crea el punto de interseccion del rayo con
            //hay que obetner el punto más cercano pero que no contenga al punto
            if (ci != null) {
                Rayo rayo = new Rayo(new LineSegment(src, ci), dist, angulo);
                result.add(rayo); // result.add(ci);
                // currentPixels.add(new Pixel(ci,rayo.intensidad));
                // double ang = RayCast.getAngulo(activeSegments, ray);
                // castRays(ang,ci,1,100);
                /*
                //obtner el angulo entre el segmento de intersecion y un punto
                LineSegment interseco = RayCast.getSegmentIntersection(ray, activeSegments);//OBTENGO EL SEGMENTO CON EL QUE INTERSECO
                Vector normal = RayCast.normaliza(interseco.dir);//obtengo la normal del vector intersecado
                double angRayNormal = Math.toDegrees(RayCast.calcularAngulo(normal, ray.dir));
                


                //System.out.println("ang2 normal " + Math.toDegrees(RayCast.calcularAngulo(interseco.dir, ray.dir)));
                LineSegment li = new LineSegment(new Point(20,30), new Point(40,30));
                double angBase = Math.toDegrees(RayCast.calcularAngulo(interseco.dir, li.dir));
                

                
                Vector v = RayCast.normaliza(ray.dir);
                



                double ang = Math.toDegrees(RayCast.getAngulo(activeSegments, ray));//retorna en radianes
                ang = RayCast.in180(ang);

                System.out.println("Ang incidencia : " + ang);
                
                System.out.println("Ang incidencia : " + ang);
                System.out.println("ang rayo : "  + angulo);
                System.out.println("m = " + (angulo + ang *2));
                Point newP =  RayCast.rotarPuntos(angulo - ang*2, ci, src);
                LineSegment line = new LineSegment (newP, ci);
                double angNuevo = Math.toDegrees(RayCast.calcularAngulo(line.dir, li.dir));
                System.out.println("ang nuevo : " + angNuevo);
                

                double trueAng = RayCast.in360(angulo+ angBase + RayCast.in180(ang)-180);
                //System.out.println(RayCast.in180(ang));
                //System.out.println("true " + trueAng);

               */
            
                LineSegment interseco = RayCast.getSegmentIntersection(ray, activeSegments);//OBTENGO EL SEGMENTO CON EL QUE INTERSECO
                //Point interseccion = RayCast.getIntersection (ray, interseco);
        
                //if (interseccion !=null){
                    Point reflected = RayCast.getReflected(interseco, ray, src, target);
                    System.out.println("target : " + target.toString());
                    System.out.println("reflected : " + reflected.toString());
                    double angTrue = RayCast.calculateAngle(ci, reflected);
                    int distNew = dist - (int) RayCast.distance(src, ci)-1;
                    //double ang = Math.toDegrees(RayCast.getAngulo(activeSegments, ray));//retorna en radianes
                    
                    cast(result, angTrue, ci, distNew, n - 1);//angulo - ang * 2
               // }
                
                
            }
            else {
                result.add(new Rayo(ray, dist, angulo)); // result.add(target);
            }    
        }
    }


/*
    public ArrayList<Rayo> castRays(LineSegment seg, double angulo, Point src, int n, int dist) {
        ArrayList<Rayo> result = new ArrayList<>();
        int cantRayos = RayCast.random.nextInt(80 - 30) + 30;// crea la cantidad de rayos random entre 30-80

        double max_Angulo = RayCast.calcRotationAngle2(src, seg.B);
        double min_Angulo = RayCast.calcRotationAngle2(src, seg.A);
        if (min_Angulo > max_Angulo) {
            System.out.println("min>max");
            // double temp_max = max;
            double temp_min = min_Angulo;
            min_Angulo = max_Angulo;
            max_Angulo = temp_min;
        } else {
            System.out.println("max>min");
        }

        for (int i = 0; i < cantRayos; i++) {
            double angle_div;
            int new_dist;
            if (i == 0) {
                angle_div = angulo; // crea el angulo del rayo principal
                new_dist = dist;
            } else {// REVISAR EL ANGULO ALEATORIO SOLO SIRVE PARA EL SONAR<<<<<<<<<<<<<<<<<<<
                angle_div = RayCast.random.nextInt((int) (max_Angulo) - (int) (min_Angulo)) + (int) (min_Angulo); // crea
                                                                                                                  // el
                                                                                                                  // angulo
                                                                                                                  // random
                                                                                                                  // del
                                                                                                                  // rayo
                                                                                                                  // secundario
                // new_dist = (int)RayCast.getDistRayoSecundario(dist, angulo, angle_div);
                // angle_div = angulo;
                new_dist = dist;
            }

            Point target = RayCast.calcularPunto(angle_div, src, new_dist); // calcula el punto destino del rayo
            LineSegment ray = new LineSegment(src, target);// crea el segmento del rayo
            Point ci = RayCast.getClosestIntersection(ray, activeSegments);// crea el punto de interseccion del rayo con
                                                                           // un segmento

            if (ci != null) {
                Rayo rayo = new Rayo(new LineSegment(src, ci), RayCast.distance(src, ci), angulo);
                result.add(rayo); // result.add(ci);
                // currentPixels.add(new Pixel(ci,rayo.intensidad));
                // double ang = RayCast.getAngulo(activeSegments, ray);
                // castRays(ang,ci,1,100);
            } else {
                result.add(new Rayo(ray, new_dist, angulo)); // result.add(target);
            }
        }
        return result;
    }
    */

    Sonar sonar = new Sonar(new Point(50, 150));

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLUE);
        for (Polygon p : activePolygons) {
            g.drawPolygon(p);
        }

        g.setColor(Color.GREEN);
        g.drawPolygon(sonar.getPoligono());

        g.setColor(Color.RED);
        for (Rayo p : currentRays) {
            // g.drawLine((int)mousePos.x,(int)mousePos.y,(int)p.x,(int)p.y);
            // g.drawLine((int)sonar.getD().x,(int)sonar.getD().y,(int)p.x,(int)p.y);
            g.drawLine((int) p.linea.A.x, (int) p.linea.A.y, (int) p.linea.B.x, (int) p.linea.B.y);
            // g.fillOval((int)p.x-5,(int)p.y-5,10,10);
        }

        for (Pixel pixel : currentPixels) {
            g.setColor(pixel.color);
            g.drawLine((int) pixel.point.x, (int) pixel.point.y, (int) pixel.point.x, (int) pixel.point.y);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int b = e.getKeyCode();
        if (b == KeyEvent.VK_LEFT) {
            this.sonar.cambiarPosicion(1);
        }
        if (b == KeyEvent.VK_UP) {
            this.sonar.cambiarPosicion(2);
        }
        if (b == KeyEvent.VK_RIGHT) {
            this.sonar.cambiarPosicion(3);
        }
        if (b == KeyEvent.VK_DOWN) {
            this.sonar.cambiarPosicion(4);
        }
        if (b == KeyEvent.VK_R) {
            this.sonar.cambiarAngulo(true);
        }
        if (b == KeyEvent.VK_T) {
            this.sonar.cambiarAngulo(false);
        }

        //this.currentRays = castRays(sonar.Boca, sonar.getAngulo(), sonar.getD(), 50, 100);
        this.currentRays.clear();
        System.out.println("a : " + sonar.getAngulo());
        cast (this.currentRays, sonar.getAngulo(), sonar.getD(), 200,5);
        System.out.println("------------------------");
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
