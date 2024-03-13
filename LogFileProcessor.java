/**
 * LogFileProcessor.java accepts command line args for a file name and number, then processes the log
 * file to track the number of unique IPs and usernames. The number input can be used to pick between 2 detailed
 * outputs and a default
 * @author Jared Rosenberger
 * @version 2.1
 * Assignment 4
 * CS322 - Compiler Construction
 * Spring 2024
 */
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
public class LogFileProcessor {
    private static HashMap<String, Integer> userMap;
    private static HashMap<String, Integer> ipMap;
    public static void main (String[] args) {
        //Test for correct number of args
        if(args.length<2) {
            System.out.println("This program takes 2 args: the name of the file to parse, and a number for the output mode");
            System.exit(1);
        }
        else if(args.length > 2) {
            System.out.println("This program only takes 2 args: the name of the file to parse, and a number for the output mode");
            System.exit(1);
        }
        //Declare regex patterns for usernames and IPs
        String userPattern = "user [A-a-Z-z]+";
        String ipPattern = "((\\d)+\\.)+(\\d)+";
        //Declare HashMaps for usernames and IPs
        userMap = new HashMap<String, Integer>();
        ipMap = new HashMap<String, Integer>();
        //Declare auth file with file name passed in as args[0]
        File auth = new File(args[0]);

        //Scanning log file
        try {
            Scanner readAuth = new Scanner(auth);
            //Declare counters
            int lineCount =0;
            //Declare regEx patterns and matchers
            Pattern username = Pattern.compile(userPattern);
            Pattern ip = Pattern.compile(ipPattern);
            Matcher userMatch;
            Matcher ipMatch;
            System.out.println("Processing...");
            //While loop to scan the whole file
            while(readAuth.hasNextLine()) {
                String temp = readAuth.nextLine();
                userMatch = username.matcher(temp);
                //Checks for username matches
                if(userMatch.find()) {
                    String tempUser = temp.substring(userMatch.start(), userMatch.end());
                    //Puts match and 0 onto userMap if the match is a new unique username
                    if(!userMap.containsKey(tempUser)) {
                        userMap.put(tempUser, 0);
                    }
                    //Updates the count of a duplicate username is found
                    else {
                        int adder = userMap.get(tempUser);
                        adder++;
                        //userMap.put(tempUser, adder);
                        userMap.replace(tempUser, adder);
                    }
                }//end if for userMatch
                ipMatch = ip.matcher(temp);
                //Checks for IP matches
                if(ipMatch.find()) {
                    String tempIP = temp.substring(ipMatch.start(), ipMatch.end());
                    //Puts match and 0 onto userMap if the match is a new unique IP
                    if(!ipMap.containsKey(tempIP)) {
                        ipMap.put(tempIP, 0);
                    }
                    //Updates the count of a duplicate username is found
                    else {
                        int adder = ipMap.get(tempIP);
                        adder++;
                        //ipMap.put(tempIP, adder);
                        ipMap.replace(tempIP, adder);
                    }
                }//end if for ipMatch
                lineCount++;
            }//end While

            //Checks String[] args for which output mode to use
            if(Integer.parseInt(args[1]) == 1) {
                option1(lineCount);
            }
            else if(Integer.parseInt(args[1]) == 2) {
                option2(lineCount);
            }
            else {
                option0(lineCount);
            }
            readAuth.close();
        }catch(FileNotFoundException e) {System.out.println("Could not find the file " + args[0]);}
    }//end main

    /**
     * getIPMapSize gives the current size of ipMap
     * @return is the int size of ipMap
     */
    private static int getIPMapSize() {
        return ipMap.size();
    }//end getIPMapSize

    /**
     * getUserMapSize gives the current size of userMap
     * @return is the int size of userMap
     */
    private static int getUserMapSize() {
        return userMap.size();
    }//end getUserMapSize

    /**
     * printIPMap prints out each unique IP and it's number of occurences
     */
    private static void printIPMap() {
        for(HashMap.Entry<String, Integer> entry : ipMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + ": " + value);
        }
    }//end printIPMap

    /**
     * printUserMap prints out each unique username and it's number of occurences
     */
    private static void printUserMap() {
        for(HashMap.Entry<String, Integer> entry : userMap.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            //Each key entry is "user *username*", so the user name must be taken out of the key
            String user = key.substring(key.indexOf(" ")+1);
            System.out.println(user + ": " + value);
        }
    }//end printIPMap

    /**
     * option0 handles the output for the default case, String args[1] != 1 or 2
     * @param count is the number of lines in the given file that were processed
     */
    private static void option0(int  count) {
        System.out.println("-----------------");
        System.out.println(count + " lines in the log file were parsed.");
        System.out.println("There are " + getIPMapSize() + " unique IP addresses in the log.");
        System.out.println("There are " + getUserMapSize() + " unique users in the log.");
    }//end option0

    /**
     * option1 handles the output for case 1, String args[1] == 1 where ipMap is printed and then the default option
     * @param count is the number of lines in the given file that were processed
     */
    private static void option1(int  count) {
        printIPMap();
        option0(count);
    }//end option1

    /**
     * option2 handles the output for case 2, String args[1] == 2 where userMap is printed out and then the default option
     * @param count is the number of lines in the given file that were processed
     */
    private static void option2(int count) {
        printUserMap();
        option0(count);
    }//end option3
}//end LogFileProcessor