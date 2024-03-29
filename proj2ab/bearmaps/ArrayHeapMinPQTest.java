package bearmaps;

import org.junit.Test;
import edu.princeton.cs.algs4.Stopwatch;
import static org.junit.Assert.*;

public class ArrayHeapMinPQTest {
    private void speedHelp(ExtrinsicMinPQ<String> pq, int n, int m) {
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < n; i += 1) {
            pq.add("hi" + i, Math.random());
        }
        System.out.println("Adding " + n + " items costs "
                + pq.getClass() + ' ' + sw.elapsedTime() + " seconds.");
        sw = new Stopwatch();
        for (int i = 0; i < m; i += 1) {
            pq.removeSmallest();
        }
        System.out.println("Removing " + m + " items costs "
                + pq.getClass() + ' ' + sw.elapsedTime() + " seconds.");
        System.out.println();
    }

    @Test
    public void testSpeed() {
        ExtrinsicMinPQ<String> heap = new ArrayHeapMinPQ<>();
        ExtrinsicMinPQ<String> naive = new NaiveMinPQ<>();
        speedHelp(heap, 1000000, 1000);
        speedHelp(naive, 1000000, 1000);
    }

    @Test
    public void testRemoveSmallest() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 10000; i += 1) {
            pq.add("hi" + i, Math.random() * 100);
        }
        for (int i = 0; i < 5000; i += 1) {
            pq.removeSmallest();
        }
        assertEquals(5000, pq.size());
    }

    @Test
    public void testBasics() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 1000; i += 1) {
            pq.add("hi" + i, i * 0.9);
        }
        assertEquals(1000, pq.size());
        assertEquals("hi0", pq.getSmallest());
    }

    @Test
    public void testChangePriority() {
        ArrayHeapMinPQ<String> pq = new ArrayHeapMinPQ<>();
        for (int i = 0; i < 4000; i += 1) {
            pq.add("hi" + i, i);
        }
        for (int i = 0; i < 3200; i += 1) {
            pq.changePriority("hi" + (int) (Math.random() * 1000),
                    Math.random() * 1000);
        }
        assertTrue(pq.priorityCheck());
    }
}
