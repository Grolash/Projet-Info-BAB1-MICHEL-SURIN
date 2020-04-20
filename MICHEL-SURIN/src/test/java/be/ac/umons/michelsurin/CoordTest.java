package be.ac.umons.michelsurin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import be.ac.umons.michelsurin.tools.Coord;

public class CoordTest {

    @Test
    public void addition() {

        Coord c1 = new Coord(4,4);
        Coord c2 = new Coord(1,0);
        Coord c3 = new Coord(0,0);
        Coord c4 = new Coord(5,4);
        Coord res1 = new Coord(5,4);
        Coord res2 = new Coord(9,8);
        Assertions.assertTrue(res1.equals(Coord.add(c1, c2)));
        Assertions.assertTrue(res1.equals(Coord.add(res1, c3)));
        Assertions.assertTrue(res2.equals(Coord.add(c4, c1)));
        Assertions.assertFalse(c1.equals(Coord.add(c4, c1)));
    }
}
