import static org.junit.Assert.*;
import java.io.*;
import org.junit.*;
import org.junit.jupiter.api.AfterEach;

public class SimpleTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }
    @AfterEach
    public void Simulate_Initialize_City() throws IOException {
        String testFileOne = "test1.txt";
        SimplifiedMain.main(new String []{testFileOne});
    }

    @Test
    public void err() {
        System.err.print("Error OutPut");
        assertEquals("Error OutPut", errContent.toString());
    }

    @Test
    public void testCase1() throws IOException {
        String testFileOne = "test1.txt";
        SimplifiedMain.main(new String []{testFileOne});
        assertEquals("0 1 4", outContent.toString());
    }

    @Test
    public void testCase2() throws IOException {
        String testFileOne = "test2.txt";
        SimplifiedMain.main(new String []{testFileOne});
        assertEquals("out of fuel", outContent.toString());
    }
    @Test
    public void testCase3() throws IOException {
        String testFileOne = "test3.txt";
        SimplifiedMain.main(new String []{testFileOne});
        assertEquals("out of bound", outContent.toString());
    }
    @Test
    public void testCase4() throws IOException {
        String testFileOne = "test4.txt";
        SimplifiedMain.main(new String []{testFileOne});
        assertEquals("road accident", outContent.toString());
    }

    @Test
    public void testCase5() throws IOException {
        String testFileOne = "test5.txt";
        SimplifiedMain.main(new String []{testFileOne});
        assertEquals("vehicles are too close to each other", outContent.toString());
    }

    @Test
    public void testFuel() {
        Car testCar = new Car(0, 2, 1, 10);
        boolean ItHasFuel = true;
        assertEquals(ItHasFuel, testCar.enoughFuel());
    }

    @Test
    public void testFuel2() {
        Car testCar = new Car(0, 2, 1, 0);
        boolean ItHasFuel = false;
        assertEquals(ItHasFuel, testCar.enoughFuel());
    }

    @Test
    public void testMove() throws FuelException {
        Move dummyMove = new Move(0, 0, 4, 7);
        Truck BigTruck = new Truck(1, 0,0, 15);
        Cell[][] dummyGrid = new Cell[12][12];
        BigTruck.apply(dummyMove, dummyGrid);
        int newX = BigTruck.x;
        int newY = BigTruck.y;
        assertEquals(4, newX);
        assertEquals(7, newY);
    }

}