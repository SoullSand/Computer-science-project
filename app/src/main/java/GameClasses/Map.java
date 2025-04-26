package GameClasses;

import android.content.Context;

import java.util.Random;

import Tiles.BombTile;
import Tiles.EmptyTile;
import Tiles.NumberTile;
import Tiles.Tile;

public class Map {
    private Tile[][] tiles;
    private int xMapSize, yMapSize;

    private Context context;

    public Map(int xMapSize, int yMapSize, Context context) {
        this.xMapSize = xMapSize;
        this.yMapSize = yMapSize;
        tiles = new Tile[xMapSize][yMapSize];
        this.context = context;
    }

    // generates a new map
    public void generateMap(int amountOfBombs) {
        generateEmptyMap();
        generateBombs(amountOfBombs);
    }

    // sets the array tiles
    private void generateEmptyMap() {
        for (int i = 0; i < xMapSize; i++) {
            for (int j = 0; j < yMapSize; j++) {
                tiles[i][j] = new EmptyTile(i, j, context);
            }
        }
    }

    // adds the bombs to the array
    private void generateBombs(int amountOfBombs) {
        Random rnd = new Random();
        for (int i = 0; i < amountOfBombs; i++) {
            int x = rnd.nextInt(xMapSize);
            int y = rnd.nextInt(yMapSize);
            while (tiles[x][y] instanceof BombTile) {
                x = rnd.nextInt(xMapSize);
                y = rnd.nextInt(yMapSize);
            }
            tiles[x][y] = new BombTile(x, y, context);
            addSurroundingNumbers(x, y);
        }
    }

    public Tile[][] getMap() {
        return tiles;
    }


    // Adds the numbers around a bomb
    private void addSurroundingNumbers(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < xMapSize && j >= 0 && j < yMapSize) {
                    if (tiles[i][j] instanceof NumberTile) {
                        ((NumberTile) tiles[i][j]).addOneToNumber();
                    } else if (!(tiles[i][j] instanceof BombTile)) {
                        tiles[i][j] = new NumberTile(i, j, 1, context);
                    }
                }
            }
        }
    }
}
