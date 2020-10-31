package de_task.model.errors;

import de_task.model.base.Errors;
import de_task.model.base.NumericalMethod;
import de_task.model.base.Point;
import javafx.scene.chart.XYChart;

import java.util.List;

import static java.lang.Math.abs;

public class GlobalTruncationErrors extends Errors {

    public GlobalTruncationErrors(){super();}

    @Override
    public void calculateErrors(NumericalMethod method) {
        this.getErrors().clear();
        List<Point<Double>> methodPoints = method.getGrid();
        for(int i = 0; i < methodPoints.size(); i++) {
            Double tempX = methodPoints.get(i).getX();
            Double exactY = NumericalMethod.exactFunction.getFunctionResult(tempX);
            Double tempY = methodPoints.get(i).getY();
            Point<Double> tempPoint = new Point<>(tempX, abs(exactY - tempY));
            this.getErrors().add(tempPoint);
        }
    }
}
