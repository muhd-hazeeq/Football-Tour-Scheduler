public class MatchSchedule
{
    //attributes
    String teamName;
    Slot matchSlot; 
    String venue; 
    int venueCapacity;
    
    //default constructor 
    public MatchSchedule() {}
        
    //normal constructor 
    public MatchSchedule(String teamName,String date, String time,String venue, int venueCapacity) {
        this.teamName = teamName;
        this.matchSlot = new Slot(date, time);
        this.venue = venue;
        this.venueCapacity = venueCapacity;
    }
 
    //mutator 
    public void setTeamName(String teamName)   { this.teamName = teamName; }
    public void setSlot(Slot matchSlot)        { this.matchSlot = matchSlot; }
    public void setVenue(String venue)         { this.venue = venue; }
    public void setCapacity(int venueCapacity) { this.venueCapacity = venueCapacity; }
 
    //accessor 
    public String getTeamName() { return this.teamName; }
    public Slot getSlot()       { return this.matchSlot; }
    public String getVenue()    { return this.venue; }
    public int getCapacity()    { return this.venueCapacity; }
 
    //printer 
    public String toString() {
        return "\nTEAM NAME: " +getTeamName()+
               "\nVENUE: " +getVenue()+
               "\nVENUE CAPACITY: " + getCapacity() +
               getSlot().toString();

    }
 
    //processor 
    public boolean isHomeVenue() {
        boolean isHomeVenue = false;
        if(getVenue().equalsIgnoreCase("Stadium Raub")) {
            isHomeVenue = true;
        }
        return isHomeVenue;
    }
}