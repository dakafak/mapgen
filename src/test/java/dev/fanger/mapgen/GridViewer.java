package dev.fanger.mapgen;

import dev.fanger.mapgen.config.ResourceConfig;
import dev.fanger.mapgen.map.Chunk;
import dev.fanger.mapgen.map.GameMap;
import dev.fanger.mapgen.map.location.TileCoordinate;
import dev.fanger.mapgen.map.Tile;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class GridViewer extends JComponent implements ActionListener {

    private GameMap gameMap;
    private ConcurrentLinkedQueue<TileCoordinate> chunksToGenerate;

    public GridViewer(GameMap gameMap) {
        this.gameMap = gameMap;
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

        int drawingWidth = Math.round(getWidth() / gameMap.getChunkGrid().getWidth());
        int drawingHeight = Math.round(getHeight() / gameMap.getChunkGrid().getHeight());

        if(drawingWidth <= drawingHeight) {
            chunkDrawSize = drawingWidth;
        } else {
            chunkDrawSize = drawingHeight;
        }
        tileDrawSize = chunkDrawSize / gameMap.getChunkSize();
        resourceDrawSize = chunkDrawSize / gameMap.getChunkSize();

        int totalDrawingWidth = (int) Math.round(gameMap.getChunkGrid().getWidth() * chunkDrawSize);
        gridStartX = middleX - totalDrawingWidth / 2;
        int totalDrawingHeight = (int) Math.round(gameMap.getChunkGrid().getHeight() * chunkDrawSize);
        gridStartY = middleY - totalDrawingHeight / 2;
    }

    int currentRadius = 1;
    public void testAddChunks() {
        for(int y = -currentRadius; y <= currentRadius; y++) {
            for(int x = -currentRadius; x <= currentRadius; x++) {
                if(Math.abs(y) == currentRadius || Math.abs(x) == currentRadius) {
                    chunksToGenerate.add(TileCoordinate.from(x, y));
                }
            }
        }

        currentRadius++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < gameMap.getChunkGrid().getWidth(); i++) {
            TileCoordinate chunkTileCoordinate = chunksToGenerate.poll();
            if (chunkTileCoordinate != null) {
                gameMap.generateChunk(chunkTileCoordinate.getX(), chunkTileCoordinate.getY());
                resetDrawingValues();
            }
        }

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for(int y = gameMap.getChunkGrid().getFirstY(); y < gameMap.getChunkGrid().getLastY(); y++) {
            for(int x = gameMap.getChunkGrid().getFirstX(); x < gameMap.getChunkGrid().getLastX(); x++) {
                Chunk currentChunk = gameMap.getChunk(x, y);
                double chunkDrawingX = gridStartX + ((x + gameMap.getChunkGrid().getZeroX()) * chunkDrawSize);
                double chunkDrawingY = gridStartY + ((y + gameMap.getChunkGrid().getZeroY()) * chunkDrawSize);

                if(currentChunk != null) {
                    // Draw tiles
                    for (int tileY = 0; tileY < gameMap.getChunkSize(); tileY++) {
                        for (int tileX = 0; tileX < gameMap.getChunkSize(); tileX++) {
                            Tile currentTile = currentChunk.getTileGrid()[tileY][tileX];
                            if(currentTile != null) {
                                g.setColor(currentTile.getTileConfig().getTileColor());
                                int tileDrawingX = (int) Math.round(chunkDrawingX + (tileX * tileDrawSize));
                                int tileDrawingY = (int) Math.round(chunkDrawingY + (tileY * tileDrawSize));
                                g.fillRect(tileDrawingX, tileDrawingY, (int) Math.ceil(tileDrawSize), (int) Math.ceil(tileDrawSize));
                            }

                            // Draw resources
                            ResourceConfig resourceConfig = currentTile.getResourceConfig();
                            if(resourceConfig != null) {
                                g.setColor(resourceConfig.getResourceColor());
                                int tileDrawingX = (int) Math.round(chunkDrawingX + (tileX * resourceDrawSize));
                                int tileDrawingY = (int) Math.round(chunkDrawingY + (tileY * resourceDrawSize));
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
