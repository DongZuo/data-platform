package com.data.util.numeric;

public class DoubleDivider implements BinaryOperator<Double> {
	@Override
	public Double identity() {
		return 1d;
	}

	@Override
	public Double operate(Double left, Double right) {
		return left / right;
	}
}
