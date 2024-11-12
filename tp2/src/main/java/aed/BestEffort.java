package aed;

import java.util.ArrayList;

public class BestEffort {
    //Completar atributos privados
    private Ciudad[] ciudades;
    private Heap<Traslado> heapRedituable;
    private Heap<Traslado> heapAntiguo;
    private int gananciaTotal;
    private int trasladosDespachados;
    private Heap<Ciudad> ciudadConMayorSuperavit;
    private int maxGanancia;
    private int maxPerdida;
    private ArrayList<Integer> ciudadesMaxGanancia;
    private ArrayList<Integer> ciudadesMaxPerdida;

    public BestEffort(int cantCiudades, Traslado[] traslados){
        this.heapRedituable = new Heap<>(Traslado.byGananciaRedituable()); // O(1)
        this.heapAntiguo = new Heap<>(Traslado.byAntiguedad()); // O(1)
        this.gananciaTotal = 0; // O(1)
        this.trasladosDespachados = 0; // O(1)
        this.ciudades = new Ciudad[cantCiudades]; // O(C)
        this.ciudadConMayorSuperavit = new Heap<>(Ciudad.byMayorSuperavit()); // O(1)
        this.maxGanancia = 0; // O(1)
        this.maxPerdida = 0; // O(1)
        this.ciudadesMaxGanancia = new ArrayList<>(); // O(1)
        this.ciudadesMaxPerdida = new ArrayList<>(); // O(1)

        for (int i = 0; i < cantCiudades; i++) {
            ciudades[i] = new Ciudad(i);
        } // O(C)

        // Registrar traslados iniciales
        registrarTraslados(traslados); // O(T)
    } // Complejidad Temporal total ~ O(C)+O(T)
    

    public void registrarTraslados(Traslado[] traslados){
        for (Traslado t : traslados) {
            heapRedituable.insert(t); // O(LOG N)
            heapAntiguo.insert(t); // O(LOG N) 
        } 
        // Complejidad Temporal total ~ O(T * LOG(N)), donde T es el número de traslados y N es el tamaño de los heaps.
    }
    
    private void actualizarEstadisticas(Traslado traslado) {
        int ganancia = traslado.getGananciaNeta(); // O(1)
        Ciudad origen = ciudades[traslado.getOrigen()]; // O(1)
        Ciudad destino = ciudades[traslado.getDestino()]; // O(1)
    
        // Actualiza las ganancias y pérdidas
        origen.agregarGanancia(ganancia); // O(1)
        destino.agregarPerdida(ganancia); // O(1)
    
        // Actualiza las ciudades con mayor ganancia
        int gananciaOrigen = origen.getGananciaTotal(); // O(1)
        if (gananciaOrigen > maxGanancia) {
            maxGanancia = gananciaOrigen;
            ciudadesMaxGanancia.clear();
            ciudadesMaxGanancia.add(origen.getId());
        } else if (gananciaOrigen == maxGanancia) {
            ciudadesMaxGanancia.add(origen.getId());
        }
    
        // Actualiza las ciudades con mayor pérdida
        int perdidaDestino = destino.getPerdidaTotal(); // O(1)
        if (perdidaDestino > maxPerdida) {
            maxPerdida = perdidaDestino;
            ciudadesMaxPerdida.clear();
            ciudadesMaxPerdida.add(destino.getId());
        } else if (perdidaDestino == maxPerdida) {
            ciudadesMaxPerdida.add(destino.getId());
        }
    
        gananciaTotal += ganancia; // O(1)
        trasladosDespachados++; // O(1)
    } // Complejidad Temporal total ~ O(1)

    public ArrayList<Integer> despacharMasRedituables(int n) {
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        for (int i = 0; i < n && !heapRedituable.isEmpty(); i++) {
            Traslado traslado = heapRedituable.extractRoot(); // O(LOG(T))
            idsDespachados.add(traslado.getId()); // O(1)
            actualizarEstadisticas(traslado); // O(1)
            
            // Sincronización: eliminar el traslado de heapAntiguo si existe
            sincronizarHeapConOtro(traslado, heapAntiguo); // O(LOG(T))
        } // O(N)
        return idsDespachados;
    } // Complejidad Temporal total ~ O(N*LOG(T))

    public ArrayList<Integer> despacharMasAntiguos(int n){
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        for (int i = 0; i < n && !heapAntiguo.isEmpty(); i++) {
            Traslado traslado = heapAntiguo.extractRoot(); // O(LOG(T))
            idsDespachados.add(traslado.getId());// O(1)
            actualizarEstadisticas(traslado); // O(1)

            // Sincronización: eliminar el traslado de heapRedituable si existe
            sincronizarHeapConOtro(traslado, heapRedituable); // O(LOG(T))
        } // O(N)
        return idsDespachados;
    } // Complejidad Temporal total ~ O(N*LOG(T))
    
    // Método para eliminar un traslado de un heap específico si existe
    private void sincronizarHeapConOtro(Traslado traslado, Heap<Traslado> otroHeap) {
        // Usamos equals para comparar si ambos heaps son iguales (si sobrescribes equals en Heap)
        boolean isMaxHeap = otroHeap.equals(heapRedituable);  // Si es el heap máximo
    
        // Llamamos al método remove, indicando si es un heap máximo o mínimo
        otroHeap.remove(traslado, isMaxHeap);  // Eliminar del heap correspondiente
    }
    
    
    
    public int ciudadConMayorSuperavit() {
        if (ciudadConMayorSuperavit.isEmpty()) {return -1;}
        
        // Extraemos la ciudad con el mayor superávit
        Ciudad ciudad = ciudadConMayorSuperavit.extractRoot(); // O(1)
        return ciudad.getId(); // O(1)
    }

    public ArrayList<Integer> ciudadesConMayorGanancia(){
        return ciudadesMaxGanancia;
    } // Complejidad Temporal total ~ O(1)


    public ArrayList<Integer> ciudadesConMayorPerdida(){
        return ciudadesMaxPerdida;
    } // Complejidad Temporal total ~ O(1)

    public int gananciaPromedioPorTraslado(){
        return gananciaTotal / trasladosDespachados;
    } // Complejidad Temporal total ~ O(1)
    
}