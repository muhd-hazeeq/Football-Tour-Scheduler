import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.*;

public class MatchScheduleLLApp
{
    static Scanner in = new Scanner(System.in);
    static Scanner inLine = new Scanner(System.in);
    static LinkedList matchList = new LinkedList();
    static LinkedList uitmList = new LinkedList();
    static LinkedList nonUitmList = new LinkedList();
    
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
        LinkedList list= readFile(listToRead());
        MatchSchedule ms = (MatchSchedule) list.getFirst();
        if(ms != null){
            int count = 1;
            while(ms != null){
                System.out.println("\n" + count + ")" + ms.toString());
                count++;
                ms = (MatchSchedule) list.getNext();
            }
        }
        else{
            System.out.println("\nThe list chosen is currently empty"); 
        }
    }
    
    public static void removeMatches() throws Exception {
        System.out.println("\f\tUiTM RAUB FOOTBALL TOUR MATCHES LIST");
        System.out.println("\n**********REMOVE MATCHES**********");
        matchList = readFile("MatchesLL");
        MatchSchedule ms = (MatchSchedule) matchList.removeFirst();
        if(ms != null){
            System.out.println("\nRemoving data from matchList into uitmList and nonUitmList...");
            while(ms != null){
                if(ms.getTeamName().substring(0,4).equalsIgnoreCase("UiTM")){
                    uitmList.addLast(ms);
                }
                else{
                    nonUitmList.addLast(ms);
                }
                ms = (MatchSchedule) matchList.removeFirst();
            }
            writeFile(matchList, uitmList, nonUitmList, "MatchesLL.txt");
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
        LinkedList list= readFile(listToRead());
        MatchSchedule ms = (MatchSchedule) list.getFirst();
        if(ms != null){
            MatchSchedule msSearched = new MatchSchedule();
            boolean found = false;
            System.out.print("\nDate (Ex. 20 Januari 2024): ");
            String input = inLine.nextLine();
            while(ms != null){
                if(ms.getSlot().getDate().equalsIgnoreCase(input)){
                    msSearched = ms;
                    found = true;
                }
                ms = (MatchSchedule) list.getNext();
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
        matchList = readFile("MatchesLL");
        MatchSchedule ms = (MatchSchedule) matchList.getFirst();
        System.out.println("\nUpdating data in matchList...");
        while(ms != null){
            if(ms.isHomeVenue()){
                ms.setCapacity(1000);
            }
            ms = (MatchSchedule) matchList.getNext();
        }
        uitmList= readFile("UitmMatches");
        ms = (MatchSchedule) uitmList.getFirst();
        System.out.println("\nUpdating data in uitmList...");
        while(ms != null){
            if(ms.isHomeVenue()){
                ms.setCapacity(1000);
            }
            ms = (MatchSchedule) uitmList.getNext();
        }
        System.out.println("\nUpdating data in nonUitmList...");
        nonUitmList= readFile("NonUitmMatches");
        ms = (MatchSchedule) nonUitmList.getFirst();
        while(ms != null){
            if(ms.isHomeVenue()){
                ms.setCapacity(1000);
            }
            ms = (MatchSchedule) nonUitmList.getNext();
        }
        writeFile(matchList, uitmList, nonUitmList, "MatchesLL.txt");
        System.out.println("\nProcess finished");
    }
    
    public static String listToRead(){
        System.out.println("\nA - matchList\nB - uitmList\nC - nonUitmList");
        System.out.print("\nList (A/B/C) : ");
        char choice = in.next().charAt(0);
        String file = "";
        if(choice == 'A' || choice == 'a') file = "MatchesLL";
        else if(choice == 'B' || choice == 'b') file = "UitmMatches";
        else if(choice == 'C' || choice == 'c') file = "NonUitmMatches";
        else{
            System.out.println("\nInvalid input, please only enter A/B/C");
            file = listToRead();
        }
        return file;
    }
    
    public static LinkedList readFile(String listToRead) throws Exception{
        LinkedList list = new LinkedList();
        try{
            BufferedReader br = new BufferedReader(new FileReader("MatchesLL.txt"));
            String line = br.readLine();
            if(listToRead.equalsIgnoreCase("MatchesLL")){
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
                    list.addLast(ms);
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
                    list.addLast(ms);
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
                    list.addLast(ms);
                    line = br.readLine();
                }
            }
            br.close();
        }
        catch(Exception e){
            System.err.println(e.getMessage());
        }
        return list;
    }
    
    public static void writeFile(LinkedList matchList, LinkedList uitmList, LinkedList nonUitmList, String file){
        try{
            PrintWriter pw = new PrintWriter(new FileWriter(new File("MatchesLL.txt"), false));
            MatchSchedule ms = (MatchSchedule) matchList.getFirst();
            pw.println("--------------------------------------------------------------");
            pw.println("********MATCH LIST********");
            while(ms != null){
                String input = ms.getTeamName() + "-" + ms.getSlot().getDate() + "-" + ms.getSlot().getTime();
                input += "-" + ms.getVenue() + "-" + ms.getCapacity();
                pw.println(input);
                ms = (MatchSchedule) matchList.getNext();
            }
            ms = (MatchSchedule) uitmList.getFirst();
            pw.println("--------------------------------------------------------------");
            pw.println("********UiTM MATCH LIST********");
            while(ms != null){
                String input = ms.getTeamName() + "-" + ms.getSlot().getDate() + "-" + ms.getSlot().getTime();
                input += "-" + ms.getVenue() + "-" + ms.getCapacity();
                pw.println(input);
                ms = (MatchSchedule) uitmList.getNext();
            }
            ms = (MatchSchedule) nonUitmList.getFirst();
            pw.println("--------------------------------------------------------------");
            pw.println("********NON-UiTM MATCH LIST********");
            while(ms != null){
                String input = ms.getTeamName() + "-" + ms.getSlot().getDate() + "-" + ms.getSlot().getTime();
                input += "-" + ms.getVenue() + "-" + ms.getCapacity();
                pw.println(input);
                ms = (MatchSchedule) nonUitmList.getNext();
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
