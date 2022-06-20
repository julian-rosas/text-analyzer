package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            this.color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            // Aquí va su código.
            String s = "";
            if(this.color == Color.NEGRO){
                s += "N{" + this.elemento + "}";
            }else if(this.color == Color.ROJO)
                s += "R{" + this.elemento + "}";
            return s;
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
            if(this.color != vertice.color) return false;
            return super.equals(objeto);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }


    /**
     * Metodo para facilitar los casts entre VerticeRojinegro y sus super clases Vertice y VerticeARbolBinario
     */
    private VerticeRojinegro verticeRojinegroCast(VerticeArbolBinario<T> vertice){
         VerticeRojinegro vRoj = (VerticeRojinegro)vertice;
         return vRoj;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if(vertice.getClass() != VerticeRojinegro.class) throw new ClassCastException();
        VerticeRojinegro vR = (VerticeRojinegro)vertice;
        return vR.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        
        super.agrega(elemento);
        VerticeRojinegro vR = verticeRojinegroCast(ultimoAgregado);
        vR.color = Color.ROJO;
        rebalanceoAgrega(vR);
        
    }

    private void rebalanceoAgrega(VerticeRojinegro vertice){
        
        //caso 1
        if(!vertice.hayPadre()){
            vertice.color = Color.NEGRO;
            return;
        }

        //caso 2
        VerticeRojinegro padre =  getPadre(vertice);
        if(esNegro(padre))
            return;


        //caso 3
        VerticeRojinegro abuelo = getAbuelo(vertice);

        VerticeRojinegro tio = getTio(vertice);

        if(esRojo(tio)){
            tio.color = Color.NEGRO;
            padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            rebalanceoAgrega(abuelo);
        }else{

            VerticeRojinegro auxpadre = padre;

            //Caso 4
            if(abuelo.izquierdo == padre && padre.derecho == vertice){
                super.giraIzquierda(padre);
                padre = vertice;
                vertice = auxpadre;
            }else if(abuelo.derecho == padre && padre.izquierdo == vertice){
                super.giraDerecha(padre);
                padre = vertice;
                vertice = auxpadre;
            }

            // Caso 5
            padre.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            if(vertice == padre.izquierdo)
                super.giraDerecha(abuelo);
            else
                super.giraIzquierda(abuelo);
        }
    }


    private boolean esNegro(VerticeRojinegro vertice){
        return(vertice == null || vertice.color == Color.NEGRO);
    }



    private boolean esRojo(VerticeRojinegro vertice){
        return(vertice != null && vertice.color == Color.ROJO);
    }



    private VerticeRojinegro getPadre(VerticeRojinegro vertice){
        VerticeRojinegro padre = verticeRojinegroCast(vertice.padre);
        return padre;
    }



    private VerticeRojinegro getTio(VerticeRojinegro vertice){
        VerticeRojinegro abuelo = getAbuelo(vertice);
        if(vertice.padre == abuelo.izquierdo)
            return verticeRojinegroCast(abuelo.derecho);
        else
            return verticeRojinegroCast(abuelo.izquierdo);
    }



    private VerticeRojinegro getAbuelo(VerticeRojinegro vertice){
        VerticeRojinegro padre = verticeRojinegroCast(vertice.padre);
        return getPadre(padre);
    }

    private VerticeRojinegro getHermano(VerticeRojinegro vertice){
        VerticeRojinegro padre = getPadre(vertice);

        if(vertice == padre.izquierdo){
            return verticeRojinegroCast(padre.derecho);
        }else{
            return verticeRojinegroCast(padre.izquierdo);
        }
    }


    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeRojinegro verticeAeliminar = verticeRojinegroCast(super.busca(elemento));
        if(verticeAeliminar == null) return;

        elementos--;

        if(elementos == 0){
            raiz = null;
            return;
        }
        

        if(verticeAeliminar.hayIzquierdo() && verticeAeliminar.hayDerecho()){

            Vertice eliminable = super.intercambiaEliminable(verticeAeliminar);
            verticeAeliminar = verticeRojinegroCast(eliminable);
        }

        VerticeRojinegro fantasma = null;

        if(!verticeAeliminar.hayIzquierdo() && !verticeAeliminar.hayDerecho()){
            fantasma = (VerticeRojinegro)nuevoVertice(null);
            fantasma.color = Color. NEGRO;
            verticeAeliminar.izquierdo = fantasma;
            fantasma.padre = verticeAeliminar;
        }



        VerticeRojinegro hijo; 

        if(verticeAeliminar.hayIzquierdo()){
            hijo = verticeRojinegroCast(verticeAeliminar.izquierdo);
        }else{
            hijo = verticeRojinegroCast(verticeAeliminar.derecho);
        }

        eliminaVertice(verticeAeliminar);


        if(esRojo(hijo)){
            hijo.color = Color.NEGRO;
            return;
        }
        
        if(esNegro(verticeAeliminar) && esNegro(hijo)){
            rebalanceoElimina(hijo);
        }

        if(fantasma != null){
            eliminaVertice(fantasma);
        }


    }






    private void rebalanceoElimina(VerticeRojinegro vertice){
        //Caso 1


        if(!vertice.hayPadre()){
            return;
        }


        //referencias

        VerticeRojinegro padre =  verticeRojinegroCast(vertice.padre);
        VerticeRojinegro hermano = getHermano(vertice);


        //caso 2
        if(esRojo(hermano)){
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;

            if(vertice == padre.derecho){
                super.giraDerecha(padre);
            }else{
                super.giraIzquierda(padre);
            }

            //referencias 2

            padre = getPadre(vertice);

            hermano = getHermano(vertice);
        
        }

        // referencias 3

        VerticeRojinegro hi =  verticeRojinegroCast(hermano.izquierdo);

        VerticeRojinegro hd = verticeRojinegroCast(hermano.derecho);

        //caso 3

        if(esNegro(padre) && esNegro(hermano) && esNegro(hi) && esNegro(hd)){
            hermano.color = Color.ROJO;
            rebalanceoElimina(padre);
            return;
        }

        //caso 4


        if(esNegro(hermano) && esNegro(hi) && esNegro(hd) && esRojo(padre)){
            hermano.color = Color.ROJO;
            padre.color = Color.NEGRO;
            return;
        }


        //caso 5


        if(padre.izquierdo == vertice && esRojo(hi) && esNegro(hd)){
            hermano.color = Color.ROJO;
            hi.color = Color.NEGRO;
            super.giraDerecha(hermano);
            padre =  getPadre(vertice);
            hermano = getHermano(vertice);
        }

        if(padre.derecho == vertice && esNegro(hi) && esRojo(hd)){
            hermano.color = Color.ROJO;
            hd.color = Color.NEGRO;
            super.giraIzquierda(hermano);
            padre = getPadre(vertice);
            hermano = getHermano(vertice);
        }

        //referencias 4

        hi = verticeRojinegroCast(hermano.izquierdo);
        hd = verticeRojinegroCast(hermano.derecho);

        //caso 6

        hermano.color = padre.color;
        padre.color = Color.NEGRO;
        if(padre.derecho == vertice && esRojo(hi)){
            hi.color = Color.NEGRO;
            super.giraDerecha(padre);
        }else{
            hd.color = Color.NEGRO;
            super.giraIzquierda(padre);
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
