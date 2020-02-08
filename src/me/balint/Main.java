package me.balint;

import me.balint.generation.ChunkGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

public class Main implements Runnable {

    public static void main(String[] args) {
        new Main().start();
    }

    private int processed = 0;
    private int found = 0;
    private int lastProcessed = 0;
    private boolean running = true;

    public void start() {
        ChunkGenerator generator = new ChunkGenerator();
        Thread printingThread = new Thread(this);
        printingThread.start();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("seeds.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                processed++;
                long seed = Long.parseLong(line);
                if (generator.populate(seed)) {
                    found++;
                    writer.write(line + " " + generator.treeCount + "\n");
                }
            }
            reader.close();
            writer.close();
            running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            printingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        DecimalFormat formatter = new DecimalFormat("#.##");
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) { }
            System.out.println("Processed " + processed + " seeds, found " + found + " correct ones. Speed: "
                    + formatter.format((double)(processed - lastProcessed) / 1000000) + "m/s, percentage of correct seeds: " + formatter.format((double) found / processed * 100) + "%");
            lastProcessed = processed;
        }
    }
}
