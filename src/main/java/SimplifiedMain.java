import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
/**
 * <h1>SimplifiedMain Class</h1>
 * Is the main class which acts as a starter or cranking motor for all the methods and classes instances
 * <b>
 * THE MAIN DIFFERENCE BETWEEN THIS CLASS AND THE MORE COMPLEX VERSION, IS THAT THIS ONE IS EASIER TO APPLY TESTS
 * MAINLY BECAUSE IT HAS A SIMPLIFIED MECHANISM OF READING, THE FILE NAME IS BEING PASSED AS AN ARGUMENT TO THE MAIN FUNCTION
 * AND THEN THE TESTS ARE BEING RUN, TO USE THIS CLASS IT IS RECOMMENDED FIRST TO COMMENT THE OTHER VERSION (MainClass.java),
 * IN ORDER NOT TO GET DUPLICATES WARNINGS FROM THE IDE.
 *</b>
 * A summary to the class functionality would be:
 * <ul>
 * <li>Reading the input from user in which each line has different meaning knowing which one corresponds to which
 * <li>Initializing and setting up the City grid with different types (including, plain cells, building, vehicles (cars and trucks)
 * <li>Applying moves to the scene (runs the city simulation)
 * <li>Throwing the right exceptions from the right methods, in case the move results in some undesired behaviour
 * <li>Print the final answer in the required form
 * </ul>
 * @author Ali Alridha Abdulkarim
 */
public class SimplifiedMain {
    public static void main(String[] args) throws IOException {
        String file = "";
        if(args.length > 0) file = args[0];
        // ____________________READ DIMENSIONS_________________
        List<String> allLines = Files.readAllLines(Paths.get("src/ContestTests/"+file));
        int[] Dimension = new int[2];
        String[] line = allLines.get(0).split(" ");
        Dimension[0] = Integer.parseInt(line[0]);
        Dimension[1] = Integer.parseInt(line[1]);
        int M = Dimension[0];
        int N = Dimension[1];
        // ____________________READ BUILDINGS_________________
        String[] info = allLines.get(1).split(" ");
        int[][] arrayOfBuildings = new int[info.length / 4][4];
        // ____________________READ VEHICLES__________________
        String[] data = allLines.get(2).split(" ");
        int[][] arrayOfVehicles = new int[data.length / 4][4];
        // ____________________READ MOVES____________________
        String[] from_to = allLines.get(3).split(" ");
        int[][] arrayOfMoves = new int[from_to.length / 4][4];
        // ____________________CLUSTER OF MOVES______________
        int c = 0;
        for (int i = 0; i < arrayOfMoves.length; i++) {
            for (int j = 0; j < arrayOfMoves[i].length; j++) {
                arrayOfMoves[i][j] = Integer.parseInt(from_to[c++]);
            }
        }
        // ____________________OBJECTIFY MOVES________________
        ArrayList<Move> objectsOfMoves = new ArrayList<>();
        int from_x, from_y, to_x, to_y;

        for (int i = 0; i < from_to.length / 4; i++) {
            from_x = arrayOfMoves[i][0] - 1;
            from_y = arrayOfMoves[i][1] - 1;
            to_x = arrayOfMoves[i][2] - 1;
            to_y = arrayOfMoves[i][3] - 1;
            objectsOfMoves.add(new Move(from_x, from_y, to_x, to_y));
        }
        // ____________________CLUSTER OF BUILDINGS__________
        int leftDown_x, leftDown_y, upperRight_x, upperRight_y;
        int q = 0;
        for (int i = 0; i < arrayOfBuildings.length; i++) {
            for (int j = 0; j < arrayOfBuildings[i].length; j++) {
                arrayOfBuildings[i][j] = Integer.parseInt(info[q++]);
            }
        }
        // ____________________Play the Scene__________
        int k = 0;
        for (int i = 0; i < arrayOfVehicles.length; i++) {
            for (int j = 0; j < arrayOfVehicles[i].length; j++) {
                arrayOfVehicles[i][j] = Integer.parseInt(data[k++]);
            }
        }

        int type, x, y, fuel;
        Cell[][] Grid = new Cell[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                Grid[i][j] = new Cell(3,i,j);
            }
        }

        for (int i = 0; i < data.length / 4; i++) {
            type = arrayOfVehicles[i][0];
            x = arrayOfVehicles[i][1] - 1;
            y = arrayOfVehicles[i][2] - 1;
            fuel = arrayOfVehicles[i][3];

            if (type == 0)
                Grid[x][y] = new Car(type, x, y, fuel);
            else
                Grid[x][y] = new Truck(type, x, y, fuel);
        }

        for (int i = 0; i < info.length / 4; i++) {
            leftDown_x = arrayOfBuildings[i][0] - 1;
            leftDown_y = arrayOfBuildings[i][1] - 1;
            upperRight_x = arrayOfBuildings[i][2] - 1;
            upperRight_y = arrayOfBuildings[i][3] - 1;

            for (int x_b = leftDown_x; x_b <= upperRight_x; x_b++) {
                for (int y_b = leftDown_y; y_b <= upperRight_y; y_b++) {
                    Grid[x_b][y_b] = new Building_Cell(2, x_b, y_b);
                }
            }
        }

