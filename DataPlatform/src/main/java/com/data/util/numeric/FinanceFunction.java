package com.data.util.numeric;

import java.lang.reflect.Array;

public class FinanceFunction {
	/**
	 * 等额本息计算
	 *
	 * @param interestRate  利率
	 * @param repayNumber   还款序号
	 * @param period        期数
	 * @param loanPrincipal 贷款本金
	 * @return 每月应还本金
	 */
	public static double ppmt(double interestRate, int repayNumber, int period, double loanPrincipal) {
		return loanPrincipal * interestRate * Math.pow(1 + interestRate, repayNumber - 1) / (Math.pow(1 + interestRate, period) - 1);
	}

	/**
	 * reduce
	 *
	 * @param numbers        数据
	 * @param binaryOperator 二元运算符
	 * @param <T>            数值类型
	 * @return 运算结果
	 */
	public static <T extends Number> T reduce(T[] numbers, BinaryOperator<T> binaryOperator) {
		T result = binaryOperator.identity();
		for (T number :
				numbers) {
			result = binaryOperator.operate(number, result);
		}

		return result;
	}

	/**
	 * 顺序操作
	 *
	 * @param numbers        数据
	 * @param binaryOperator 二元运算符
	 * @param <T>            数值类型
	 * @return 运算结果
	 */
	public static <T extends Number> T[] sequence(Class<T> type, T[] numbers, BinaryOperator<T> binaryOperator) {
		T[] result = (T[]) Array.newInstance(type, numbers.length);
		for (int i = 0; i < numbers.length; i++) {
			if (i == 0)
				result[i] = binaryOperator.operate(numbers[i], binaryOperator.identity());
			else
				result[i] = binaryOperator.operate(numbers[i], result[i - 1]);
		}

		return result;
	}

	/**
	 * 对位操作
	 *
	 * @param left           左数组
	 * @param right          右数组
	 * @param binaryOperator 操作符
	 * @param <T>            数值类型
	 * @return 运算结果
	 */
	public static <T extends Number> T[] align(Class<T> type, T[] left, T[] right, BinaryOperator<T> binaryOperator) {
		T[] result = (T[]) Array.newInstance(type, left.length);

		for (int i = 0; i < left.length; i++) {
			try {
				result[i] = binaryOperator.operate(left[i], right[i]);
			} catch (ArithmeticException e) {
				System.out.println("Align error!!!");
				result[i] = binaryOperator.identity();
			}
		}

		return result;
	}
}
