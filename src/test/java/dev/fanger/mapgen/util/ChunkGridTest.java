package dev.fanger.mapgen.util;

import dev.fanger.mapgen.map.Chunk;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ChunkGridTest {

    @Test
    public void testGrow() {
        ChunkGrid chunkGrid = new ChunkGrid();
        assertEquals(1, chunkGrid.getWidth());
        assertEquals(1, chunkGrid.getHeight());
        assertEquals(0, chunkGrid.getZeroX());
        assertEquals(0,chunkGrid.getZeroY());

        chunkGrid.growNorth();
        assertEquals(1, chunkGrid.getWidth());
        assertEquals(2, chunkGrid.getHeight());
        assertEquals(0, chunkGrid.getZeroX());
        assertEquals(1,chunkGrid.getZeroY());

        chunkGrid.growEast();
        assertEquals(2, chunkGrid.getWidth());
        assertEquals(2, chunkGrid.getHeight());
        assertEquals(0, chunkGrid.getZeroX());
        assertEquals(1,chunkGrid.getZeroY());

        chunkGrid.growSouth();
        assertEquals(2, chunkGrid.getWidth());
        assertEquals(3, chunkGrid.getHeight());
        assertEquals(0, chunkGrid.getZeroX());
        assertEquals(1,chunkGrid.getZeroY());

        chunkGrid.growWest();
        assertEquals(3, chunkGrid.getWidth());
        assertEquals(3, chunkGrid.getHeight());
        assertEquals(1, chunkGrid.getZeroX());
        assertEquals(1,chunkGrid.getZeroY());
    }

    @Test
    public void testAddAndGet() {
        ChunkGrid chunkGrid = new ChunkGrid();
        assertNull(chunkGrid.getChunk(-3, 5));

        Chunk testChunk = new Chunk(-3, 5, 5, null);
        chunkGrid.setChunk(-3, 5, testChunk);
        assertNotNull(chunkGrid.getChunk(-3, 5));

        Chunk anotherChunk = new Chunk(16, -2, 5, null);
        chunkGrid.setChunk(16, -2, anotherChunk);
        assertNotNull(chunkGrid.getChunk(16, -2));
    }

}
