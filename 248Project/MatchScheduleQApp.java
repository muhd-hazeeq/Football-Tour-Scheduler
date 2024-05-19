import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class MatchScheduleQApp
{
    static Scanner in = new Scanner(System.in);
    static Scanner inLine = new Scanner(System.in);
    static Queue matchQ = new Queue();
    static Queue uitmQ = new Queue();
    static Queue nonUitmQ = new Queue();
    static Queue tempQ = new Queue();
    static Queue tempQ2 = new Queue();
    static Queue tempQ3 = new Queue();
    
    public static void main(String[] args) throws Exception {
        boolean exit = false;
        while(!exit){
            boolean repeat = true;
            System.out.println("\f\tUiTM RAUB FOOTBALL TOUR MATCHES LIST");
            System.out.println("\n**********MENU**********");
            System.out.printf("%-20s %-5s %-40s", "\nA - View matches", "==>", "Choose from matchList, uitmList or nonUitmList");
            System.out.printf("%-20s %-5s %-40s", "\nB - Remove matches", "==>", "Remove from matchList and into uitmList or nonUitmList");
            System.out.printf("%-20s %-5s %-40s", "\nC - Search matches", "==>", "Search specific match by dates");
            System.out.printf("%-20s %-5s %-40s", "\nD - Update matches", "==>", "Update Stadium Raub's capacity from 1200 to 1000");
            System.out.println("\nE - Exit");
            System.out.print("\nAction (A/B/C/D/E): ");
            char action = in.next().charAt(0);
            if(action == 'A' || action == 'a'){
                while(repeat){
                    viewMatches();
                    repeat = repeatProcess();
                }
            }
            else if(action == 'B' || action == 'b'){
                while(repeat){
                    removeMatches();
                    repeat = repeatProcess();
                }
            }
            else if(action == 'C' || action == 'c'){
                while(repeat){
                    searchMatches();
                    repeat = repeatProcess();
                }
            }
            else if(action == 'D' || action == 'd'){
                while(repeat){
                    updateMatches();
                    repeat = repeatProcess();
                }
            }
            else if(action == 'E' || action == 'e')
                exit = true;
            
            else
                System.out.println("\nInvalid input, please only enter A/B/C/D/E");
        }
    }
    
    public static void viewMatches() throws Exception {
        System.out.println("\f\tUiTM RAUB FOOTBALL TOUR MATCHES LIST");
        System.out.println("\n**********VIEW MATCHES**********");
        System.out.println("\nChoose list(s) to view from");
        Queue q = readFile(listToRead());
        if(!q.isEmpty()){
            int count = 1;
            while(!q.isEmpty()){
                MatchSchedule ms = (MatchSchedule) q.dequeue();
                System.out.println("\n" + count + ")" + ms.toString());
                count++;
            }
        }
        else{
            System.out.println("\nThe list chosen is currently empty");    
        }
    }
    
    public static void removeMatches() throws Exception {
        System.out.println("\f\tUiTM RAUB FOOTBALL TOUR MATCHES LIST");
        System.out.println("\n**********REMOVE MATCHES**********");
        matchQ = readFile("MatchesQ");
        if(!matchQ.isEmpty()){
            System.out.println("\nRemoving data from matchList into uitmList and nonUitmList...");
            while(!matchQ.isEmpty()){
                MatchSchedule ms = (MatchSchedule) matchQ.dequeue();
                if(ms.getTeamName().substring(0,4).equalsIgnoreCase("UiTM")){
                    uitmQ.enqueue(ms);
                }
                else{
                    nonUitmQ.enqueue(ms);
                }
            }
            writeFile(matchQ, uitmQ, nonUitmQ, "MatchesQ.txt");
            System.out.println("\nProcess finished");
        }
        else{
            System.out.println("\nData has already been removed from matchList into uitmList and nonUitmList");
        }
    }
    
    public static void searchMatches() throws Exception {
        System.out.println("\f\tUiTM RAUB FOOTBALL TOUR MATCHES LIST");
        System.out.println("\n**********SEARCH MATCHES**********");
        System.out.println("\nChoose list(s) to search from");
        Queue q = readFile(listToRead());
        if(!q.isEmpty()){
            MatchSchedule msSearched = new MatchSchedule();
            boolean found = false;
            System.out.print("\nDate (Ex. 20 Januari 2024): ");
            String input = inLine.nextLine();
            while(!q.isEmpty()){
                MatchSchedule ms = (MatchSchedule) q.dequeue();
                if(ms.getSlot().getDate().equalsIgnoreCase(input)){
                    msSearched = ms;
                    found = true;
                    break;
                }
            }
            if(!found)
                System.out.println("\nSorry, there is no match schedule based on the input");
            else{
                System.out.println("\nFound:\n" + msSearched.toString());
            }
        }
        else{
            System.out.println("\nThe list chosen is currently empty");    
        }
        
    }
    
    public static void updateMatches() throws Exception {
        System.out.println("\f\tUiTM RAUB FOOTBALL TOUR MATCHES LIST");
        System.out.println("\n**********UPDATE MATCHES**********");
        matchQ= readFile("MatchesQ");
        System.out.println("\nUpdating data in matchList...");
        while(!matchQ.isEmpty()){
            MatchSchedule ms = (MatchSchedule) matchQ.dequeue();
            if(ms.isHomeVenue()){
                ms.setCapacity(1000);
            }
            tempQ.enqueue(ms);
        }
        uitmQ= readFile("UitmMatches");
        System.out.println("\nUpdating data in uitmList...");
        while(!uitmQ.isEmpty()){
            MatchSchedule ms = (MatchSchedule) uitmQ.dequeue();
            if(ms.isHomeVenue()){
                ms.setCapacity(1000);
            }
            tempQ2.enqueue(ms);
        }
        System.out.println("\nUpdating data in nonUitmList...");
        nonUitmQ= readFile("NonUitmMatches");
        while(!nonUitmQ.isEmpty()){
            MatchSchedule ms = (MatchSchedule) nonUitmQ.dequeue();
            if(ms.isHomeVenue()){
                ms.setCapacity(1000);
            }
            tempQ3.enqueue(ms);
        }
        writeFile(tempQ, tempQ2, tempQ3, "MatchesQ.txt");
        System.out.println("\nProcess finished");
    }
    
    public static String listToRead(){
        System.out.println("\nA - matchList\nB - uitmList\nC - nonUitmList");
        System.out.print("\nList (A/B/C) : ");
        char choice = in.next().charAt(0);
        String file = "";
        if(choice == 'A' || choice == 'a') file = "MatchesQ";
        else if(choice == 'B' || choice == 'b') file = "UitmMatches";
        else if(choice == 'C' || choice == 'c') file = "NonUitmMatches";
        else{
            System.out.println("\nInvalid input, please only enter A/B/C");
            file = listToRead();
        }
        return file;
    }
    
    public static Queue readFile(String listToRead) throws Exception{
        Queue q = new Queue();
        try{
            BufferedReader br = new BufferedReader(new FileReader("MatchesQ.txt"));
            String line = br.readLine();
            if(listToRead.equalsIgnoreCase("MatchesQ")){
                line = br.readLine();
                line = br.readLine();
                while(!line.equalsIgnoreCase("--------------------------------------------------------------")){
                    StringTokenizer st = new StringTokenizer(line, "-");
                    String teamName = st.nextToken();
                    String date = st.nextToken();
                    String time = st.nextToken();
                    String venue = st.nextToken();
                    int venueCapacity = Integer.parseInt(st.nextToken());
                    MatchSchedule ms = new MatchSchedule(teamName, date, time, venue, venueCapacity);
                    q.enqueue(ms);
                    line = br.readLine();
                }
            }
            else if(listToRead.equalsIgnoreCase("UitmMatches")){
                while(!line.equalsIgnoreCase("********UiTM MATCH LIST********")){
                    line = br.readLine();
                }
                line = br.readLine();
                while(!line.equalsIgnoreCase("--------------------------------------------------------------")){
                    StringTokenizer st = new StringTokenizer(line, "-");
                    String teamName = st.nextToken();
                    String date = st.nextToken();
                    String time = st.nextToken();
                    String venue = st.nextToken();
                    int venueCapacity = Integer.parseInt(st.nextToken());
                    MatchSchedule ms = new MatchSchedule(teamName, date, time, venue, venueCapacity);
                    q.enqueue(ms);
                    line = br.readLine();
                }
            }
            else if(listToRead.equalsIgnoreCase("NonUitmMatches")){
                while(!line.equalsIgnoreCase("********NON-UiTM MATCH LIST********")){
                    line = br.readLine();
                }
                line = br.readLine();
                while(line != null){
                    StringTokenizer st = new StringTokenizer(line, "-");
                    String teamName = st.nextToken();
                    String date = st.nextToken();
                    String time = st.nextToken();
                    String venue = st.nextToken();
                    int venueCapacity = Integer.parseInt(st.nextToken());
                    MatchSchedule ms = new MatchSchedule(teamName, date, time, venue, venueCapacity);
                    q.enqueue(ms);
                    line = br.readLine();
                }
            }
            br.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return q;
    }
    
    public static void writeFile(Queue matchQ, Queue uitmQ, Queue nonUitmQ, String file){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File("MatchesQ.txt"), false));
            pw.println("--------------------------------------------------------------");
            pw.println("********MATCH LIST********");
            while(!matchQ.isEmpty()){
                MatchSchedule ms = (MatchSchedule) matchQ.dequeue();
                String input = ms.getTeamName() + "-" + ms.getSlot().getDate() + "-" + ms.getSlot().getTime();
                input += "-" + ms.getVenue() + "-" + ms.getCapacity();
                pw.println(input);
            }
            pw.println("--------------------------------------------------------------");
            pw.println("********UiTM MATCH LIST********");
            while(!uitmQ.isEmpty()){
                MatchSchedule ms = (MatchSchedule) uitmQ.dequeue();
                String input = ms.getTeamName() + "-" + ms.getSlot().getDate() + "-" + ms.getSlot().getTime();
                input += "-" + ms.getVenue() + "-" + ms.getCapacity();
                pw.println(input);
            }
            pw.println("--------------------------------------------------------------");
            pw.println("********NON-UiTM MATCH LIST********");
            while(!nonUitmQ.isEmpty()){
                MatchSchedule ms = (MatchSchedule) nonUitmQ.dequeue();
                String input = ms.getTeamName() + "-" + ms.getSlot().getDate() + "-" + ms.getSlot().getTime();
                input += "-" + ms.getVenue() + "-" + ms.getCapacity();
                pw.println(input);
            }
            pw.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
    
    public static boolean repeatProcess(){
        char input;
        System.out.println("\nPlease enter N to return to menu, otherwise enter any keys to go repeat this process");
        System.out.print("\nRepeat? : ");
        input = in.next().charAt(0);
        if(input == 'N' || input == 'n')
            return false;
        else
            return true;
    }
}
