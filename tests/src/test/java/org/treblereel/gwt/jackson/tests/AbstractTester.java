package org.treblereel.gwt.jackson.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

/**
 * Extends {@link Assert} because GWT does not support {@link org.junit.Assert}
 *
 * @author Nicolas Morel
 */
public abstract class AbstractTester extends Assert {

    /*
    /**********************************************************
    /* Shared helper classes
    /**********************************************************
     */

    /**
     * Simple wrapper around boolean types, usually to test value
     * conversions or wrapping
     */
    public static class BooleanWrapper {

        public Boolean b;

        //@JsonCreator TODO
        public BooleanWrapper(Boolean value) {
            b = value;
        }

        //@JsonValue TODO
        public Boolean value() {
            return b;
        }
    }

    public static class IntWrapper {

        public int i;

        public IntWrapper() {
        }

        public IntWrapper(int value) {
            i = value;
        }
    }

    /**
     * Simple wrapper around String forType, usually to test value
     * conversions or wrapping
     */
    public static class StringWrapper {

        public String str;

        public StringWrapper() {
        }

        public StringWrapper(String value) {
            str = value;
        }
    }

    public static class ObjectWrapper {

        //@JsonCreator TODO
        static ObjectWrapper jsonValue(final Object object) {
            return new ObjectWrapper(object);
        }

        private final Object object;

        protected ObjectWrapper(final Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }
    }

    public static class ListWrapper<T> {

        public List<T> list;

        public ListWrapper(T... values) {
            list = new ArrayList<T>();
            for (T value : values) {
                list.add(value);
            }
        }
    }

    public static class MapWrapper<K, V> {

        public Map<K, V> map;

        public MapWrapper(Map<K, V> m) {
            map = m;
        }
    }

    public static class ArrayWrapper<T> {

        public T[] array;

        public ArrayWrapper(T[] v) {
            array = v;
        }
    }

    @SuppressWarnings("deprecation")
    public static long getUTCTime(int year, int month, int day, int hour, int minute, int second, int milli) {
        return Date.UTC(year - 1900, month - 1, day, hour, minute, second) + milli;
    }

    public static Date getUTCDate(int year, int month, int day, int hour, int minute, int second, int milli) {
        return new Date(getUTCTime(year, month, day, hour, minute, second, milli));
    }

    public boolean isArray2dEquals(Object[][] a1, Object[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            Object[] e1 = a1[i];
            Object[] e2 = a2[i];

            if (!Arrays.deepEquals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(boolean[][] a1, boolean[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            boolean[] e1 = a1[i];
            boolean[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(byte[][] a1, byte[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            byte[] e1 = a1[i];
            byte[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(char[][] a1, char[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            char[] e1 = a1[i];
            char[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(double[][] a1, double[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            double[] e1 = a1[i];
            double[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(float[][] a1, float[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            float[] e1 = a1[i];
            float[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(int[][] a1, int[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            int[] e1 = a1[i];
            int[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(long[][] a1, long[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            long[] e1 = a1[i];
            long[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public boolean isArray2dEquals(short[][] a1, short[][] a2) {
        if (a1 == a2) {
            return true;
        }
        if (a1 == null || a2 == null) {
            return false;
        }
        int length = a1.length;
        if (a2.length != length) {
            return false;
        }

        for (int i = 0; i < length; i++) {
            short[] e1 = a1[i];
            short[] e2 = a2[i];

            if (!Arrays.equals(e1, e2)) {
                return false;
            }
        }
        return true;
    }

    public <T> T[][] newArray2d(T[]... arrays) {
        return arrays;
    }

    public boolean[][] newArray2d(boolean[]... arrays) {
        return arrays;
    }

    public byte[][] newArray2d(byte[]... arrays) {
        return arrays;
    }

    public char[][] newArray2d(char[]... arrays) {
        return arrays;
    }

    public double[][] newArray2d(double[]... arrays) {
        return arrays;
    }

    public float[][] newArray2d(float[]... arrays) {
        return arrays;
    }

    public int[][] newArray2d(int[]... arrays) {
        return arrays;
    }

    public long[][] newArray2d(long[]... arrays) {
        return arrays;
    }

    public short[][] newArray2d(short[]... arrays) {
        return arrays;
    }
}

