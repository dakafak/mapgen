package dev.fanger.mapgen;

import dev.fanger.mapgen.map.Chunk;
import dev.fanger.mapgen.map.Resource;
import dev.fanger.mapgen.map.location.Point;
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
    private ConcurrentLinkedQueue<Point> chunksToGenerate;

    public GridViewer(Map map) {
        this.map = map;
        this.chunksToGenerate = new ConcurrentLinkedQueue();
        resetDrawingValues();
        Timer timer  = new Timer(33, this);
        timer.start();
    }

    private double chunkDrawSize;
    private double tileDrawSize;
    private double resourceDrawSize;
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
        tileDrawSize = chunkDrawSize / map.getChunkSize();
        resourceDrawSize = chunkDrawSize / map.getChunkSize();

        int totalDrawingWidth = (int) Math.round(map.getChunkGrid().getWidth() * chunkDrawSize);
        gridStartX = middleX - totalDrawingWidth / 2;
        int totalDrawingHeight = (int) Math.round(map.getChunkGrid().getHeight() * chunkDrawSize);
        gridStartY = middleY - totalDrawingHeight / 2;
    }

    int currentRadius = 1;
    public void testAddChunks() {
        for(int y = -currentRadius; y <= currentRadius; y++) {
            for(int x = -currentRadius; x <= currentRadius; x++) {
                if(Math.abs(y) == currentRadius || Math.abs(x) == currentRadius) {
                    chunksToGenerate.add(new Point(x, y));
                }
            }
        }

        currentRadius++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < map.getChunkGrid().getWidth(); i++) {
            Point chunkPoint = chunksToGenerate.poll();
            if (chunkPoint != null) {
                map.generateChunk(chunkPoint.getX(), chunkPoint.getY());
                resetDrawingValues();
            }
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
                double chunkDrawingX = gridStartX + ((x + map.getChunkGrid().getZeroX()) * chunkDrawSize);
                double chunkDrawingY = gridStartY + ((y + map.getChunkGrid().getZeroY()) * chunkDrawSize);

                if(currentChunk != null) {
                    // Draw tiles
                    for (int tileY = 0; tileY < map.getChunkSize(); tileY++) {
                        for (int tileX = 0; tileX < map.getChunkSize(); tileX++) {
                            Tile currentTile = currentChunk.getTileGrid()[tileY][tileX];
                            if(currentTile != null) {
                                g.setColor(currentTile.getTileConfig().getTileColor());
                                int tileDrawingX = (int) Math.round(chunkDrawingX + (tileX * tileDrawSize));
                                int tileDrawingY = (int) Math.round(chunkDrawingY + (tileY * tileDrawSize));
                                g.fillRect(tileDrawingX, tileDrawingY, (int) Math.ceil(tileDrawSize), (int) Math.ceil(tileDrawSize));
                            }
                        }
                    }

                    // Draw resources
                    for (int resourceY = 0; resourceY < map.getChunkSize(); resourceY++) {
                        for (int resourceX = 0; resourceX < map.getChunkSize(); resourceX++) {
                            Resource currentResource = currentChunk.getResourceGrid()[resourceY][resourceX];
                            if(currentResource != null) {
                                g.setColor(currentResource.getResourceConfig().getResourceColor());
                                int tileDrawingX = (int) Math.round(chunkDrawingX + (resourceX * resourceDrawSize));
                                int tileDrawingY = (int) Math.round(chunkDrawingY + (resourceY * resourceDrawSize));
                                g.fillOval(tileDrawingX, tileDrawingY, (int) Math.ceil(resourceDrawSize), (int) Math.ceil(resourceDrawSize));
                            }
                        }
                    }

                    // Draw chunk bounds
                    if(x == 0 && y == 0) {
                        g.setColor(Color.RED);
                        g.drawRect((int) Math.round(chunkDrawingX), (int) Math.round(chunkDrawingY), (int) Math.round(chunkDrawSize), (int) Math.round(chunkDrawSize));
                    }

                }
            }
        }
    }

}
