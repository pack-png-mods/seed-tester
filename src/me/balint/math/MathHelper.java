package me.balint.math;
// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode


public class MathHelper
{

    public MathHelper()
    {
    }

    public static float sin(float f)
    {
        return SIN_TABLE[(int)(f * 10430.38F) & 0xffff];
    }

    public static float cos(float f)
    {
        return SIN_TABLE[(int)(f * 10430.38F + 16384F) & 0xffff];
    }

    public static float sqrt_float(float f)
    {
        return (float)Math.sqrt(f);
    }

    public static float sqrt_double(double d)
    {
        return (float)Math.sqrt(d);
    }

    public static int convertToBlockCoord(float f)
    {
        int i = (int)f;
        return f >= (float)i ? i : i - 1;
    }

    public static int convertToBlockCoord_00(double d)
    {
        int i = (int)d;
        return d >= (double)i ? i : i - 1;
    }

    public static float abs(float f)
    {
        return f < 0.0F ? -f : f;
    }

    public static double abs_max(double d, double d1)
    {
        if(d < 0.0D)
        {
            d = -d;
        }
        if(d1 < 0.0D)
        {
            d1 = -d1;
        }
        return Math.max(d, d1);
    }

    public static int bucketInt(int i, int j)
    {
        if(i < 0)
        {
            return -((-i - 1) / j) - 1;
        } else
        {
            return i / j;
        }
    }

    private static float[] SIN_TABLE;

    static
    {
        SIN_TABLE = new float[0x10000];
        for(int i = 0; i < 0x10000; i++)
        {
            SIN_TABLE[i] = (float)Math.sin(((double)i * 3.1415926535897931D * 2D) / 65536D);
        }

    }
}
