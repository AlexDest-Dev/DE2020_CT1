package de_task.model.base;

import javafx.scene.chart.XYChart;

import java.util.List;

public abstract class Errors<T extends NumericalMethod> extends Grid<Double> {

    public Errors() {
        super();
    }

    public List<Point<Double>> getErrors(){
        return this.getGrid();
    }

    public abstract void calculateErrors(T method);
}
