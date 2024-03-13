/**
 * NovelProcessor.java accepts command line input of two files names, one txt and
 * one with regEx patterns. It scans the text file with the given regEx patterns
 * @author Jared Rosenberger
 * @version 1.0
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
public class NovelProcessor {
    public static void main(String[] args) {
        //Test for correct number of args
        if(args.length<2) {
            System.out.println("This program takes 2 args: the name of the file to parse, and the name of the file with regEx patterns.");
            System.exit(1);
        }
        else if(args.length > 2) {
            System.out.println("This program only takes 2 args: the name of the file to parse, and the name of the file with regEx patterns.");
            System.exit(1);
        }
        //Counter & Tracker variables for matches and patterns
        String pattern1="",pattern2="",pattern3="",pattern4="",pattern5="",pattern6="",pattern7="";
        int pat1=0,pat2=0,pat3=0,pat4=0,pat5=0,pat6=0,pat7=0;

        //Accepting user input for filenames
        String bookName = args[0];
        String regExName = args[1];
        
        //Try&Catch for FileNotFoundException that can be thrown by Scanner
        try {
            //Create files and pattern file Scanner
            File book = new File(bookName);
            File patternFile = new File(regExName);
            Scanner patternScan = new Scanner(patternFile);
            //loopCount tracks which pattern the loop is on
            int loopCount = 0;
            //Outer loop iterrates through pattern file
            System.out.println("Processing...");
            while(patternScan.hasNextLine()) {
                loopCount++;
                //Pattern creation
                String regEx = patternScan.nextLine();
                Pattern pat = Pattern.compile(regEx);
                //Match counter
                int count = 0;
                //Initialzation of book scanner here allows book to be reloaded for each pattern
                Scanner bookScan = new Scanner(book);
                
                //Inner loop iterrates through txt file looking for matches to outer loop's pattern
                while(bookScan.hasNextLine()) {
                    //match checks for specified pattern in each line read from txt file
                    Matcher match = pat.matcher(bookScan.nextLine().toLowerCase());
                    if(match.find()) {
                        count++;
                        //System.out.println("Match found!");
                    }
                }
                //Just to make VSCode happy :/
                bookScan.close();

                //Conditions to save each pattern's string and match count
                if(loopCount == 1) {
                    pat1 = count;
                    pattern1 = regEx;
                }
                else if(loopCount == 2) {
                    pat2 = count;
                    pattern2 = regEx;
                }
                else if(loopCount == 3) {
                    pat3 = count;
                    pattern3 = regEx;
                }
                else if(loopCount == 4) {
                    pat4 = count;
                    pattern4 = regEx;
                }
                else if(loopCount == 5) {
                    pat5 = count;
                    pattern5 = regEx;
                }
                else if(loopCount == 6) {
                    pat6 = count;
                    pattern6 = regEx;
                }
                else {
                    pat7 = count;
                    pattern7 = regEx;
                }
                System.out.println("Pattern" + loopCount + " checked.");
            }
            /* 
            //Cmd line output to show match number
            System.out.println("Pattern1 had " + pat1 + " matches");
            System.out.println("Pattern2 had " + pat2 + " matches");
            System.out.println("Pattern3 had " + pat3 + " matches");
            System.out.println("Pattern4 had " + pat4 + " matches");
            System.out.println("Pattern5 had " + pat5 + " matches");
            System.out.println("Pattern6 had " + pat6 + " matches");
            System.out.println("Pattern7 had " + pat7 + " matches");
            */
            //Create and Write to output file
            int dotIndex = bookName.indexOf(".");
            String outputName = bookName.substring(0, dotIndex);
            File output = new File(outputName + "_wc.txt");
            try {
                output.createNewFile();
                FileWriter write = new FileWriter(outputName + "_wc.txt");
                writeOutput(write, pattern1, pat1);
                writeOutput(write, pattern2, pat2);
                writeOutput(write, pattern3, pat3);
                writeOutput(write, pattern4, pat4);
                writeOutput(write, pattern5, pat5);
                writeOutput(write, pattern6, pat6);
                writeOutput(write, pattern7, pat7);
                write.close();
            }catch(IOException e) {System.out.println("There was an error creating the output file");}
            patternScan.close();
        }catch(FileNotFoundException e) {System.out.println("Could not find pattern or book file");}
    }//end main

    /**
     * writeOutput writes a line of output consisting of a regex pattern and its number of matches 
     * to a premade/specified file
     * @param writer is the FileWriter to write with and the File to write to
     * @param pattern is the regEx pattern to write
     * @param counter is the number of matches for the regEx pattern
     */
    private static void writeOutput(FileWriter writer, String pattern, int counter) {
        try {
            writer.write(pattern + " | " + counter + "\n");
        }catch(IOException e) {System.out.println("There was an eror writing output");}
        }//end writeOutput
}//end NovelProcessor