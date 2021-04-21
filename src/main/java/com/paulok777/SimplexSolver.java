package com.paulok777;

public class SimplexSolver {

    private SimplexTable table;

    public SimplexSolver(SimplexTable table) {
        this.table = table;
    }

    public SimplexTable solve() {
        while (isTableOptimal()) {
            int variableToPutInBasis = chooseColumnToPutInBasis();
            int variableToRemoveFromBasis = chooseRowToRemoveFromBasis();
            double[][] temp = buildNewMatrix(variableToPutInBasis, variableToRemoveFromBasis);
            fillMatrixByRectangleRule(variableToPutInBasis, variableToRemoveFromBasis, temp);
            table.setMatrix(temp);
        }

        return table;
    }

    private boolean isTableOptimal() {
        return false;
    }

    private int chooseColumnToPutInBasis() {
        return 0;
    }

    private int chooseRowToRemoveFromBasis() {
        return 0;
    }

    private double[][] buildNewMatrix(int variableToPutInBasis, int variableToRemoveFromBasis) {
        return new double[0][0];
    }

    private void fillMatrixByRectangleRule(int columnPositionOfLeadingElement,
                                           int rowPositionOfLeadingElement, double[][] temp) {

    }
}
