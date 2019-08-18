package es.datastructur.synthesizer;
import java.util.Set;
import java.util.HashSet;

/** The GuitarString uses an ArrayRingBuffer to replicate
 *  the sound of a plucked string.
 *
 * @author Dawei Gu
 */
public class GuitarString {
    /** Sampling rate. */
    private static final int SR = 44100;
    /** Energy decay factor. */
    private static final double DECAY = .996;

    /** Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /** Create a guitar string of the given frequency.
     *
     * @param frequency Frequency of a given Concert.
     * */
    public GuitarString(double frequency) {
        buffer = new ArrayRingBuffer<>((int) Math.round(SR / frequency));

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.enqueue(0.0);
        }
    }


    /** Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        double r = Math.random() - 0.5;
        Set<Double> randomNum = new HashSet<>();

        while (randomNum.size() < buffer.capacity()) {
            randomNum.add(Math.random() - 0.5);
        }

        for (double num: randomNum) {
            buffer.dequeue();
            buffer.enqueue(num);
        }
    }

    /** Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double a = buffer.dequeue();
        double b = buffer.peek();

        buffer.enqueue(0.5 * (a + b) * DECAY);
    }

    /** Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
