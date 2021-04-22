package com.paulok777;

import java.util.ArrayList;
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
        for (int i = numberOfVariables; i < numberOfLimitations + numberOfVariables; i++) {
            numbers[index++] = i;
        }

        indexesOfBasisVariables = new ArrayList<Integer>(Arrays.asList(numbers));
    }

    public SimplexTable(double[][] matrix, int numberOfVariables, int numberOfLimitations,
                        List<Integer> indexesOfBasisVariables) {
        this.matrix = matrix;
        this.numberOfVariables = numberOfVariables;
        this.numberOfLimitations = numberOfLimitations;
        this.indexesOfBasisVariables = indexesOfBasisVariables;
    }

    public void changeVariablesInBasis(int indexOfVariableToPutInBasis, int indexOfRowOfVariableToRemoveFromBasis) {
        indexesOfBasisVariables.set(indexOfRowOfVariableToRemoveFromBasis, indexOfVariableToPutInBasis);
    }

    public void printCurrentTableSolution() {
        System.out.println("Solution:");
        System.out.println("Target function: " + TARGET_FUNCTION + " = " + matrix[0][numberOfVariables + numberOfLimitations]);

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < numberOfVariables; i++) {
            if (indexesOfBasisVariables.contains(i)) {
                int rowOfVariable = indexesOfBasisVariables.indexOf(i) + 1;
                result.append(VARIABLE_NAME)
                        .append(i + 1)
                        .append(" = ")
                        .append(matrix[rowOfVariable][numberOfVariables + numberOfLimitations])
                        .append(", ");
            } else {
                result.append(VARIABLE_NAME).append(i + 1).append(" = 0, ");
            }
        }
        System.out.println(result.substring(0, result.length() - 2) + ".");
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
                    String numberString = String.valueOf(matrix[i][j - 1]);
                    if (numberString.contains(".") && numberString.indexOf(".") + 4 <= numberString.length()) {
                        numberString = numberString.substring(0, numberString.indexOf(".") + 4);
                    }
                    System.out.format(format, numberString);
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

    public String getColumnVariable(int index) {
        if (index < numberOfVariables) {
            return VARIABLE_NAME + ++index;
        } else {
            return SYNTHETIC_VARIABLE_NAME + (index - numberOfVariables + 1);
        }
    }

    public String getRowVariable(int index) {
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
                String numberString = String.valueOf(elem);
                if (numberString.contains(".") && numberString.indexOf(".") + 4 <= numberString.length()) {
                    numberString = numberString.substring(0, numberString.indexOf(".") + 4);
                }
                int numberLength = numberString.length();
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

}
