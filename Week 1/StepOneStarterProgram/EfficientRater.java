
/**
 * Write a description of class Rater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    //private ArrayList<Rating> myRatings;
    private HashMap<String, Rating> myRatings; //key is movie ID, value is rating associated with this movie

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String, Rating>();
    }

    public void addRating(String item, double rating) { //item is movieID
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
        return myRatings.containsKey(item);
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
        return myRatings.get(item).getValue();
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
        ArrayList<String> list = new ArrayList<String>();
        
        /*
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        */
        
        for (String movieID : myRatings.keySet()) {
            list.add(movieID);
        }
       
        return list;
    }
}
