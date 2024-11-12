package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comparator) {
        this.heap = new ArrayList<>();
        this.comparator = comparator;
    }

    public void insert(T element) {
        //Añade elemento al final y luego sube, si corresponde
        heap.add(element);
        heapifyUp(heap.size() - 1);
    }

    public T extractRoot() {
        if (heap.isEmpty()) return null;

        T root = heap.get(0); //
        T lastElement = heap.remove(heap.size() - 1); //remove quita del arraylist el ùltimo y devuelve su valor
        //Reemplazamos la raiz por lastElement y bajamos, de ser necesario
        if (!heap.isEmpty()) {
            heap.set(0, lastElement);
            heapifyDown(0);
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

    private void heapifyUp(int index) {
        // Hijo_izq: 2*id_padre + 1
        // Hijo_der: 2*in_padre + 2
        int parentIndex = (index - 1) / 2; //toma siempre parte entera
        // En un COMPARADOR, si el valor es NEGATIVO, estamos cumpliendo el orden.
        // Siempre compara el elemento de la derecha con el de la izquierda del ORDEN defindo
        // Heapmax tiene orden hijo < padre, entonces compare(hijo,padre) hijo - padre < 0
        // Heapmin tiene orden padre < hijo, entonces compare(hijo,padre) padre - hijo < 0
        while (index > 0 && comparator.compare(heap.get(index), heap.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1; // Hijo_izq
            int right = 2 * index + 2; // Hijo_der
            int target = index; // Nodo potencial a cambiar

            // En un COMPARADOR, si el valor es NEGATIVO, estamos cumpliendo el orden.
            // Siempre compara el elemento de la derecha con el de la izquierda del ORDEN defindo
            // Heapmax tiene orden hijo < padre, entonces compare(hijo,padre) hijo - padre < 0
            // Heapmin tiene orden padre < hijo, entonces compare(hijo,padre) padre - hijo < 0
            if (left < size && comparator.compare(heap.get(left), heap.get(target)) > 0) {
                target = left;
            }
            if (right < size && comparator.compare(heap.get(right), heap.get(target)) > 0) {
                target = right;
            }
            if (target == index) break;

            swap(index, target);
            index = target;
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public void remove(Traslado element, boolean isMaxHeap) {
        // Determinar el índice dependiendo del tipo de heap
        int index = isMaxHeap ? element.getHeapIndexMax() : element.getHeapIndexMin();
        if (index == -1 || index >= heap.size()) return;  // Verificar si el índice es válido
    
        // Intercambiamos el elemento con el último del heap
        swap(index, heap.size() - 1);
    
        // Eliminamos el último elemento (el que intercambiamos)
        heap.remove(heap.size() - 1);
    
        // Actualizamos el índice del elemento eliminado
        if (isMaxHeap) {
            element.setHeapIndexMax(-1);
        } else {
            element.setHeapIndexMin(-1);
        }
    
        // Restauramos la propiedad del heap con heapifyDown o heapifyUp desde el índice afectado
        heapifyDown(index);  // O(LOG N)
        heapifyUp(index);    // O(LOG N)
    }
    
    public void remove(Ciudad element) {
        int index = element.getHeapIndex();
        if (index == -1 || index >= heap.size()) return;  // Verificar si el índice es válido
    
        // Intercambiamos el elemento con el último del heap
        swap(index, heap.size() - 1);
    
        // Eliminamos el último elemento (el que intercambiamos)
        heap.remove(heap.size() - 1);
    
        // Actualizamos el índice del elemento eliminado
            element.setHeapIndex(-1);
    
        // Restauramos la propiedad del heap con heapifyDown o heapifyUp desde el índice afectado
        heapifyDown(index);  // O(LOG N)
        heapifyUp(index);    // O(LOG N)
    }    
}
