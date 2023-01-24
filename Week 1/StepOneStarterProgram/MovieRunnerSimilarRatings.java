
/**
 * Write a description of MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class MovieRunnerSimilarRatings {
    
    public FourthRatings newFourthRatings() {
        //reads data into the RaterDatabase
        String raterFile = "ratings.csv";
        //String raterFile = "ratings_short.csv";
        //String raterFile = "ratings_test.csv";
        RaterDatabase.initialize(raterFile);
        
        FourthRatings fr = new FourthRatings();
        //FourthRatings fr = new FourthRatings("data/ratings_short.csv");
        //FourthRatings fr = new FourthRatings("data/ratings_test.csv");
        
        //Print the number of raters after creating a FourthRating object
        System.out.println("Number of raters: " + RaterDatabase.size());
        
        //call the MovieDatabase initialize method with the moviefile to set up the movie database
        //String movieFile = "ratedmovies_short.csv";
        String movieFile = "ratedmoviesfull.csv";
        MovieDatabase.initialize(movieFile);
        
        //Print the number of movies in the database. 
        System.out.println("Number of movies in the database: " + MovieDatabase.size());
        
        return fr;
    }

    public void printAverageRatings() {
        int minimalRaters = 35;
        
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();

        //call getAverageRatings with a minimal number of raters to return an ArrayList of type Rating
        ArrayList<Rating> averageRatingList = fr.getAverageRatings(minimalRaters);
        
        //Print out how many movies with ratings are returned, then sort them, and print out the rating and title of each movie.
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
        }
        
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        int minimalRaters = 8;
        
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();

        //create an AllFilters object that includes year and genre criterias
        int year = 1990;
        String genre = "Drama";
        
        AllFilters allF = new AllFilters();
        allF.addFilter(new YearAfterFilter(year));
        allF.addFilter(new GenreFilter(genre));
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = fr.getAverageRatingsByFilter(minimalRaters, allF);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Year: " + MovieDatabase.getYear(rating.getItem()) + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t Genres: " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printSimilarRatings() {
        
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();
        
        //calls getSimilarRatings
        String raterID = "71";
        int minRaters = 5;
        int numTopSimilarRaters = 20;
        ArrayList<Rating> res = fr.getSimilarRatings(raterID, numTopSimilarRaters, minRaters); //return an ArrayList of type Rating, of movie IDs and their weighted average ratings
        
        for (Rating entry : res) {
            System.out.println("Movie ID: " + entry.getItem());
            System.out.println("Movie Title: " + MovieDatabase.getTitle(entry.getItem()));
            System.out.println("Weighted Average: " + entry.getValue());
            System.out.println();
        }

    }
    
    public void printSimilarRatingsByGenre() {
        
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();
        
        //calls getSimilarRatings
        String raterID = "964";
        int minRaters = 5;
        int numTopSimilarRaters = 20;
        
        String genre = "Mystery";
        GenreFilter genreF = new GenreFilter(genre);
        ArrayList<Rating> res = fr.getSimilarRatingsByFilter(raterID, numTopSimilarRaters, minRaters, genreF); //return an ArrayList of type Rating, of movie IDs and their weighted average ratings
        
        for (Rating entry : res) {
            System.out.println("Movie ID: " + entry.getItem());
            System.out.println("Movie Title: " + MovieDatabase.getTitle(entry.getItem()));
            System.out.println("Weighted Average: " + entry.getValue());
            System.out.println();
        }

    }
    
    public void printSimilarRatingsByDirector() {
    
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();
        
        //calls getSimilarRatings
        String raterID = "120";
        int minRaters = 2;
        int numTopSimilarRaters = 10;

        String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
        DirectorsFilter dirF = new DirectorsFilter(directors);
        ArrayList<Rating> res = fr.getSimilarRatingsByFilter(raterID, numTopSimilarRaters, minRaters, dirF); //return an ArrayList of type Rating, of movie IDs and their weighted average ratings
        
        for (Rating entry : res) {
            System.out.println("Movie ID: " + entry.getItem());
            System.out.println("Movie Title: " + MovieDatabase.getTitle(entry.getItem()));
            System.out.println("Weighted Average: " + entry.getValue());
            System.out.println();
        }
        
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();
        
        //calls getSimilarRatings
        String raterID = "168";
        int minRaters = 3;
        int numTopSimilarRaters = 10;

        String genre = "Drama";
        int minMins = 80;
        int maxMins = 160;
        
        AllFilters allF = new AllFilters();
        allF.addFilter(new GenreFilter(genre));
        allF.addFilter(new MinutesFilter(minMins, maxMins));
        ArrayList<Rating> res = fr.getSimilarRatingsByFilter(raterID, numTopSimilarRaters, minRaters, allF); //return an ArrayList of type Rating, of movie IDs and their weighted average ratings
        
        for (Rating entry : res) {
            System.out.println("Movie ID: " + entry.getItem());
            System.out.println("Movie Title: " + MovieDatabase.getTitle(entry.getItem()));
            System.out.println("Weighted Average: " + entry.getValue());
            System.out.println();
        }
        
    }
    
    public void printSimilarRatingsByYearAfterAndMinutess() {
        
        //create a FourthRatings object
        FourthRatings fr = newFourthRatings();
        
        //calls getSimilarRatings
        String raterID = "314";
        int minRaters = 5;
        int numTopSimilarRaters = 10;

        int year = 1975;
        int minMins = 70;
        int maxMins = 200;
        
        AllFilters allF = new AllFilters();
        allF.addFilter(new YearAfterFilter(year));
        allF.addFilter(new MinutesFilter(minMins, maxMins));
        ArrayList<Rating> res = fr.getSimilarRatingsByFilter(raterID, numTopSimilarRaters, minRaters, allF); //return an ArrayList of type Rating, of movie IDs and their weighted average ratings
        
        for (Rating entry : res) {
            System.out.println("Movie ID: " + entry.getItem());
            System.out.println("Movie Title: " + MovieDatabase.getTitle(entry.getItem()));
            System.out.println("Weighted Average: " + entry.getValue());
            System.out.println();
        }
        
    }
    
}
