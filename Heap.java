package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    public ArrayList<T> heap;
    private Comparator<T> comparator;
   
    

    public Heap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public Heap(T[] inicioDeSist, Comparator<T> comparator, boolean isMax) {
        //Hacer el heapify
        this.heap = new ArrayList<>();
        this.comparator = comparator;
        for (int i=0; i < inicioDeSist.length;i++){
            T element = inicioDeSist[i];
            heap.add(inicioDeSist[i]);
            int sizeHeap = heap.size()-1;
            if (element instanceof Traslado) {
                Traslado traslado = (Traslado) element;
                if (isMax){
                    traslado.setHeapIndexMax(sizeHeap);
                } else {
                    traslado.setHeapIndexMin(sizeHeap);
                }
            }
        }
       array2heap(isMax);   

    }

    public void array2heap(boolean isMax){

        for(int i = size()-1 ; i >=0 ; i-- ){ //O(n) por algoritmo de floyd
            heapifyDown(i, isMax);   
        }
    }

    public void insert(T element, boolean isMax ) {
        //Añade elemento al final y luego sube, si corresponde
        heap.add(element);
        int sizeHeap = heap.size()-1;
        if (element instanceof Traslado) {
            Traslado traslado = (Traslado) element;
            if (isMax){
                traslado.setHeapIndexMax(sizeHeap);
            } else {
                traslado.setHeapIndexMin(sizeHeap);
            }
        }

        heapifyUp(heap.size() - 1, isMax);
    }

    public T extractRoot(boolean isMax) {
        if (heap.isEmpty()) return null;

        T root = heap.get(0); //
        if (root instanceof Traslado) {
            Traslado trasladoRoot = (Traslado) root;
            remove(trasladoRoot, isMax);
        } else {
        //Reemplazamos la raiz por lastElement y bajamos, de ser necesario
            T lastElement = heap.remove(heap.size() - 1); //remove quita del arraylist el ùltimo y devuelve su valor
            if (!heap.isEmpty()) {
                heap.set(0, lastElement);
                heapifyDown(0, isMax);
            }
        }
        return root;
    }

    public T peekRoot() {
        return heap.isEmpty() ? null : heap.get(0);// si está vacío poner null, de lo contrario root
    }

    public int size() {
        return heap.size(); //Tamaño del heap
    }

    public boolean isEmpty() {
        return heap.isEmpty(); // verifica si está vacío
    }

    private void heapifyUp(int index, boolean isMax) {
        // Hijo_izq: 2*id_padre + 1
        // Hijo_der: 2*in_padre + 2
        int parentIndex = (index - 1) / 2; //toma siempre parte entera
        // En un COMPARADOR, si el valor es NEGATIVO, estamos cumpliendo el orden.
        // Siempre compara el elemento de la derecha con el de la izquierda del ORDEN defindo
        // Heapmax tiene orden hijo < padre, entonces compare(hijo,padre) hijo - padre < 0
        // Heapmin tiene orden padre < hijo, entonces compare(hijo,padre) padre - hijo < 0
        while (index > 0 && comparator.compare(heap.get(index), heap.get(parentIndex)) < 0) {
            swap(index, parentIndex, isMax);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index, boolean isMax) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1; // Hijo_izq
            int right = 2 * index + 2; // Hijo_der
            int target = index; // Nodo potencial a cambiar

            // En un COMPARADOR, si el valor es NEGATIVO, estamos cumpliendo el orden.
            // Siempre compara el elemento de la derecha con el de la izquierda del ORDEN defindo
            // Heapmax tiene orden hijo < padre, entonces compare(hijo,padre) hijo - padre < 0
            // Heapmin tiene orden padre < hijo, entonces compare(hijo,padre) padre - hijo < 0
            if (left < size && comparator.compare(heap.get(left), heap.get(target)) < 0) {
                target = left;
            }
            if (right < size && comparator.compare(heap.get(right), heap.get(target)) < 0) {
                target = right;
            }
            if (target == index) break;

            swap(index, target, isMax);
            index = target;
        }
    }

    private void swap(int i, int j, boolean isMax) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
        if (temp instanceof Traslado) {
            Traslado traslado1 = (Traslado) heap.get(i); 
            Traslado traslado2 = (Traslado) heap.get(j); 
            swapIndices(traslado1,traslado2, isMax);
        }
    }

    public void swapIndices (Traslado traslado1, Traslado traslado2, boolean isMax){
        int indiceMax1 = traslado1.getHeapIndexMax();
        int indiceMax2 = traslado2.getHeapIndexMax();
        int indiceMin1 = traslado1.getHeapIndexMin();
        int indiceMin2 = traslado2.getHeapIndexMin();
        if (isMax){
            traslado1.setHeapIndexMax(indiceMax2);
            traslado2.setHeapIndexMax(indiceMax1);
        } else {
            traslado1.setHeapIndexMin(indiceMin2);
            traslado2.setHeapIndexMin(indiceMin1);
        }
    }

    public void remove(Traslado element, boolean isMaxHeap) {
        // Determinar el índice dependiendo del tipo de heap
        int index = element.getHeapIndexMax();
        if(!isMaxHeap){
            index = element.getHeapIndexMin();
        } 
        if (index == -1 || index >= heap.size()) return;  // Verificar si el índice es válido
    
        
        if (index == heap.size() - 1){
            // Actualizamos el índice del elemento eliminado
            if (isMaxHeap) {
                element.setHeapIndexMax(-1);
            } else {
                element.setHeapIndexMin(-1);
            }
            heap.remove(heap.size() - 1);
            
        } else {
        // Intercambiamos el elemento con el último del heap
            swap(index, heap.size() - 1, isMaxHeap);

            // Actualizamos el índice del elemento eliminado
            if (isMaxHeap) {
                element.setHeapIndexMax(-1);
            } else {
                element.setHeapIndexMin(-1);
            }

            // Eliminamos el último elemento (el que intercambiamos)
            heap.remove(heap.size() - 1);
        
            // Restauramos la propiedad del heap con heapifyDown o heapifyUp desde el índice afectado
            heapifyDown(index, isMaxHeap);  // O(LOG N)
            heapifyUp(index,isMaxHeap);    // O(LOG N)
        }
    }
            
        
    
    public void remove(Ciudad element) {
        int index = element.getHeapIndex();
        if (index == -1 || index >= heap.size()) return;  // Verificar si el índice es válido
    
        // Intercambiamos el elemento con el último del heap
        swap(index, heap.size() - 1,true);
    
        // Eliminamos el último elemento (el que intercambiamos)
        heap.remove(heap.size() - 1);
    
        // Actualizamos el índice del elemento eliminado
            element.setHeapIndex(-1);
    
        // Restauramos la propiedad del heap con heapifyDown o heapifyUp desde el índice afectado
        heapifyDown(index, true);  // O(LOG N)
        heapifyUp(index, true);    // O(LOG N)
    }    

    
}
