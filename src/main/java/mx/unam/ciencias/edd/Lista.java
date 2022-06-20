package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            // Aquí va su código.
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return this.siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if(!hasNext()) throw new NoSuchElementException();

            this.anterior = this.siguiente;
            this.siguiente = this.siguiente.siguiente;
            return this.anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            return this.anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
            if(!hasPrevious()) throw new NoSuchElementException();

            this.siguiente = this.anterior;
            this.anterior = this.anterior.anterior;
            return this.siguiente.elemento;

        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            this.siguiente = cabeza;
            this.anterior = null;

        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            this.anterior = rabo;
            this.siguiente = null;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return this.longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return this.longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return this.cabeza == null && this.rabo == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException("No se puede agregar un nodo vacio");
        Nodo nodo = new Nodo(elemento);
        if(esVacia()){
            this.cabeza = nodo;
            this.rabo = this.cabeza;
            this.cabeza.anterior = null;
            this.rabo.siguiente = null;
        }else {
            this.rabo.siguiente = nodo;
            nodo.anterior = this.rabo;
            this.rabo = nodo;
            this.rabo.siguiente = null;
        }
        this.longitud++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException("No se puede agregar un nodo vacio");
        Nodo nodo = new Nodo(elemento);
        if(esVacia()){
            this.cabeza = nodo;
            this.rabo = this.cabeza;
        }else{
            this.cabeza.anterior = nodo;
            nodo.siguiente = this.cabeza;
            this.cabeza = nodo;
            nodo.anterior = null;
        }
        this.longitud++;

    }


    /**
    * Busca un nodo mediante un identificador y regresa dicho nodo
    * @param i indice del nodo requerido
    * @return Nodo - nodo representante del indice pasado
    */

    private Nodo regresaIesimoNodo(int i){
        Nodo nodo = this.cabeza;
        for(int j = 0; j < i && nodo != null; j++){
            nodo = nodo.siguiente;
        }
        return nodo;
    }


    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException();
        if(i < 1){
            agregaInicio(elemento);
        }else if(i >= this.longitud){
            agregaFinal(elemento);
        }else{
            Nodo nodo = new Nodo(elemento);
            Nodo nodoBuscado = regresaIesimoNodo(i);
            nodo.siguiente = nodoBuscado;
            nodo.anterior = nodoBuscado.anterior;
            nodoBuscado.anterior.siguiente = nodo;
            nodoBuscado.anterior = nodo;
            this.longitud++;
        }
    }

    /**
    * Busca un nodo mediante su elemento en la lista. Si el nodo no se encuentra, regresa null
    * de otro modo, regresa el nodo del elemento.
    * @param elemento el elemento del nodo a bsucar.
    * @return Nodo retorna null si no se encuentra, y el Nodo con el elemento a buscar si se encuentra.
    */

    private Nodo buscaNodo(T elemento){
        if(esVacia()) return null;
        Nodo nodoIterativo = this.cabeza;
        int i = 0;
        while(i < this.longitud){
            if(nodoIterativo.elemento.equals(elemento)){
                return nodoIterativo;
            }else{
                nodoIterativo = nodoIterativo.siguiente;
                i++;
            }
        }
        return null;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null) return;
        Nodo nodoAeliminar = buscaNodo(elemento);
        if(nodoAeliminar != null){
            if(this.longitud == 1){
                this.rabo = null;
                this.cabeza = this.rabo;

            }else if(this.cabeza.equals(nodoAeliminar)){
                this.cabeza = this.cabeza.siguiente;
                this.cabeza.anterior = null;

            }else if(this.rabo.equals(nodoAeliminar)){
                this.rabo = this.rabo.anterior;
                this.rabo.siguiente = null;

            }else{
                nodoAeliminar.anterior.siguiente = nodoAeliminar.siguiente;
                nodoAeliminar.siguiente.anterior = nodoAeliminar.anterior;

            }
            this.longitud--;

        }else{
            return;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(esVacia()) throw new NoSuchElementException("La lista es vacia");
        T elemento = this.cabeza.elemento;
        if(this.longitud == 1){
            this.cabeza = this.rabo = null;
        }else{
            this.cabeza = this.cabeza.siguiente;
            this.cabeza.anterior = null;
        }
        this.longitud--;

        return elemento;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(esVacia()) throw new NoSuchElementException("La lista es vacia");
        T elemento = this.rabo.elemento;
        if(this.longitud == 1){
            this.rabo = this.cabeza = null;
        }else{
          this.rabo = this.rabo.anterior;
          this.rabo.siguiente = null;
        }

        this.longitud--;
        return elemento;

    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Nodo nodoIterativo = this.cabeza;
        Lista<T> lista = new Lista<T>();
        if(esVacia()) return lista;
        while(nodoIterativo != null){
            lista.agregaInicio(nodoIterativo.elemento);
            nodoIterativo = nodoIterativo.siguiente;
        }
        return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        Nodo nodoIterativo = this.cabeza;
        Lista<T> lista = new Lista<T>();
        if(esVacia()) return lista;
        while (nodoIterativo != null) {
            lista.agregaFinal(nodoIterativo.elemento);
            nodoIterativo = nodoIterativo.siguiente;
        }
        return lista;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        this.cabeza = null;
        this.rabo = null;
        this.longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if(esVacia()) throw new NoSuchElementException("La lista es vacia");
        return this.cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if(esVacia()) throw new NoSuchElementException("La lista es vacia");
        return this.rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if(i < 0 || i >= this.longitud) throw new ExcepcionIndiceInvalido("el indice es menor a 0 o mayor a la longitud de la lista");

        T elemento = regresaIesimoNodo(i).elemento;
        return elemento;

    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        Nodo nodo = this.cabeza;
        int i = 0;
        while(nodo != null){
            if(nodo.elemento.equals(elemento)){
                return i;
            }else{
                nodo = nodo.siguiente;
                i++;
            }
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(esVacia()) return "[]";
        Nodo nodo = this.cabeza;
        String s = "[" + nodo.elemento;
        int i = 0;
        if(this.longitud == 1) return s += "]";
        do{
            nodo = nodo.siguiente;
            s += ", " + nodo.elemento;
            i++;
        }while(i < this.longitud - 1);
        return s + "]";
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.
        Nodo nodo1 = this.cabeza;
        Nodo nodo2 = lista.cabeza;
        if(this.longitud != lista.getLongitud()) return false;

        while(nodo1 != null && nodo2 != null){
            if(nodo1.elemento.equals(nodo2.elemento)){
                nodo1 = nodo1.siguiente;
                nodo2 = nodo2.siguiente;
            }else{
                return false;
            }
        }
        return nodo1 == null && nodo2 == null;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }


    private Lista<T> mezcla(Lista<T> lista1, Lista<T> lista2, Comparator<T> comparador){
        Lista<T> listaOrdenada = new Lista<T>();

        Nodo i = lista1.cabeza;
        Nodo j = lista2.cabeza;

        while(i != null && j != null){
            if(comparador.compare(i.elemento, j.elemento) <= 0){
                listaOrdenada.agrega(i.elemento);
                i = i.siguiente;
            }else{
                listaOrdenada.agrega(j.elemento);
                j = j.siguiente;
            }
        }
        if(i == null){
            while(j != null){
                listaOrdenada.agrega(j.elemento);
                j = j.siguiente;
            }
        }else{
            while(i != null){
                listaOrdenada.agrega(i.elemento);
                i = i.siguiente;
            }
        }
        return listaOrdenada;

    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        if(this.longitud <= 1) return copia();
        Lista<T> sublistaIzq = new Lista<T>();
        Lista<T> sublistaDer = new Lista<T>();
        Nodo nodoIterativo = this.cabeza;
        int i = 0;
        while(nodoIterativo != null){
            if(i < this.longitud/2){
                sublistaIzq.agrega(nodoIterativo.elemento);
            }else{
                sublistaDer.agrega(nodoIterativo.elemento);
            }
            nodoIterativo = nodoIterativo.siguiente;
            i++;
        }
        return mezcla(sublistaIzq.mergeSort(comparador), sublistaDer.mergeSort(comparador), comparador);
    }

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        Nodo nodoIterativo = this.cabeza;
        while(nodoIterativo != null){
            if(comparador.compare(nodoIterativo.elemento, elemento) == 0)
                return true;
            nodoIterativo = nodoIterativo.siguiente;
        }
        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
