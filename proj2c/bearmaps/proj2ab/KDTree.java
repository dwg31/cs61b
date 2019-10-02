package bearmaps.proj2ab;

import java.util.List;

/**
 * KDTree implementation. Specifically, a 2DTree.
 *
 * @author Dawei Gu
 */
public class KDTree implements PointSet {
    /** the amount of Points stored in this KDTree. */
    private int size;
    /** KDTree itself. Pointing to the root of the tree. */
    private Node root;

    /**  */
    private static class Node {
        /** the point itself. */
        private Point p;
        /** the left/up child. */
        private Node left;
        /** the right/down child. */
        private Node right;
        /** indicating the direction that this
         * Node divides the space into. true = left/right and false = up/down.
         */
        private boolean direction;

        /** Node class constructor.
         *
         * @param pnt the point to be added
         * @param d direction
         */
        private Node(Point pnt, boolean d) {
            p = new Point(pnt.getX(), pnt.getY());
            left = null;
            right = null;
            direction = d;
        }
    }

    /** KDTree constructor.
     *
     * @param points the point set to be added
     */
    public KDTree(List<Point> points) {
        root = null;
        size = 0;
        for (Point p: points) {
            root = insert(root, p, true);
        }
    }

    /** Insert the point to be tree.
     *
     * @param curr current node
     * @param pnt the point to be added
     * @param d direction of this node
     * @return Node
     */
    private Node insert(Node curr, Point pnt, boolean d) {
        if (curr == null) {
            size += 1;
            return new Node(pnt, d);
        }
        if (right(pnt, curr)) {
            curr.right = insert(curr.right, pnt, !curr.direction);
        } else {
            curr.left = insert(curr.left, pnt, !curr.direction);
        }
        return curr;
    }

    /**
     * @param p the given point.
     * @param n the Node to compare.
     * @return true if p should be the right child of n.
     */
    private boolean right(Point p, Node n) {
        return n.direction && p.getX() > n.p.getX()
                || !n.direction && p.getY() > n.p.getY();
    }

    /**
     * @param p the given point.
     * @param n the current Node.
     * @param d the best distance so far.
     * @return true if the best possible distance between p and the
     * "bad side" of the current Node is smaller than d.
     */
    private boolean badSide(Point p, Node n, double d) {
        if (n.direction) {
            return Math.abs(p.getX() - n.p.getX()) < Math.sqrt(d);
        } else {
            return Math.abs(p.getY() - n.p.getY()) < Math.sqrt(d);
        }
    }

    /**
     * @param p the given point.
     * @param nearestP the nearest point so far.
     * @param d the best distance so far.
     * @param n the current Node.
     * @return the nearest point to the given point.
     */
    private Point nearHelp(Point p, Point nearestP, double d, Node n) {
        if (n == null) {
            return nearestP;
        }
        if (Point.distance(n.p, p) <= d) {
            d = Point.distance(n.p, p);
            nearestP = n.p;
        }
        if (right(p, n)) {
            nearestP = nearHelp(p, nearestP, d, n.right);
            d = Point.distance(nearestP, p);
            if (badSide(p, n, d)) {
                nearestP = nearHelp(p, nearestP, d, n.left);
                d = Point.distance(nearestP, p);
            }
        } else {
            nearestP = nearHelp(p, nearestP, d, n.left);
            d = Point.distance(nearestP, p);
            if (badSide(p, n, d)) {
                nearestP = nearHelp(p, nearestP, d, n.right);
                d = Point.distance(nearestP, p);
            }
        }
        return nearestP;
    }

    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        Point nearestP = root.p;
        double shortest = Point.distance(root.p, p);
        return nearHelp(p, nearestP, shortest, root);
    }

    /**
     * @return the size of the KDTree.
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the root Node of the KDTree
     */
    public Node getRoot() {
        return root;
    }
}
