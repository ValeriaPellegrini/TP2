package aed;

import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<T> heap;
    private Comparator<T> comparator;

    public Heap(Comparator<T> comparator) {
        heap = new ArrayList<>();
        this.comparator = comparator;
    }

    // Método para insertar un nuevo elemento en el heap
    public void insert(T value, int prioridad) {
        heap.add(value);
        siftUp(heap.size() - 1, prioridad);
    }

    // Método para obtener el elemento máximo sin eliminarlo
    public T peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    // Método para eliminar y devolver el elemento máximo
    public T sacar(int prioridad) {
        if (heap.isEmpty()) {
            return null;
        }
        T max = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0, prioridad);
        }
        return max;
    }

    // Método para reajustar hacia arriba después de una inserción. Prioridad 0= Antiguedad, 1=Ganancia
    private void siftUp(int index, int prioridad) {
        if (prioridad == 0){
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                if (comparator.compare(heap.get(index), heap.get(parentIndex)) >= 0) {
                    break;
                }
                swap(index, parentIndex);
                index = parentIndex;
            }
        } else if (prioridad == 1){
            while (index > 0) {
                int parentIndex = (index - 1) / 2;
                if (comparator.compare(heap.get(index), heap.get(parentIndex)) <= 0) {
                    break;
                }
                swap(index, parentIndex);
                index = parentIndex;
            }
        }
        
    }

    // Método para reajustar hacia abajo después de eliminar el máximo. Prioridad 0= Antiguedad, 1=Ganancia
    private void siftDown(int index, int prioridad) {
        if (prioridad == 0){
            int leftChild, rightChild, smallestChild;
            while (index * 2 + 1 < heap.size()) {
                leftChild = index * 2 + 1;
                rightChild = index * 2 + 2;
                smallestChild = leftChild;
    
                if (rightChild < heap.size() && comparator.compare(heap.get(rightChild), heap.get(leftChild)) < 0) {
                    smallestChild = rightChild;
                }
    
                if (comparator.compare(heap.get(index), heap.get(smallestChild)) <= 0) {
                    break;
                }
    
                swap(index, smallestChild);
                index = smallestChild;
            }  
        } else if (prioridad == 1 ){
            int leftChild, rightChild, largestChild;
        while (index * 2 + 1 < heap.size()) {
            leftChild = index * 2 + 1;
            rightChild = index * 2 + 2;
            largestChild = leftChild;

            if (rightChild < heap.size() && comparator.compare(heap.get(rightChild), heap.get(leftChild)) > 0) {
                largestChild = rightChild;
            }

            if (comparator.compare(heap.get(index), heap.get(largestChild)) >= 0) {
                break;
            }

            swap(index, largestChild);
            index = largestChild;
        }
        }
        
    }

    // Método auxiliar para intercambiar elementos en el ArrayList
    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Método para verificar si el heap está vacío
    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
