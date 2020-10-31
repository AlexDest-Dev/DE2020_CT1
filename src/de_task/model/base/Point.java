package de_task.model.base;

public class Point<T extends Number> {
    private T x;
    private T y;
    public Point(){

    }
    public Point(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(T x, T y) {
        this.x = x;
        this.y = y;
    }

    public T getX(){return x;}
    public T getY(){return y;}
}
