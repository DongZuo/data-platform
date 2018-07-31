package com.data.util.numeric;

import java.lang.reflect.Array;

public class Matrix<T extends Number> {
	private final Class<T> type;
	private T[][] matrix;
	private int xSize;
	private int ySize;

	public Matrix(Class<T> type, int xSize, int ySize) {
		this.type = type;
		this.xSize = xSize;
		this.ySize = ySize;
		this.matrix = (T[][]) Array.newInstance(type, xSize, ySize);
	}

	public int getxSize() {
		return xSize;
	}

	public int getySize() {
		return ySize;
	}

	public void setCell(int x, int y, T value) {
		matrix[x][y] = value;
	}

	public T[] getRow(int x) {
		T[] row = (T[]) Array.newInstance(type, ySize);
		System.arraycopy(matrix[x], 0, row, 0, ySize);
		return row;
	}

	public void setRow(int x, T[] row) {
		System.arraycopy(row, 0, matrix[x], 0, row.length);
	}

	public void setColumn(int y, T[] column) {
		int columnLength = column.length;
		if (columnLength > xSize)
			throw new ArrayIndexOutOfBoundsException(String.format("Column length %d exceeded matrix size (%d,%d)", columnLength, xSize, ySize));
		else {
			for (int x = 0; x < xSize; x++) {
				matrix[x][y] = column[x];
			}
		}
	}

	public T[] getColumn(int y) {
		T[] column = (T[]) Array.newInstance(type, xSize);
		for (int i = 0; i < xSize; i++) {
			column[i] = matrix[i][y];
		}
		return column;
	}

	public T[] applyOnX(BinaryOperator<T> operator) {
		T[] result = (T[]) Array.newInstance(type, ySize);

		for (int i = 0; i < result.length; i++) {
			result[i] = operator.identity();
		}
		for (T[] row :
				matrix) {
			result = FinanceFunction.align(type, row, result, operator);
		}

		return result;
	}

	public T[] applyOnY(BinaryOperator<T> operator) {
		Matrix<T> transposed = transpose();
		return transposed.applyOnX(operator);
	}

	public Matrix<T> transpose() {
		Matrix<T> transposed = new Matrix<>(type, ySize, xSize);

		for (int y = 0; y < xSize; y++) {
			T[] column = getRow(y);
			transposed.setColumn(y, column);
		}

		return transposed;
	}
}
