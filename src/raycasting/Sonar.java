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
        actualizarPoligono();
    }
    
    public void cambiarPosicion(int direccion){
        if(direccion == 1){//IZQUIERDA
            this.A.x += -10; this.B.x += -10; this.C.x += -10; this.D.x += -10; 
        }else if(direccion == 2){//ARRIBA
            this.A.y += -10; this.B.y += -10; this.C.y += -10; this.D.y += -10;
        }else if(direccion == 3){//DERECHA
            this.A.x += 10; this.B.x += 10; this.C.x += 10; this.D.x += 10; 
        }else if(direccion == 4){//ABAJO
            this.A.y += 10; this.B.y += 10; this.C.y += 10; this.D.y += 10;
        }
        //actualiza los segmentos del sonar.
        actualizarSegmentos();
        //actualiza los puntos del poligono.
        actualizarPoligono();
    }
    
    /*
    public void rotarPosicion(){        
        //Rota el vértice D alrededor del punto C.
        System.out.println("A -> X:"+getA().x+"/ Y:"+getA().y);
        System.out.println("B -> X:"+getB().x+"/ Y:"+getB().y);
        System.out.println("C -> X:"+getC().x+"/ Y:"+getC().y);
        System.out.println("D -> X:"+getD().x+"/ Y:"+getD().y);

        // newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
        getC().x = getD().x + (getC().x - getD().x) * Math.cos(Math.toRadians(this.angulo))-(getC().y - getD().y)* Math.sin(Math.toRadians(this.angulo));
        // newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);
        getC().y = getD().y + (getC().x - getD().x)* Math.sin(Math.toRadians(this.angulo)) + (getC().y - getD().y)* Math.cos(Math.toRadians(this.angulo));
        //-----------
        getA().x = getD().x + (getA().x - getD().x) * Math.cos(Math.toRadians(this.angulo))-(getA().y - getD().y)* Math.sin(Math.toRadians(this.angulo));
        getA().y = getD().y + (getA().x - getD().x)* Math.sin(Math.toRadians(this.angulo)) + (getA().y - getD().y)* Math.cos(Math.toRadians(this.angulo));
        //-----------
        getB().x = getD().x + (getB().x - getD().x) * Math.cos(Math.toRadians(this.angulo))-(getB().y - getD().y)* Math.sin(Math.toRadians(this.angulo));
        getB().y = getD().y + (getB().x - getD().x)* Math.sin(Math.toRadians(this.angulo)) + (getB().y - getD().y)* Math.cos(Math.toRadians(this.angulo));
        System.out.println("---------------------------------");
        System.out.println("A -> X:"+getA().x+"/ Y:"+getA().y);
        System.out.println("B -> X:"+getB().x+"/ Y:"+getB().y);
        System.out.println("C -> X:"+getC().x+"/ Y:"+getC().y);
        System.out.println("D -> X:"+getD().x+"/ Y:"+getD().y);
        //actualiza los segmentos del sonar.
        actualizarSegmentos();
        //actualiza los puntos del poligono.
        actualizarPoligono();
    }*/
    
    public void rotarPosicion(){        
        //Rota el vértice D alrededor del punto C.
        System.out.println("A -> X:"+getA().x+"/ Y:"+getA().y);
        System.out.println("B -> X:"+getB().x+"/ Y:"+getB().y);
        System.out.println("C -> X:"+getC().x+"/ Y:"+getC().y);
        System.out.println("D -> X:"+getD().x+"/ Y:"+getD().y);
        this.setC( this.rotarPuntos2(this.angulo,getD(),getC()) );
        this.setA( this.rotarPuntos2(this.angulo,getD(),getA()) );
        this.setB( this.rotarPuntos2(this.angulo,getD(),getB()) );
        System.out.println("---------------------------------");
        System.out.println("A -> X:"+getA().x+"/ Y:"+getA().y);
        System.out.println("B -> X:"+getB().x+"/ Y:"+getB().y);
        System.out.println("C -> X:"+getC().x+"/ Y:"+getC().y);
        System.out.println("D -> X:"+getD().x+"/ Y:"+getD().y);
        //actualiza los segmentos del sonar.
        actualizarSegmentos();
        //actualiza los puntos del poligono.
        actualizarPoligono();
    }
    
    public Point rotarPuntos(double _angulo,Point pCentral,Point pARotar){
        // newX = centerX + (point2x-centerX)*Math.cos(x) - (point2y-centerY)*Math.sin(x);
        double x = pCentral.x + (pARotar.x - pCentral.x) * Math.cos(Math.toRadians(_angulo))-(pARotar.y - pCentral.y)* Math.sin(Math.toRadians(_angulo));
        // newY = centerY + (point2x-centerX)*Math.sin(x) + (point2y-centerY)*Math.cos(x);
        double y = pCentral.y + (pARotar.x - pCentral.x)* Math.sin(Math.toRadians(_angulo)) + (pARotar.y - pCentral.y)* Math.cos(Math.toRadians(_angulo));
        return new Point(x,y);
    }
    
    public Point rotarPuntos2(double _angulo,Point pCentral,Point pARotar){
        //newX = centerX + ( cosX * (point2X-centerX) + sinX * (point2Y -centerY))
        //newY = centerY + ( -sinX * (point2X-centerX) + cosX * (point2Y -centerY))
        double x = pCentral.x +( Math.cos(Math.toRadians(_angulo)) * (pARotar.x - pCentral.x) + Math.sin(Math.toRadians(_angulo))*(pARotar.y - pCentral.y));
        double y = pCentral.y +( -Math.sin(Math.toRadians(_angulo)) * (pARotar.x - pCentral.x) + Math.cos(Math.toRadians(_angulo))*(pARotar.y - pCentral.y));
        return new Point(x,y);
    }
    
    
    
    public double rad_a_gra(double radianes){
        double grados = radianes * 180 / Math.PI; 
        return grados;
    }
    
    public void cambiarAngulo(boolean lado){
        if(lado){
            this.angulo = this.angulo - 10;
            this.rotarPosicion();
        }else{
            this.angulo = this.angulo + 10;
            this.setAngulo(getAngulo()*-1);
            this.rotarPosicion();
            this.setAngulo(getAngulo()*-1);
        }
        
    }
    
    public void actualizarSegmentos() {
        this.Boca.actualizarDir();
        this.Lado1.actualizarDir();
        this.Lado2.actualizarDir();
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
