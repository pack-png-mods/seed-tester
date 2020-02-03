package me.balint.math;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

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

    public double[] func_807_a(double noise[], double d, double d1, double d2,
                               int width, int height, int depth, double d3, double d4,
                               double d5)
    {
        if(noise == null)
        {
            noise = new double[width * height * depth];
        } else
        {
            for(int l = 0; l < noise.length; l++)
            {
                noise[l] = 0.0D;
            }

        }
        double d6 = 1.0D;
        for(int i1 = 0; i1 < octaves; i1++)
        {
            noises[i1].func_805_a(noise, d, d1, d2, width, height, depth, d3 * d6, d4 * d6, d5 * d6, d6);
            d6 /= 2D;
        }

        return noise;
    }

    public double[] func_4109_a(double ad[], int i, int j, int k, int l, double d,
                                double d1, double d2)
    {
        return func_807_a(ad, i, 10D, j, k, 1, l, d, 1.0D, d1);
    }

    private NoiseGeneratorPerlin noises[];
    private int octaves;
}
