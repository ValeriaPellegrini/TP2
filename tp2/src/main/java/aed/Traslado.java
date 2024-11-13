package aed;

import java.util.Comparator;

public class Traslado {
    
    private int id;
    private int origen;
    private int destino;
    private int gananciaNeta;
    private int timestamp;
    private int heapIndexMin = -1;  // Índice en el heap mínimo
    private int heapIndexMax = -1;  // Índice en el heap máximo

    public Traslado(int id, int origen, int destino, int gananciaNeta, int timestamp){
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.gananciaNeta = gananciaNeta;
        this.timestamp = timestamp;
    }

    public int getId() { return id; }
    public int getOrigen() { return origen; }
    public int getDestino() { return destino; }
    public int getGananciaNeta() { return gananciaNeta; }
    public int getTimestamp() { return timestamp; }
    public int getHeapIndexMax() { return heapIndexMax; }
    public int getHeapIndexMin() { return heapIndexMin; }
    public void setHeapIndexMin(int index) { this.heapIndexMin = index; }
    public void setHeapIndexMax(int index) { this.heapIndexMax = index; }


    // Comparador para redituabilidad (usado en maxHeap)
    public static Comparator<Traslado> byGananciaRedituable() {
        return Comparator.comparingInt(Traslado::getGananciaNeta).reversed()
                         .thenComparingInt(Traslado::getId);
    }

    // Comparador para antigüedad (usado en minHeap)
    public static Comparator<Traslado> byAntiguedad() {
        return Comparator.comparingInt(Traslado::getTimestamp);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Traslado traslado = (Traslado) obj;
        return id == traslado.id;  // 'id' es único para cada Traslado
    }
}