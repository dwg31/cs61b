package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /** Lose 0.03 units of energy on a move action and 0.01
     * on a stay action.
     */
    private static final double MOVE_LOSS = 0.03;
    private static final double STAY_LOSS = 0.01;

    /**
     * Creates a clorus with energy e.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /**
     * Called when clorus moves.
     */
    @Override
    public void move() {
        energy -= MOVE_LOSS;
    }

    /**
     * Called when clorus attacks C.
     */
    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Called when clorus chooses replicate.
     */
    @Override
    public Creature replicate() {
        Clorus rep = new Clorus(energy / 2);
        energy /= 2;
        return rep;
    }

    /**
     * Called when clorus chooses stay.
     */
    @Override
    public void stay() {
        energy -= STAY_LOSS;
    }

    /**
     * Returns an action. Cloruses should obey exactly the following behavioral rules:
     *
     *     1. If there are no empty squares, the Clorus will STAY (even if there
     *        are Plips nearby they could attack since plip squares do not count as empty squares).
     *
     *     2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     *
     *     3. Otherwise, if the Clorus has energy greater than or equal to one, it will
     *        REPLICATE to a random empty square.
     *
     *     4. Otherwise, the Clorus will MOVE to a random empty square.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> seenPlip = new ArrayDeque<>();
        boolean anyPlip = false;

        for (Map.Entry<Direction, Occupant> entry: neighbors.entrySet()) {
            if (entry.getValue().name().equals("empty")) {
                emptyNeighbors.addLast(entry.getKey());
            } else if (entry.getValue().name().equals("plip")) {
                anyPlip = true;
                seenPlip.addLast(entry.getKey());
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (anyPlip) {
            return new Action(Action.ActionType.ATTACK, randomEntry(seenPlip));
        } else if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        } else {
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }
    }

    /**
     * Return the color of a clorus.
     */
    @Override
    public Color color() {
        return new Color(r, g, b);
    }
}
