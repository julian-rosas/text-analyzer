package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return (indice < elementos && arbol[indice] != null);
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if(hasNext())
                return arbol[indice++];
            throw new NoSuchElementException();
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.indice = -1;

        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.
            return elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
        arbol = nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        int indice = 0;
        arbol = nuevoArreglo(n);
        
        for(T e : iterable){
            arbol[indice] = e;
            arbol[indice].setIndice(indice);
            indice++;
        }
        
        elementos = n;

        for(int i = (elementos/2 - 1); i >= 0; i--){
            acomodaAbajo(i);
        }

    }

    private boolean indiceValido(int i){
        return (i >= 0 && i < this.elementos);
    }


    private void intercambiaElementos(T a, T b) {
        int aux = a.getIndice();
        arbol[a.getIndice()] = b;
        arbol[b.getIndice()] = a;
        b.setIndice(a.getIndice());
        a.setIndice(aux);
    }


    private int masChico(int a, int b){
        if(!indiceValido(b))
            return a;
        else if (arbol[a].compareTo(arbol[b]) <= 0)
            return a;
        return b;
    }



    private void acomodaArriba(int i){
        int padre = (i - 1)/2;
        int menor = i;

        if(indiceValido(padre) && arbol[padre].compareTo(arbol[i]) >= 0){
            menor = padre;
        }

        if(menor != i){
            T aux = arbol[i];

            arbol[i] = arbol[padre];
            arbol[i].setIndice(i);

            arbol[padre] = aux;
            arbol[padre].setIndice(padre);

            acomodaArriba(menor);
        }
    }

    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T e) {
        // Aquí va su código.
        if(elementos == arbol.length){
            T[] temp = nuevoArreglo(arbol.length*2);
            for(int i = 0; i < arbol.length; i++){
                temp[i] = arbol[i];
            } 
            this.arbol = temp;
        }
        
        arbol[elementos] = e;
        arbol[elementos].setIndice(elementos);
        acomodaArriba(elementos++);
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
        if(esVacia()) throw new IllegalStateException();
        T raiz = arbol[0];
        intercambiaElementos(raiz, arbol[elementos - 1]);
        elementos--;
        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        acomodaAbajo(0);
        return raiz;
    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        if(elemento == null) return;
        int indice = elemento.getIndice();
        intercambiaElementos(arbol[indice], arbol[elementos - 1]);
        elementos--;

        arbol[elementos].setIndice(-1);
        arbol[elementos] = null;
        reordena(arbol[indice]);

    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if(!indiceValido(elemento.getIndice())) return false;
        return arbol[elemento.getIndice()] == elemento;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return this.elementos == 0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        for(int i = 0; i < this.elementos; i++){
            arbol[i] = null;
        }
        this.elementos = 0;
    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.

        if(elemento == null) return;
        int indice = elemento.getIndice();

        acomodaArriba(indice);
        acomodaAbajo(indice);

    }

    /* Acomoda hacia abajo (min-heapify); ve si el nodo actual (i) es mayor que
     * alguno de sus hijos (2*i+1, 2*i+2). Si así es, reemplaza el nodo con el
     * hijo menor, y recursivamente hace lo mismo con el hijo menor (que tiene
     * el valor del que era su padre). */
    private void acomodaAbajo(int i) {
        // Aquí va su código.
        
        int izquierdo = (i * 2) + 1;
        int derecho = (i * 2) + 2;

        if (!indiceValido(izquierdo)&& !indiceValido(derecho))
            return;
        
            
        int menor = masChico(izquierdo, derecho);
        menor = masChico(i, menor);

        if(menor != i){
            T aux = arbol[i];

            arbol[i] = arbol[menor];
            arbol[i].setIndice(i);

            arbol[menor] = aux;
            arbol[menor].setIndice(menor);


            acomodaAbajo(menor);
        }
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if(!indiceValido(i)) throw new NoSuchElementException();
        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s = "";
        for(int i = 0; i < arbol.length; i++){
            s += arbol[i]+ ", ";
        }
        return s;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
        // Aquí va su código.
        if(elementos != monticulo.elementos) return false;
        for(int i = 0; i < elementos; i++){
            if(!arbol[i].equals(monticulo.arbol[i]))
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
        Lista<Adaptador<T>> listaAdaptadores = new Lista<>();
        
        for(T e : coleccion){
            listaAdaptadores.agrega(new Adaptador<T>(e));
        }

        Lista<T> listaOrenada = new Lista<>();

        MonticuloMinimo<Adaptador<T>> monticulo = new MonticuloMinimo<>(listaAdaptadores);

        while(!monticulo.esVacia()){
            Adaptador<T> elementoMinimo = monticulo.elimina();
            listaOrenada.agrega(elementoMinimo.elemento);
        }

        return listaOrenada;
        
    }
}
