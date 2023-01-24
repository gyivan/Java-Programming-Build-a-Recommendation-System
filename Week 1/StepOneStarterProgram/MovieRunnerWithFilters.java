
/**
 * Write a description of MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class MovieRunnerWithFilters {
    
    public ThirdRatings newThirdRatings() {
        ThirdRatings tr = new ThirdRatings("data/ratings.csv");
        //ThirdRatings tr = new ThirdRatings("data/ratings_short.csv");
        //ThirdRatings tr = new ThirdRatings("data/ratings_test.csv");
        
        //Print the number of raters after creating a ThirdsRating object
        System.out.println("Number of raters: " + tr.getRaterSize());
        
        //call the MovieDatabase initialize method with the moviefile to set up the movie database
        //String movieFile = "ratedmovies_short.csv";
        String movieFile = "ratedmoviesfull.csv";
        MovieDatabase.initialize(movieFile);
        
        //Print the number of movies in the database. 
        System.out.println("Number of movies in the database: " + MovieDatabase.size());
        
        return tr;
    }

    public void printAverageRatings() {
        int minimalRaters = 35;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //call getAverageRatings with a minimal number of raters to return an ArrayList of type Rating
        ArrayList<Rating> averageRatingList = tr.getAverageRatings(minimalRaters);
        
        //Print out how many movies with ratings are returned, then sort them, and print out the rating and title of each movie.
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
        }
        
    }
    
    public void printAverageRatingsByYearAfter() {
        int minimalRaters = 20;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //create a YearAfterFilter
        int year = 2000;
        YearAfterFilter yaf = new YearAfterFilter(year);
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = tr.getAverageRatingsByFilter(minimalRaters, yaf);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Year: " + MovieDatabase.getYear(rating.getItem()) + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
        }
        
    }
    
    public void printAverageRatingsByGenre() {
        int minimalRaters = 20;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //create a GenreFilter
        String genre = "Comedy";
        GenreFilter genreF = new GenreFilter(genre);
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = tr.getAverageRatingsByFilter(minimalRaters, genreF);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t Genres: " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByMinutes() {
        int minimalRaters = 5;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //create a MinutesFilter
        int minMins = 105;
        int maxMins = 135;
        MinutesFilter minF = new MinutesFilter(minMins, maxMins);
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = tr.getAverageRatingsByFilter(minimalRaters, minF);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Time: " + MovieDatabase.getMinutes(rating.getItem()) + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByDirectors() {
        int minimalRaters = 4;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //create a DirectorsFilter
        String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
        DirectorsFilter dirF = new DirectorsFilter(directors);
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = tr.getAverageRatingsByFilter(minimalRaters, dirF);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t Directors: " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        int minimalRaters = 8;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //create an AllFilters object that includes year and genre criterias
        int year = 1990;
        String genre = "Drama";
        
        AllFilters allF = new AllFilters();
        allF.addFilter(new YearAfterFilter(year));
        allF.addFilter(new GenreFilter(genre));
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = tr.getAverageRatingsByFilter(minimalRaters, allF);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Year: " + MovieDatabase.getYear(rating.getItem()) + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t Genres: " + MovieDatabase.getGenres(rating.getItem()));
        }
    }
    
    public void printAverageRatingsByDirectorsAndMinutes() {
        int minimalRaters = 3;
        
        //create a ThirdRatings object
        ThirdRatings tr = newThirdRatings();

        //create an AllFilters object that includes year and genre criterias
        String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
        int minMins = 90;
        int maxMins = 180;
        
        AllFilters allF = new AllFilters();
        allF.addFilter(new DirectorsFilter(directors));
        allF.addFilter(new MinutesFilter(minMins, maxMins));
        
        //call getAverageRatingsByFilter to get an ArrayList of type Rating of all the movies that have a specified number of minimal ratings and came out in a specified year or later
        ArrayList<Rating> averageRatingList = tr.getAverageRatingsByFilter(minimalRaters, allF);
        
        //Print the number of movies found
        System.out.println("Number of movies with ratings returned: " + averageRatingList.size());
        Collections.sort(averageRatingList);
        
        //for each movie found, print its rating, its year, and its title.
        for (Rating rating : averageRatingList) {
            System.out.println("Rating: " + rating.getValue() + "\t Time: " + MovieDatabase.getMinutes(rating.getItem()) + "\t Title: " + MovieDatabase.getTitle(rating.getItem()));
            System.out.println("\t Directors: " + MovieDatabase.getDirector(rating.getItem()));
        }
    }
    
    
}
