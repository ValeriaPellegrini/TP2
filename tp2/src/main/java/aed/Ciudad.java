package aed;

import java.util.Comparator;

public class Ciudad {
    public int id;
    private int gananciaTotal;
    private int perdidaTotal;
    private int heapIndex = -1;


    public Ciudad(int id) {
        this.id = id;
        this.gananciaTotal = 0;
        this.perdidaTotal = 0;
    } //Complejidad Temporal total ~ O(1)

    public int getId() { return id; } //Complejidad Temporal total ~ O(1)
    public int getGananciaTotal() { return gananciaTotal; } //Complejidad Temporal total ~ O(1)
    public int getPerdidaTotal() { return perdidaTotal; } //Complejidad Temporal total ~ O(1)
    public int getSuperavit() { return gananciaTotal - perdidaTotal; } //Complejidad Temporal total ~ O(1)
    public int getHeapIndex() { return heapIndex; } //Complejidad Temporal total ~ O(1)
    public void setHeapIndex(int index) { this.heapIndex = index; } //Complejidad Temporal total ~ O(1)

    public void agregarPerdida(int perdida) {
        this.perdidaTotal += perdida;
    } //Complejidad Temporal total ~ O(1)

    public void agregarGanancia(int ganancia) {
        this.gananciaTotal += ganancia;
    } //Complejidad Temporal total ~ O(1)


    public static Comparator<Ciudad> byMayorSuperavit() {
        return Comparator.comparingInt(Ciudad::getSuperavit).reversed()
                         .thenComparingInt(Ciudad::getId);
    } //Complejidad Temporal total ~ O(1)


}
