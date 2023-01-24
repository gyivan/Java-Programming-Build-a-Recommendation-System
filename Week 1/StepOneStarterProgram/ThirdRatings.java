
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

public class ThirdRatings {
    //private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        // default constructor
        this("data/ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        //myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    /*
    public int getMovieSize() {
        return myMovies.size();
    }
    */
    
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        
        //find the average rating for every movie that has been rated by at least minimalRaters raters
        for (String movieID : movies) {
            double averageRating = getAverageByID(movieID, minimalRaters);
            
            if (averageRating > 0.0) {
                //Store each such rating in a Rating object in which the movie ID and the average rating are used in creating the Rating object.
                Rating currentMovieRating = new Rating(movieID, averageRating);
                res.add(currentMovieRating);
            }
        }
        
        //return an ArrayList of all the Rating objects for movies that have at least the minimal number of raters supplying a rating
        return res;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> res = new ArrayList<Rating>();
        
        //create the ArrayList of type String of movie IDs from the MovieDatabase using the filterBy method before calculating those averages
        ArrayList<String> filtered = MovieDatabase.filterBy(filterCriteria);
        
        //ArrayList<Rating> averageRatingList = getAverageRatings(minimalRaters);
        
        for (String movieID : filtered) {
            double averageRating = getAverageByID(movieID, minimalRaters);
            
            if (averageRating > 0.0) {
                //Store each such rating in a Rating object in which the movie ID and the average rating are used in creating the Rating object.
                Rating currentMovieRating = new Rating(movieID, averageRating);
                res.add(currentMovieRating);
            }
        }
        
        return res; //return an ArrayList of all the movies that have at least minimalRaters ratings and satisfies the filter criteria
    }
    
}
