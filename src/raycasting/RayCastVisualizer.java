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



public class RayCastVisualizer extends JPanel implements MouseMotionListener, KeyListener{
     public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("RayCast Visualizer");
        window.setSize(640,380);

        RayCastVisualizer rcv = new RayCastVisualizer();

        window.add(rcv);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setVisible(true);
    }

    public RayCastVisualizer(){
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
    private void initPolygons(){

        //Border Polygon
        Polygon b = new Polygon();
        b.addPoint(0,0);
        b.addPoint(640,0);
        b.addPoint(640,360);
        b.addPoint(0,360);
        activePolygons.add(b);


        Polygon p1 = new Polygon();
        p1.addPoint(100,150);
        p1.addPoint(120,50);
        p1.addPoint(200,80);
        p1.addPoint(140,210);
        activePolygons.add(p1);

        Polygon p2 = new Polygon();
        p2.addPoint(100,200);
        p2.addPoint(120,250);
        p2.addPoint(60,300);
        activePolygons.add(p2);

        Polygon p3 = new Polygon();
        p3.addPoint(200,260);
        p3.addPoint(220,150);
        p3.addPoint(300,200);
        p3.addPoint(350,320);
        activePolygons.add(p3);

        Polygon p4 = new Polygon();
        p4.addPoint(340,60);
        p4.addPoint(360,40);
        p4.addPoint(370,70);
        activePolygons.add(p4);

        Polygon p5 = new Polygon();
        p5.addPoint(450,190);
        p5.addPoint(560,170);
        p5.addPoint(540,270);
        p5.addPoint(430,290);
        activePolygons.add(p5);

        Polygon p6 = new Polygon();
        p6.addPoint(400,95);
        p6.addPoint(580,50);
        p6.addPoint(480,150);
        p6.addPoint(400,95);
        activePolygons.add(p6);
    }

    ArrayList<LineSegment> activeSegments = new ArrayList<>();
    private void initSegments(){
        for(Polygon p : activePolygons){
            for(int i=0;i<p.npoints;i++){

                Point start = new Point(p.xpoints[i],p.ypoints[i]);
                Point end;
                if(i==p.npoints-1){
                    end = new Point(p.xpoints[0],p.ypoints[0]);
                }else{
                    end = new Point(p.xpoints[i+1],p.ypoints[i+1]);
                }
                activeSegments.add(new LineSegment(start,end));
                //System.out.println("new segment : " + start + " -> " + end);
            }
        }
    }

    //Point mousePos = new Point(1,1);

    ArrayList<Point> currentRays = new ArrayList<>();

    @Override
    public void mouseMoved(MouseEvent e) {
       /* mousePos = new Point(e.getX(),e.getY());
        currentRays = castRays(mousePos,50,100);
        repaint();*/
    }

    public ArrayList<Point> castRays(double angulo,Point src,int n,int dist){
        ArrayList<Point> result = new ArrayList<>();
        //double angle_div = 2 * Math.PI / n;
        //double angle_div = Math.toRadians(sonar.getAngulo())/n;
        double angle_div = Math.toRadians(angulo);
        //System.out.println("Angulo : " + angle_div);
        for (int i = 0; i < n; i++) {
            //System.out.println("Angulo X:"+(int)(src.x+Math.cos(angle_div*i)*dist)+" / Angulo Y:"+(int)(src.y+Math.sin(angle_div*i)*dist));
            //Point target = new Point((src.x+Math.cos(angle_div*i)*dist),(src.y+Math.sin(angle_div*i)*dist));//angle_div*i
            Point target = new Point((src.x+Math.cos(angle_div)*dist),(src.y+Math.sin(angle_div)*dist));//angle_div*i
            LineSegment ray = new LineSegment(src,target);
            Point ci = RayCast.getClosestIntersection(ray,activeSegments);
            if(ci != null){
                result.add(ci);
                //double ang = RayCast.getAngulo(activeSegments, ray);
                //castRays(ang,ci,1,100);
            }else{ 
                result.add(target);
            }
        }
        return result;
    }

    Sonar sonar = new Sonar(new Point(50,150));
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.WHITE);
        for(Polygon p : activePolygons){
            g.drawPolygon(p);
        }
        
        g.setColor(Color.GREEN);
        g.drawPolygon(sonar.getPoligono());

        g.setColor(Color.RED);
        for(Point p : currentRays){
            //g.drawLine((int)mousePos.x,(int)mousePos.y,(int)p.x,(int)p.y);
            g.drawLine((int)sonar.getD().x,(int)sonar.getD().y,(int)p.x,(int)p.y);
            g.fillOval((int)p.x-5,(int)p.y-5,10,10);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int b = e.getKeyCode();
        if(b == KeyEvent.VK_LEFT){
            this.sonar.cambiarPosicion(1);
        }
        if(b == KeyEvent.VK_UP){
            this.sonar.cambiarPosicion(2);
        }
        if(b == KeyEvent.VK_RIGHT){
            this.sonar.cambiarPosicion(3);
        }
        if(b == KeyEvent.VK_DOWN){
            this.sonar.cambiarPosicion(4);
        }
        if(b == KeyEvent.VK_R){
            this.sonar.cambiarAngulo(true);
            //System.out.println("rotar izquierda");
        }
        if(b == KeyEvent.VK_T){
            this.sonar.cambiarAngulo(false);
            //System.out.println("rotar derecha");
        }
        
        currentRays = castRays(sonar.getAngulo(),sonar.getD(),50,100);
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

}
