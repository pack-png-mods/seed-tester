package me.balint.generation;

import kaptainwutax.seedcracker.util.Rand;
import kaptainwutax.seedcracker.util.math.LCG;

@SuppressWarnings("unused")
public class  ChunkGenerator {

    // todo: set to actual values
    private static final int WATERFALL_X = 14;
    private static final int WATERFALL_Z = 10;
    private static final int TREE1_X = WATERFALL_X - 5; // Left tree on the image
    private static final int TREE1_Z = WATERFALL_Z - 8;
    private static final int TREE1_HEIGHT = 5;
    private static final int TREE2_X = WATERFALL_X - 3; // right tree on the image
    private static final int TREE2_Z = WATERFALL_Z + 3;
    private static final int TREE2_HEIGHT = 5;
    private static final int TREE3_MIN_X = WATERFALL_X + 3; // blob
    private static final int TREE3_MAX_X = WATERFALL_X + 5; // blob
    private static final int TREE3_MIN_Z = WATERFALL_Z - 9;
    private static final int TREE3_MAX_Z = WATERFALL_Z - 6;
    private static final boolean THIRD_TREE_ENABLED = false;
    private static final int ILLEGAL_TREE_MIN_X = 8; // Minimum positions of the part where trees didn't spawn but could
    private static final int ILLEGAL_TREE_MIN_Z = 8; // Should probably be changed to something less inclusive

    private static final LCG advance_3759 = Rand.JAVA_LCG.combine(3759);

    public boolean populate(long chunkSeed) {
        int worldX = 0;
        int worldZ = 0;

        Rand random = new Rand(advance_3759.nextSeed(chunkSeed), false);

        // Trees
        double treeNoiseScale = 0.5D;
        int maxBaseTreeCount = 12;
        if (random.nextInt(10) == 0)
            return false;

        return checkTrees(random, maxBaseTreeCount);
    }

    private boolean checkTrees(Rand random, int maxTreeCount) {
        boolean firstTreeFound = false;
        boolean secondTreeFound = false;
        boolean thirdTreeFound = false;
        int foundTrees = 0;
        for (int i = 0; i < maxTreeCount; i++) {
            int treeX = random.nextInt(16);
            int treeZ = random.nextInt(16);
            int height = random.nextInt(3) + 4;
            if (!firstTreeFound && treeX == TREE1_X && treeZ == TREE1_Z && height == TREE1_HEIGHT) {
                generateLeafPattern(random);
                foundTrees++;
                firstTreeFound = true;
            } else if (!secondTreeFound && treeX == TREE2_X && treeZ == TREE2_Z && height == TREE2_HEIGHT) {
                boolean[] leafPattern = generateLeafPattern(random);
                if (!leafPattern[0] && leafPattern[4]) {
                    foundTrees++;
                    secondTreeFound = true;
                } else {
                    return false;
                }
            }

            if (THIRD_TREE_ENABLED) {
                if (!thirdTreeFound && treeX >= TREE3_MIN_X && treeX <= TREE3_MAX_X && treeZ >= TREE3_MIN_Z && treeZ <= TREE3_MAX_Z) {
                    generateLeafPattern(random);
                    foundTrees++;
                    thirdTreeFound = true;
                }
            }

            if ((THIRD_TREE_ENABLED && foundTrees == 3) || (!THIRD_TREE_ENABLED && foundTrees == 2))
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

