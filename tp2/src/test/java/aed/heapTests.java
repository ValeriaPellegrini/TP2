package aed;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class heapTests {
    int cantCiudades;
    Traslado[] listaTraslados;
    ArrayList<Integer> actual;

    @BeforeEach
    void init(){
        //Reiniciamos los valores de las ciudades y traslados antes de cada test
        cantCiudades = 7;
        listaTraslados = new Traslado[] {
                                            new Traslado(1, 0, 1, 100, 10),
                                            new Traslado(2, 0, 1, 400, 20),
                                            new Traslado(3, 3, 4, 500, 50),
                                            new Traslado(4, 4, 3, 500, 11),
                                            new Traslado(5, 1, 0, 1000, 40),
                                            new Traslado(6, 1, 0, 1000, 41),
                                            new Traslado(7, 6, 3, 2000, 42)
                                        };
    }

    void assertSetEquals(ArrayList<Integer> s1, ArrayList<Integer> s2) {
        assertEquals(s1.size(), s2.size());
        for (int e1 : s1) {
            boolean encontrado = false;
            for (int e2 : s2) {
                if (e1 == e2) encontrado = true;
            }
            assertTrue(encontrado, "No se encontró el elemento " +  e1 + " en el arreglo " + s2.toString());
        }
    }
    
    @Test
    void chequeoHeapInvarianteRedituable(){
        Heap<Traslado> redituable = new Heap<Traslado>(listaTraslados,Traslado.byGananciaRedituable(),true);
        assertTrue(invarianteHeap(redituable,Traslado.byGananciaRedituable()),"Cumple INvariante" + redituable.heap.toString() ); 
       
    }

    @Test
    void chequeoHeapInvarianteTimeStamp(){
        Heap<Traslado> antiguo = new Heap<Traslado>(listaTraslados, Traslado.byAntiguedad(), false);
        assertTrue(invarianteHeap(antiguo, Traslado.byAntiguedad()),"Cumple INvariante" + antiguo.heap.toString() );
    }

    boolean invarianteHeap(Heap<Traslado> heap, Comparator<Traslado> compardor){
        boolean res = true;
        int i = 0;
        while (i< heap.heap.size() && res == true && (hijo_izq(i) < heap.heap.size() || hijo_der(i)<heap.heap.size())) {
            Traslado padre = heap.heap.get(i);
            if(compardor.compare(padre, heap.heap.get(hijo_izq(i))) > 0){
                res = false;
            }
            if (hijo_der(i)>=heap.heap.size()){
                break;
            }
            else{
                if(compardor.compare(padre, heap.heap.get(hijo_der(i))) > 0){
                    res = false;
                }
            }
            i++;
        }
        return res;
    }
    int hijo_der(int i){
        return 2*i+2;
    }
    int hijo_izq(int i){
        return 2*i+1;
    }
}
