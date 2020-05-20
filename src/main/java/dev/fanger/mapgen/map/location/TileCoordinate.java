package dev.fanger.mapgen.map.location;

import java.util.Objects;
import java.util.Set;

public class TileCoordinate {

    private int x;
    private int y;

    public TileCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileCoordinate that = (TileCoordinate) o;
        return x == that.x &&
                y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Gets a TileCoordinate from a specified location
     *
     * @param x
     * @param y
     * @return
     */
    public static TileCoordinate from(int x, int y) {
        return new TileCoordinate(x, y);
    }
}
