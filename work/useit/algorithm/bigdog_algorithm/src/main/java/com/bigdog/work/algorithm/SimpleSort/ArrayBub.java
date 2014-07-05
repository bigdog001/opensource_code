package com.bigdog.work.algorithm.SimpleSort;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/3/13
 * Time: 7:22 AM
 */
class BubbleSort {
}

public class ArrayBub {
    private long[] a;
    private int nElems;

    public ArrayBub(int max) {
        a = new long[max];
        nElems = 0;
    }

    public void insert(long value) {
        a[nElems] = value;
        nElems++;

    }

    public void Display() {
        for (long l : a) {
            System.out.print(l + ",");
        }
        System.out.println();
    }

    public void bubbleSort() {
        int out, in;
        for (out = nElems - 1; out > 1; out--) {
            for (in = 0; in < out; in++) {
                if (a[in] > a[in + 1]) swap(in, in + 1);
            }

        }
    }

    private void swap(int one, int two) {
        long temp = a[one];
        a[one] = a[two];
        a[two] = temp;

    }
}

