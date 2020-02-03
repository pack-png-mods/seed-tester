package me.balint.math;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode

import java.util.Random;

public class NoiseGeneratorPerlin
{

    public NoiseGeneratorPerlin()
    {
        this(new Random());
    }

    public NoiseGeneratorPerlin(Random random)
    {
        permutations = new int[512];
        xCoord = random.nextDouble() * 256D;
        y_03 = random.nextDouble() * 256D;
        z_03 = random.nextDouble() * 256D;
        for(int i = 0; i < 256; i++)
        {
            permutations[i] = i;
        }

        for(int j = 0; j < 256; j++)
        {
            int k = random.nextInt(256 - j) + j;
            int l = permutations[j];
            permutations[j] = permutations[k];
            permutations[k] = l;
            permutations[j + 256] = permutations[j];
        }

    }

    public double generateNoise(double d, double d1, double d2)
    {
        double d3 = d + xCoord;
        double d4 = d1 + y_03;
        double d5 = d2 + z_03;
        int i = (int)d3;
        int j = (int)d4;
        int k = (int)d5;
        if(d3 < (double)i)
        {
            i--;
        }
        if(d4 < (double)j)
        {
            j--;
        }
        if(d5 < (double)k)
        {
            k--;
        }
        int l = i & 0xff;
        int i1 = j & 0xff;
        int j1 = k & 0xff;
        d3 -= i;
        d4 -= j;
        d5 -= k;
        double d6 = d3 * d3 * d3 * (d3 * (d3 * 6D - 15D) + 10D);
        double d7 = d4 * d4 * d4 * (d4 * (d4 * 6D - 15D) + 10D);
        double d8 = d5 * d5 * d5 * (d5 * (d5 * 6D - 15D) + 10D);
        int k1 = permutations[l] + i1;
        int l1 = permutations[k1] + j1;
        int i2 = permutations[k1 + 1] + j1;
        int j2 = permutations[l + 1] + i1;
        int k2 = permutations[j2] + j1;
        int l2 = permutations[j2 + 1] + j1;
        return lerp(d8, lerp(d7, lerp(d6, grad(permutations[l1], d3, d4, d5), grad(permutations[k2], d3 - 1.0D, d4, d5)), lerp(d6, grad(permutations[i2], d3, d4 - 1.0D, d5), grad(permutations[l2], d3 - 1.0D, d4 - 1.0D, d5))), lerp(d7, lerp(d6, grad(permutations[l1 + 1], d3, d4, d5 - 1.0D), grad(permutations[k2 + 1], d3 - 1.0D, d4, d5 - 1.0D)), lerp(d6, grad(permutations[i2 + 1], d3, d4 - 1.0D, d5 - 1.0D), grad(permutations[l2 + 1], d3 - 1.0D, d4 - 1.0D, d5 - 1.0D))));
    }

    public final double lerp(double d, double d1, double d2)
    {
        return d1 + d * (d2 - d1);
    }

    public final double func_4110_a(int i, double d, double d1)
    {
        int j = i & 0xf;
        double d2 = (double)(1 - ((j & 8) >> 3)) * d;
        double d3 = j >= 4 ? j != 12 && j != 14 ? d1 : d : 0.0D;
        return ((j & 1) != 0 ? -d2 : d2) + ((j & 2) != 0 ? -d3 : d3);
    }

    public final double grad(int i, double d, double d1, double d2)
    {
        int j = i & 0xf;
        double d3 = j >= 8 ? d1 : d;
        double d4 = j >= 4 ? j != 12 && j != 14 ? d2 : d : d1;
        return ((j & 1) != 0 ? -d3 : d3) + ((j & 2) != 0 ? -d4 : d4);
    }

    public double getNoise2D(double x, double y)
    {
        return generateNoise(x, y, 0.0D);
    }

