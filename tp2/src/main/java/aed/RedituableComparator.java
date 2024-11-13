package aed;

import java.util.Comparator;

public class RedituableComparator implements Comparator<Traslado>{
    @Override
    public int compare(Traslado cab1, Traslado cab2){
        // Si 2 ganancias son iguales, compara los ids
        if(Integer.compare(cab1.getGananciaNeta(),cab2.getGananciaNeta()) == 0){
            return Integer.compare(cab1.getId(), cab2.getId()) * (-1);
        }

        return (Integer.compare(cab1.getGananciaNeta(),cab2.getGananciaNeta()));
    }
}