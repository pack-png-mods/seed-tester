package me.balint;

import kaptainwutax.seedcracker.util.Rand;
import kaptainwutax.seedcracker.util.math.LCG;

import java.util.*;

@SuppressWarnings("PointlessArithmeticExpression")
public class XFilter {

    // waterfall z
    private static final int Z = -30;
    private static final int CHUNK_Z = Z >> 4;

    // advances from the seed to the first nextDouble() we need to check
    // 134 for each z coordinate we skip, plus 4 for two nextDouble() calls in our iteration that we don't care about
    private static final LCG INITIAL_ADVANCE = Rand.JAVA_LCG.combine(134 * ((Z - 1) & 15) + 4);
    // advances enough calls for +1x, -2z, minus 2 for the nextDouble() call we pay attention to
    private static final LCG X_M2Z_ADVANCE = Rand.JAVA_LCG.combine(134 * 14 - 2);
    // advances enough calls for +1z, minus 2 for the nextDouble() call we pay attention to
    private static final LCG Z_ADVANCE = Rand.JAVA_LCG.combine(134 - 2);

    // prints the valid x coordinates of the waterfall within a given range, in order of likeliness
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter minimum x coordinate: ");
        int minX = Integer.parseInt(scan.nextLine());
        System.out.print("Enter maximum x coordinate: ");
        int maxX = Integer.parseInt(scan.nextLine());
        if (maxX < minX) {
            System.out.println("Maximum is less than minimum");
            return;
        }

        List<XCoord> xCoords = new ArrayList<>();
        int minChunkX = minX >> 4;
        int maxChunkX = maxX >> 4;
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            Rand rand = new Rand(chunkX * 341873128712L + CHUNK_Z * 132897987541L, true);
            rand.setSeed(INITIAL_ADVANCE.nextSeed(rand.getSeed()), false);
            for (int x = 0; x < 16; x++) {
                double dLeft = rand.nextDouble();
                rand.setSeed(Z_ADVANCE.nextSeed(rand.getSeed()), false);
                double dMiddle = rand.nextDouble();
                rand.setSeed(Z_ADVANCE.nextSeed(rand.getSeed()), false);
                double dRight = rand.nextDouble();
                rand.setSeed(X_M2Z_ADVANCE.nextSeed(rand.getSeed()), false);
                if (dMiddle > dRight) {
                    int globalX = chunkX << 4 | x;
                    if (globalX >= minX && globalX <= maxX) {
                        xCoords.add(new XCoord(globalX, dLeft - dMiddle));
                    }
                }
            }
        }

        Collections.sort(xCoords);
        xCoords.forEach(System.out::println);
    }

    private static class XCoord implements Comparable<XCoord> {
        int val;
        double leftDoubleDifference; // higher values worse

        public XCoord(int val, double leftDoubleDifference) {
            this.val = val;
            this.leftDoubleDifference = leftDoubleDifference;
        }

        @Override
        public int compareTo(XCoord xCoord) {
            return Comparator.<XCoord>comparingDouble(x -> x.leftDoubleDifference).thenComparingInt(x -> x.val).compare(this, xCoord);
        }

        @Override
        public String toString() {
            return String.valueOf(val);
        }
    }

}
