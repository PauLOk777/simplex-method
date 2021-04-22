package com.paulok777;

public class Main {

    public static void main(String[] args) {
        SimplexTable table = new SimplexTable(getData(), 2, 3);
        SimplexSolver solver = new SimplexSolver(table);
        SimplexTable optimalTable = solver.solve();
        System.out.println("It's optimal solution.");
        optimalTable.print();
        optimalTable.printCurrentTableSolution();
    }

    private static double[][] getData() {
        return new double[][] {
                { -4, -9, 0, 0, 0,  0 },
                { -4,  3, 1, 0, 0, 11 },
                { -1,  2, 0, 1, 0,  2 },
                {  2,  1, 0, 0, 1,  5 },
        };
    }
}
