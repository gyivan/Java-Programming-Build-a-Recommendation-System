
/**
 * Write a description of SecondRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("data/ratedmoviesfull.csv", "data/ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        //returns a double representing the average movie rating for this ID if there are at least minimalRaters ratings
        int ratingsCount = 0;
        double totalScore = 0.0;
        
        for (Rater rater : myRaters) {
            if (rater.hasRating(id)) {
                ratingsCount++;
                totalScore += rater.getRating(id);
            }
        }
        
        if (ratingsCount >= minimalRaters) {
            return totalScore/ratingsCount;
        } else {
            return 0.0;
        }
        
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> res = new ArrayList<Rating>();
        
        //find the average rating for every movie that has been rated by at least minimalRaters raters
        for (Movie entry : myMovies) {
            String movieID = entry.getID();
            double averageRating = getAverageByID(entry.getID(), minimalRaters);
            
            if (averageRating > 0.0) {
                //Store each such rating in a Rating object in which the movie ID and the average rating are used in creating the Rating object.
                Rating currentMovieRating = new Rating(movieID, averageRating);
                res.add(currentMovieRating);
            }
        }
        
        //return an ArrayList of all the Rating objects for movies that have at least the minimal number of raters supplying a rating
        return res;
    }
    
    public String getTitle(String id) { //returns the title of the movie with that id
        for (Movie entry : myMovies) {
            if (entry.getID().equals(id)) {
                return entry.getTitle();
            }
        }
        return "ID was not found";
    }
    
    public String getID(String title) {
        for (Movie entry : myMovies) {
            if (entry.getTitle().equals(title)) {
                return entry.getID();
            }
        }
        return "NO SUCH TITLE";
    }
    
}
