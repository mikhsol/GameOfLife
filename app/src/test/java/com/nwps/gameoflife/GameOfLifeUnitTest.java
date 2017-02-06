package com.nwps.gameoflife;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameOfLifeUnitTest {

    private Game game;
    private int[][] grid;
    private int[][] nextGenerationgrid;

    @Before
    public void setUp() throws Exception {
        game = new Game(5, 5);
        game.init();
        grid = game.getGrid();
        nextGenerationgrid = game.getNextGenerationGrid();
    }

    @Test
    public void initFixedSizeGameTest() throws Exception {
        assertThat(game.getRows(), is(5));
        assertThat(game.getCols(), is(5));
        assertThat(grid[0][0], is(0));
        assertThat(grid[4][4], is(0));
    }

    @Test
    public void initAliveCellsTest() throws Exception {
        initAliveCellsForTestGame();
        assertThat(grid[1][1], is(1));
        assertThat(grid[1][2], is(1));
        assertThat(grid[2][1], is(1));
        assertThat(grid[2][3], is(1));
    }

    private void initAliveCellsForTestGame() {
        game.makeAliveOnInit(1, 1);
        game.makeAliveOnInit(1, 2);
        game.makeAliveOnInit(2, 1);
        game.makeAliveOnInit(2, 3);
    }

    @Test
    public void ifLiveCellHaveLessThenTwoLiveNeighbours_CellDies() throws Exception {
        initAliveCellsForTestGame();
        processBaseTestCells();
        assertThat(nextGenerationgrid[1][1], is(1));
        assertThat(nextGenerationgrid[1][2], is(1));
        assertThat(nextGenerationgrid[2][1], is(1));
        assertThat(nextGenerationgrid[2][3], is(0));
    }

    private void processBaseTestCells() {
        game.processCell(1, 1);
        game.processCell(1, 2);
        game.processCell(2, 1);
        game.processCell(2, 3);
    }

    @Test
    public void ifLiveCellHaveTwoOrThreeLiveNeighbours_CellAliveInNextGeneration() throws Exception {
        initAliveCellsForTestGame();
        processBaseTestCells();
        assertThat(nextGenerationgrid[1][1], is(1));
        assertThat(nextGenerationgrid[1][2], is(1));
        assertThat(nextGenerationgrid[2][1], is(1));
        assertThat(nextGenerationgrid[2][3], is(0));
    }

    @Test
    public void ifLiveCellHaveMoreThenThreeLiveNeighbours_CellDiesOfOverpopulation() throws Exception {
        initAliveCellsForTestGame();
        intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        processBaseTestCells();
        processAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(nextGenerationgrid);
    }

    private void intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours() {
        game.makeAliveOnInit(0, 1);
        game.makeAliveOnInit(1, 0);
    }

    private void processAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours() {
        game.processCell(0, 1);
        game.processCell(1, 0);
    }

    private void assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(int[][] grid) {
        assertThat(grid[1][1], is(0));
        assertThat(grid[1][2], is(0));
        assertThat(grid[2][1], is(1));
        assertThat(grid[2][3], is(0));
        assertThat(grid[0][1], is(1));
        assertThat(grid[1][0], is(1));
    }

    @Test
    public void ifDeadCellHaveThreeAliveNeighbours_ItBecomesAliveByReproduction() throws Exception {
        initAliveCellsForTestGame();
        intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        processBaseTestCells();
        processAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        processAdditionalCellsForTestIfDeadCellHaveThreeAliveNeighbours();
        assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(nextGenerationgrid);
        assertAdditionalCellsForIfDeadCellHaveThreeAliveNeighbours(nextGenerationgrid);
    }

    private void assertAdditionalCellsForIfDeadCellHaveThreeAliveNeighbours(int[][] grid) {
        assertThat(grid[0][0], is(1));
        assertThat(grid[0][2], is(1));
        assertThat(grid[2][0], is(1));
    }

    private void processAdditionalCellsForTestIfDeadCellHaveThreeAliveNeighbours() {
        game.processCell(0, 0);
        game.processCell(0, 2);
        game.processCell(2, 0);
    }

    @Test
    public void createNextGeneration() throws Exception {
        initAliveCellsForTestGame();
        intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        game.createNextGeneration();
        grid = game.getGrid();
        assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(grid);
        assertAdditionalCellsForIfDeadCellHaveThreeAliveNeighbours(grid);
        assertThat(grid[1][1], is(0));
        assertThat(grid[1][2], is(0));
        assertThat(grid[2][2], is(0));
        assertThat(grid[2][3], is(0));
        assertThat(grid[3][3], is(0));
        assertThat(grid[4][0], is(0));
        assertThat(grid[4][4], is(0));
    }
}