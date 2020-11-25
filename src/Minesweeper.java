public class Minesweeper {

    // Methods *************************************************************************
    //This section was created with my wife's uncle Darius Paulauskas

    private static void parseArguments (String[] args) {

        int numberOfTiles = 0;
        double mineProbability = -1.0;
        long debugSeed = -1;
        int i = 0;

        if (args.length != 0) {

            while (i < args.length) {

                switch (args[i]) {

                    case "-numTiles":

                        if (i + 1 < args.length) {

                            try {

                                numberOfTiles = Integer.parseInt (args[++i]);

                            } catch (NumberFormatException e) {

                                System.out.println ("Usage: java Minesweeper -numTiles <integer value>");
                                return;
                            }

                        } else {

                            System.out.println ("Usage: java Minesweeper -numTiles <integer value>");
                            return;
                        }

                        break;

                    case "-mineProb":

                        if (i + 1 < args.length) {

                            try {

                                mineProbability = Double.parseDouble (args[++i]);

                            } catch (NumberFormatException e) {

                                System.out.println ("Usage: java Minesweeper -mineProb <decimal value>");
                                return;
                            }

                        } else {

                            System.out.println ("Usage: java Minesweeper -mineProb <decimal value>");
                            return;
                        }

                        break;

                    case "-seed":

                        if (i + 1 < args.length) {

                            try {

                                debugSeed = Long.parseLong (args[++i]);

                            } catch (NumberFormatException e) {

                                System.out.println ("Usage: java Minesweeper -seed <integer value>");
                                return;
                            }

                        } else {

                            System.out.println ("Usage: java Minesweeper -seed <integer value>");
                            return;
                        }

                        break;

                    default:
                    case "-help":

                        System.out.println ("Usage: java Minesweeper -option1 param1 -option2 param2 ...");
                        System.out.println ("Where options include:");
                        System.out.println ("\t-numTiles <integer value>\tThe number of tiles wide and tall");
                        System.out.println ("\t-mineProb <decimal value>\tThe probability of a tile being a mine");
                        System.out.println ("\t-seed <integer value>\t\tThe exact random seed to use");
                        System.out.println ("\t-help\t\t\t\tPrints the help message");
                        return;
                }
                i++;
            }
        }

        new MinesweeperFrame (numberOfTiles, mineProbability, debugSeed);
    }

    public static void main (String[] args) {

        parseArguments (args);

    }

}