package com.data.util.numeric;

public interface BinaryOperator<T extends Number> {
	T identity();

	T operate(T left, T right);
}
