package mx.unam.ciencias.edd.proyecto3.estructuras_svg;

import mx.unam.ciencias.edd.*;

/**
 * Clase abstracta genérica que extiende a {@link DibujaEstructuras} que establece los
 * requerimientos mínimos para dibujar un árbol en SVG, y así, ser implementada concretamente.
 * 
 * @author Julián Rosas Scull
 */
public abstract class DibujaArbol <T extends Comparable<T>> extends DibujaEstructuras<T>{

    /** {@link ArbolBinario} que va a ser extendido por alguno de sus subtipos */
    protected ArbolBinario<T> arbol;

    /**
     *  Método abstracto que va a decidir como va a ser la implementación del dibujado de 
     * un vértice 
     * @param colorVertice Color del que va a ser el vértice.
     * @param posX Posición en el eje X del vértice.
     * @param posY Posición en el eje Y del vértice.
     * @param text Texto del elemento del vértice.
     * @param balance Balance del vértice (en caso de ser AVL).
     * @param AVL Bandera que referencía a si el arbol es AVL o no.
     * @return
     */
    protected abstract String dibujaVertice(String colorVertice, int posX, int posY, String text, String balance, boolean AVL);


    /**
     * Método que establece como se van a dibujar las aristas de un {@link ArbolBinario}.
     * @param x1 conexión al primer extremo de la arista en el eje X.
     * @param y1 conexión al primer extremo de la arista en el eje Y.
     * @param x2 conexión al segundo extremo de la arista en el eje X.
     * @param y2 conexión al segundo extremo de la arista en el eje Y.
     * @return Cadena con la etiqueta de la arista de dos nodos generada. 
     */
    protected String dibujaArista(int x1 , int y1, int x2, int y2){
        return String.format("<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='3'/>\n", x1, y1, x2, y2);
    }

}
