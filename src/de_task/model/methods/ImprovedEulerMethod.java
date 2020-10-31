package de_task.model.methods;

import de_task.model.base.NumericalMethod;

public class ImprovedEulerMethod extends NumericalMethod {
    public ImprovedEulerMethod(){super();}
    public ImprovedEulerMethod(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }

    @Override
    public Double getFunctionResult(Double x, Double... y) {

        double step = this.getStep();
        double interMainFunction = NumericalMethod.mainFunction.getFunctionResult(x, y[0]);
        return y[0] + (step / 2) * (interMainFunction
                + NumericalMethod.mainFunction.getFunctionResult(x + step, y[0]
                + step * interMainFunction));
    }
}
