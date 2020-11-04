/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raycasting;

import java.awt.Color;

/**
 *
 * @author Carlos
 */
public class Pixel {
    public Point point;
    public Color color;

    public Pixel(Point point, int intensidad) {
        this.point = point;
        this.color = RayCast.getColor(intensidad);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
}
