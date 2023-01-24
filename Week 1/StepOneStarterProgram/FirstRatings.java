
/**
 * Write a description of FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename) {
        ArrayList<Movie> res = new ArrayList<Movie>();
        
        //process every record from the CSV file whose name is filename
        FileResource fr = new FileResource(filename);
        for (CSVRecord record : fr.getCSVParser()) {
            Movie currentMovie = new Movie(record.get("id"), record.get("title"), record.get("year"), record.get("genre"), record.get("director"),
                                            record.get("country"), record.get("poster"), Integer.parseInt(record.get("minutes")));
            res.add(currentMovie);
        }
        
        //return an ArrayList of type Movie with all of the movie data from the file
        return res;
    }
    
    public ArrayList<Rater> loadRaters(String filename) {
        ArrayList<Rater> res = new ArrayList<Rater>();
        ArrayList<String> raterIdList = new ArrayList<String>();
        
        //process every record from the CSV file (file of raters and ratings)
        FileResource fr = new FileResource(filename);
        for (CSVRecord record : fr.getCSVParser()) {
            Rater current = new EfficientRater(record.get("rater_id"));
            
            if (!raterIdList.contains(record.get("rater_id"))) { //if rater is new
                raterIdList.add(current.getID());
                current.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
                res.add(current);
            } else { //if rater has given existing ratings before
                Rater target = res.get(raterIdList.indexOf(current.getID())); //obtain the rater that corresponds to the existing ID
                target.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating"))); //add the rating
                res.set(raterIdList.indexOf(current.getID()), target); //update the ArrayList of raters
            }

        }
        
        return res;
    }
    
    public void testLoadMovies() {
        //ArrayList<Movie> movieList = loadMovies("data/ratedmovies_short.csv");
        ArrayList<Movie> movieList = loadMovies("data/ratedmoviesfull.csv");
        System.out.println("Number of movies: " + movieList.size());
        
        String targetGenre = "Comedy";
        int numGenre = 0; //how many movies include a certain genre
        
        int threshMins = 150;
        int numGreater = 0; //how many movies greater than a certain number of minutes in length
        
        HashMap<String, Integer> directorNumMoviesMap = new HashMap<String, Integer>();
        int maxFilmsByDirector = 0;
        String directorWithMostFilms = "";
        
        for (Movie movie : movieList) {
            //System.out.println(movie);
            
            if (movie.getGenres().indexOf(targetGenre) != -1) { //if movie genre contains comedy
                numGenre++;
            }
            
            if (movie.getMinutes() > threshMins) {
                numGreater++;
            }
            
            String currMovieDirectors = movie.getDirector();
            
            if (!directorNumMoviesMap.containsKey(currMovieDirectors)) { //if key is not yet in HashMap
                directorNumMoviesMap.put(currMovieDirectors, 1);
            } else {
                directorNumMoviesMap.put(currMovieDirectors, directorNumMoviesMap.get(currMovieDirectors) + 1);
            }
            
        }
        
        for (String director : directorNumMoviesMap.keySet()) {
            if (directorNumMoviesMap.get(director) > maxFilmsByDirector) {
                maxFilmsByDirector = directorNumMoviesMap.get(director);
                directorWithMostFilms = director;
            }
        }
        
        System.out.println("Number of movies in " + targetGenre + " genre: " + numGenre);
        System.out.println("Number of movies greater than " + threshMins + " mins in length: " + numGreater);
        System.out.println("Maximum number of movies by one director: " + maxFilmsByDirector);
        System.out.println("Director with most films: " + directorWithMostFilms);
        
    }
    
    
    public void testLoadRaters() {
        //ArrayList<Rater> raterList = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raterList = loadRaters("data/ratings.csv");
        
        int raterIDSearch = 193;
        
        System.out.println("Number of entries: " + raterList.size());
        System.out.println("Number of ratings for rater with rater_id " + raterIDSearch + ": " + findNumRatingsForRater(raterList, raterIDSearch));
        findMaxNumRatings(raterList);
        System.out.println("Number of different movies rated: " + findNumDiffMoviesRated(raterList));
    }
    
    private int findNumRatingsForRater(ArrayList<Rater> raterList, int rater_id) {
        
        for (Rater rater : raterList) {
            if (rater.getID().equals(String.valueOf(rater_id))) { //if the current rater ID corresponds to the ID being searched for
                return rater.numRatings();
            }
        }
        
        return -1;
    }
    
    private void findMaxNumRatings(ArrayList<Rater> raterList) {
        int largestNumRatings = 0;
        ArrayList<String> raterIDs = new ArrayList<String>();
        String movieIDSearch = "1798709";
        
        for (Rater rater : raterList) {
            if (rater.numRatings() > largestNumRatings) { //determine the largest number of ratings
                largestNumRatings = rater.numRatings();
            }
        }
        
        for (Rater rater : raterList) {
            if (rater.numRatings() == largestNumRatings) {
                raterIDs.add(rater.getID());
            }
        }
        
        System.out.println("Maximum number of ratings by any user: " + largestNumRatings);
        System.out.println("Rater IDs with this maximum number of ratings: " + raterIDs);
        System.out.println("Number of ratings movie " + movieIDSearch + " has: " + findNumRatingsForMovie(raterList, movieIDSearch));
        System.out.println("Number of different movies rated: " + findNumDiffMoviesRated(raterList));
    }
    
    
    private int findNumRatingsForMovie(ArrayList<Rater> raterList, String movieID) {
        int numRatings = 0;
        
        for (Rater rater : raterList) {
            if (rater.getItemsRated().contains(movieID)) {
                numRatings++;
            }
        }
        
        return numRatings;
    }
    
    
    private int findNumDiffMoviesRated(ArrayList<Rater> raterList) {
        ArrayList<String> foundMovies = new ArrayList<String>();
        
        for (Rater rater: raterList) {
            ArrayList<String> ratedMovies = rater.getItemsRated();
            
            for (String movie : ratedMovies) {
                if (!foundMovies.contains(movie)) {
                    foundMovies.add(movie);
                    //System.out.println(movie);
                }
            }
            
        }
        
        return foundMovies.size();
    }
}
