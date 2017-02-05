package com.nwps.gameoflife;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class GameOfLifeUnitTest {

    private Game game;
    private int[][] greed;
    private int[][] nextGenerationGreed;

    @Before
    public void setUp() throws Exception {
        game = new Game(5, 5);
        game.init();
        greed = game.getGreed();
        nextGenerationGreed = game.getNextGenerationGreed();
    }

    @Test
    public void initFixedSizeGameTest() throws Exception {
        assertThat(game.getRows(), is(5));
        assertThat(game.getCols(), is(5));
        assertThat(greed[0][0], is(0));
        assertThat(greed[4][4], is(0));
    }

    @Test
    public void initAliveCellsTest() throws Exception {
        initAliveCellsForTestGame();
        assertThat(greed[1][1], is(1));
        assertThat(greed[1][2], is(1));
        assertThat(greed[2][1], is(1));
        assertThat(greed[2][3], is(1));
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
        assertThat(nextGenerationGreed[1][1], is(1));
        assertThat(nextGenerationGreed[1][2], is(1));
        assertThat(nextGenerationGreed[2][1], is(1));
        assertThat(nextGenerationGreed[2][3], is(0));
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
        assertThat(nextGenerationGreed[1][1], is(1));
        assertThat(nextGenerationGreed[1][2], is(1));
        assertThat(nextGenerationGreed[2][1], is(1));
        assertThat(nextGenerationGreed[2][3], is(0));
    }

    @Test
    public void ifLiveCellHaveMoreThenThreeLiveNeighbours_CellDiesOfOverpopulation() throws Exception {
        initAliveCellsForTestGame();
        intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        processBaseTestCells();
        processAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(nextGenerationGreed);
    }

    private void intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours() {
        game.makeAliveOnInit(0, 1);
        game.makeAliveOnInit(1, 0);
    }

    private void processAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours() {
        game.processCell(0, 1);
        game.processCell(1, 0);
    }

    private void assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(int[][] greed) {
        assertThat(greed[1][1], is(0));
        assertThat(greed[1][2], is(0));
        assertThat(greed[2][1], is(1));
        assertThat(greed[2][3], is(0));
        assertThat(greed[0][1], is(1));
        assertThat(greed[1][0], is(1));
    }

    @Test
    public void ifDeadCellHaveThreeAliveNeighbours_ItBecomesAliveByReproduction() throws Exception {
        initAliveCellsForTestGame();
        intiAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        processBaseTestCells();
        processAdditionalCellsForTestIfCellHaveMoreThenThreeNeighbours();
        processAdditionalCellsForTestIfDeadCellHaveThreeAliveNeighbours();
        assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(nextGenerationGreed);
        assertAdditionalCellsForIfDeadCellHaveThreeAliveNeighbours(nextGenerationGreed);
    }

    private void assertAdditionalCellsForIfDeadCellHaveThreeAliveNeighbours(int[][] greed) {
        assertThat(greed[0][0], is(1));
        assertThat(greed[0][2], is(1));
        assertThat(greed[2][0], is(1));
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
        greed = game.getGreed();
        assertLiveCellHaveMoreThenThreeLiveNeighboursRuleTestCases(greed);
        assertAdditionalCellsForIfDeadCellHaveThreeAliveNeighbours(greed);
        assertThat(greed[1][1], is(0));
        assertThat(greed[1][2], is(0));
        assertThat(greed[2][2], is(0));
        assertThat(greed[2][3], is(0));
        assertThat(greed[3][3], is(0));
        assertThat(greed[4][0], is(0));
        assertThat(greed[4][4], is(0));
    }
}