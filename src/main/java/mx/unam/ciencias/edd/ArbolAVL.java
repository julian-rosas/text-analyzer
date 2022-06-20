package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
            this.altura = 0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return this.altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.

            
            String s = elemento.toString() + " " + this.altura + "/" + balance(this);
            return s;
             
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return(this.altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        // Aquí va su código.
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    private VerticeAVL verticeAVLCast(VerticeArbolBinario<T> vertice){
        VerticeAVL avl = (VerticeAVL)vertice;
        return avl;
    }

    private void calcularAltura(VerticeAVL v){
        VerticeAVL izq = verticeAVLCast(v.izquierdo);
        VerticeAVL der = verticeAVLCast(v.derecho);
        v.altura = 1 + Math.max(getAltura(izq),getAltura(der));
    }

   private int getAltura(VerticeAVL v){
       if(v == null) return -1;
        
       return v.altura;
   }

   private int balance(VerticeAVL v){

       return (getAltura(verticeAVLCast(v.izquierdo)) - getAltura(verticeAVLCast(v.derecho)));
   }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        rebalancea(verticeAVLCast(ultimoAgregado.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        if(elemento == null) return;

        VerticeAVL verticeAeliminar =  verticeAVLCast(super.busca(elemento));

        if(verticeAeliminar == null) return;

        elementos--;



        if(elementos == 0){
            raiz = null;
            ultimoAgregado = null;
            return;
        }

        
        if(verticeAeliminar.hayIzquierdo() && verticeAeliminar.hayDerecho()){
            verticeAeliminar = verticeAVLCast(super.intercambiaEliminable(verticeAeliminar));
            super.eliminaVertice(verticeAeliminar);
        }else{
            super.eliminaVertice(verticeAeliminar);
        }
        
        VerticeAVL padre = verticeAVLCast(verticeAeliminar.padre);
        
    
        rebalancea(padre);
      
    }

    private void rebalancea(VerticeAVL vertice){
        if(vertice == null) return;
        calcularAltura(vertice);
        int bal = balance(vertice);

        if(bal == -2){
            VerticeAVL q = verticeAVLCast(vertice.derecho);
            if(balance(q) == 1){
                super.giraDerecha(q);
                calcularAltura(q);
                calcularAltura(verticeAVLCast(q.padre));
            }
            
            if(balance(q) == 0 || balance(q) == -1 || balance(q) == -2){
                super.giraIzquierda(vertice);
                calcularAltura(vertice);
                calcularAltura(verticeAVLCast(vertice.padre));
            }

        }else if(bal == 2){
            VerticeAVL p = verticeAVLCast(vertice.izquierdo);
            if(balance(p) == -1){
                super.giraIzquierda(p);
                calcularAltura(p);
                calcularAltura(verticeAVLCast(p.padre));
            }

            if(balance(p) == 0 || balance(p) == 1 || balance(p) == 2){
                super.giraDerecha(vertice);
                calcularAltura(vertice);
                calcularAltura(verticeAVLCast(vertice.padre));
            }
        }

        rebalancea(verticeAVLCast(vertice.padre));

    }
    

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
