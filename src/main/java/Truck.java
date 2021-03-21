public class Truck extends Vehicle {
    Truck(int type, int x, int y, int fuel) {
        super(type, x, y, fuel);
    }
    /**
     * <p>
     * This method simply returns a boolean indicating the state of the fuel tank inside a Truck
     * the state of the fuel tank is either there is an enough amount of fuel inside to apply the move in this case it returns true
     * or the amount is not sufficient then it returns false.
     * This method is an override method to the method in the parent class Vehicle,
     * in order to make the judgement of fuel sufficiency suitable for vehicles of type Truck.
     * </p>
     * @see Vehicle#enoughFuel()
     * @see Car#enoughFuel()
     * returns boolean True if the fuel is enough, false if not
     */
    @Override
    public boolean enoughFuel(){
        return this.fuel >= 2;
    }
}