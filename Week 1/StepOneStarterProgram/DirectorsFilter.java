import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class DirectorsFilter implements Filter {
    private String[] myDirectorsArr;
    private ArrayList<String> myDirectorsAL;
    
    public DirectorsFilter(String directors) {
        myDirectorsArr = directors.split(",", 0);
        myDirectorsAL = new ArrayList<String>();
        Collections.addAll(myDirectorsAL, myDirectorsArr);
    }
    
    @Override
    public boolean satisfies(String id) {
        String dir = MovieDatabase.getDirector(id);
        String[] dirArr = dir.split(",");
        for (String director : dirArr) {
            if (myDirectorsAL.contains(director.trim())) {
                return true;
            }
        }
        return false;
    }
}
