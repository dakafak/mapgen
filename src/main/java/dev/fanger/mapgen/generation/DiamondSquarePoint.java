package dev.fanger.mapgen.generation;

import dev.fanger.mapgen.map.location.TileCoordinate;

public class DiamondSquarePoint {

    private int x;
    private int y;
    private int squareSize;

    public DiamondSquarePoint(int x, int y, int squareSize) {
        this.x = x;
        this.y = y;
        this.squareSize = squareSize;
    }

    public int getHalfDistance() {
        return (squareSize - 1) / 2;
    }

    public TileCoordinate getDiamondPoint() {
        return TileCoordinate.from(x + getHalfDistance(), y + getHalfDistance());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistanceBetweenPoints() {
        return squareSize - 1;
    }

    public int getSquareSize() {
        return squareSize;
    }

    @Override
    public String toString() {
        return "DiamondSquarePoint{" +
                "x=" + x +
                ", y=" + y +
                ", squareSize=" + squareSize +
                '}';
    }
}
