package me.balint.generation;

import kaptainwutax.seedcracker.util.Rand;
import me.balint.math.MathHelper;
import me.balint.math.NoiseGeneratorOctaves;
import me.balint.math.Vector;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ChunkGenerator {

    public enum TreeType {
        NORMAL,
        BIG
    }

    public class Tree {

        public Vector position;
        int height;
        public TreeType type;

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
        /*
        int baseTreeCount = (int)((treeNoise.genOctaves((double)worldX * treeNoiseScale, (double)worldZ * treeNoiseScale) / 8D + rand.nextDouble() * 4D + 4D) / 3D);
        important parts:
            (treeNoise.genOctaves((double)worldX, (double)worldZ) / 8D + rand.nextDouble() * 4D + 4D) / 3D
         */
        int maxBaseTreeCount = 18; // 13 + 5
        TreeType type = TreeType.NORMAL;
        if (random.nextInt(10) == 0)
            maxBaseTreeCount++;
        if (random.nextInt(10) == 0)
            return false;

        

        return true;
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

    private boolean checkTrees(Rand random, int worldX, int worldZ, Tree[] trees, int currentTree, int offset, int maxTreeCount) {
        long seed = random.getSeed();
        int treeX = worldX + random.nextInt(16) + 8;
        int treeZ = worldZ + random.nextInt(16) + 8;

        // TODO: check tree
        //      correct: check for other trees
        //      incorrect: go back

        return true;
    }
}

