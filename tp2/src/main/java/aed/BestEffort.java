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
        this.heapRedituable = new Heap<>(traslados, Traslado.byGananciaRedituable(), true); // O(|T|)
        this.heapAntiguo = new Heap<>(traslados, Traslado.byAntiguedad(), false); // O(|T|)
        this.gananciaTotal = 0; // O(1)
        this.trasladosDespachados = 0; // O(1)
        this.ciudades = new Ciudad[cantCiudades]; // O(|C|)
        this.maxGanancia = 0; // O(1)
        this.maxPerdida = 0; // O(1)
        this.ciudadesMaxGanancia = new ArrayList<>(cantCiudades); // O(1)
        this.ciudadesMaxPerdida = new ArrayList<>(cantCiudades); // O(1)

        for (int i = 0; i < cantCiudades; i++) { //O(|C|)
            ciudades[i] = new Ciudad(i); //O(1)
        } // O(|C|)
        this.ciudadConMayorSuperavit = new Heap<>(ciudades, Ciudad.byMayorSuperavit(), true); // O(|C|)
       
    } //Complejidad Temporal total ~ O(|C| + |T|)

    public void registrarTraslados(Traslado[] traslados){
        
        for (Traslado traslado : traslados) { //O(|T|)
            
            heapRedituable.agregar(traslado, true); // O(LOG |T|)
            heapAntiguo.agregar(traslado, false); // (LOG |T|)
            
        } // Complejidad Temporal total ~ O(|T|(LOG |T|))
    }
    
    private void actualizarEstadisticas(Traslado traslado) {
        int ganancia = traslado.getGananciaNeta(); // O(1)
        Ciudad origen = ciudades[traslado.getOrigen()]; // O(1)
        Ciudad destino = ciudades[traslado.getDestino()]; // O(1)

        //Sacamos origen y destino del heap de Superavit
        ciudadConMayorSuperavit.sacar(origen); //O(LOG(|C|))
        ciudadConMayorSuperavit.sacar(destino); //O(LOG(|C|))
    
        // Actualiza las ganancias y pérdidas
        origen.agregarGanancia(ganancia); // O(1)
        destino.agregarPerdida(ganancia); // O(1)

        //Una vez actualizados los valores, agregamos origen y destino al heap de superavit
        ciudadConMayorSuperavit.agregar(origen,true); //O(LOG(|C|))
        ciudadConMayorSuperavit.agregar(destino,true); //O(LOG(|C|))
    
    
        // Actualiza las ciudades con mayor ganancia
        int gananciaOrigen = origen.getGananciaTotal(); // O(1)
        if (gananciaOrigen > maxGanancia) { // O(1)
            maxGanancia = gananciaOrigen; // O(1)
            ciudadesMaxGanancia.clear(); // O(1)
            ciudadesMaxGanancia.add(origen.getId()); // O(1)
        } else if (gananciaOrigen == maxGanancia) { // O(1)
            ciudadesMaxGanancia.add(origen.getId()); // O(1)
        } //O(1)
    
        // Actualiza las ciudades con mayor pérdida
        int perdidaDestino = destino.getPerdidaTotal(); // O(1)
        if (perdidaDestino > maxPerdida) { // O(1)
            maxPerdida = perdidaDestino; // O(1)
            ciudadesMaxPerdida.clear(); // O(1)
            ciudadesMaxPerdida.add(destino.getId()); // O(1)
        } else if (perdidaDestino == maxPerdida) { // O(1)
            ciudadesMaxPerdida.add(destino.getId()); // O(1)
        } //O(1)
    
        gananciaTotal += ganancia; // O(1)
        trasladosDespachados++; // O(1)
    } // Complejidad Temporal total ~ //O(LOG(|C|))

    public ArrayList<Integer> despacharMasRedituables(int n) {
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        for (int i = 0; i < n && !heapRedituable.estaVacio(); i++) {
            Traslado traslado = heapRedituable.raiz(); // O(LOG(T))
            idsDespachados.add(traslado.getId()); // O(1)
            actualizarEstadisticas(traslado); // //O(LOG(|C|))
            heapRedituable.sacar(traslado, true); //O(LOG(T))
           
            
            // Sincronización: eliminar el traslado de heapAntiguo
            heapAntiguo.sacar(traslado, false); // O(LOG(T))
            
        } // O(N)
       
        return idsDespachados;
    } // Complejidad Temporal total ~ O(N*(LOG(|T|) + LOG(|C|)))

    public ArrayList<Integer> despacharMasAntiguos(int n){
        ArrayList<Integer> idsDespachados = new ArrayList<>();
        for (int i = 0; i < n && !heapAntiguo.estaVacio(); i++) {
            Traslado traslado = heapAntiguo.raiz(); // O(LOG(T))
            idsDespachados.add(traslado.getId());// O(1)
            actualizarEstadisticas(traslado); // //O(LOG(|C|))
            heapAntiguo.sacar(traslado, false); // O(LOG(T))

            // Sincronización: eliminar el traslado de heapRedituable si existe
            heapRedituable.sacar(traslado, true);; // O(LOG(T))
        } // O(N)
       
        return idsDespachados;
    } // Complejidad Temporal total ~ O(N*(LOG(|T|) + LOG(|C|)))
    
    public int ciudadConMayorSuperavit() {
        if (ciudadConMayorSuperavit.estaVacio()) {return -1;}
        
        // Extraemos la ciudad con el mayor superávit
        Ciudad ciudad = ciudadConMayorSuperavit.raiz(); // O(1)
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
