package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.*;

/**
 * Clase genérica que implementa concretamente a {@link DibujaArbol} de manera que se pueden dibujar
 * arboles binarios completos o arboles binarios ordenados en SVG.
 * 
 * @author Julián Rosas Scull
 */
public class DibujaArbolOrdenadoCompleto<T extends Comparable<T>> extends DibujaArbol<T>{

    /**
     * Constructor único que establece los elementos del arbol y el tipo de árbol. 
     * @param listaElementos {@link Lista} de elementos de árbol.
     * @param estructura Tipo de árbol que puede ser , ya sea {@link ArbolBinarioCompleto} o {@link ArbolBinarioOrdenado}.
     */
    public DibujaArbolOrdenadoCompleto(Lista<T> listaElementos, Estructuras estructura){
        this.elementos = listaElementos;

        if(estructura == Estructuras.ARBOL_COMPLETO){
            arbol = new ArbolBinarioCompleto<T>(listaElementos);
        }else if(estructura == Estructuras.ARBOL_ORDENADO){
            arbol = new ArbolBinarioOrdenado<T>(listaElementos);
        }

    }

    /**
     * Implementación concreta del método el cual decide como generar la etiqueta del 
     * vértice ,del texto del mismo, y el balance en dado caso que el árbol sea de tipo
     * AVL.
     */
    @Override
    public String dibujaVertice(String colorVertice, int posX, int posY, String text, String balance, boolean AVL){
        String circle_tag = String.format("<circle cx='%d' cy='%d' r='%d' fill='%s' stroke-width='%d'/>\n",
        posX,
        posY,
        20,
        colorVertice,
        10);

        String text_tag = String.format("<text fill='%s' font-family='sans-serif' font-size='%d' x='%d' y='%d' text-anchor='middle'>%s</text>\n",
        colorVertice.equals("black") ? "white" : "black",
        11,
        posX,
        posY + 5,
        text);
       
        if (AVL) {
            String balance_tag = String.format("<text x='%d' y='%d' fill='%s' font-family='sans-serif' font-size='%d' text-anchor='middle'>%s</text>\n",
            posX - 35,
            posY + 15,
            "black",
            12,
            balance);

            return circle_tag + text_tag + balance_tag; 
        }
        

        return circle_tag + text_tag;
    }

    /**
     * Metodo que decide cuales van a ser las coordenadas de los vertices del arbol.
     * @param colorVertice Color del vertice a generar.
     * @param vertice {@link VerticeArbolBinario} que se va a dibujar.
     * @param decrementoX Decremento en el caso de que sea hijo izquierdo respecto al padre, 
     *                    incremento en el caso de que sea hijo derecho respecto al padre en el eje X.
     * @param posX Posición en el eje X del vértice-
     * @param posY Posición en el eje Y del vértice.
     * @return cadena con el vértice creado, auxiliado con {@link #dibujaVertice(String, int, int, String, String, boolean)}
     */
    protected String generaCoordenadasVertices(String colorVertice , VerticeArbolBinario<T> vertice, double decrementoX, int  posX ,int posY){
        String s = "";

        if(vertice == null) return s;
        
        s += dibujaVertice(colorVertice, posX, posY, vertice.get().toString(), null, false);

        if(vertice.hayIzquierdo() && vertice.hayDerecho()){
            s += generaCoordenadasVertices(colorVertice,vertice.izquierdo(), decrementoX/2, (int)Math.floor(posX - decrementoX) - 5, posY + 100) +
            generaCoordenadasVertices(colorVertice,vertice.derecho(), decrementoX/2, (int)Math.floor(posX + decrementoX) + 5, posY + 100);
        }else if(vertice.hayIzquierdo()){
            s += generaCoordenadasVertices(colorVertice,vertice.izquierdo(), decrementoX/2, (int)Math.floor(posX - decrementoX) - 5, posY + 100);
        }else if(vertice.hayDerecho()){
            s += generaCoordenadasVertices(colorVertice,vertice.derecho(), decrementoX/2, (int)Math.floor(posX + decrementoX) + 5, posY + 100);
        }

        return s;

    }

    /**
     * Metodo que decide cuales van a ser las coordenadas de las aristas del arbol.
     * 
     * @param vertice {@link VerticeArbolBinario} que va a estar conectado a una arista.
     * @param decrementoX Decremento en el eje X en caso de que sea hijo izquierdo el vértice, incremento
     *                    en el caso de que sea hijo derecho el vértice.
     * @param posX Posición en el eje X de la arista con respecto al vértice.
     * @param posY Posición en el eje Y de la arista con respecto al vértice.
     * @return
     */
    protected String generaCoordenadasAristas(VerticeArbolBinario<T> vertice, double decrementoX, int posX, int posY){
        String s = "";

        if(vertice == null) return s;

        if(vertice.hayIzquierdo() && vertice.hayDerecho()){
            s += dibujaArista(posX, posY, (int)Math.floor(posX - decrementoX) - 5, posY + 100)+
            dibujaArista(posX, posY, (int)Math.floor(posX + decrementoX) + 5, posY + 100) +
            generaCoordenadasAristas(vertice.izquierdo(), decrementoX/2, (int)Math.floor(posX - decrementoX) - 5, posY + 100) +
            generaCoordenadasAristas(vertice.derecho(), decrementoX/2, (int)Math.floor(posX + decrementoX) + 5, posY + 100);
        }else if(vertice.hayIzquierdo()){
            s += dibujaArista(posX, posY, (int)Math.floor(posX - decrementoX) - 5, posY + 100)+
            generaCoordenadasAristas(vertice.izquierdo(), decrementoX/2, (int)Math.floor(posX - decrementoX) - 5, posY + 100);
        }else if(vertice.hayDerecho()){
            s += dibujaArista(posX, posY, (int)Math.floor(posX + decrementoX) + 5, posY + 100) +
            generaCoordenadasAristas(vertice.derecho(), decrementoX / 2, (int) Math.floor(posX + decrementoX) + 5,posY + 100);
        }

        return s;
    }

    /**
     * Implementación concreta del método que dibuja, esta vez, Arboles Ordenados o 
     * Arboles Completos.
     */
    @Override 
    public String dibuja() {
        String s = "";

        int profundidad = arbol.altura() == 0 ? 1 : arbol.altura();

        int altura = 100 + profundidad*100;

        int ancho = (int)(Math.pow(2, profundidad) * 20 * 4);
        
        
        s += empiezaSVG(altura, ancho);
        s += G_OPEN_TAG;

        if(!arbol.esVacia()){
            s += generaCoordenadasAristas(arbol.raiz(), (ancho/2)/2, ancho/2, 50);
            s += generaCoordenadasVertices("white",arbol.raiz(), (ancho/2)/2, ancho/2, 50);
        }

        s += G_CLOSE_TAG;
        s += SVG_CLOSE_TAG;

        return s;



    }

}
