package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

      /**
      * Intercambia elementos de los índices de un arreglo.
      * @param <T> tipo del que puede ser el arreglo.
      * @param arreglo arreglo cuyos índices se van a intercambiar.
      * @param i indice a intercambiar con j.
      * @param j indice a intercambiar con i.
      */
    private static <T> void intercambia(T[] arreglo, int i, int j){
        T elemento = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = elemento;
    }

      /**
      * Ordena el arreglo usando QuickSort.
      * @param <T> tipo del que puede ser el arreglo a ordenar.
      * @param arreglo arreglo a ordenar mediante QuickSort.
      * @param inicio indice del inicio del arreglo a ordenar.
      * @param fin indice del fin del arreglo a ordenar.
      * @param comparador el comparador para ordenar el arreglo.
      */
    private static <T> void quickSort(T[] arreglo, int inicio, int fin, Comparator<T> comparador){
        if(fin <= inicio) return;
        int i = inicio + 1;
        int j = fin;

        while(i < j){
            if((comparador.compare(arreglo[i] , arreglo[inicio]) > 0) && comparador.compare(arreglo[j], arreglo[inicio]) <= 0){
                intercambia(arreglo,i,j);
                i = i + 1;
                j = j - 1;
            }else if(comparador.compare(arreglo[i], arreglo[inicio]) <= 0){
                i = i + 1;
            }else{
                j = j - 1;
            }
        }
        if(comparador.compare(arreglo[i] ,arreglo[inicio]) > 0){
            i = i - 1;
        }
        intercambia(arreglo, inicio,i);
        quickSort(arreglo, inicio, i - 1, comparador);
        quickSort(arreglo, inicio + 1, fin, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        quickSort(arreglo,0,arreglo.length - 1,comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        for(int i = 0; i < arreglo.length; i++){
            int m = i;
            for(int j = i + 1; j < arreglo.length; j++){
                if(comparador.compare(arreglo[j],arreglo[m]) < 0)
                    m = j;
            }
            intercambia(arreglo, i, m);
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        return busquedaBinaria(arreglo, elemento, 0, arreglo.length - 1, comparador);
    }

    private static <T> int busquedaBinaria(T[] arreglo, T elemento, int inicio, int fin, Comparator<T> comparador){
        if(inicio > fin) return -1;
        int mitad = (inicio + fin)/2;
        if(comparador.compare(arreglo[mitad],elemento) == 0){
            return mitad;
        }else if(comparador.compare(arreglo[mitad], elemento) > 0){
            return busquedaBinaria(arreglo, elemento, inicio, fin - 1, comparador);
        }else{
            return busquedaBinaria(arreglo, elemento, inicio + 1, fin, comparador);
        }
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
