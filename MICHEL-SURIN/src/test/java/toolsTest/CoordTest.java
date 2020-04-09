package toolsTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.Coord;

public class CoordTest {

    @Test
    @DisplayName("NAME")
    public void addition() {

        Coord c1 = new Coord(4,4);
        Coord c2 = new Coord(1,0);
        Coord res1 = new Coord(5,4);
        Assertions.assertTrue(res1.equals(Coord.add(c1, c2)));
    }
}
