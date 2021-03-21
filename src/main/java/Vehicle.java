public class Vehicle extends Cell{
    int fuel;

    public Vehicle(int type, int x, int y, int fuel) {
        super(type, x, y);
        this.fuel = fuel;
    }

    /**
     * <p>
     * This method simply returns a boolean indicating the state of the fuel tank inside a vehicle
     * the state of the fuel tank is either there is an enough amount of fuel inside to apply the move in this case it returns true
     * or the amount is not sufficient then it returns false.
     * This method is being overridden within the two subclasses Car and Truck of the parent class Vehicle.
     * </p>
     * @see Car#enoughFuel()
     * @see Truck#enoughFuel()
     * returns boolean True if the fuel is enough, false if not
     */
    public boolean enoughFuel() {
        return false;
    }
    /**
     * <p> after this method is being called as a first step in the process of applying a move on a vehicle
     * Though it might be not a good practice but it also handles unlisted (unspecified) exceptions and catch them with a print statement.
     * </p>
     * @param someMove of type Move refers to the move that is meant to be applied on the corresponding cell,
     * @param Grid a two dimensional array of a custom defined type Cell,
     * @throws FuelException means that the available amount of fuel is not enough to apply the corresponding move
     * @see #moveVehicle(Move, Cell[][])
     */
    public void apply(Move someMove, Cell[][] Grid) throws FuelException {
        if (!enoughFuel()) {
            throw new FuelException("out of fuel");
        }
        else {
            try { this.moveVehicle(someMove, Grid); }
            catch (TrafficExceptions trafficExceptions) {
                System.out.println("error");
            }
        }
    }
    /**
     * <p> after this method is being called by the apply method it does the actual action of moving to the corresponding grid cell
     * Since all the moves are valid, the target cell is guaranteed to be either a car or a truck,
     * the move is being applied just by changing the (x,y) coords for the target cell.
     * </p>
     * @param someMove of type Move refers to the move that is meant to be applied on the corresponding cell,
     * @param Grid a two dimensional array of a custom defined type Cell,
     * @throws TrafficExceptions which is unspecified exception in the problem statement which can be interpreted as (any other error)
     * @see #apply(Move, Cell[][])
     */
    public void moveVehicle(Move someMove, Cell[][] Grid) throws TrafficExceptions {
        if (enoughFuel()) {
            Cell temp = Grid[someMove.from_x][someMove.from_y];
            Grid[someMove.from_x][someMove.from_y] = Grid[someMove.to_x][someMove.to_y];
            Grid[someMove.to_x][someMove.to_y] = temp;

            this.x = someMove.to_x;
            this.y = someMove.to_y;
            this.fuel -= 1;
        }
        else throw new TrafficExceptions("error");
    }
}


