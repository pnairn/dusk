package com.stewsters.dusk.core.map.gen

import com.stewsters.dusk.core.flyweight.TileType
import com.stewsters.dusk.core.map.LevelMap
import com.stewsters.dusk.core.map.Tile
import com.stewsters.util.math.MatUtils
import com.stewsters.util.noise.OpenSimplexNoise

class SurfaceMapGenerator implements MapGenerator {

    int playerX
    int playerY

    @Override
    LevelMap reGenerate(int x, int y, int level) {


        LevelMap map = new LevelMap(x, y, level)
        map.xSize.times { iX ->
            map.ySize.times { iY ->
                //if on the edge, we will need to win
                if (iX == 0 || iY == 0 || iX == map.xSize - 1 || iY == map.ySize - 1) {
                    map.ground[iX][iY] = new Tile(TileType.GAME_WIN)
                } else {
                    map.ground[iX][iY] = new Tile(MatUtils.randVal(TileType.grassTypes))
                }

            }
        }

        growTrees(map)

        // TODO: throw down some enemies

        int distToCenter = Integer.MAX_VALUE
        int tX = 0, tY = 0

        map.xSize.times { xp ->
            map.ySize.times { yp ->
                if (!map.ground[x][y].tileType.blocks) {

                    int tDist = MatUtils.manhattanDistance(xp, yp, (int) (map.getXSize() / 2), (int) (map.getYSize() / 2))
                    if (tDist < distToCenter) {
                        distToCenter = tDist
                        tX = xp
                        tY = yp
                    }

                }
            }
        }
        playerX = tX
        playerY = tY

        map.ground[tX][tY].tileType = TileType.STAIRS_DOWN

        return map
    }

    @Override
    int getPlayerStartX() {
        return playerX
    }

    @Override
    int getPlayerStartY() {
        return playerY
    }


    private static void growTrees(LevelMap map) {
        //Randomly place trees on grass squares
        OpenSimplexNoise openSimplexNoise = new OpenSimplexNoise()

        map.xSize.times { x ->
            map.ySize.times { y ->

                //if grass
                if (TileType.grassTypes.contains(map.ground[x][y].tileType)) {
                    if (openSimplexNoise.eval((double) x / 10.0, (double) y / 10.0) > 0.5) {
                        map.ground[x][y].tileType = TileType.TREE
                    }
                }

            }
        }
    }
}
