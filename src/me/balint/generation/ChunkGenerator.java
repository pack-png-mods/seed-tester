package me.balint.generation;

import kaptainwutax.seedcracker.util.Rand;
import me.balint.math.Vector;

@SuppressWarnings("unused")
public class ChunkGenerator {

    // todo: set to actual values
    private static final int TREE1_X = 5; // Left tree on the image
    private static final int TREE1_Z = 5;
    private static final int TREE2_X = 7; // right tree on the image
    private static final int TREE2_Z = 7;
    private static final int ILLEGAL_TREE_MIN_X = 8; // Minimum positions of the part where trees didn't spawn but could
    private static final int ILLEGAL_TREE_MIN_Y = 8; // Should probably be changed to something less inclusive
    private static final int WATERFALL_X = 5;
    private static final int WATERFALL_Y = 5;
    private static final int REQUIRED_TREES = 2;

    public enum TreeType {
        NORMAL,
        BIG
    }

    public static class Tree {

        public final Vector position;
        public final int height;
        public final TreeType type;

        public Tree(Vector position, int height, TreeType type) {
            this.position = position;
            this.height = height;
            this.type = type;
        }
    }

    public boolean populate(long chunkSeed, Tree[] trees) {
        int worldX = 0;
        int worldZ = 0;

        Rand random = new Rand(chunkSeed, false);

        // Dungeons
        for (int i = 0; i < 8; i++) {
            int x = worldX + random.nextInt(16) + 8;
            int y = random.nextInt(128);
            int z = worldZ + random.nextInt(16) + 8;
            generateDungeon(random, x, y, z);
        }

        // Clay
        for (int i = 0; i < 10; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(128);
            int z = worldZ + random.nextInt(16);
            generateClay(random, x, y, z);
        }

        // Dirt
        for (int i = 0; i < 20; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(128);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 32);
        }

        // Gravel
        for (int i = 0; i < 10; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(128);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 32);
        }

        // Coal
        for (int i = 0; i < 20; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(128);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 16);
        }

        // Iron
        for (int i = 0; i < 20; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(64);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 8);
        }

        // Gold
        for (int i = 0; i < 2; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(32);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 8);
        }

        // Redstone
        for (int i = 0; i < 8; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(16);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 7);
        }

        // Diamond
        for (int i = 0; i < 1; i++) {
            int x = worldX + random.nextInt(16);
            int y = random.nextInt(16);
            int z = worldZ + random.nextInt(16);
            generateOre(random, x, y, z, 7);
        }

        // Trees
        double treeNoiseScale = 0.5D;
        int maxBaseTreeCount = 18; // 13 + 5
        if (random.nextInt(10) == 0)
            maxBaseTreeCount++;
        if (random.nextInt(10) == 0)
            return false;

        return checkTrees(random, worldX, worldZ, maxBaseTreeCount);
    }

    private void generateDungeon(Rand random, int x, int y, int z) {
        //byte height = 3;
        int width = random.nextInt(2) + 2;
        int depth = random.nextInt(2) + 2;
        // Not really needed
    }

    private void generateClay(Rand random, int x, int y, int z) {
        // Water's unlikely
    }

    private void generateOre(Rand random, int x, int y, int z, int blockNum) {
        float f = random.nextFloat() * 3.141593f;
        //double xMin = (float)(x + 8) + (MathHelper.sin(f) * (float) blockNum) / 8F;
        //double xMax = (float)(x + 8) - (MathHelper.sin(f) * (float) blockNum) / 8F;
        //double zMin = (float)(z + 8) + (MathHelper.cos(f) * (float) blockNum) / 8F;
        //double zMax = (float)(z + 8) - (MathHelper.cos(f) * (float) blockNum) / 8F;
        double yMin = y + random.nextInt(3) + 2;
        double yMax = y + random.nextInt(3) + 2;
        for (int i = 0; i < blockNum; i++) {
            //double blockX = xMin + (xMax - xMin) * ((double) i / (double) blockNum);
            //double blockY = yMin + (yMax - yMin) * ((double) i / (double) blockNum);
            //double blockZ = zMin + (zMax - zMin) * ((double) i / (double) blockNum);
            double d9 = (random.nextDouble() * (double) blockNum) / 16D;
            //double d10 = (double)(MathHelper.sin(((float)i * 3.141593F) / (float) blockNum) + 1.0F) * d9 + 1.0D;
            //double d11 = (double)(MathHelper.sin(((float)i * 3.141593F) / (float) blockNum) + 1.0F) * d9 + 1.0D;
            // Loops don't advance the RNG
        }
    }

    private boolean checkTrees(Rand random, int worldX, int worldZ, int maxTreeCount) {
        boolean firstTreeFound = false;
        boolean secondTreeFound = false;
        int foundTrees = 0;
        for (int i = 0; i < maxTreeCount; i++) {
            int treeX = worldX + random.nextInt(16) + 8;
            int treeZ = worldZ + random.nextInt(16) + 8;
            if (!firstTreeFound && treeX == TREE1_X && treeZ == TREE1_Z) {
                generateLeafPattern(random);
                foundTrees++;
                firstTreeFound = true;
            } else if (!secondTreeFound && treeX == TREE2_X && treeZ == TREE2_Z) {
                boolean[] leafPattern = generateLeafPattern(random);
                if (!leafPattern[0] && leafPattern[4]) {
                    foundTrees++;
                    secondTreeFound = true;
                } else {
                    return false;
                }
            } else if (treeX > 8 && treeZ > 8) {
                return false;
            }

            if (foundTrees == 2)
                return true;
        }
        return false;
    }

    private boolean[] generateLeafPattern(Rand random) {
        boolean[] out = new boolean[16];
        for (int i = 0; i < 16; i++) {
            out[i] = random.nextInt(2) != 0;
        }
        return out;
    }
}

