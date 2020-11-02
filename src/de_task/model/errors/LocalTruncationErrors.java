package de_task.model.errors;

import de_task.model.base.Errors;
import de_task.model.base.NumericalMethod;
import de_task.model.base.Point;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

import static java.lang.Math.abs;
import static java.lang.Math.tan;

public class LocalTruncationErrors extends Errors {
    public LocalTruncationErrors(){
        super();
    }
    @Override
    public void calculateErrors(NumericalMethod method) {
        this.getErrors().clear();
        List<Point<Double>> methodPoints = method.getGrid();
        //if (method.getX0() < method.getX()) Collections.reverse(methodPoints);
        this.getErrors().add(new Point<Double>(methodPoints.get(0).getX(), 0.0));
        for(int i = 1; i < methodPoints.size(); i++) {
            Double tempX = methodPoints.get(i-1).getX();
            Double exactSolutionInX = NumericalMethod
                    .exactFunction
                    .getFunctionResult(tempX);
            Double newYInExactY = method.getFunctionResult(tempX, exactSolutionInX);
            tempX = methodPoints.get(i).getX();
            exactSolutionInX = NumericalMethod.exactFunction.getFunctionResult(tempX);
            Point<Double> tempPoint =
                    new Point<>(methodPoints.get(i).getX(),
                    abs(exactSolutionInX - newYInExactY));
            this.getErrors().add(tempPoint);
        }
    }
}
