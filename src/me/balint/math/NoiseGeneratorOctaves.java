package me.balint.math;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

import java.util.Arrays;
import java.util.Random;

public class NoiseGeneratorOctaves
{

    public NoiseGeneratorOctaves(Random random, int i)
    {
        octaves = i;
        noises = new NoiseGeneratorPerlin[i];
        for(int j = 0; j < i; j++)
        {
            noises[j] = new NoiseGeneratorPerlin(random);
        }

    }

    public double genOctaves(double x, double y)
    {
        double noiseTotal = 0.0D;
        double frequency = 1.0D;
        for(int i = 0; i < octaves; i++)
        {
            noiseTotal += noises[i].getNoise2D(x * frequency, y * frequency) / frequency;
            frequency /= 2D;
        }

        return noiseTotal;
    }

    public double[] genOctaves(double[] noise,
                               double x, double y, double z,
                               int width, int height, int depth,
                               double xScale, double yScale, double zScale)
    {
        if(noise == null)
        {
            noise = new double[width * height * depth];
        }
        else
        {
            Arrays.fill(noise, 0);
        }
        double d6 = 1.0D;
        for(int i1 = 0; i1 < octaves; i1++)
        {
            noises[i1].generateNoise(noise, x, y, z, width, height, depth, xScale * d6, yScale * d6, zScale * d6, d6);
            d6 /= 2D;
        }

        return noise;
    }

    public double[] genOctaves(double[] noise,
                               int x, int z,
                               int width, int depth,
                               double xScale, double zScale, double d2)
    {
        return genOctaves(noise, x, 10D, z, width, 1, depth, xScale, 1.0D, zScale);
    }

    private NoiseGeneratorPerlin[] noises;
    private int octaves;
}
