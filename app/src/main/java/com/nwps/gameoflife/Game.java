package com.nwps.gameoflife;

import java.util.Random;

class Game {
    private int rows;
    private int cols;
    private int[][] grid;
    private int[][] nextGenerationGrid;

    Game(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    void init() {
        grid = new int[rows][cols];
        nextGenerationGrid = new int[rows][cols];
    }

    void fillGridRandomly(double density) throws WrongGridDensityException {
        if (density > 1.0 || density < 0.0)
            throw new WrongGridDensityException(density);
        double cells = density*rows*cols;
        aliveCellsOnInitRandomly((int)cells);
    }

    private void aliveCellsOnInitRandomly(int cells) {
        Random rand = new Random();
        while (cells > 0) {
            int r = rand.nextInt(rows);
            int c = rand.nextInt(cols);
            if (!isAlive(r, c)) {
                makeAliveOnInit(r, c);
                cells--;
            }
        }
    }

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }

    private void setRows(int rows) {
        this.rows = rows;
    }

    private void setCols(int cols) {
        this.cols = cols;
    }

    int[][] getGrid() {
        return grid;
    }

    void makeAliveOnInit(int row, int col) {
        grid[row][col] = 1;
        nextGenerationGrid[row][col] = 1;
    }

    void processCell(int row, int col) {
        int aliveNeighbours = countAliveNeighbours(row, col);
        if (!isAlive(row, col)) {
            if (aliveNeighbours == 3) makeAlive(row, col);
            return;
        }
        if (aliveNeighbours < 2 || aliveNeighbours > 3) killCell(row, col);
    }

    private void makeAlive(int row, int col) {
        nextGenerationGrid[row][col] = 1;
    }

    private int countAliveNeighbours(int row, int col) {
        int aliveCnt = 0;
        if (row-1 >= 0 && col-1 >= 0 && grid[row-1][col-1] == 1) aliveCnt++;
        if (row-1 >= 0 && grid[row-1][col] == 1) aliveCnt++;
        if (row-1 >= 0 && col+1 < cols && grid[row-1][col+1] == 1) aliveCnt++;
        if (col-1 >= 0 && grid[row][col-1] == 1) aliveCnt++;
        if (col+1 < cols && grid[row][col+1] == 1) aliveCnt++;
        if (row+1 < rows && col-1 >= 0 && grid[row+1][col-1] == 1) aliveCnt++;
        if (row+1 < rows && grid[row+1][col] == 1) aliveCnt++;
        if (row+1 < rows && col+1 < cols && grid[row+1][col+1] == 1) aliveCnt++;
        return aliveCnt;
    }

    private void killCell(int row, int col) {
        nextGenerationGrid[row][col] = 0;
    }

    private boolean isAlive(int row, int col) {
        return grid[row][col] == 1;
    }

    int[][] getNextGenerationGrid() {
        return nextGenerationGrid;
    }

    void createNextGeneration() {
        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                processCell(row, col);
        grid = nextGenerationGrid;
    }
}
