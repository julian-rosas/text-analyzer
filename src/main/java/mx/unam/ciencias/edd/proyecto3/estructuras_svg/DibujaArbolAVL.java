package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.*;


/**
 * Clase genérica que extiende a {@link DibujaArbolOrdenadoCompleto} y establece
 * como se vaa ver un {@link ArbolAVL} en SVG.
 * 
 * @author Julián Rosas Scull
 */
public class DibujaArbolAVL<T extends Comparable<T>> extends DibujaArbolOrdenadoCompleto<T> {
    

    /**
     * Constructor único que invoca al de la super clase y define el arbol 
     * (mediante polimorfismo) como {@link ArbolAVL}.
     * @param listaElementos
     * @param estructuras
     */
    public DibujaArbolAVL(Lista<T> listaElementos, Estructuras estructuras){
        super(listaElementos, estructuras);
        arbol = new ArbolAVL<>(elementos);
    }

    /**
     * Metodo auxiliar que detecta donde se encuentra el balance en el {@link ArbolAVL#toString()} del vértice.
     * @param toStringVertice el {@link ArbolAVL#toString()} del vértice.
     * @return balance del vértice.
     */
    private String getBalanceVertice(String toStringVertice){
        String balance = "";

        int barra = toStringVertice.indexOf('/');

        balance = toStringVertice.substring(barra - 1, barra + 2);

        return balance;
    }

    /**
     * Metodo que genera las coordenadas de los vertices de los arboles avl con ayuda
     * del metodo auxiliar {@link #getBalanceVertice(String)}.
     */
    @Override
    protected String generaCoordenadasVertices(String colorVertice , VerticeArbolBinario<T> vertice, double decrementoX, int  posX ,int posY){
        String s = "";

        if (vertice == null) return s;

        String balance = getBalanceVertice(vertice.toString());

        s += dibujaVertice(colorVertice, posX, posY, vertice.get().toString(), balance, true);
       
        if (vertice.hayIzquierdo() && vertice.hayDerecho()) {
            s += generaCoordenadasVertices(colorVertice, vertice.izquierdo(), decrementoX / 2,
                    (int) Math.floor(posX - decrementoX) - 5, posY + 100) +
                    generaCoordenadasVertices(colorVertice, vertice.derecho(), decrementoX / 2,
                            (int) Math.floor(posX + decrementoX) + 5, posY + 100);
        } else if (vertice.hayIzquierdo()) {
            s += generaCoordenadasVertices(colorVertice, vertice.izquierdo(), decrementoX / 2,
                    (int) Math.floor(posX - decrementoX) - 5, posY + 100);
        } else if (vertice.hayDerecho()) {
            s += generaCoordenadasVertices(colorVertice, vertice.derecho(), decrementoX / 2,
                    (int) Math.floor(posX + decrementoX) + 5, posY + 100);
        }

        return s;
    }
    
    /**
     * Implementación concreta del método de {@link DibujaEstructura} que dibuja, esta vez 
     * arboles AVL. 
     */
    @Override
    public String dibuja() {
        String s = "";
        int profundidad = arbol.altura() == 0 ? 1 : arbol.altura();

        int altura = 100 + profundidad * 100;

        int ancho = (int) (Math.pow(2, profundidad) * 20 * 4);

        s += empiezaSVG(altura, ancho);
        s += G_OPEN_TAG;

        if (!arbol.esVacia()) {
            s += generaCoordenadasAristas(arbol.raiz(), (ancho / 2) / 2, ancho / 2, 50);
            s += generaCoordenadasVertices("white", arbol.raiz(), (ancho / 2) / 2, ancho / 2, 50);
        }

        s += G_CLOSE_TAG;
        s += SVG_CLOSE_TAG;

        return s;

    }


}
