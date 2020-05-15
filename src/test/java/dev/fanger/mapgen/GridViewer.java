package dev.fanger.mapgen;

import dev.fanger.mapgen.map.Chunk;
import dev.fanger.mapgen.map.ChunkCoordinate;
import dev.fanger.mapgen.map.Map;
import dev.fanger.mapgen.map.Tile;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GridViewer extends JComponent implements ActionListener {

    private Map map;
    private ConcurrentLinkedQueue<ChunkCoordinate> chunksToGenerate;

    public GridViewer(Map map) {
        this.map = map;
        this.chunksToGenerate = new ConcurrentLinkedQueue();
        resetDrawingValues();
        Timer timer  = new Timer(33, this);
        timer.start();
    }

    private int chunkDrawSize;
    private int tileDrawSize;
    private int middleX;
    private int middleY;
    private int gridStartX;
    private int gridStartY;
    public void resetDrawingValues() {
        middleX = getWidth() / 2;
        middleY = getHeight() / 2;

        int drawingWidth = Math.round(getWidth() / map.getChunkGrid().getWidth());
        int drawingHeight = Math.round(getHeight() / map.getChunkGrid().getHeight());

        if(drawingWidth <= drawingHeight) {
            chunkDrawSize = drawingWidth;
        } else {
            chunkDrawSize = drawingHeight;
        }
        tileDrawSize = (int) Math.round((double) chunkDrawSize / map.getChunkSize());

        int totalDrawingWidth = map.getChunkGrid().getWidth() * chunkDrawSize;
        gridStartX = middleX - totalDrawingWidth / 2;
        int totalDrawingHeight = map.getChunkGrid().getHeight() * chunkDrawSize;
        gridStartY = middleY - totalDrawingHeight / 2;
    }

    int currentRadius = 1;
    public void testAddChunks() {
        for(int y = -currentRadius; y <= currentRadius; y++) {
            for(int x = -currentRadius; x <= currentRadius; x++) {
                if(Math.abs(y) == currentRadius || Math.abs(x) == currentRadius) {
                    chunksToGenerate.add(new ChunkCoordinate(x, y));
                }
            }
        }

        currentRadius++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ChunkCoordinate chunkCoordinate = chunksToGenerate.poll();
        if(chunkCoordinate != null) {
            map.generateChunk(chunkCoordinate.getX(), chunkCoordinate.getY());
            resetDrawingValues();
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for(int y = map.getChunkGrid().getFirstY(); y < map.getChunkGrid().getLastY(); y++) {
            for(int x = map.getChunkGrid().getFirstX(); x < map.getChunkGrid().getLastX(); x++) {
                Chunk currentChunk = map.getChunk(x, y);
                int chunkDrawingX = gridStartX + ((x + map.getChunkGrid().getZeroX()) * chunkDrawSize);
                int chunkDrawingY = gridStartY + ((y + map.getChunkGrid().getZeroY()) * chunkDrawSize);

                if(currentChunk != null) {
                    // Draw tiles
                    for (int tileY = 0; tileY < map.getChunkSize(); tileY++) {
                        for (int tileX = 0; tileX < map.getChunkSize(); tileX++) {
                            Tile currentTile = currentChunk.getTileGrid()[tileY][tileX];
                            if(currentTile != null) {
                                g.setColor(currentTile.getTileConfig().getTileColor());
                                int tileDrawingX = chunkDrawingX + (tileX * tileDrawSize);
                                int tileDrawingY = chunkDrawingY + (tileY * tileDrawSize);
                                g.fillRect(tileDrawingX, tileDrawingY, tileDrawSize, tileDrawSize);
                            }
                        }
                    }

                    // Draw chunk bounds
                    if(x == 0 && y == 0) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(currentChunk.getRegionConfig().getRegionColor());
                    }
                    g.drawRect(chunkDrawingX, chunkDrawingY, chunkDrawSize, chunkDrawSize);
                }
            }
        }
    }

}
