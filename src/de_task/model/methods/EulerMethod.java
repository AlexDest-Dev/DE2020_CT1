package de_task.model.methods;

import de_task.model.base.NumericalMethod;

public class EulerMethod extends NumericalMethod {

    public EulerMethod(double x0, double y0, double X, int N){
        super(x0, y0, X, N);
    }
    public EulerMethod(){super();}
    @Override
    public Double getFunctionResult(Double x, Double... y) {
        Double temopDouble = NumericalMethod.mainFunction.getFunctionResult(x, y[0]);
        Double stepDouble = y[0] + this.getStep() * temopDouble;
        return stepDouble;
    }
}
