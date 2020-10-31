package de_task.model.base;

public interface IFunction <T extends Number> {
    public T getFunctionResult(T x, T... y);
}
