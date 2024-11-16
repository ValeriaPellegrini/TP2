package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    public ArrayList<T> heap;
    private Comparator<T> comparador;
   
    public Heap(T[] inicioDeSist, Comparator<T> comparator, boolean esMax) {
        //Hacer el heapify
        this.heap = new ArrayList<>(); //O(1)
        this.comparador = comparator; //O(1)
        for (int i=0; i < inicioDeSist.length;i++){ //O(|traslados|)*O(1)
            T element = inicioDeSist[i]; //O(1)
            heap.add(inicioDeSist[i]); //O(1)
            int sizeHeap = heap.size()-1; //O(1)
            if (element instanceof Traslado) { //O(1)
                Traslado traslado = (Traslado) element; //O(1)
                if (esMax){ //O(1)
                    traslado.setHeapIndexMax(sizeHeap); //O(1)
                } else { //O(1)
                    traslado.setHeapIndexMin(sizeHeap); //O(1)
                }
            }
            if (element instanceof Ciudad){
                Ciudad ciudad = (Ciudad) element;
                ciudad.setHeapIndex(sizeHeap);
            }
        }
       array2heap(esMax); //O(|traslados|)

    } //Complejidad Temporal total ~ O(|traslados|)

    public void array2heap(boolean esMax){

        for(int i = tamaño()-1 ; i >=0 ; i-- ){ //O(tamañoHeap) por algoritmo de floyd
            heapifyDown(i, esMax); //O(tamañoHeap). El método heapifyDown es O(log(tamaño)) pero, en este caso, lo peor
                                //sería que cada llamada al bajar haga el máximo de intercambios posibles, que
                                //como vimos en la teórica, tendrá una cota de 3/2(tamaño + 1)
        }
    } //Complejidad Temporal total ~ O(tamañoHeap). 

    public void agregar(T elemento, boolean esMax ) {
        //Añade elemento al final y luego sube, si corresponde
        heap.add(elemento); //O(1)
        int sizeHeap = heap.size()-1; //O(1)
        if (elemento instanceof Traslado) { //O(1)
            Traslado traslado = (Traslado) elemento; //O(1)
            if (esMax){
                traslado.setHeapIndexMax(sizeHeap);
            } else {
                traslado.setHeapIndexMin(sizeHeap);
            }
        }

        heapifyUp(heap.size() - 1, esMax); //O(log(tamañoHeap))
    } //Complejidad Temporal total ~ O(log(tamañoHeap)). 



    public T raiz() {
        return heap.isEmpty() ? null : heap.get(0); //O(1)
    } //Complejidad Temporal total ~ O(1)

    public int tamaño() {
        return heap.size(); 
    } //Complejidad Temporal total ~ O(1)

    public boolean estaVacio() {
        return heap.isEmpty(); 
    } //Complejidad Temporal total ~ O(1)

    private void heapifyUp(int indice, boolean esMax) {
        int indicePadre = (indice - 1) / 2; //O(1)

        while (indice > 0 && comparador.compare(heap.get(indice), heap.get(indicePadre)) < 0) { //O(log(tamañoHeap)). Recorrre medio árbol
            swap(indice, indicePadre, esMax); //O(1)                                             
            indice = indicePadre; //O(1) 
            indicePadre = (indice - 1) / 2; //O(1) 
        }
    } //Complejidad Temporal total ~ O(log(tamañoHeap)). 

    private void heapifyDown(int indice, boolean esMax) {
        int size = heap.size(); //O(1)
        while (indice < size) { //O(log(tamañoHeap)). Recorrre medio árbol
            int hijoIzq = 2 * indice + 1; //O(1)
            int hijoDer = 2 * indice + 2; //O(1)
            int padre = indice; //O(1)

            if (hijoIzq < size && comparador.compare(heap.get(hijoIzq), heap.get(padre)) < 0) { //O(1)
                padre = hijoIzq; //O(1)
            }
            if (hijoDer < size && comparador.compare(heap.get(hijoDer), heap.get(padre)) < 0) { //O(1)
                padre = hijoDer; //O(1)
            }
            if (padre == indice) break; //O(1)

            swap(indice, padre, esMax); //O(1)
            indice = padre; //O(1)
        }
    } //Complejidad Temporal total ~ O(log(tamañoHeap)). 

    private void swap(int i, int j, boolean isMax) {
        T temp = heap.get(i); //O(1)
        heap.set(i, heap.get(j)); //O(1)
        heap.set(j, temp); //O(1)
        if (temp instanceof Traslado) { //O(1)
            Traslado traslado1 = (Traslado) heap.get(i);  //O(1)
            Traslado traslado2 = (Traslado) heap.get(j);  //O(1)
            swapIndices(traslado1,traslado2, isMax); //O(1)
        }
        if (temp instanceof Ciudad){
            Ciudad ciudad1 = (Ciudad) heap.get(i);
            Ciudad ciudad2 = (Ciudad) heap.get(j);
            swapIndicesCiudad(ciudad1,ciudad2);
        }
    } //Complejidad Temporal total ~ O(1). 

    public void swapIndices (Traslado traslado1, Traslado traslado2, boolean isMax){
        int indiceMax1 = traslado1.getHeapIndexMax(); //O(1)
        int indiceMax2 = traslado2.getHeapIndexMax(); //O(1)
        int indiceMin1 = traslado1.getHeapIndexMin(); //O(1)
        int indiceMin2 = traslado2.getHeapIndexMin(); //O(1)
        if (isMax){ //O(1)
            traslado1.setHeapIndexMax(indiceMax2); //O(1)
            traslado2.setHeapIndexMax(indiceMax1); //O(1)
        } else { //O(1)
            traslado1.setHeapIndexMin(indiceMin2); //O(1)
            traslado2.setHeapIndexMin(indiceMin1); //O(1)
        }
    } //Complejidad Temporal total ~ O(1). 

    public void swapIndicesCiudad(Ciudad ciudad1, Ciudad ciudad2){
        int indiceMax1 = ciudad1.getHeapIndex(); //O(1)
        int indiceMax2 = ciudad2.getHeapIndex(); //O(1)
        ciudad1.setHeapIndex(indiceMax2); //O(1)
        ciudad2.setHeapIndex(indiceMax1); //O(1)

    }

    public void sacar(Traslado elemento, boolean esMax) {
        // Determinar el índice dependiendo del tipo de heap
        int indice = elemento.getHeapIndexMax(); //O(1)
        if(!esMax){ //O(1)
            indice = elemento.getHeapIndexMin(); //O(1)
        }  //O(1)
        if (indice == -1 || indice >= heap.size()) return;  //O(1)
    
        
        if (indice == heap.size() - 1){ //O(1)
            // Actualizamos el índice del elemento eliminado
            if (esMax) { //O(1)
                elemento.setHeapIndexMax(-1); //O(1)
            } else { //O(1)
                elemento.setHeapIndexMin(-1); //O(1)
            } //O(1)
            heap.remove(heap.size() - 1); //O(1), por explicación en enunciado de TP, estamos sacando el último.
            
        } else { //O(1)
        // Intercambiamos el elemento con el último del heap
            swap(indice, heap.size() - 1, esMax); //O(1)

            // Actualizamos el índice del elemento eliminado
            if (esMax) { //O(1)
                elemento.setHeapIndexMax(-1); //O(1)
            } else { //O(1)
                elemento.setHeapIndexMin(-1); //O(1)
            } //O(1)

            // Eliminamos el último elemento
            heap.remove(heap.size() - 1); //O(1), por explicación en enunciado de TP, estamos sacando el último.
        
            // Restauramos la propiedad del heap 
            heapifyDown(indice, esMax);  // O(LOG (tamañoHeap))
            heapifyUp(indice,esMax);    // O(LOG (tamañoHeap))
        }
    } //Complejidad Temporal total ~ O(LOG (tamañoHeap)). 
            
        
    
    public void sacar(Ciudad elemento) {
        int indice = elemento.getHeapIndex(); //O(1)
        if (indice == -1 || indice >= heap.size()) return;  //O(1)
        if (indice == heap.size() - 1){ //O(1)
            elemento.setHeapIndex(-1); //O(1)
            } else {//O(1)
            heap.remove(heap.size() - 1); //O(1), por explicación en enunciado de TP, estamos sacando el último.
            swap(indice, heap.size() - 1,true); //O(1)
            heap.remove(heap.size() - 1); //O(1) 
            elemento.setHeapIndex(-1); //O(1)
            heapifyDown(indice, true);  // O(LOG (tamañoHeap))
            heapifyUp(indice, true);    // O(LOG (tamañoHeap))
            }
    } //Complejidad Temporal total ~ O(LOG (tamañoHeap)). 

    
}
