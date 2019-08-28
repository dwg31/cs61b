package bearmaps;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {

    @Test
    public void testBasics() {
        ArrayHeapMinPQ<String> PQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000; i += 1) {
            PQ.add("hi" + i, i * 0.9);
        }
        assertEquals(1000, PQ.size());
        assertEquals("hi0", PQ.getSmallest());
    }

    @Test
    public void testSpeed() {
        System.out.println("Testing ArrayHeapMinPQ speed...");
        ArrayHeapMinPQ<String> PQ = new ArrayHeapMinPQ<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2000; i += 1) {
            PQ.add("hi" + i, Math.random() * 10);
        }
        long end = System.currentTimeMillis();
        System.out.println("Inserting 2000 items costs " + (end - start) / 1000.0 + " seconds.");

        start = System.currentTimeMillis();
        for (int i = 2000; i < 6000; i += 1) {
            PQ.add("hi" + i, Math.random() * 10);
        }
        end = System.currentTimeMillis();
        System.out.println("Inserting 4000 items costs " + (end - start) / 1000.0 + " seconds.");

        start = System.currentTimeMillis();
        for (int i = 6000; i < 22000; i += 1) {
            PQ.add("hi" + i, Math.random() * 10);
        }
        end = System.currentTimeMillis();
        System.out.println("Inserting 16000 items costs " + (end - start) / 1000.0 + " seconds.");
        System.out.println("Does inserting look logarithmic?");
        System.out.println();

        start = System.currentTimeMillis();
        for (int i = 0; i < 2000; i += 1) {
            PQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Removing 2000 items costs " + (end - start) / 1000.0 + " seconds.");

        for (int i = 0; i < 4000; i += 1) {
            PQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Removing 4000 items costs " + (end - start) / 1000.0 + " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 16000; i += 1) {
            PQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Removing 16000 items costs " + (end - start) / 1000.0 + " seconds.");
        System.out.println("Does Removing look logarithmic?");
        System.out.println();

        System.out.println("Testing NaiveMinPQ speed...");
        NaiveMinPQ<String> NPQ = new NaiveMinPQ<>();
        start = System.currentTimeMillis();
        for (int i = 0; i < 2000; i += 1) {
            NPQ.add("hi" + i, Math.random() * 10);
        }
        end = System.currentTimeMillis();
        System.out.println("Inserting 2000 items costs " + (end - start) / 1000.0 + " seconds.");

        start = System.currentTimeMillis();
        for (int i = 2000; i < 6000; i += 1) {
            NPQ.add("hi" + i, Math.random() * 10);
        }
        end = System.currentTimeMillis();
        System.out.println("Inserting 4000 items costs " + (end - start) / 1000.0 + " seconds.");

        start = System.currentTimeMillis();
        for (int i = 6000; i < 22000; i += 1) {
            NPQ.add("hi" + i, Math.random() * 10);
        }
        end = System.currentTimeMillis();
        System.out.println("Inserting 16000 items costs " + (end - start) / 1000.0 + " seconds.");
        System.out.println();

        start = System.currentTimeMillis();
        for (int i = 0; i < 2000; i += 1) {
            NPQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Removing 2000 items costs " + (end - start) / 1000.0 + " seconds.");

        for (int i = 0; i < 4000; i += 1) {
            NPQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Removing 4000 items costs " + (end - start) / 1000.0 + " seconds.");

        start = System.currentTimeMillis();
        for (int i = 0; i < 16000; i += 1) {
            NPQ.removeSmallest();
        }
        end = System.currentTimeMillis();
        System.out.println("Removing 16000 items costs " + (end - start) / 1000.0 + " seconds.");
        System.out.println();
    }/** !!!!!!!!!!!!!!!!!!测试remove的方法不对!!!!!!!!!!!!!!!!!!!!!!!! */

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> PQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000; i += 1) {
            PQ.add("hi" + i, Math.random() * 100);
        }
        for (int i = 0; i < 10000; i += 1) {
            PQ.removeSmallest();
        }
        assertEquals(0, PQ.size());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> PQ = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 4000; i += 1) {
            PQ.add("hi" + i, i);
        }
        for (int i = 0; i < 3200; i += 1) {
            PQ.changePriority("hi" + (int) (Math.random() * 1000), Math.random() * 1000);
        }
        while(PQ.size() > 1) {
            double parentPriority = PQ.heap.get(0).getPriority();
            PQ.removeSmallest();
            double childPriority = PQ.heap.get(0).getPriority();
            assertTrue(parentPriority <= childPriority);
        }
    }
}
