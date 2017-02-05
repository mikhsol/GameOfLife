package com.nwps.gameoflife;


class WrongGridDensityException extends GameException {
    WrongGridDensityException(double density) {
        System.out.printf("Incorrect grid density: %f\n", density);
    }
}
