package de_task.model;

import de_task.model.base.IFunction;

import static java.lang.Math.*;

public class MainFunction implements IFunction<Double> {
    @Override
    public Double getFunctionResult(Double x, Double... y) {
        Double temp1 = 2 * sqrt(y[0]) * cos(x) / x;
        Double temp2 = 2 * y[0] / x;
        Double tempDouble = temp1 - temp2;
        return tempDouble;
    }
}
