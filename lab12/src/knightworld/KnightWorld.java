package knightworld;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

/**
 * Draws a world consisting of knight-move holes.
 */
public class KnightWorld {

    private TETile[][] tiles;
    private int width;
    private int height;
    private int holeSize;

    public KnightWorld(int width, int height, int holeSize) {
        this.width = width;
        this.height = height;
        this.holeSize = holeSize;
        this.tiles = new TETile[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.tiles[x][y] = setHoles(holeSize, x, y);
            }
        }
    }

    private TETile setHoles(int size, int x, int y) {
        int xNum = x % ((1 + 2 + 2) * size) / size;
        int floorNum = y % ((1 + 2 + 2) * size) / size;
        switch (floorNum) {
            case 0 -> {
                if (xNum == 1) {
                    return Tileset.NOTHING;
                } else {
                    return Tileset.LOCKED_DOOR;
                }
            }
            case 1 -> {
                if (xNum == 4) {
                    return Tileset.NOTHING;
                } else {
                    return Tileset.LOCKED_DOOR;
                }
            }
            case 2 -> {
                if (xNum == 2) {
                    return Tileset.NOTHING;
                } else {
                    return Tileset.LOCKED_DOOR;
                }
            }
            case 3 -> {
                if (xNum == 0) {
                    return Tileset.NOTHING;
                } else {
                    return Tileset.LOCKED_DOOR;
                }
            }
            case 4 -> {
                if (xNum == 3) {
                    return Tileset.NOTHING;
                } else {
                    return Tileset.LOCKED_DOOR;
                }
            }
            default -> {
                return Tileset.LOCKED_DOOR;
            }
        }
    }

    /** Returns the tiles associated with this KnightWorld. */
    public TETile[][] getTiles() {
        return tiles;
    }

    public static void main(String[] args) {
        // Change these parameters as necessary
        int width = 60;
        int height = 40;
        int holeSize = 3;

        KnightWorld knightWorld = new KnightWorld(width, height, holeSize);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);
        ter.renderFrame(knightWorld.getTiles());

    }
}
