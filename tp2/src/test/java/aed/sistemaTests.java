package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class sistemaTests {

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
    void todosPerdidaMenosUno(){
        int cantCiudades = 9;
        Traslado[] listaTraslados1 = new Traslado[] {
            new Traslado(1, 6, 1, 1000, 9),
            new Traslado(2, 6, 2, 1000, 20),
            new Traslado(3, 6, 4, 1000, 10),
            new Traslado(4, 6, 3, 1000, 5),
            new Traslado(5, 6, 0, 1000, 4),
            new Traslado(6, 6, 8, 1000, 1),
            new Traslado(7, 6, 7, 1000, 2)
        };
        BestEffort sis = new BestEffort(cantCiudades, listaTraslados1);
        assertSetEquals(new ArrayList<>(Arrays.asList(6,7,5,4,1,3,2)),sis.despacharMasAntiguos(listaTraslados1.length+1));
        assertSetEquals(new ArrayList<>(Arrays.asList(6)), sis.ciudadesConMayorGanancia());
        assertSetEquals(new ArrayList<>(Arrays.asList(8,7,0,3,1,4,2)), sis.ciudadesConMayorPerdida());
        sis.registrarTraslados(listaTraslados);

        assertEquals(6,sis.ciudadConMayorSuperavit());

    }

    @Test
    void stress(){
        int cantCiudades = 10000;
        int cantTraslados = 20000;
        Traslado[] traslados = new Traslado[cantTraslados];
        //Genero Tralados y los agrego al array
        for (int i = 0; i < cantTraslados; i++) {
            int origen = i % cantCiudades;
            int destino = (i + 1) % cantCiudades;
            int gananciaNeta = (int) (Math.random() * 1000) + 1; // Numero random entre 0 y 1000
            int timestamp = i;
            traslados[i]=(new Traslado(i, origen, destino, gananciaNeta, timestamp));
        }
        
        BestEffort sis = new BestEffort(cantCiudades, traslados);
        ArrayList<Integer> despachadosRedituables = sis.despacharMasRedituables(10000);
        assertEquals(10000, despachadosRedituables.size());

        ArrayList<Integer> despachadosAntiguos = sis.despacharMasAntiguos(30000);
        assertNotEquals(30000,despachadosAntiguos.size());
        assertEquals(10000, despachadosAntiguos.size());

        int ciudadConMayorSuperavit = sis.ciudadConMayorSuperavit();
        assertTrue(ciudadConMayorSuperavit >= 0 && ciudadConMayorSuperavit < cantCiudades);

        ArrayList<Integer> ciudadesConMayorGanancia = sis.ciudadesConMayorGanancia();
        assertFalse(ciudadesConMayorGanancia.isEmpty());

        

    }
}