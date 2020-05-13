
/**
 * TowersOfHanoiIteration 
 * 
 * This code represents the iterative solution for the Towers of Hanoi problem
 * The code runs through each disk of the totalDisks value and prints the output to a file
 * 
 * The use of 3 stacks is implemented to handle the 3 possible moves for the game TOH
 * 
 * This program has a totalDisks = 20 for file size convenience, however this program ran as
 * high as totalDisks = 31
 * 
 * @version 1.0
 * @author sDantzler
 * */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TowersOfHanoiIteration {

   public static void main(String[] args) throws IOException {

      BufferedWriter output, times;

      // Check for command line arguments
      if (args.length != 1) {
         System.out.println(
               "Usage: java TowersOfHanoiRecursion [output filepathname]");
         System.exit(1);
      }

      // initialized n to 1 disk to start
      int n = 1;
      int totalDisks = 20;

      // Create 3 stacks to hold the disks during 3 max movement period(s)
      IntegerStack A = new IntegerStack(totalDisks);
      IntegerStack B = new IntegerStack(totalDisks);
      IntegerStack C = new IntegerStack(totalDisks);

      try {
         output = new BufferedWriter(new FileWriter(args[0]));

         /*
          * // Created a times output file to record times for higher numbers of
          * n times = new BufferedWriter(new FileWriter(
          * "iterative time output file"));
          */

         try {
            while (n <= totalDisks) {

               // Create and initialize totalMoves for n disks
               int totalMoves = (int) (Math.pow(2, n) - 1);

               // clear all the stacks after each n increase
               clear(A, B, C);

               // Initialize Source Stack A with n disks
               for (int i = n; i >= 1; i--) {
                  A.push(i);
               }

               output.write("\n");
               output.write("The moves for disk " + n + " are:\n");
               long startTime = System.nanoTime();

               diskMoves(n, totalMoves, A, B, C, output);

               long endTime = System.nanoTime();
               long elapsedTime = (endTime - startTime);

               /*
                * // Created an output for just iterative times
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
    * This method will run a for loop from 1 to totalMoves and output the
    * results. The disk moves change when n is odd versus even and the
    * legalMoves method is called to determine a valid disk move
    * 
    * @param diskNumber, totalMoves, fromTower, toTower, auxTower, output
    * @throws Exception
    */
   public static void diskMoves(int diskNumber, int totalMoves,
         IntegerStack fromTower, IntegerStack toTower, IntegerStack auxTower,
         BufferedWriter output) throws IOException {
      try {
         if (diskNumber % 2 == 1) {
            for (int i = 1; i <= totalMoves; i++) {
               // if the n disk is odd
               if (i % 3 == 1) {
                  legalMoves(fromTower, toTower, "A", "B", output);
               } else if (i % 3 == 2) {
                  legalMoves(fromTower, auxTower, "A", "C", output);
               } else if (i % 3 == 0) {
                  legalMoves(toTower, auxTower, "B", "C", output);
               } // end inner if-else black statements
            } // end for loop
         } else {
            for (int i = 1; i <= totalMoves; i++) {
               // if the n disk is odd
               if (i % 3 == 1) {
                  legalMoves(fromTower, auxTower, "A", "C", output);
               } else if (i % 3 == 2) {
                  legalMoves(fromTower, toTower, "A", "B", output);
               } else if (i % 3 == 0) {
                  legalMoves(auxTower, toTower, "C", "B", output);
               } // end inner if-else black statements
            } // end for loop
         }
      } // end try
      catch (IOException ioe) {
         output.write("No output file defined");
      } // end catch

   }// end diskMoves method

   /**
    * There are 3 rules to keep in mind: You can only move the top disk at the
    * top of a tower You can only move 1 disk at a time You cannot place a disk
    * on top of a smaller one
    * 
    * This method demonstrates the 3 rules mentioned above given any 2 disks and
    * returns the results
    * 
    * @param fromTower, toTower, output
    * @throws Exception
    */
   public static void legalMoves(IntegerStack fromTower, IntegerStack toTower,
         String from, String to, BufferedWriter output) throws IOException {
      try {
         int disk1, disk2;

         // if fromTower is empty
         if (fromTower.isEmpty() && !toTower.isEmpty()) {
            disk2 = toTower.pop();
            fromTower.push(disk2);
            output.write("Move disk " + disk2 + " from tower " + to
                  + " to tower " + from + "\n");
         }
         // if toTower is empty
         else if (toTower.isEmpty() && !fromTower.isEmpty()) {
            disk1 = fromTower.pop();
            toTower.push(disk1);
            output.write("Move disk " + disk1 + " from tower " + from
                  + " to tower " + to + "\n");
         }

         else if (!toTower.isEmpty() && !fromTower.isEmpty()) {

            disk1 = fromTower.pop();
            disk2 = toTower.pop();

            // if disk1 is greater than disk2, put disk1 back in the stack and
            // add disk2 on top
            if (disk1 > disk2) {
               fromTower.push(disk1);
               fromTower.push(disk2);
               output.write("Move disk " + disk2 + " from tower " + to
                     + " to tower " + from + "\n");
            }
            // if disk2 is greater than disk1, put disk2 back in the stack and
            // add disk1 on top
            else {
               toTower.push(disk2);
               toTower.push(disk1);
               output.write("Move disk " + disk1 + " from tower " + from
                     + " to tower " + to + "\n");
            } // end if-elif block statements
         } // end if else-if block statements

      } // end try
      catch (IOException ioe) {
         output.write("No output file defined");
      } // end catch
   }// end legalMoves method

   /**
    * clears the towers after each increment of n disks
    * 
    * @param fromTower, toTower, auxTower
    */
   public static void clear(IntegerStack toTower, IntegerStack fromTower,
         IntegerStack auxTower) {

      while (!toTower.isEmpty()) {
         toTower.pop();
      } // end while loop

      while (!fromTower.isEmpty()) {
         fromTower.pop();
      } // end while loop

      while (!auxTower.isEmpty()) {
         auxTower.pop();
      } // end while loop
   }// end clear method

}// end class TowersOfHanoiIteration
