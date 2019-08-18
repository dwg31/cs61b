package creatures;

import huglife.*;
import org.junit.Test;
import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;


public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(2);
        assertEquals(2, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(1.97, c.energy(), 0.01);
        c.move();
        assertEquals(1.94, c.energy(), 0.01);
        c.stay();
        assertEquals(1.93, c.energy(), 0.01);
        c.stay();
        assertEquals(1.92, c.energy(), 0.01);
        c.replicate();
        assertEquals(0.96, c.energy(), 0.01);
    }

    @Test
    public void testAttack() {
        /* No empty adjacent spaces; stay. */
        Clorus c = new Clorus(2);
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, p);
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action expected = new Action(Action.ActionType.STAY);
        Action actual = c.chooseAction(surrounded);

        assertEquals(expected, actual);


        /* Any Plips are seen, the Clorus will ATTACK one of them randomly. */
        c = new Clorus(3);
        HashMap<Direction, Occupant> anyPlip = new HashMap<Direction, Occupant>();
        anyPlip.put(Direction.TOP, new Empty());
        anyPlip.put(Direction.BOTTOM, p);
        anyPlip.put(Direction.LEFT, new Empty());
        anyPlip.put(Direction.RIGHT, new Impassible());

        expected = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);
        actual = c.chooseAction(anyPlip);

        assertEquals(expected, actual);


        /* if the Clorus' energy >= 1, it will REPLICATE to a random empty square. */
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> anyEmpty = new HashMap<Direction, Occupant>();
        anyEmpty.put(Direction.TOP, new Impassible());
        anyEmpty.put(Direction.BOTTOM, new Impassible());
        anyEmpty.put(Direction.LEFT, new Empty());
        anyEmpty.put(Direction.RIGHT, new Impassible());

        expected = new Action(Action.ActionType.REPLICATE, Direction.LEFT);
        actual = c.chooseAction(anyEmpty);

        assertEquals(expected, actual);


        /* If the Clorus' energy <= 1, it will MOVE to a random empty square. */
        c = new Clorus(0.7);
        HashMap<Direction, Occupant> oneEmpty = new HashMap<Direction, Occupant>();
        oneEmpty.put(Direction.TOP, new Impassible());
        oneEmpty.put(Direction.BOTTOM, new Impassible());
        oneEmpty.put(Direction.LEFT, new Empty());
        oneEmpty.put(Direction.RIGHT, new Impassible());

        expected = new Action(Action.ActionType.MOVE, Direction.LEFT);
        actual = c.chooseAction(oneEmpty);

        assertEquals(expected, actual);
    }
}
