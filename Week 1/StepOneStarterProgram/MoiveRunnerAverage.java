
/**
 * Write a description of MoiveRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class MoiveRunnerAverage {
    
    public SecondRatings newSecondRatings() {
        SecondRatings sr = new SecondRatings("data/ratedmoviesfull.csv", "data/ratings.csv");
        //SecondRatings sr = new SecondRatings("data/ratedmovies_short.csv", "data/ratings_short.csv");
        //SecondRatings sr = new SecondRatings("data/ratedmovies_test.csv", "data/ratings_test.csv");
        return sr;
    }
    
    public void printAverageRatings() {
        int minimalRaters = 12;
        
        //create a SecondRatings object and use the CSV filenames of movie information and ratings information from the first assignment when calling the constructor.
        SecondRatings sr = newSecondRatings();
        
        //Print the number of movies and number of raters from the two files by calling the appropriate methods in the SecondRatings class.
        System.out.println("Number of movies: " + sr.getMovieSize());
        System.out.println("Number of raters: " + sr.getRaterSize());
        
        //print the list of movies, one movie per line (print its rating first, followed by its title) in sorted order by ratings, lowest rating to highest rating.
        ArrayList<Rating> averageRatingList = sr.getAverageRatings(minimalRaters);
        Collections.sort(averageRatingList);
        
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Title: " + sr.getTitle(rating.getItem()));
        }
        
        System.out.println("Average Rating List size: " + averageRatingList.size());
    }
    
    public void getAverageRatingOneMovie() {
        //create a SecondRatings object
        SecondRatings sr = newSecondRatings();
        int minimalRaters = 0;
        
        //print out the average ratings for a specific movie title
        String movieTitle = "Vacation";
        
        ArrayList<Rating> averageRatingList = sr.getAverageRatings(minimalRaters);
        for (Rating rating : averageRatingList) {
            if (movieTitle.equals(sr.getTitle(rating.getItem()))) {
                System.out.println("Average rating for movie: " + movieTitle + " - " + rating.getValue());
            }
        }

    }
    
}
