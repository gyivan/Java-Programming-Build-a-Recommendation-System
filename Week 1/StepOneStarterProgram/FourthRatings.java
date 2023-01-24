
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

public class FourthRatings {
    //private ArrayList<Movie> myMovies;
    //private ArrayList<Rater> myRaters;
    
    public FourthRatings() {
        // default constructor
        //this("data/ratings.csv");
    }
    
    public FourthRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        //myMovies = fr.loadMovies(moviefile);
        //myRaters = fr.loadRaters(ratingsfile); 
    }
    
    private double getAverageByID(String id, int minimalRaters) {
        //returns a double representing the average movie rating for this ID if there are at least minimalRaters ratings
        int ratingsCount = 0;
        double totalScore = 0.0;
        
        for (Rater rater : RaterDatabase.getRaters()) {
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
    
    private double dotProduct(Rater me, Rater r) {
        double res = 0.0;
        
        //translate a rating from the scale 0 to 10 to the scale -5 to 5
        for (String movieID : me.getItemsRated()) { //find common movies rated by both raters by ID
            if (r.hasRating(movieID)) { //if movieID is rated by both raters
                double meRatingScore = me.getRating(movieID) - 5;
                double rRatingScore = r.getRating(movieID) - 5;
                
                res += meRatingScore*rRatingScore; //calculate the dot product and add it to the result
            }
        }
        
        //return the dot product of the ratings of the movies that they both rated
        return res;
    }
    
    //computes a similarity rating for each rater in the RaterDatabase (except the rater with the ID given by the parameter)
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> res = new ArrayList<Rating>(); //returns an ArrayList of type Rating sorted by ratings from highest to lowest rating
        
        for (Rater rater : RaterDatabase.getRaters()) {
            if (!rater.getID().equals(id)) { //do not calculate similarities with itself (the target)
                double dotP = dotProduct(RaterDatabase.getRater(id), rater);
                
                //only including those raters who have a positive similarity rating since those with negative values are not similar in any way
                if (dotP > 0) {
                //in each Rating object the item field is a rater’s ID, and the value field is the dot product comparison
                res.add(new Rating(rater.getID(), dotP));
                }
                
            }
        }
        Collections.sort(res, Collections.reverseOrder()); //sort from highest to lowest rating
        return res;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) { //id represents rater ID
        return getSimilarRatingsByFilter(id, numSimilarRaters, minimalRaters, new TrueFilter());
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        /*
        ArrayList<Rating> res = new ArrayList<Rating>();
        
        ArrayList<Rating> movieIdWeightedAvg = getSimilarRatings(id, numSimilarRaters, minimalRaters);
        ArrayList<String> filteredMovies = MovieDatabase.filterBy(filterCriteria);
        
        for (Rating rating : movieIdWeightedAvg) { //for each rating(movieID : weightedAvg)
            
            String currMovieId = rating.getItem(); //get the current movie ID from the current rating object
            
            if (filteredMovies.contains(currMovieId)) { //if the filtered movie list contains the current movie ID
                res.add(rating); //add the current rating to the final ArrayList
            }
            
        }
        
        return res;
        */
        ArrayList<Rating> res = new ArrayList<Rating>();
        
        //return an ArrayList of type Rating, of movie IDs and their weighted average ratings
        //using only the top numSimilarRaters with positive ratings
        //including only those movies that have at least minimalRaters ratings from those most similar raters (not just minimalRaters ratings overall)
        //Rating objects should be returned in sorted order by weighted average rating from largest to smallest ratings
        
        
        //For every rater, get their similarity rating to the given parameter rater id.
        //Include only those raters with positive similarity ratings—those that are more similar to rater id.
        ArrayList<Rating> similarityRatingList = getSimilarities(id);

        
        for (String movieID : MovieDatabase.filterBy(filterCriteria)) {        
            //For each movie, calculate a weighted average movie rating based on:
            double weightedAvg = 0.0;
            int numRatings = 0;
            
            //Use only the top (largest) numSimilarRaters raters.
            for (int i = 0; i < numSimilarRaters; i++) {
                Rating r = similarityRatingList.get(i);
                String raterID = r.getItem();
                double weight = r.getValue();
                Rater currentRater = RaterDatabase.getRater(raterID);
                
                //use Rater id and weight in r to update running totals
                if (currentRater.hasRating(movieID)) { //if rater has rated the current movie
                    weightedAvg += weight*currentRater.getRating(movieID); //multiply their similarity rating by the rating they gave that movie
                    numRatings++;
                }
            }
            //weighted average movie rating for a particular movie is the sum of these weighted average ratings divided by the total number of such ratings
            weightedAvg /= numRatings;
            
            if (numRatings >= minimalRaters) { //if these movies have at least minimalRaters ratings from those most similar raters
                res.add(new Rating(movieID, weightedAvg)); //add Rating for movieID to res
            }

        }
        
        //sort first
        Collections.sort(res, Collections.reverseOrder());
        
        return res;
    }
    
}
