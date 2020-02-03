package me.balint;

import me.balint.math.NoiseGeneratorPerlin;

import java.io.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        Random random = new Random(2);
        NoiseGeneratorPerlin perlin = new NoiseGeneratorPerlin(random);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("out.csv"));

            for (int x = 0; x < 100; x++) {
                for (int y = 0; y < 100; y++) {
                    writer.write(perlin.generateNoise(x / 10d, y / 10d, 0) + ";");
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
