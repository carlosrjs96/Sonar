
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
    public boolean verPologonos = false;
    public boolean verRayos = false;
    
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("RayCast Visualizer");
        window.setSize(600, 600);

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
        b.addPoint(500, 0);
        b.addPoint(500, 500);
        b.addPoint(0, 500);//0, 360
        activePolygons.add(b);

        Polygon p1 = new Polygon();
        p1.addPoint(100, 150);
        p1.addPoint(120, 50);
        p1.addPoint(200, 80);
        p1.addPoint(150, 170);
        activePolygons.add(p1);

        Polygon p2 = new Polygon();
        p2.addPoint(100, 200);
        p2.addPoint(150, 300);
        p2.addPoint(50, 300);
        activePolygons.add(p2);

        Polygon p3 = new Polygon();
        p3.addPoint(200, 260);
        p3.addPoint(220, 150);
        p3.addPoint(300, 200);
        p3.addPoint(350, 280);
        activePolygons.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint(340, 60);
        p4.addPoint(360, 40);
        p4.addPoint(370, 70);
        activePolygons.add(p4);

        Polygon p5 = new Polygon();
        p5.addPoint(100, 350);
        p5.addPoint(120, 440);
        p5.addPoint(300, 400);
        p5.addPoint(200, 290);
        activePolygons.add(p5);

        Polygon p6 = new Polygon();
        p6.addPoint(400, 95);
        p6.addPoint(480, 340);
        p6.addPoint(480, 150);
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

    public void cast(LineSegment seg,ArrayList<Rayo> result, double angulo, Point src, int dist, int n) {
        

        angulo = angulo %360;
        int cantRayos = RayCast.random.nextInt(20 - 10) + 10;// crea la cantidad de rayos random entre 30-80
        //cantRayos = 3;
        Point basePoint = null;

        if (dist > 0 && n < 3) {
            for (int i = 0; i < cantRayos; i++) {
                double angle_div;
                int new_dist;
                if (i == 0) {
                    angle_div = angulo; // crea el angulo del rayo principal
                    new_dist = dist;
                }else{
                    int min_Angulo = Math.abs((int)RayCast.calculateAngle(src, seg.A));
                    int max_Angulo = Math.abs((int)RayCast.calculateAngle(src, seg.B));

                    while (min_Angulo>=max_Angulo){
                        max_Angulo+=360;
                    }

                    angle_div = RayCast.random.nextInt((int) (max_Angulo) - (int) (min_Angulo)) + (int) (min_Angulo); // crea

                    new_dist = (int)RayCast.getDistRayoSecundario2(dist, src, angulo, angle_div);

                }
                if(n==0){
                    RayCast.DIST_TOTAL = dist;
                    //aqui empieza el sonar 
                }
                Point target = RayCast.calcularPunto(angle_div, src, new_dist); // calcula el punto destino del rayo
                LineSegment ray = new LineSegment(src, target);// crea el segmento del rayo
                Point ci = RayCast.getClosestIntersection(ray, activeSegments);// crea el punto de interseccion del rayo con
                //hay que obetner el punto más cercano pero que no contenga al punto
                if (ci != null) {
                    LineSegment interseco = RayCast.getSegmentIntersection(ray, activeSegments);//OBTENGO EL SEGMENTO CON EL QUE INTERSECO
                    new_dist = dist - (int) RayCast.distance(src, ci);
                    Rayo rayo = new Rayo(new LineSegment(src, ci), dist, angle_div);
                    if (interseco == sonar.Boca) {
                        int distRestante = ((int) RayCast.DIST_TOTAL - new_dist) / 2;
                        
                            Point point = RayCast.calcularPunto(sonar.angulo, sonar.D, distRestante);
                            // rayo.intensidad
                            if (RayCast.distance(point, sonar.D) > 5)
                             this.currentPixels.add(new Pixel(point, rayo.intensidad));
                        
                        
                    } else if (interseco==sonar.Lado1 || interseco==sonar.Lado2) {
                        return;
                    } else {
                        
                        Point reflected = RayCast.getRayReflectedTip(interseco, ray);
                        if (basePoint == null) basePoint = reflected;
                        double angTrue = RayCast.calculateAngle(ci, reflected);
                        
                        int intensidad = 100 - (int)RayCast.calcularAngulo(ci, reflected,basePoint) * 100 / 180 ;
                        intensidad = (int)RayCast.getDistRayoSecundario2(100, src, angulo, angle_div);
;
                        rayo.setIntensidad(intensidad);
                        result.add(rayo);
                        cast(interseco, result, angTrue, ci, new_dist, n + 1);//angulo - ang * 2
                    }
                } else {
                    result.add(new Rayo(ray, new_dist, angle_div)); // result.add(target);
                }
            }
        }
    }




    Sonar sonar = new Sonar(new Point(50, 150));

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (verPologonos) {
            g.setColor(Color.BLUE);
            for (Polygon p : activePolygons) {
                g.drawPolygon(p);
            }
        }
        
        g.setColor(Color.GREEN);
        g.drawPolygon(sonar.getPoligono());
        
        if (verRayos) {
            g.setColor(Color.RED);
            for (Rayo p : currentRays) {
                // g.drawLine((int)mousePos.x,(int)mousePos.y,(int)p.x,(int)p.y);
                // g.drawLine((int)sonar.getD().x,(int)sonar.getD().y,(int)p.x,(int)p.y);
                g.drawLine((int) p.linea.A.x, (int) p.linea.A.y, (int) p.linea.B.x, (int) p.linea.B.y);
                // g.fillOval((int)p.x-5,(int)p.y-5,10,10);
            }
        }
        
        for (Pixel pixel : currentPixels) {
            if (pixel.getColor().getRed()!=0 ){
                g.setColor(pixel.color);
                g.drawLine((int) pixel.point.x, (int) pixel.point.y, (int) pixel.point.x, (int) pixel.point.y);
            }


            
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
        if (b == KeyEvent.VK_SPACE) {
            this.verPologonos =! this.verPologonos;
        }
        if (b == KeyEvent.VK_C) {
            this.currentPixels.clear();
        }
        if (b == KeyEvent.VK_P) {
            this.verRayos =! this.verRayos;
        }
        

        //this.currentRays = castRays(sonar.Boca, sonar.getAngulo(), sonar.getD(), 50, 100);
        this.currentRays.clear();
        //System.out.println("a : " + sonar.getAngulo());
        this.activeSegments.add(sonar.Boca);
        this.activeSegments.add(sonar.Lado1);
        this.activeSegments.add(sonar.Lado2);
        System.out.println("-------------------------------");
        cast (sonar.Boca,this.currentRays, sonar.getAngulo(), sonar.getD(),(int)RayCast.DIST_MAX_RAYO,0);
        this.activeSegments.remove(sonar.Boca);
        this.activeSegments.remove(sonar.Lado1);
        this.activeSegments.remove(sonar.Lado2);
        RayCast.DIST_TOTAL = 300;
//System.out.println("------------------------");
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
