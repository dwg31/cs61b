package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import edu.princeton.cs.algs4.Stopwatch;

public class KDTreeTest {
    @Test
    public void testBasics() {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random(10);
        for (int i = 0; i < 1000; i += 1) {
            double x = random.nextDouble() * 100;
            double y = random.nextDouble() * 100;
            points.add(new Point(x, y));
        }
        KDTree kd = new KDTree(points);
        NaivePointSet naive = new NaivePointSet(points);
        for (int i = 0; i < 200; i += 1) {
            double x = random.nextDouble() * 100;
            double y = random.nextDouble() * 100;
            Point p1 = kd.nearest(x, y);
            Point p2 = naive.nearest(x, y);
            assertEquals(p1, p2);
        }
    }

    private double speedTestHelper(PointSet set) {
        Random random = new Random(10);
        Stopwatch sw = new Stopwatch();
        for (int i = 0; i < 10000; i += 1) {
            double x = random.nextDouble() * 100;
            double y = random.nextDouble() * 100;
            set.nearest(x, y);
        }
        return sw.elapsedTime();
    }

    @Test
    public void testQuerySpeed() {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random(10);
        for (int i = 0; i < 100000; i += 1) {
            double x = random.nextDouble() * 100;
            double y = random.nextDouble() * 100;
            points.add(new Point(x, y));
        }
        KDTree kd = new KDTree(points);
        NaivePointSet naive = new NaivePointSet(points);
        System.out.println("10,000 queries with 100,000 points costs(seconds)");
        System.out.println("KDTree: " + speedTestHelper(kd));
        System.out.println("NaivePointSet:" + speedTestHelper(naive));
    }

    @Test
    public void testCorrectness() {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100000; i += 1) {
            double x = random.nextDouble() * 100;
            double y = random.nextDouble() * 100;
            points.add(new Point(x, y));
        }
        KDTree kd = new KDTree(points);
        NaivePointSet naive = new NaivePointSet(points);
        for (int i = 0; i < 1000; i += 1) {
            double x = random.nextDouble() * 100;
            double y = random.nextDouble() * 100;
            assertEquals(kd.nearest(x, y), naive.nearest(x, y));
        }
    }
}

