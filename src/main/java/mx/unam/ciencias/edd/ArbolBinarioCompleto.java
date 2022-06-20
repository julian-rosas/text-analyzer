package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
            this.cola = new Cola<Vertice>();
            if(!esVacia()) this.cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !this.cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            Vertice vertice = this.cola.saca();
            if(vertice.hayIzquierdo()) this.cola.mete(vertice.izquierdo);
            if(vertice.hayDerecho()) this.cola.mete(vertice.derecho);
            return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException();
        Vertice vertice = super.nuevoVertice(elemento);
        super.elementos++;

        if(super.esVacia()){
          super.raiz = vertice;
          return;
       }

        Cola<Vertice> cola = new Cola<>();
        cola.mete(super.raiz);
        while(!cola.esVacia()){
            Vertice verticeIterativo = cola.saca();
            if(!verticeIterativo.hayIzquierdo()){
                verticeIterativo.izquierdo = vertice;
                vertice.padre = verticeIterativo;
                return;
            }
            cola.mete(verticeIterativo.izquierdo);
            if(!verticeIterativo.hayDerecho()){
                verticeIterativo.derecho = vertice;
                vertice.padre = verticeIterativo;
                return;
            }
            cola.mete(verticeIterativo.derecho);
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento == null)
        return;

        Vertice verticeAeliminar = vertice(busca(elemento));
        if(verticeAeliminar == null)
            return;
        super.elementos--;
        if(super.elementos == 0){
            super.raiz = null;
            return;
        }
        Vertice verticeUltimo = getUltimoBFS();
        intercambia(verticeUltimo, verticeAeliminar);
        Vertice padre = verticeUltimo.padre;
        if(padre.izquierdo.elemento.equals(elemento))
            padre.izquierdo = null;
        else{
            padre.derecho = null;
        }  
        verticeUltimo.padre = null;
    }
        

    private void intercambia(Vertice v1, Vertice v2){
        T elementoAcambiar = v1.elemento;
        v1.elemento = v2.elemento;
        v2.elemento = elementoAcambiar;
    }


    private Vertice getUltimoBFS(){
        Cola<Vertice> cola = new Cola<Vertice>();
        Vertice verticeIterativo = super.raiz;
        cola.mete(verticeIterativo);
        while(!cola.esVacia()){
            verticeIterativo = cola.saca();
            if(verticeIterativo.hayIzquierdo())
                cola.mete(verticeIterativo.izquierdo);
            if(verticeIterativo.hayDerecho())
                cola.mete(verticeIterativo.derecho);
        }
        return verticeIterativo;
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
        if(super.esVacia()) return -1;
        return (int)Math.floor(Math.log(super.elementos)/Math.log(2));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(super.esVacia()) return;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(super.raiz);
        while(!cola.esVacia()){
            Vertice vertice = cola.saca();
            accion.actua(vertice);
            if(vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if(vertice.hayDerecho())
                cola.mete(vertice.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
