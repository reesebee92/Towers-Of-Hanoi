
/**
 * TowersOfHanoiRecursion
 * 
 * This code represents the recursive solution for the Towers of Hanoi problem
 * The code runs through each disk of the totalDisks value and prints the output to a file
 * 
 * This program has a totalDisks = 20 for file size convenience, however this program ran as
 * high as totalDisks = 45
 * 
 * @version 1.0
 * @author sDantzler
 * */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TowersOfHanoiRecursion {

   public static void main(String[] args) throws IOException {

      BufferedWriter output, times;

      // Check for command line arguments.
      if (args.length != 1) {
         System.out.println(
               "Usage: java TowersOfHanoiRecursion [output filepathname]");
         System.exit(1);
      }

      // initialized n to 1 disk to start
      int n = 1;
      int totalDisks = 20;

      try {
         output = new BufferedWriter(new FileWriter(args[0]));

         /*
          * Created a times output file to record times for higher numbers of n
          * times = new BufferedWriter(new FileWriter(
          * "recursive time output file"));
          */
         try {
            while (n <= totalDisks) {

               output.write("\n");
               output.write("The moves for disk " + n + " are:\n");
               long startTime = System.nanoTime();

               diskMoves(n, "A", "B", "C", output);

               long endTime = System.nanoTime();
               long elapsedTime = (endTime - startTime);

               /*
                * Created an output for just recursive times
                * 
                * times.write("The elapsed time for " + n + " disks is " +
                * elapsedTime + " ns"); times.write("\n");
                */

               output.write("\n");
               output.write("The elapsed time for " + n + " disks is "
                     + elapsedTime + " ns");
               output.write("\n");

               // increase n
               n++;
            } // end while loop

         } // end inner try
         catch (OutOfMemoryError none) {
            output.write("Memory Error: " + none);

         } // end inner catch
         finally {
            try {
               output.close();
               // times.close();
            } // end finally try
            catch (Exception x) {
               output.write(x.toString());
            } // end finally catch
         } // end finally
         return;
      } // end outer try
      catch (IOException e) {
         System.out.println("Output file is invalid");
      } // end outer catch

   }// end main method

   /**
    * This method recursively calls each n disk move The disk moves are
    * dependent on whether n is odd versus even, there are 3 moves total per
    * recursive call, and each result is printed to the output file
    * 
    * @param diskNumber, fromTower, toTower, auxTower, output
    * @throws Exception
    */
   public static void diskMoves(int diskNumber, String fromTower,
         String toTower, String auxTower, BufferedWriter output)
         throws IOException {
      try {
         if (diskNumber == 1) {
            output.write("Move disk " + diskNumber + " from " + fromTower
                  + "to " + toTower + "\n");
         } else {
            diskMoves(diskNumber - 1, fromTower, auxTower, toTower, output);
            output.write("Move disk " + diskNumber + " from " + fromTower
                  + "to " + toTower + "\n");
            diskMoves(diskNumber - 1, auxTower, toTower, fromTower, output);
         } // end if-else
      } // end try
      catch (IOException ioe) {
         output.write("No output file defined");
      } // end catch
   }// end diskMoves method

}// end class TowersOfHanoiRecursion
