import org.junit.Test;
import static org.junit.Assert.*;

/**
 * A test file for UnionFind class.
 *
 * @author Dawei Gu
 */

public class TestUnionFind {

    @Test
    public void testBasics() {
        UnionFind set1 = new UnionFind(10);
        set1.union(0, 1);
        set1.union(2, 3);
        set1.union(4, 5);
        set1.union(6, 7);
        set1.union(8, 9);

        set1.union(0, 5);

        assertTrue(set1.connected(0, 4));
        assertEquals(4, set1.sizeOf(1));
        assertEquals(5, set1.find(0));
        assertEquals(5, set1.find(1));
    }

}
