import java.util.Comparator;
/**
 * A beautiful class that implements Comparator interface
 * It has one method called compare that takes two objects of type Vehicles and does integer comparison to their fuel amounts
 * In order to sort them in the descending order before printing the Vehicle with the largest amount of fuel at the end of the simulation.
 * @author Ali Alridha Abdulkarim
 *
 */
public class FuelSorter implements Comparator<Vehicle> {
    public int compare(Vehicle v1, Vehicle v2) {
        return Integer.compare(v2.fuel, v1.fuel);
    }
}