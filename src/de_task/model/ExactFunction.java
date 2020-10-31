package de_task.model;

import de_task.model.base.IFunction;

import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

public class ExactFunction implements IFunction<Double> {
    Double initX, initY, constant;
    public ExactFunction(Double initX, Double initY) {
        this.initX = initX;
        this.initY = initY;
    }
    public ExactFunction(){}
    @Override
    public Double getFunctionResult(Double x, Double... y) {
        constant = calculateConstant(initX, initY);
        return (sin(x) + constant)*(sin(x) + constant)/(x*x);
    }

    public void setInitialValues(Double initX, Double initY) {
        this.initX = initX;
        this.initY = initY;
    }

    private Double calculateConstant(Double x, Double y) {
        return sqrt(x*x*y) - sin(x);
    }
}
