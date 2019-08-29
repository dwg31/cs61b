package bearmaps;

import java.util.List;

public class KDTree implements PointSet{
    private int size;
    private Node root;

    private static class Node {
        Point p;
        Node left;
        Node right;
        boolean direction;

        private Node (Point pnt, boolean d) {
            p = new Point(pnt.getX(), pnt.getY());
            left = null;
            right = null;
            direction = d;
        }
    }

    public KDTree(List<Point> points) {
        root = null;
        size = 0;
        for (Point p: points) {
            root = insert(root, p, true);
        }
    }

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

    private boolean right(Point p, Node n) {
        return n.direction && p.getX() > n.p.getX()
                || !n.direction && p.getY() > n.p.getY();
    }

    private boolean badSide(Point p, Node n, double d) {
        return n.direction && p.getX() - n.p.getX() <= d;
    }

    private Point nearHelp(Point p, Point nearestP, double d, Node n) {
        if (n == null) {
            return nearestP;
        }
        if (Point.distance(n.p, p) < d) {
            d = Point.distance(n.p, p);
            nearestP = n.p;
            if (right(p, n)) {
                nearestP = nearHelp(p, nearestP, d, n.right);
                if (badSide(p, n, d)) {
                    nearestP = nearHelp(p, nearestP, d, n.left);
                }
            } else {
                nearestP = nearHelp(p, nearestP, d, n.left);
                if (badSide(p, n, d)) {
                    nearestP = nearHelp(p, nearestP, d, n.right);
                }
            }
        }
        return nearestP;
    }

    @Override
    public Point nearest(double x, double y) {
        Point p = new Point(x, y);
        Point nearestP = root.p;
        double shortest = Point.distance(root.p, p);
        nearHelp(p, nearestP, shortest, root);
        return null;
    }

    public int getSize() {
        return size;
    }

    public Node getRoot() {
        return root;
    }
}
