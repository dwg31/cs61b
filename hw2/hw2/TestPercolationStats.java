package hw2;


import org.junit.Test;

public class TestPercolationStats {

    @Test
    public void testOutput() {
        PercolationStats experiment =
                new PercolationStats(100, 1000, new PercolationFactory());
        System.out.println(experiment.mean());
        System.out.println(experiment.stddev());
    }

}
