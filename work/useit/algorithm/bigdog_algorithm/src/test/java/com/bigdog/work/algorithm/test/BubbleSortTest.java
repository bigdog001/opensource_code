package com.bigdog.work.algorithm.test;
import com.bigdog.work.algorithm.SimpleSort.ArrayBub;

/**
 * Created with IntelliJ IDEA.
 * User: bigdog
 * Date: 9/3/13
 * Time: 7:34 AM
 */
public class BubbleSortTest {
    public BubbleSortTest() {
        int maxSize = 100;
        ArrayBub arr;
        arr = new ArrayBub(maxSize);
        arr.insert(102);
        arr.insert(1);
        arr.insert(2);
        arr.insert(76);
        arr.insert(32);
        arr.insert(78);
        arr.insert(21);
        arr.insert(45);
        arr.insert(67);
        arr.insert(81);
        arr.insert(10);
        arr.Display();
        arr.bubbleSort();
        arr.Display();

    }
}
