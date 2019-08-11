public class Lists1Exercises {
    /** Returns an IntList identical to L, but with
      * each element incremented by x. L is not allowed
      * to change. */
    public static IntList incrList(IntList L, int x) {
        /* Your code here. */
        if (L == null) {
            return null;
        } else {
            IntList p = new IntList(L.first + 3, incrList(L.rest, x));
            return p;
        }
    }

    /** Returns an IntList identical to L, but with
      * each element incremented by x. Not allowed to use
      * the 'new' keyword. */
    public static IntList dincrList(IntList L, int x) {
        /* Your code here. */
        IntList q = L;
        while (q != null) {
            q.first += x;
            q = q.rest;
        }
        q = L;
        return q;
    }

    public static void main(String[] args) {
        IntList L = new IntList(5, null);
        L.rest = new IntList(7, null);
        L.rest.rest = new IntList(9, null);

        System.out.println(L.size());
        System.out.println(L.iterativeSize());

        // Test your answers by uncommenting. Or copy and paste the
        // code for incrList and dincrList into IntList.java and
        // run it in the visualizer.
        System.out.println(L.get(2));
        System.out.println(incrList(L, 3).get(2));
        System.out.println(dincrList(L, 3).get(2));        
    }
}