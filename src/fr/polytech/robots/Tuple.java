package fr.polytech.robots;

import java.net.Inet4Address;

public class Tuple {

    private int x;
    private int y;

    public Tuple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(Integer.toString(x) + Integer.toString(y));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple))
            return false;

        Tuple tuple = (Tuple) obj;

        return tuple.getX() == x && tuple.getY() == y;
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}