/**
 * WordCounter.java builds a hashmaps from the output files of NovelProcessor.java in order to track
 * the total number of matches for each pattern across mutliple books
 * @author Jared Rosenberger
 * @version 2.0
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class WordCounter {
    private static HashMap<String, Integer> patterns;
    public static void main(String[] args) {
        //Initialize the patterns hashmap
        patterns = new HashMap<String, Integer>();
        //Get file path for current working directory
        String path = new File("").getAbsolutePath();
        //Create file to represent current working directory
        File directory = new File(path);
        searchDirectory(directory);    
        printOutput();
    }//end main

    /**
     * printOutput is used to print out and format the output of the patterns hashmap
     */
    private static void printOutput() {
        System.out.println("---------------");
        //Print outut from hashmap
        System.out.println("Total word counts for each pattern:");
        for(HashMap.Entry<String, Integer> entry : patterns.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " | " + value);
        }
    }//end printOutput

    /**
     * searchDirectory searchs the current directory for files containing _wc.txt and adds them to the hashmap
     * @param directory is a File that represents the current working directory
     */
    private static void searchDirectory(File directory) {
        File[] files = directory.listFiles();
        int wcFileCount = 0;
        for(int i =0; i<files.length; i++) {
            if(files[i].getPath().contains("_wc.txt")) {
                File wcFile = new File(files[i].getPath());
                //System.out.println(wcFile.getName());
                addToMap(wcFile);
                wcFileCount++;
            }
        }
        if(wcFileCount == 0) {
            System.out.println("No _wc.txt files could be found in " + directory.getPath());
        }
    }//end searchDirectory

    /**
     * addToMap scans a given file and add it's pattern and number to the hashmao
     * @param toRead is the txt file wuth eahc line being *pattern* | *number*
     */
    private static void addToMap(File toRead) {
        try {             
            Scanner fileReader = new Scanner(toRead);
            String pattern = "";
            String number = "";
            String temp = "";
            int num = 0;
            while(fileReader.hasNextLine()) {
                temp = fileReader.nextLine();
                //The | character is used to split the line into a pattern and a number
                int mid = temp.lastIndexOf("|");
                pattern = temp.substring(0, mid-1);
                number = temp.substring(mid+2);
                num = Integer.parseInt(number);
                //If pattern is not in map, put the pattern and it's count into the hashmap
                if(!patterns.containsKey(pattern)) {
                    patterns.put(pattern, num);
                }
                //If the pattern is in the map, add the stored number and the new count, and store the new value
                else {
                    int adder = patterns.get(pattern);
                    adder += num;
                    //patterns.put(pattern, adder);
                    patterns.replace(pattern, adder);
                }
            }//end while
            fileReader.close();
            //System.out.println(patterns);
        }catch(FileNotFoundException e){System.out.println("could not find file " +toRead);}
    }//end addToMap
}//end WordCounter