package dev.fanger.mapgen;

import dev.fanger.mapgen.config.MapConfig;
import dev.fanger.mapgen.map.GameMap;
import dev.fanger.mapgen.util.ConfigLoader;
import org.json.JSONObject;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class TestMapGen {

    public static void main(String[] args) {
        try {
            JSONObject loadedJsonConfig = ConfigLoader.jsonFromResource("exampleConfig.json");
            System.out.println(loadedJsonConfig);
            MapConfig mapConfig = new MapConfig(loadedJsonConfig);
            System.out.println(mapConfig);

            long seed = 3456;
//            long seed = SeedGen.newSeed();
            // 4 seems to be the best gameMap chunk magnitude, 5 also seems ok
            // 6 and 7 still look good but are waaaay too big for a game
            GameMap gameMap = new GameMap(5, mapConfig, seed);

            JFrame viewerFrame = new JFrame();
            viewerFrame.setSize(800, 400);
            viewerFrame.setLocationRelativeTo(null);
            viewerFrame.setResizable(true);
            viewerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            viewerFrame.setLayout(new BorderLayout());

            GridViewer gridViewer = new GridViewer(gameMap);
            viewerFrame.setContentPane(gridViewer);
            viewerFrame.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    gridViewer.resetDrawingValues();
                }

                @Override
                public void componentMoved(ComponentEvent e) {

                }

                @Override
                public void componentShown(ComponentEvent e) {

                }

                @Override
                public void componentHidden(ComponentEvent e) {

                }
            });
            viewerFrame.setFocusable(true);
            viewerFrame.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    gridViewer.testAddChunks();
                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            viewerFrame.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
