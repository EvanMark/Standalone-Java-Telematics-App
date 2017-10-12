package basics;

import java.io.Serializable;

public class Coordinates implements Serializable {

    private String type;
    private double x;
    private double y;

    public Coordinates(String type, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public String getType() {
        return type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public void setX(double newX) {
        this.x = newX;
    }

    public void setY(double newY) {
        this.y = newY;
    }

}
