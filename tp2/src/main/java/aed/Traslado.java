package aed;

import java.util.Comparator;

public class Traslado {
    
    public int id;
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
    } //Complejidad Temporal total ~ O(1)

    public int getId() { return id; } //Complejidad Temporal total ~ O(1)
    public int getOrigen() { return origen; } //Complejidad Temporal total ~ O(1)
    public int getDestino() { return destino; } //Complejidad Temporal total ~ O(1)
    public int getGananciaNeta() { return gananciaNeta; } //Complejidad Temporal total ~ O(1)
    public int getTimestamp() { return timestamp; } //Complejidad Temporal total ~ O(1)
    public int getHeapIndexMax() { return heapIndexMax; } //Complejidad Temporal total ~ O(1)
    public int getHeapIndexMin() { return heapIndexMin; } //Complejidad Temporal total ~ O(1)
    public void setHeapIndexMin(int index) { this.heapIndexMin = index; } //Complejidad Temporal total ~ O(1)
    public void setHeapIndexMax(int index) { this.heapIndexMax = index; } //Complejidad Temporal total ~ O(1)


    // Comparador para redituabilidad (usado en maxHeap)
    public static Comparator<Traslado> byGananciaRedituable() {
        return Comparator.comparingInt(Traslado::getGananciaNeta).reversed()
                         .thenComparingInt(Traslado::getId);
    } //Complejidad Temporal total ~ O(1)

    // Comparador para antigüedad (usado en minHeap)
    public static Comparator<Traslado> byAntiguedad() {
        return Comparator.comparingInt(Traslado::getTimestamp);
    } //Complejidad Temporal total ~ O(1)
    

}

