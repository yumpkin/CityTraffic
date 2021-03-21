final class ThingsOnGrid {

    private final int [][]arrayOfSomething;
    private final int numberOfSameThing;

    public ThingsOnGrid(String[] dataToParse) {
        int k = 0;
        int[][] arrayOfSomething = new int[dataToParse.length / 4][4];
        for (int i = 0; i < arrayOfSomething.length; i++) {
            for (int j = 0; j < arrayOfSomething[i].length; j++) {
                arrayOfSomething[i][j] = Integer.parseInt(dataToParse[k++]);
            }
        }
        this.arrayOfSomething = arrayOfSomething;
        this.numberOfSameThing = dataToParse.length / 4;
    }

    public int[][] getThingOnGrid() {
        return arrayOfSomething;
    }
    public int getHowManyOfThat() {
        return numberOfSameThing;
    }
}