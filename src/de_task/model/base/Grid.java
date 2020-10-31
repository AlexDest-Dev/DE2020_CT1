package de_task.model.base;

import java.util.ArrayList;
import java.util.List;

public class Grid<T extends Number> {
    private List<Point<T>> grid;

    public Grid() {
        grid = new ArrayList<>();
    }

    public void addPoint(T x, T y) {
        grid.add(new Point<>(x, y));
    }

    public void addPoint(Point<T> point) {
        grid.add(point);
    }

    public List<Point<T>> getGrid() {
        return grid;
    }

    public Point<T> getPoint(int index) {
        return grid.get(index);
    }

}
