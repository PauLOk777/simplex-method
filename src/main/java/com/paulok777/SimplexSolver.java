package com.paulok777;

public class SimplexSolver {

    private SimplexTable table;
    private boolean maximization;

    public SimplexSolver(SimplexTable table, boolean maximization) {
        this.table = table;
        this.maximization = maximization;
    }

    public SimplexTable solve() {
        while (!isTableOptimal()) {
            table.print();
            table.printCurrentTableSolution();
            System.out.println("Simplex table isn't optimal, because it contains negative numbers in target function row.");
            System.out.println("Let's choose variable to put in basis.");
            int variableToPutInBasis = chooseColumnToPutInBasis();
            System.out.println("Let's choose variable to remove from basis.");
            int variableToRemoveFromBasis = chooseRowToRemoveFromBasis(variableToPutInBasis);
            System.out.println("Building new matrix and using rectangle rule...");
            double[][] temp = buildNewMatrix(variableToPutInBasis, variableToRemoveFromBasis);
            table.changeVariablesInBasis(variableToPutInBasis, variableToRemoveFromBasis - 1);
            fillMatrixByRectangleRule(variableToPutInBasis, variableToRemoveFromBasis, temp);
            table.setMatrix(temp);
        }

        return table;
    }

    private boolean isTableOptimal() {
        for (int i = 0; i < table.getMatrix()[0].length - 1; i++) {
            if ((maximization && table.getMatrix()[0][i] < 0)
                    ||(!maximization && table.getMatrix()[0][i] > 0)) {
                return false;
            }
        }
        return true;
    }

    private int chooseColumnToPutInBasis() {
        double maxValue = 0;
        int index = 0;

        for (int i = 0; i < table.getMatrix()[0].length - 1; i++) {
            if ((maximization && table.getMatrix()[0][i] < 0 && Math.abs(table.getMatrix()[0][i]) > maxValue)
                    || (!maximization && table.getMatrix()[0][i] > 0 && table.getMatrix()[0][i] > maxValue)) {
                maxValue = table.getMatrix()[0][i];
                index = i;
            }
        }

        System.out.println("The largest number by module is " + maxValue + ". It's " + table.getColumnVariable(index));
        return index;
    }

    private int chooseRowToRemoveFromBasis(int columnToPutInBasis) {
        double[][] matrix = table.getMatrix();
        int lastColumn = table.getNumberOfVariables() + table.getNumberOfLimitations();
        double minCalculation = Integer.MAX_VALUE;
        int rowIndex = 1;

        System.out.println("Choose minimum result of dividing result to positive element in column to put in basis:");
        for (int i = 0; i < table.getNumberOfLimitations(); i++) {
            if (matrix[i + 1][columnToPutInBasis] <= 0) {
                continue;
            }
            double calculation = matrix[i + 1][lastColumn] / matrix[i + 1][columnToPutInBasis];
            System.out.println(matrix[i + 1][lastColumn] + " / " + matrix[i + 1][columnToPutInBasis]
                    + " = " + calculation);
            if (calculation < minCalculation) {
                minCalculation = calculation;
                rowIndex = i + 1;
            }
        }

        System.out.println("The minimum number is " + minCalculation + ". It's " + table.getRowVariable(rowIndex - 1));
        return rowIndex;
    }

    private double[][] buildNewMatrix(int variableToPutInBasis, int variableToRemoveFromBasis) {
        double[][] matrix = table.getMatrix();
        int numberOfRows = table.getNumberOfLimitations() + 1;
        int numberOfColumns = table.getNumberOfVariables() + table.getNumberOfLimitations() + 1;
        double[][] temp = new double[numberOfRows][numberOfColumns];
        double leadingElement = matrix[variableToRemoveFromBasis][variableToPutInBasis];

        for (int i = 0; i < numberOfRows; i++) {
            if (i == variableToRemoveFromBasis) {
                temp[i][variableToPutInBasis] = 1;
            } else {
                temp[i][variableToPutInBasis] = 0;
            }
        }

        for (int i = 0; i < numberOfColumns; i++) {
            temp[variableToRemoveFromBasis][i] = matrix[variableToRemoveFromBasis][i] / leadingElement;
        }

        return temp;
    }

    private void fillMatrixByRectangleRule(int columnPositionOfLeadingElement,
                                           int rowPositionOfLeadingElement, double[][] temp) {
        double[][] matrix = table.getMatrix();
        double leadingElement = matrix[rowPositionOfLeadingElement][columnPositionOfLeadingElement];
        for (int i = 0; i < matrix.length; i++) {
            if (i == rowPositionOfLeadingElement) {
                continue;
            }
            for (int j = 0; j < matrix[i].length; j++) {
                if (j == columnPositionOfLeadingElement) {
                    continue;
                }
                temp[i][j] = (matrix[i][j] * leadingElement
                        - matrix[i][columnPositionOfLeadingElement] * matrix[rowPositionOfLeadingElement][j])
                        / leadingElement;
            }
        }
    }
}
