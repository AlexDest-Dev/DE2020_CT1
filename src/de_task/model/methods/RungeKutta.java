package de_task.model.methods;

import de_task.model.base.NumericalMethod;

public class RungeKutta extends NumericalMethod {
    public RungeKutta(){super();}
    public RungeKutta(double x0, double y0, double X, int N) {
        super(x0, y0, X, N);
    }

    @Override
    public Double getFunctionResult(Double x, Double... y) {
        double step = this.getStep();
        double k1 = mainFunction.getFunctionResult(x, y[0]);
        double k2 = mainFunction.getFunctionResult(x+step/2, y[0]+(step/2)*k1);
        double k3 = mainFunction.getFunctionResult(x+step/2, y[0]+(step/2)*k2);
        double k4 = mainFunction.getFunctionResult(x+step, y[0]+step*k3);
        return y[0] + (step/6)*(k1+2*k2+2*k3+k4);
    }
}
