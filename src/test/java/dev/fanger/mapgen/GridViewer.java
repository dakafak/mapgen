package dev.fanger.mapgen;

import dev.fanger.mapgen.map.Chunk;
import dev.fanger.mapgen.map.Map;
import dev.fanger.mapgen.map.Tile;

import javax.swing.JComponent;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridViewer extends JComponent implements ActionListener {

    private Map map;

    public GridViewer(Map map) {
        this.map = map;
        resetDrawingValues();
        Timer timer  = new Timer(100, this);
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
        tileDrawSize = chunkDrawSize / map.getChunkSize();

        int totalDrawingWidth = map.getChunkGrid().getWidth() * chunkDrawSize;
        gridStartX = middleX - totalDrawingWidth / 2;
        int totalDrawingHeight = map.getChunkGrid().getHeight() * chunkDrawSize;
        gridStartY = middleY - totalDrawingHeight / 2;
    }

    int startingGenerateX = 0;
    int startingGenerateY = 0;
    public void testAddChunks() {
        System.out.println("Growing the map");
        resetDrawingValues();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        for(int y = 0; y < map.getChunkGrid().getHeight(); y++) {
            for(int x = 0; x < map.getChunkGrid().getWidth(); x++) {
                Chunk currentChunk = map.getChunk(x, y);
                int chunkDrawingX = gridStartX + (x * chunkDrawSize);
                int chunkDrawingY = gridStartY + (y * chunkDrawSize);

                if(currentChunk != null) {
                    // Draw tiles
                    for (int tileY = 0; tileY < map.getChunkSize(); tileY++) {
                        for (int tileX = 0; tileX < map.getChunkSize(); tileX++) {
                            Tile currentTile = currentChunk.getTileGrid()[tileY][tileX];
                            g.setColor(currentTile.getTileConfig().getTileColor());
                            int tileDrawingX = chunkDrawingX + (tileX * tileDrawSize);
                            int tileDrawingY = chunkDrawingY + (tileY * tileDrawSize);
                            g.fillRect(tileDrawingX, tileDrawingY, tileDrawSize, tileDrawSize);
                            g.setColor(Color.WHITE);
                            g.drawRect(tileDrawingX, tileDrawingY, tileDrawSize, tileDrawSize);
                        }
                    }
                }

                // Draw chunk bounds
                if(map.getChunkGrid().getZeroX() == x
                && map.getChunkGrid().getZeroY() == y) {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.drawRect(chunkDrawingX, chunkDrawingY, chunkDrawSize, chunkDrawSize);
            }
        }
    }

}
