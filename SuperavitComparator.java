package aed;
import java.util.Comparator;

public class SuperavitComparator implements Comparator<Ciudad>{
    
    @Override
    public int compare(Ciudad city1, Ciudad city2){
        return (Integer.compare(city1.getSuperavit(),city2.getSuperavit()));
    }
}
