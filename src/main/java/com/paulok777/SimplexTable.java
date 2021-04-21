package com.paulok777;

import java.util.Arrays;
import java.util.List;

public class SimplexTable {

    private static final String VARIABLE_NAME = "X";
    private static final String SYNTHETIC_VARIABLE_NAME = "S";
    private static final String TARGET_FUNCTION = "Z";
    private static final String RESULT_COLUMN = "P";

    private double[][] matrix;
    private int numberOfVariables;
    private int numberOfLimitations;
    private List<Integer> indexesOfBasisVariables;

    public SimplexTable(double[][] matrix, int numberOfVariables, int numberOfLimitations) {
        this.matrix = matrix;
        this.numberOfVariables = numberOfVariables;
        this.numberOfLimitations = numberOfLimitations;

        Integer[] numbers = new Integer[numberOfLimitations];
        int index = 0;
        for (int i = numberOfLimitations; i < numberOfLimitations + numberOfVariables; i++) {
            numbers[index++] = i;
        }

        indexesOfBasisVariables = Arrays.asList(numbers);
    }

    public SimplexTable(double[][] matrix, int numberOfVariables, int numberOfLimitations,
                        List<Integer> indexesOfBasisVariables) {
        this.matrix = matrix;
        this.numberOfVariables = numberOfVariables;
        this.numberOfLimitations = numberOfLimitations;
        this.indexesOfBasisVariables = indexesOfBasisVariables;
    }

    public void printCurrentTableSolution() {

    }

    public void print() {
        int maxSize = findSizeOfTheLongestNumber();
        String format = " %" + maxSize + "s |";
        int horizontalLineLength = (maxSize + 3) * (numberOfVariables + numberOfLimitations + 2) + 1;

        printHorizontalLine(horizontalLineLength);
        printHeader(format);
        printHorizontalLine(horizontalLineLength);

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length + 1; j++) {
                if (j == 0) {
                    System.out.print("|");
                    if (i == 0) {
                        System.out.format(format, TARGET_FUNCTION);
                    } else {
                        System.out.format(format, getRowVariable(i - 1));
                    }
                } else {
                    System.out.format(format, matrix[i][j - 1]);
                }
            }
            System.out.println();
            printHorizontalLine(horizontalLineLength);
        }
    }

    private void printHeader(String format) {
        System.out.print("|");
        System.out.format(format, "");

        int variablesIndex = 0;
        int syntheticVariablesIndex = 0;
        for (int i = 0; i < matrix[0].length; i++) {
            if (variablesIndex < numberOfVariables) {
                variablesIndex++;
                String column = VARIABLE_NAME + variablesIndex;
                System.out.format(format, column);
            } else if (syntheticVariablesIndex < numberOfLimitations) {
                syntheticVariablesIndex++;
                String column = SYNTHETIC_VARIABLE_NAME + syntheticVariablesIndex;
                System.out.format(format, column);
            } else  {
                System.out.format(format, RESULT_COLUMN);
            }
        }
        System.out.println();
    }

    private void printHorizontalLine(int length) {
        for (int i = 0; i < length; i++) {
            System.out.print("-");
        }
        System.out.println();
    }

    private String getRowVariable(int index) {
        int variableIndexInRow = indexesOfBasisVariables.get(index);

        if (variableIndexInRow < numberOfVariables) {
            return VARIABLE_NAME + ++variableIndexInRow;
        } else {
            return SYNTHETIC_VARIABLE_NAME + (variableIndexInRow - numberOfVariables + 1);
        }
    }

    private int findSizeOfTheLongestNumber() {
        int maxSize = 0;
        for (double[] row : matrix) {
            for (double elem : row) {
                int numberLength = String.valueOf(elem).length();
                if (maxSize < numberLength) {
                    maxSize = numberLength;
                }
            }
        }
        return maxSize;
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(double[][] matrix) {
        this.matrix = matrix;
    }

    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    public int getNumberOfLimitations() {
        return numberOfLimitations;
    }

    public List<Integer> getIndexesOfBasisVariables() {
        return indexesOfBasisVariables;
    }
}
