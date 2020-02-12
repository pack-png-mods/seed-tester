package me.balint;

import kaptainwutax.seedcracker.util.Rand;
import kaptainwutax.seedcracker.util.math.LCG;

import java.util.Scanner;

public class XFilter {

    private static final int Z = -30;
    private static final int CHUNK_Z = Z >> 4;

    private static final LCG INITIAL_ADVANCE = Rand.JAVA_LCG.combine(134 * (Z & 15) + 4);
    private static final LCG X_MZ_ADVANCE = Rand.JAVA_LCG.combine(134 * 15 - 2);
    private static final LCG Z_ADVANCE = Rand.JAVA_LCG.combine(134 - 2);

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

        int minChunkX = minX >> 4;
        int maxChunkX = maxX >> 4;
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            Rand rand = new Rand(chunkX * 341873128712L + CHUNK_Z * 132897987541L, true);
            rand.setSeed(INITIAL_ADVANCE.nextSeed(rand.getSeed()), false);
            for (int x = 0; x < 16; x++) {
                double d1 = rand.nextDouble();
                rand.setSeed(Z_ADVANCE.nextSeed(rand.getSeed()), false);
                double d2 = rand.nextDouble();
                rand.setSeed(X_MZ_ADVANCE.nextSeed(rand.getSeed()), false);
                if (d1 > d2) {
                    int globalX = chunkX << 4 | x;
                    if (globalX >= minX && globalX <= maxX) {
                        System.out.println(globalX);
                    }
                }
            }
        }
    }

}
