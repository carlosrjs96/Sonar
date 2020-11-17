/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raycasting;

import java.awt.Polygon;



/**
 *
 * @author Carlos
 */
public class Sonar {
    Polygon poligono = new Polygon();
    public Point A;
    public Point B;
    public Point C;
    public Point D;
    LineSegment Boca;
    LineSegment Lado1;
    LineSegment Lado2;
    LineSegment lineaCentral;
    double angulo;
    
    
    public Sonar( Point D) {
        this.angulo = 270;
        this.A = new Point(D.x - 20 , D.y);
        this.B = new Point(D.x + 20 , D.y);
        this.C = new Point(D.x , D.y+40);
        this.D = D;
        this.Boca = new LineSegment(A,B);
        this.Lado1 = new LineSegment(A,C);
        this.Lado2 = new LineSegment(B,C);
        this.lineaCentral = new LineSegment(D,C);
        actualizarPoligono();

        //System.out.println("ang : " + RayCast.calcularAngulo(Boca.dir, lineaCentral.dir));
    }
    
    public void cambiarPosicion(int direccion){
        int n = 1;
        if(direccion == 1){//IZQUIERDA
            this.A.x += -n; this.B.x += -n; this.C.x += -n; this.D.x += -n; 
        }else if(direccion == 2){//ARRIBA
            this.A.y += -n; this.B.y += -n; this.C.y += -n; this.D.y += -n;
        }else if(direccion == 3){//DERECHA
            this.A.x += n; this.B.x += n; this.C.x += n; this.D.x += n; 
        }else if(direccion == 4){//ABAJO
            this.A.y += n; this.B.y += n; this.C.y += n; this.D.y += n;
        }
        //actualiza los segmentos del sonar.
        actualizarSegmentos();
        //actualiza los puntos del poligono.
        actualizarPoligono();
    }
    
    
    public void rotarPosicion(){        
        //Rota el vértice D alrededor del punto C.
        this.setC( RayCast.rotarPuntos(this.angulo-180,getD(),getC()) );
        this.setA( RayCast.rotarPuntos(this.angulo-90,getD(),getA()) );
        this.setB( RayCast.rotarPuntos(this.angulo+90,getD(),getB()) );
        //actualiza los segmentos del sonar.
        actualizarSegmentos();
        //actualiza los puntos del poligono.
        actualizarPoligono();
        LineSegment l = new LineSegment(D, C);
        //System.out.println("ang rad : " + angulo);
    }
    
    public void cambiarAngulo(boolean lado){
        if(lado){
            this.angulo = this.angulo - 0.5;
        }else{
            this.angulo = this.angulo + 0.5;
        }
        this.rotarPosicion();
        //System.out.println("Angulo: "+this.angulo);
    }
    
    public void actualizarSegmentos() {
        this.Boca.actualizarDir(this.A,this.B);
        this.Lado1.actualizarDir(this.A,this.C);
        this.Lado2.actualizarDir(this.B,this.C);
    }
    
    public void actualizarPoligono() {
        poligono.reset();
        poligono.addPoint((int)A.x,(int)A.y);
        poligono.addPoint((int)B.x,(int)B.y);
        poligono.addPoint((int)C.x,(int)C.y);
        //poligono.addPoint((int)D.x,(int)D.y);
    }

    public Polygon getPoligono() {
        return poligono;
    }

    public void setPoligono(Polygon poligono) {
        this.poligono = poligono;
    }

    public Point getA() {
        return A;
    }

    public void setA(Point A) {
        this.A = A;
    }

    public Point getB() {
        return B;
    }

    public void setB(Point B) {
        this.B = B;
    }

    public Point getC() {
        return C;
    }

    public void setC(Point C) {
        this.C = C;
    }

    public Point getD() {
        return D;
    }

    public void setD(Point D) {
        this.D = D;
    }

    public LineSegment getLineaCentral() {
        return lineaCentral;
    }

    public void setLineaCentral(LineSegment lineaCentral) {
        this.lineaCentral = lineaCentral;
    }

    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }
    
    

    public LineSegment getBoca() {
        return Boca;
    }

    public void setBoca(LineSegment Boca) {
        this.Boca = Boca;
    }

    public LineSegment getLado1() {
        return Lado1;
    }

    public void setLado1(LineSegment Lado1) {
        this.Lado1 = Lado1;
    }

    public LineSegment getLado2() {
        return Lado2;
    }

    public void setLado2(LineSegment Lado2) {
        this.Lado2 = Lado2;
    }
    
    
    
}
