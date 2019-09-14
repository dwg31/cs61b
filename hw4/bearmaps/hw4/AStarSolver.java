package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A* solver implementation.
 * Find shortest path from given source to end.
 *
 * @author Dawei Gu
 */

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private double explorationTime;
    private int numStatesExplored;
    private double solutionWeight;
    private SolverOutcome outcome;
    private List<Vertex> solution = new ArrayList<>();
    private Map<Vertex, Double> distTo = new HashMap<>();
    private Map<Vertex, Vertex> edgeTo = new HashMap<>();
    private ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ<>();


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        numStatesExplored = 0;

        fringe.add(start, 0 + input.estimatedDistanceToGoal(start, end));
        distTo.put(start, 0.0);

        while (fringe.size() > 0 && sw.elapsedTime() < timeout && !fringe.getSmallest().equals(end)) {
            Vertex v = fringe.removeSmallest();
            numStatesExplored += 1;
            for (WeightedEdge<Vertex> neighbor : input.neighbors(v)) {
                relax(neighbor, input.estimatedDistanceToGoal(neighbor.to(), end));
            }
        }

        if (fringe.size() == 0) {
            outcome = SolverOutcome.UNSOLVABLE;
        } else if (fringe.getSmallest().equals(end)) {
            outcome = SolverOutcome.SOLVED;
            Vertex currVertex = end;
            while (!currVertex.equals(start)) {
                solution.add(0, currVertex);
                currVertex = edgeTo.get(currVertex);
            }
            solution.add(0, start);
            solutionWeight = distTo.get(end);
        } else {
            outcome = SolverOutcome.TIMEOUT;
        }
        explorationTime = sw.elapsedTime();
    }

    private void relax(WeightedEdge<Vertex> neighbor, double h) {
        Vertex p = neighbor.from();
        Vertex q = neighbor.to();
        double w = neighbor.weight();
        if (!distTo.containsKey(q) || distTo.get(p) + w < distTo.get(q)) {
            distTo.put(q, distTo.get(p) + w);
            edgeTo.put(q, p);
            if (fringe.contains(q)) {
                fringe.changePriority(q, distTo.get(q) + h);
            } else {
                fringe.add(q, distTo.get(q) + h);
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        if (outcome == SolverOutcome.SOLVED) {
            return solution;
        }
        return null;
    }

    @Override
    public double solutionWeight() {
        if (outcome == SolverOutcome.SOLVED) {
            return solutionWeight;
        }
        return 0;
    }

    @Override
    public int numStatesExplored() {
        return numStatesExplored;
    }

    @Override
    public double explorationTime() {
        return explorationTime;
    }
}
