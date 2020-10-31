package de_task.model.errors;

import de_task.model.base.Errors;
import de_task.model.base.NumericalMethod;
import de_task.model.base.Point;
import javafx.scene.chart.XYChart;

import java.util.List;

public class GlobalMethodsErrors extends Errors {

    private int n0, N;

    public GlobalMethodsErrors(){super();}

    public GlobalMethodsErrors(int n0, int N) {
        this.n0 = n0;
        this.N = N;
    }
    @Override
    public void calculateErrors(NumericalMethod method) {
        this.getErrors().clear();
        GlobalTruncationErrors localErrors = new GlobalTruncationErrors();
        for (Integer i = this.n0; i <= this.N; i++) {
            method.getSolution(method.getX0(), method.getY0(), method.getY0(), i);
            localErrors.calculateErrors(method);
            List<Point<Double>> tempLocalErrors = localErrors.getErrors();
            Point<Double> maxError = new Point<>(0.0, -1.0);
            for (int j = 0; j < tempLocalErrors.size(); j++) {
                if (tempLocalErrors.get(j).getY() > maxError.getY()) {
                    maxError = tempLocalErrors.get(j);
                }
            }
            this.getErrors().add(new Point<Double>(Double.valueOf(i), maxError.getY()));
        }
    }

    public void setInitialValues(int n0, int N) {
        this.n0 = n0;
        this.N = N;
    }
}