    public void func_805_a(double ad[], double xin, double d1, double d2,
                           int x1, int j, int k, double xm, double d4,
                           double d5, double d6)
    {
        if(j == 1)
        {
            int j3 = 0;
            double d12 = 1.0D / d6;
            for(int xi = 0; xi < x1; xi++)
            {
                double x = (xin + (double)xi) * xm + xCoord;
                int X = (int)x;
                if(x < (double)X)
                {
                    X--;
                }
                int k4 = X & 0xff;
                x -= X;
                double d17 = x * x * x * (x * (x * 6D - 15D) + 10D);
                for(int l4 = 0; l4 < k; l4++)
                {
                    double d19 = (d2 + (double)l4) * d5 + z_03;
                    int j5 = (int)d19;
                    if(d19 < (double)j5)
                    {
                        j5--;
                    }
                    int l5 = j5 & 0xff;
                    d19 -= j5;
                    double d21 = d19 * d19 * d19 * (d19 * (d19 * 6D - 15D) + 10D);
                    int l = permutations[k4] + 0;
                    int j1 = permutations[l] + l5;
                    int l1 = permutations[permutations[k4 + 1] + 0] + l5;
                    double d9 = lerp(d17, func_4110_a(permutations[j1], x, d19), grad(permutations[l1], x - 1.0D, 0.0D, d19));
                    double d11 = lerp(d17, grad(permutations[j1 + 1], x, 0.0D, d19 - 1.0D), grad(permutations[l1 + 1], x - 1.0D, 0.0D, d19 - 1.0D));
                    double d23 = lerp(d21, d9, d11);
                    ad[j3++] += d23 * d12;
                }

            }

            return;
        }
        int i1 = 0;
        double d7 = 1.0D / d6;
        int i2 = -1;
        boolean flag4 = false;
        boolean flag5 = false;
        boolean flag6 = false;
        boolean flag7 = false;
        boolean flag8 = false;
        boolean flag9 = false;
        double d13 = 0.0D;
        double d15 = 0.0D;
        double d16 = 0.0D;
        double d18 = 0.0D;
        for(int i5 = 0; i5 < x1; i5++)
        {
            double d20 = (xin + (double)i5) * xm + xCoord;
            int k5 = (int)d20;
            if(d20 < (double)k5)
            {
                k5--;
            }
            int i6 = k5 & 0xff;
            d20 -= k5;
            double d22 = d20 * d20 * d20 * (d20 * (d20 * 6D - 15D) + 10D);
            for(int j6 = 0; j6 < k; j6++)
            {
                double d24 = (d2 + (double)j6) * d5 + z_03;
                int k6 = (int)d24;
                if(d24 < (double)k6)
                {
                    k6--;
                }
                int l6 = k6 & 0xff;
                d24 -= k6;
                double d25 = d24 * d24 * d24 * (d24 * (d24 * 6D - 15D) + 10D);
                for(int i7 = 0; i7 < j; i7++)
                {
                    double d26 = (d1 + (double)i7) * d4 + y_03;
                    int j7 = (int)d26;
                    if(d26 < (double)j7)
                    {
                        j7--;
                    }
                    int k7 = j7 & 0xff;
                    d26 -= j7;
                    double d27 = d26 * d26 * d26 * (d26 * (d26 * 6D - 15D) + 10D);
                    if(i7 == 0 || k7 != i2)
                    {
                        i2 = k7;
                        int j2 = permutations[i6] + k7;
                        int k2 = permutations[j2] + l6;
                        int l2 = permutations[j2 + 1] + l6;
                        int i3 = permutations[i6 + 1] + k7;
                        int k3 = permutations[i3] + l6;
                        int l3 = permutations[i3 + 1] + l6;
                        d13 = lerp(d22, grad(permutations[k2], d20, d26, d24), grad(permutations[k3], d20 - 1.0D, d26, d24));
                        d15 = lerp(d22, grad(permutations[l2], d20, d26 - 1.0D, d24), grad(permutations[l3], d20 - 1.0D, d26 - 1.0D, d24));
                        d16 = lerp(d22, grad(permutations[k2 + 1], d20, d26, d24 - 1.0D), grad(permutations[k3 + 1], d20 - 1.0D, d26, d24 - 1.0D));
                        d18 = lerp(d22, grad(permutations[l2 + 1], d20, d26 - 1.0D, d24 - 1.0D), grad(permutations[l3 + 1], d20 - 1.0D, d26 - 1.0D, d24 - 1.0D));
                    }
                    double d28 = lerp(d27, d13, d15);
                    double d29 = lerp(d27, d16, d18);
                    double d30 = lerp(d25, d28, d29);
                    ad[i1++] += d30 * d7;
                }

            }

        }

    }

    private int permutations[];
    public double xCoord;
    public double y_03;
    public double z_03;
}
