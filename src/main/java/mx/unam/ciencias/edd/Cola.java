package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(super.esVacia()) return "";
        Nodo nodoIterativo = super.cabeza;
        String s = "";
        while(nodoIterativo != null){
            s += nodoIterativo.elemento + ",";
            nodoIterativo = nodoIterativo.siguiente;
        }
          return s;
    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
        if(elemento == null) throw new IllegalArgumentException();
        Nodo nodo = new Nodo(elemento);
        if(super.esVacia()){
            super.cabeza = nodo;
            super.rabo = nodo;
        }else{
            super.rabo.siguiente = nodo;
            super.rabo = nodo;
        }
    }
}
