package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.*;

/**
 * Clase genérica que extiende a {@link DibujaArbolOrdenadoCompleto} y que sigue la 
 * "jerarquía" de que un arbol rojinegro es un arbol binario ordenado. Especifica
 * como debe ser generado un {@link ArbolRojinegro} en SVG.
 * 
 * @author Julián Rosas Scull
 */
public class DibujaArbolRojinegro<T extends Comparable<T>> extends DibujaArbolOrdenadoCompleto<T> {
    

    /**
     * Constructor único que define los elementos que va a tener el arbol rojinegro
     * y define (por medio del polimorfismo) el arbol como una instancia de {@link ArbolRojinegro}.
     * @param listaElementos
     * @param estructura
     */
    public DibujaArbolRojinegro(Lista<T> listaElementos, Estructuras estructura){
        super(listaElementos, estructura);
        arbol = new ArbolRojinegro<>(elementos);
    }


    /**
     * Método que establece las coordenadas de los vertices y como se van a dibujar (respecto al color).
     */
    @Override 
    protected String generaCoordenadasVertices(String colorVertice , VerticeArbolBinario<T> vertice, double decrementoX, int  posX ,int posY){
    
        String s = "";

        if (vertice == null)
            return s;

        if(vertice.toString().equals("N{" + vertice.get().toString() + "}")){
            s += dibujaVertice("black", posX, posY,
            vertice.get().toString(), null, false);
        }else if(vertice.toString().equals("R{" + vertice.get().toString() + "}")){
            s += dibujaVertice("red", posX, posY,
            vertice.get().toString(), null, false);
        }

        if (vertice.hayIzquierdo() && vertice.hayDerecho()) {
            s += generaCoordenadasVertices(vertice.izquierdo().toString(), vertice.izquierdo(), decrementoX / 2,
                    (int) Math.floor(posX - decrementoX) - 5, posY + 100) +
                    generaCoordenadasVertices(vertice.derecho().toString(), vertice.derecho(), decrementoX / 2,
                            (int) Math.floor(posX + decrementoX) + 5, posY + 100);
        } else if (vertice.hayIzquierdo()) {
            s += generaCoordenadasVertices(vertice.izquierdo().toString(), vertice.izquierdo(), decrementoX / 2,
                    (int) Math.floor(posX - decrementoX) - 5, posY + 100);
        } else if (vertice.hayDerecho()) {
            s += generaCoordenadasVertices(vertice.derecho().toString(), vertice.derecho(), decrementoX / 2,
                    (int) Math.floor(posX + decrementoX) + 5, posY + 100);
        }

        return s;
    
    }



    /**
     * Metodo nuevamente implementado concretamente que dibuja, esta vez Arboles Rojinegros.  
     *.
     */
    @Override
    public String dibuja() {
        String s = "";

        int profundidad = arbol.altura() == 0 ? 1 : arbol.altura();

        int altura = 100 + profundidad * 100;

        int ancho = 0;
        
        
        if(arbol.getElementos() <= 9){
            ancho = (int) (Math.pow(2, profundidad) * 20*3);
            s += empiezaSVG(altura, ancho);
            s += G_OPEN_TAG;
            if (!arbol.esVacia()) {
                s += generaCoordenadasAristas(arbol.raiz(), (ancho / 2) / 2, ancho / 2, 50);
                s += generaCoordenadasVertices(arbol.raiz().toString(), arbol.raiz(), (ancho / 2) / 2, ancho / 2, 50);
            }
        }else{
            ancho = (int) (Math.pow(2, profundidad) * 30);
            s += empiezaSVG(altura, ancho);
            s += G_OPEN_TAG;
            if (!arbol.esVacia()) {
                s += generaCoordenadasAristas(arbol.raiz(), (ancho / 2) / 2 , ancho / 2 + 30 , 50);
                s += generaCoordenadasVertices(arbol.raiz().toString(), arbol.raiz(), (ancho / 2) / 2, ancho / 2 + 30, 50);
            }
        }

        s += G_CLOSE_TAG;
        s += SVG_CLOSE_TAG;

        return s;

    }
}
