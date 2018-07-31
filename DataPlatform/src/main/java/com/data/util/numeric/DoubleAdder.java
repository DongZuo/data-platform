package com.data.util.numeric;

import java.math.BigDecimal;

public class DoubleAdder implements BinaryOperator<Double> {
	@Override
	public Double identity() {
		return 0d;
	}

	@Override
	public Double operate(Double left, Double right) {
		BigDecimal dLeft = new BigDecimal(left.toString());
		BigDecimal dRight = new BigDecimal(right.toString());
		return dLeft.add(dRight).doubleValue();
	}
}
