package com.paulok777;

public class Main {

    public static void main(String[] args) {
        SimplexTable table = new SimplexTable(getData(), 2, 2);
        SimplexSolver solver = new SimplexSolver(table);
        SimplexTable optimalTable = solver.solve();
        optimalTable.print();
        optimalTable.printCurrentTableSolution();
    }

    private static double[][] getData() {
        return new double[][] {
                { -12, -4, 0, 0,  0 },
                {  -2,  1, 1, 0,  5 },
                {   9,  4, 0, 1, 54 },
        };
    }
}
