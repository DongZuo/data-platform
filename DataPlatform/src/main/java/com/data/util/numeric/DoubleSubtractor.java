package com.data.util.numeric;

public class DoubleSubtractor implements BinaryOperator<Double> {
	@Override
	public Double identity() {
		return 0d;
	}

	@Override
	public Double operate(Double left, Double right) {
		return left - right;
	}
}
