package aed;

import java.util.Comparator;

public class TimestampComparator implements Comparator<Traslado> {
    @Override
    public int compare(Traslado cab1, Traslado cab2){
        return (Integer.compare(cab1.getTimestamp(),cab2.getTimestamp()))*(-1);
    }
}