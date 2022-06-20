package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */

public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
            this.pila = new Pila<Vertice>();
            Vertice verticeIterativo = raiz;
            while(verticeIterativo != null){
                this.pila.mete(verticeIterativo);
                verticeIterativo = verticeIterativo.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !this.pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.
            Vertice verticeAsacar = this.pila.saca();
            Vertice verticeAsacarDerecho = verticeAsacar.derecho;
            while(verticeAsacarDerecho != null){
                this.pila.mete(verticeAsacarDerecho);
                verticeAsacarDerecho = verticeAsacarDerecho.izquierdo;
            }
            return verticeAsacar.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException();
        Vertice verticeAagregar = nuevoVertice(elemento);
        super.elementos++;
        if(super.esVacia()){
            super.raiz = verticeAagregar;
            super.raiz.padre = null;
            this.ultimoAgregado = verticeAagregar;
            return;
        }
        agrega(this.raiz, verticeAagregar);
    }


    private void agrega(Vertice vertice, Vertice verticeAagregar){
        if(vertice.elemento.compareTo(verticeAagregar.elemento) >= 0){
              if(!vertice.hayIzquierdo()){
                  vertice.izquierdo = verticeAagregar;
                  verticeAagregar.padre = vertice;
              }else{
                  agrega(vertice.izquierdo, verticeAagregar);
              }
        }else{
            if(!vertice.hayDerecho()){
                vertice.derecho = verticeAagregar;
                verticeAagregar.padre = vertice;
            }else{
                agrega(vertice.derecho, verticeAagregar);
            }
        }
        this.ultimoAgregado = verticeAagregar;
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException();

        Vertice verticeAbuscar = vertice(busca(elemento));
        if(verticeAbuscar == null) return;

        this.elementos--;

        if(super.elementos == 0){
            super.raiz = null;
            return;
        }

        if(verticeAbuscar.hayIzquierdo() && verticeAbuscar.hayDerecho()){
            Vertice eliminar = intercambiaEliminable(verticeAbuscar);
            eliminaVertice(eliminar);
        }else{
                eliminaVertice(verticeAbuscar);
        }
    }

    protected Vertice maximoEnSubArbol(Vertice vertice){
        if(!vertice.hayDerecho())
            return vertice;
        return maximoEnSubArbol(vertice.derecho);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.

        Vertice verticeMaximoSubarbol = maximoEnSubArbol(vertice.izquierdo);

        T elementoMaximo = verticeMaximoSubarbol.elemento;
        vertice.elemento = elementoMaximo;


        return verticeMaximoSubarbol;

    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código.

        Vertice hijo = null;

        if(!vertice.hayIzquierdo()){
            hijo = vertice.derecho;
        }else{
            hijo = vertice.izquierdo;
        }

        Vertice padre = vertice.padre;

        if(vertice.hayPadre()){
            if(vertice == padre.izquierdo){
                padre.izquierdo = hijo;
            }else{
                padre.derecho = hijo;
            }
        }else{
            raiz = hijo;
        }

        if(hijo != null){
            hijo.padre = padre;
        } 
    }


    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException();
        return busca(elemento, super.raiz);
    }


    private VerticeArbolBinario<T> busca(T elemento, Vertice vertice){
        if(vertice == null) return null;
        if(elemento.compareTo(vertice.elemento) == 0)
            return vertice;
        if(elemento.compareTo(vertice.elemento) > 0)
            return busca(elemento, vertice.derecho);
        return busca(elemento, vertice.izquierdo);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        // Aquí va su código.
        return this.ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice == null || !vertice.hayIzquierdo() || super.esVacia()) return;
        Vertice verticeAgirar = super.vertice(vertice);

        Vertice verticeIzquierdo = verticeAgirar.izquierdo;

        verticeIzquierdo.padre = verticeAgirar.padre;

        if(verticeAgirar != raiz){
            if(verticeAgirar == verticeAgirar.padre.izquierdo)
                verticeIzquierdo.padre.izquierdo = verticeIzquierdo;
            else
                verticeIzquierdo.padre.derecho = verticeIzquierdo;
        }else{
            super.raiz = verticeIzquierdo;
        }

        verticeAgirar.izquierdo = verticeIzquierdo.derecho;
        if(verticeIzquierdo.hayDerecho()){
            verticeIzquierdo.derecho.padre = verticeAgirar;
        }
        verticeIzquierdo.derecho = verticeAgirar;
        verticeAgirar.padre = verticeIzquierdo;

    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice == null || !vertice.hayDerecho() || super.esVacia()) return;

        Vertice verticeAgirar = super.vertice(vertice);

        Vertice verticeDerecho = verticeAgirar.derecho;

        verticeDerecho.padre = verticeAgirar.padre;

        if(verticeAgirar != raiz){
            if(verticeAgirar == verticeAgirar.padre.izquierdo){
                verticeDerecho.padre.izquierdo = verticeDerecho;
            }else{
                verticeDerecho.padre.derecho = verticeDerecho;
            }
        }else{
            super.raiz = verticeDerecho;
        }

        verticeAgirar.derecho = verticeDerecho.izquierdo;
        if(verticeDerecho.hayIzquierdo())
            verticeDerecho.izquierdo.padre = verticeAgirar;
        verticeDerecho.izquierdo = verticeAgirar;
        verticeAgirar.padre = verticeDerecho;
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(super.esVacia()) return;
        Vertice verticeIterativo = super.raiz;
        dfsPreOrder(verticeIterativo, accion);
    }

    private void dfsPreOrder(Vertice vertice, AccionVerticeArbolBinario<T> accion){
        if(vertice == null) return;
        accion.actua(vertice);
        dfsPreOrder(vertice.izquierdo, accion);
        dfsPreOrder(vertice.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(super.esVacia()) return;
        Vertice verticeIterativo = super.raiz;
        dfsInOrder(verticeIterativo, accion);
    }

    private void dfsInOrder(Vertice vertice, AccionVerticeArbolBinario<T> accion){
        if(vertice == null) return;
        dfsInOrder(vertice.izquierdo,accion);
        accion.actua(vertice);
        dfsInOrder(vertice.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(super.esVacia()) return;
        Vertice verticeIterativo = super.raiz;
        dfsPostOrder(verticeIterativo, accion);
    }

    private void dfsPostOrder(Vertice vertice, AccionVerticeArbolBinario<T> accion){
        if(vertice == null) return;
        dfsPostOrder(vertice.izquierdo, accion);
        dfsPostOrder(vertice.derecho, accion);
        accion.actua(vertice);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
