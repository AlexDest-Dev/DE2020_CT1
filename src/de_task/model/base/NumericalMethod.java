package de_task.model.base;

import de_task.model.ExactFunction;
import de_task.model.MainFunction;

import java.util.List;


public abstract class NumericalMethod extends Grid implements IFunction<Double> {

    private int N;
    private double x0;
    private double y0;
    private double X;
    private double step;
    public static MainFunction mainFunction;
    public static ExactFunction exactFunction;

    public NumericalMethod() {
        super();
        exactFunction = new ExactFunction();
        mainFunction = new MainFunction();
    }

    public NumericalMethod(double x0, double y0, double X, int N) {
        super();
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.N = N;
        setStep();
        exactFunction = new ExactFunction(this.getX0(), this.getY0());
        mainFunction = new MainFunction();
    }

    private void updateInitValues(Double x0, Double y0, Double X, int N) {
        this.x0 = x0;
        this.y0 = y0;
        this.X = X;
        this.N = N;
        setStep();
        exactFunction.setInitialValues(x0, y0);
    }

    private void setStep() {
        if (N > 0) {
            step = (X - x0) / N;
        }
    }
    public double getX0() { return x0; }
    public void setX0(double x0) { this.x0 = x0; }

    public double getY0() { return y0; }
    public void setY0(double y0) { this.y0 = y0; }

    public double getX() { return X; }
    public void setX(double X) { this.X = X; }

    public int getN() { return N; }
    public void setN(int N) { this.N = N; }

    public double getStep() { return step;}

    protected void calculateByMethod() {
        Double x = this.getX0();
        Double y = this.getY0();
        this.addPoint(x, y);
        for (int i = 0; i < this.getN(); i++) {
            y = this.getFunctionResult(x, y);
            x += getStep();
            this.addPoint(x, y);
        }
    }

    public List<Point<Double>> getSolution(Double x0, Double y0, Double X, int N) {
        this.getGrid().clear();
        updateInitValues(x0, y0, X, N);
        calculateByMethod();
        return this.getGrid();
    }
}
