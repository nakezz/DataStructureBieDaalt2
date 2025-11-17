import org.junit.jupiter.api.*;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

public class carParkingTest {

    @BeforeEach
    void resetGarage() {
        carParking.garage = new Stack<>();
    }

    @Test
    void testArrivalWhenGarageHasSpace() {
        carParking.process("A AR48-50");

        assertEquals(1, carParking.garage.size());
        assertEquals("AR48-50", carParking.garage.peek().plateNumber);
    }

    @Test
    void testArrivalWhenGarageIsFull() {
        for (int i = 1; i <= carParking.MAX_CAPACITY; i++) {
            carParking.process("A AR90-34" + i);
        }


        carParking.process("OVERFLOW");


        assertEquals(10, carParking.garage.size());
    }

    @Test
    void testDepartureCarExists() {
        carParking.process("A AR50-50");
        carParking.process("A AR51-51");
        carParking.process("A AR53-53");


        carParking.process("D AR51-51");

        assertEquals(2, carParking.garage.size());


        assertEquals("AR53-53", carParking.garage.pop().plateNumber);
        assertEquals("AR50-50", carParking.garage.pop().plateNumber);
    }

    @Test
    void testDepartureCarNotInGarage() {
        carParking.process("A AR50-50");
        carParking.process("A AR51-51");

        carParking.process("D AR41-41");

        assertEquals(2, carParking.garage.size());
    }

    @Test
    void testDepartureFromEmptyGarage() {
        carParking.process("D AR90-34");

        assertTrue(carParking.garage.isEmpty());
    }

    @Test
    void testArrivalAndDepartureMultiple() {
        carParking.process("A AR00-00");
        carParking.process("A BU35-75");
        carParking.process("A BA35-75");
        carParking.process("D BU35-75");

        assertEquals(2, carParking.garage.size());
        assertEquals("AR00-00", carParking.garage.get(0).plateNumber);
        assertEquals("BA35-75", carParking.garage.get(1).plateNumber);
    }
}
