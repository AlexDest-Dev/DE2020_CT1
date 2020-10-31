package de_task.model.methods;

import de_task.model.ExactFunction;
import de_task.model.base.NumericalMethod;
import javafx.scene.chart.XYChart;

public class ExactSolution extends NumericalMethod {

    public ExactSolution(){super();}
    public ExactSolution(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }

    @Override
    public Double getFunctionResult(Double x, Double... y) {
        return exactFunction.getFunctionResult(x, y[0]);
    }

    @Override
    protected void calculateByMethod() {
        double x = this.getX0();
        double y = this.getY0();
        this.addPoint(x, y);
        for (int i = 0; i < this.getN(); i++) {
            x += getStep();
            y = this.getFunctionResult(x, y);
            this.addPoint(x, y);
        }
    }
}
