/************************************************************************
 *    RandomNumberGeneration.java:                                      *
 *    Loading screen while a random number is generated                 *
 *    Copyright (c) 2016 Tyler Lewis                                    *
 ************************************************************************
 *    This is a source code file for RandomNumberGenerator:             *
 *    https://github.com/Pinguinsan/RandomNumberGenerator               *
 *    This file holds the implementation of a                           *
 *    RandomNumberGenerationProgress class                              *
 *    The source code is released under the GNU LGPL                    *
 *                                                                      *
 *    You should have received a copy of the GNU Lesser General         *
 *    Public license along with QMineSweeper                            *
 *    If not, see <http://www.gnu.org/licenses/>                        *
 ***********************************************************************/

package com.pinguinsan.randomnumbergenerator;

import android.os.SystemClock;
import java.util.Random;

/**
 * Created by Pingu on 3/21/2016.
 */
public class RandomNumberGenerator {

    private static Random randomObject;
    private static boolean isSeeded = false;

    //private static void RandomNumberGenerator() {
    //    seedRandomNumberGenerator();
    //}

    //Ew, this is ugly. Fix it?
    public static byte bRandomBetween(byte lowLimit, byte highLimit) {
        if (!isSeeded) {
            seedRandomNumberGenerator();
        }
        highLimit = highLimit == 0 ? highLimit : highLimit++;
        byte[] tempBuffer = {0};
        int divisorInt = ((Byte.MAX_VALUE/highLimit) + 1);
        int returnValue;
        do {
            randomObject.nextBytes(tempBuffer);
            returnValue = (((int) tempBuffer[0]) / divisorInt);
        } while ((returnValue > highLimit) || (returnValue < lowLimit));
        return (byte)returnValue;
    }
    public static long lRandomBetween(long lowLimit, long highLimit) {
        if (!isSeeded) {
            seedRandomNumberGenerator();
        }
        long returnValue;
        do {
            returnValue = Math.abs(lowLimit) + (long)(randomObject.nextDouble()*(highLimit - Math.abs(lowLimit)));
        } while ((returnValue > highLimit ) || (returnValue < lowLimit));
        return returnValue;
    }

    //End ugly stuff

    public static int iRandomBetween(int lowLimit, int highLimit) {
        System.out.println("lowLimit received by iRandomBetween: " + lowLimit);
        System.out.println("highLimit received by iRandombetween: " + highLimit);
        if (!isSeeded) {
            seedRandomNumberGenerator();
        }
        /*highLimit = highLimit == 0 ? highLimit : highLimit++;
        int divisor = ((Integer.MAX_VALUE/highLimit) + 1);
        int returnValue;
        do {
            returnValue = (randomObject.nextInt() / divisor);
        } while ((returnValue > highLimit) || (returnValue < lowLimit));
        return returnValue;*/
        int actualHighLimit = Math.abs(lowLimit) + highLimit;
        int returnValue;
        do {
            returnValue = (randomObject.nextInt(actualHighLimit) - Math.abs(lowLimit));
        } while ((returnValue > highLimit ) || (returnValue < lowLimit));
        return returnValue;
    }


    public static float fRandomBetween(float lowLimit, float highLimit) {
        if (!isSeeded) {
            seedRandomNumberGenerator();
        }
        highLimit = highLimit == 0 ? highLimit : highLimit++;
        float divisor = ((Long.MAX_VALUE/highLimit) + 1);
        float returnValue;
        do {
            returnValue = (randomObject.nextFloat() / divisor);
        } while ((returnValue > highLimit) || (returnValue < lowLimit));
        return returnValue;
    }

    public static double dRandomBetween(double lowLimit, double highLimit) {
        if (!isSeeded) {
            seedRandomNumberGenerator();
        }
        highLimit = highLimit == 0 ? highLimit : highLimit++;
        double divisor = ((Long.MAX_VALUE/highLimit) + 1);
        double returnValue;
        do {
            returnValue = (randomObject.nextDouble() / divisor);
        } while ((returnValue > highLimit) || (returnValue < lowLimit));
        return returnValue;
    }

    private static void seedRandomNumberGenerator() {
        if (randomObject == null) {
            randomObject = new Random(SystemClock.uptimeMillis());
            isSeeded = true;
        } else {
            randomObject.setSeed(SystemClock.uptimeMillis());
            isSeeded = true;
        }
    }
}
