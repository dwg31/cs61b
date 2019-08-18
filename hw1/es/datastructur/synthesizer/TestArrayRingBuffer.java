package es.datastructur.synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug and Dawei Gu
 */
public class TestArrayRingBuffer {
    @Test
    public void testBasics() {
        ArrayRingBuffer arb = new ArrayRingBuffer(10);
        arb.enqueue("you");
        arb.enqueue("are");
        arb.enqueue("smart!");

        assertEquals("you", arb.peek());
        assertEquals(10, arb.capacity());
        assertEquals(3, arb.fillCount());
        assertEquals("you", arb.dequeue());
        assertEquals(2, arb.fillCount());
    }
}