        // ____________________Play the Scene__________________
        for (Move anyMove : objectsOfMoves) {
            if (Grid[anyMove.from_x][anyMove.from_y] instanceof Vehicle) {
                try {
                    checkBounds(anyMove, M, N);
                    checkRoadAccident(Grid, anyMove);

                    if (noVehiclesNearby(Grid, Grid[anyMove.from_x][anyMove.from_y]))
                        ((Vehicle) Grid[anyMove.from_x][anyMove.from_y]).apply(anyMove, Grid);

                    checkSafeDistance(Grid, anyMove);

                    if (objectsOfMoves.indexOf(anyMove) == objectsOfMoves.size() - 1) {
                        answerPrinter(Grid, M, N);
                    }
                }
                catch (BoundException outOfBound) {
                    System.out.print("out of bound");
                    return;
                }
                catch (AccidentException RoadAccident) {
                    System.out.print("road accident");
                    return;
                }
                catch (FuelException outOfFuel) {
                    System.out.print("out of fuel");
                    return;
                }
                catch (DistanceException noSafeDistance) {
                    System.out.print("vehicles are too close to each other");
                    return;
                }
            }
        }
    }

    /**
     * <p>A method that is used to help printing the final answer in the required from
     * </p>
     * @param Grid a two dimensional array of a custom defined type Cell,
     * each element in this array represents a cell which has 2 coords x and y
     * @param M the width of our city grid
     * @param N the height of our city grid
     */
    public static void answerPrinter(Cell [][] Grid, int M, int N) {
        ArrayList<Vehicle> listGridVehicles = new ArrayList<>();
        for (int i=0; i<M; i++) {
            for (int j = 0; j < N; j++) {
                if (Grid[i][j] instanceof Vehicle) {
                    listGridVehicles.add((Vehicle)Grid[i][j]);
                }
            }
        }
        listGridVehicles.sort(new FuelSorter());
        for(Vehicle dummy : listGridVehicles){
            dummy.x +=1;
            dummy.y +=1;
        }


        System.out.print(
                (listGridVehicles.get(0)).type
                        + " " +
                        (listGridVehicles.get(0)).x
                        + " " +
                        (listGridVehicles.get(0)).y
        );
    }
    /**
     * <p>A method that is used to checkBounds limitations and it throws exception whenever the object is trying to move somewhere outside.
     * </p>
     * @param anyMove an object of type Move which will be received and then applied to any object that lie on the cell of coords from_x from_y,
     * since all the moves are valid, there will be for sure an object there, waiting for this move to be applied on.
     * @param M the width of our city grid
     * @param N the height of our city grid
     * @throws BoundException means that the move will cause the vehicle to be out of the city bounds
     */
    public static void checkBounds(Move anyMove, int M, int N) throws BoundException {
        if (anyMove.to_x < 0 || anyMove.to_y < 0 || anyMove.to_y > N - 1 || anyMove.to_x > M - 1){
            throw new BoundException("out of bound");
        }
    }
    /**
     * <p>A method that checks whether the meant move that is applied on a certain vehicle will cause the vehicle to crash into a building.
     * this is basically done by just checking whether the final destination of that move will be in a cell of type building.
     * </p>
     * @param Grid a two dimensional array of a custom defined type Cell,
     * each element in this array represents a cell which has 2 coords x and y
     * @param anyMove an object of type Move which will be received and then applied to any object that lie on the cell of coords from_x from_y,
     * since all the moves are valid, there will be for sure an object there, waiting for this move to be applied on.
     * @throws AccidentException means that the move will results in a road accident
     */
    public static void checkRoadAccident(Cell[][]Grid, Move anyMove) throws AccidentException {
        if (Grid[anyMove.to_x][anyMove.to_y] instanceof Building_Cell) {
            throw new AccidentException("road accident");
        }
    }
    /**
     * <p>A method that checks whether a move that is applied to some vehicle will not result in making other vehicle too close to it.
     * </p>
     * @param Grid a two dimensional array of a custom defined type Cell,
     * each element in this array represents a cell which has 2 coords x and y
     * @param anyMove an object of type Move which will be received and then applied to any object that lie on the cell of coords from_x from_y,
     * since all the moves are valid, there will be for sure an object there, waiting for this move to be applied on.
     * @throws DistanceException means that the distance between the vehicles in less or equal than 1
     */
    public static void checkSafeDistance(Cell[][]Grid, Move anyMove) throws DistanceException {
        if (!noVehiclesNearby(Grid, Grid[anyMove.to_x][anyMove.to_y])) {
            throw new DistanceException("vehicles are too close to each other");
        }
    }
    /**
     * <p>A method that checks whether a move that is applied to some vehicle will not result in making other vehicle too close to it.
     * </p>
     * @param Grid a two dimensional array of a custom defined type Cell,
     * each element in this array represents a cell which has 2 coords x and y
     * @param movingVehicle an object of type cell preferably has to be of type vehicle, but it is passed as a cell in order to make checking the type simpler.
     * @return boolean
     */
    public static boolean noVehiclesNearby(Cell[][] Grid, Cell movingVehicle){
        int i = movingVehicle.x;
        int j = movingVehicle.y;
        int[] ith = {0, 1, 1, -1, 0, -1, -1, 1};
        int[] jth = {1, 0, 1, 0, -1, -1, 1, -1};
        for (int k = 0; k < 8; k++) {
            if (isValid(i + ith[k], j + jth[k], Grid.length - 1)) {
                if ((Grid[i + ith[k]][j + jth[k]]).type == 0
                        ||
                        (Grid[i + ith[k]][j + jth[k]]).type == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid(int i, int j, int l) {
        return i >= 0 && j >= 0 && i <= l  && j <= l ;
    }
}