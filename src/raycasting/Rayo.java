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
public class Rayo {
    LineSegment linea;
    double distanciaInicial;//cuanta energia arranco  //1000 //770 //370 ---//800|80% //600|60% //450|45%
    double distanciaRestante;//cuanta energia termino //770 //370 //270**---//600|60% //450|45% //200|20%
    int intensidad;
    double angulo;
    public final int dist = 500;

    public Rayo(LineSegment linea, double distancia,double angulo) {
        this.linea = linea;
        this.distanciaInicial = distancia;
        this.distanciaRestante = distancia - RayCast.distance(this.linea.A, this.linea.B);
        this.intensidad = this.asignarIntensidad();
        //System.out.println("Intesidad>>"+ this.intensidad);
        this.angulo = angulo;
    }  
    // 1000   distrestante
    // 100      x
    public int asignarIntensidad(){ 
        //System.out.println("distancia: "+distanciaRestante);
        //System.out.println("MAX: "+((int)RayCast.DIST_MAX_RAYO));
        int intensidad = (int)(this.distanciaRestante * 100) / ((int)RayCast.DIST_MAX_RAYO);
        //System.out.println("intensidad: " + intensidad);
        return intensidad;
    }

    public LineSegment getLinea() {
        return linea;
    }

    public void setLinea(LineSegment linea) {
        this.linea = linea;
    }

    public double getDistanciaInicial() {
        return distanciaInicial;
    }

    public void setDistanciaInicial(double distanciaInicial) {
        this.distanciaInicial = distanciaInicial;
    }

    public double getDistanciaRestante() {
        return distanciaRestante;
    }

    public void setDistanciaRestante(double distanciaRestante) {
        this.distanciaRestante = distanciaRestante;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    public double getAngulo() {
        return angulo;
    }

    public void setAngulo(double angulo) {
        this.angulo = angulo;
    }
}
