
/**
 * Write a description of RecommendationRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class RecommendationRunner implements Recommender {
    
    public ArrayList<String> getItemsToRate() {
        
        ArrayList<String> res = new ArrayList<String>();
        ArrayList<String> moviesAfterYear2000 = MovieDatabase.filterBy(new YearAfterFilter(2000));
        
        for (int i = 0; i < 10; i++) { //get any 10 recent movies (after year 2000) for user to rate
            res.add(moviesAfterYear2000.get(i));
            //System.out.println(MovieDatabase.getTitle(moviesAfterYear2000.get(i)));
            //System.out.println(MovieDatabase.getYear(moviesAfterYear2000.get(i)));
        }
        
        return res;
        
    }
    
    public void printRecommendationsFor(String webRaterID) {
        
        FourthRatings fr = new FourthRatings();
        int minRaters = 0;
        int numTopSimilarRaters = 0;
        
        ArrayList<Rating> recommendationList = fr.getSimilarRatings(webRaterID, numTopSimilarRaters, minRaters);
        
        if (recommendationList.isEmpty()) { //if there are no recommendations
            System.out.println("There are no recommendations available at the moment.");
        } else {
            System.out.println("<h2>Recommended Movies:</h2>");
            System.out.println("<table><tr><th>No.</th>");
            System.out.println("<th style = \"text-align:center\">Poster</th>");
            System.out.println("<th style = \"text-align:center\">Title</th>");
            System.out.println("<th style = \"text-align:center\">Runtime</th>");
            System.out.println("<th style = \"text-align:center\">Countries</th>");
            System.out.println("<th style = \"text-align:center\">Genres</th>");
            System.out.println("<th style = \"text-align:center\">Year</th>");
            System.out.println("</tr>");
            
            int recNum = 1;
            
            for (Rating r : recommendationList) { //print out top 10 recommendations in a table
                
                if (recNum <= 10) {
                    System.out.println("<tr><td><h3>" + recNum + "</h3></td>");
                    
                    String posterURL = MovieDatabase.getPoster(r.getItem());
                    String title = MovieDatabase.getTitle(r.getItem());
                    int runtime = MovieDatabase.getMinutes(r.getItem());
                    String country = MovieDatabase.getCountry(r.getItem());
                    String genre = MovieDatabase.getGenres(r.getItem());
                    int year = MovieDatabase.getYear(r.getItem());
                    
                    if (posterURL.length() > 3) {
                        System.out.println("<td><img src = \"" + posterURL + "\" target = _blank></td>");
                    }
                    
                    System.out.println("<td><h3>" + title + "</h3></td>");
                    System.out.println("<td><h3>" + runtime + " minutes</h3></td>");
                    System.out.println("<td><h3>" + country + "</h3></td>");
                    System.out.println("<td><h3>" + genre + "</h3></td>");
                    System.out.println("<td><h3>" + year + "</h3></td>");
                    
                    recNum++;
                }
            }
            
        }
        
    }
    
}
