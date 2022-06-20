package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(super.esVacia()) return "";
        Nodo nodoIterativo = super.cabeza;
        String s = "";
        while(nodoIterativo != null){
            s += nodoIterativo.elemento + "\n";
            nodoIterativo = nodoIterativo.siguiente;
        }
        return s;
    }

    /**
     * Agrega un elemento al tope de la pila.
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
            nodo.siguiente = super.cabeza;
            super.cabeza = nodo;
        }
    }
}
