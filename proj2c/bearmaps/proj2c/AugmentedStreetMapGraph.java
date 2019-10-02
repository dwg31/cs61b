package bearmaps.proj2c;

import bearmaps.lab9.MyTrieSet;
import bearmaps.proj2ab.KDTree;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, Dawei Gu
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    private Map<Point, Node> pToNMap;
    private KDTree nodeTree;
    private Map<String, List<Node>> nameToNode;
    private MyTrieSet cleanedNameSet;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();
        pToNMap = new HashMap<>();
        nameToNode = new HashMap<>();
        cleanedNameSet = new MyTrieSet();
        for (Node node : nodes) {
            if (!neighbors(node.id()).isEmpty()) {
                Point p = new Point(node.lat(), node.lon());
                pToNMap.put(p, node);
                points.add(p);
            }
            nodeTree = new KDTree(points);
            if(node.name() != null) {
                String cleanedName = cleanString(node.name());
                if (!nameToNode.containsKey(cleanedName)) {
                    nameToNode.put(cleanedName, new LinkedList<>());
                    cleanedNameSet.add(cleanedName);
                }
                nameToNode.get(cleanedName).add(node);
            }
        }
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        Point nPoint = nodeTree.nearest(lon, lat);
        return pToNMap.get(new Point(nPoint.getX(), nPoint.getY())).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<String> cleanLocationNames = cleanedNameSet.keysWithPrefix(prefix);
        List<String> originalNames = new ArrayList<>();
        for (String cleanedName : cleanLocationNames) {
            for (Node n : nameToNode.get(cleanedName)) {
                if (!originalNames.contains(n.name())) {
                    originalNames.add(n.name());
                }
            }
        }
        return originalNames;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        String cleanedName = cleanString(locationName);
        List<Map<String, Object>> locationList = new ArrayList<>();
        if (nameToNode.containsKey(cleanedName)) {
            for (Node n : nameToNode.get(cleanedName)) {
                Map<String, Object> location = new HashMap<>();
                location.put("lat", n.lat());
                location.put("lon", n.lon());
                location.put("name", n.name());
                location.put("id", n.id());
                locationList.add(location);
            }
        }
        return locationList;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
