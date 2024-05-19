public class Slot
{
    //attributes
    String date; 
    String time;

    //default constructor 
    public Slot() {}
    
    //normal constructor 
    public Slot (String date, String time) {
        this.date = date;
        this.time = time;
    }
 
    //mutator 
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
  
    //accessor 
    public String getDate() { return this.date; }
    public String getTime() { return this.time; }
 
    //printer 
    public String toString()    {
        String receipt = "\n========================================\n";
        receipt += String.format("%-20s %-1s %-10s %n", "DATE", "|", "TIME");
        receipt += "----------------------------------------\n";
        receipt += String.format("%-20s %-1s %-10s %n", this.getDate(), "|", this.getTime());
        receipt += "========================================\n";
        return receipt;
        //"\nDate: " +getDate()+
          //     "\nTime: " +getTime();
    }
}
