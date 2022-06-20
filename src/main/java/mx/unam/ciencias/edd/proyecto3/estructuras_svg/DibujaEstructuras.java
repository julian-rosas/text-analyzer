package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.*;

/**
 * Clase abstracta genérica (que extiende a {@link Comparable} con la finalidad de poder llamar nuestras estructuras de datos genéricas)
 * la cual establece los elementos básicos que debe contener una
 * figura SVG.
 * 
 * @author Julián Rosas Scull
 */
public abstract class DibujaEstructuras<T extends Comparable<T>>  {
    
    /** Establece el encabezado del SVG, el cual siempre es el mismo */
    protected static final String XML_PROLOG = "<?xml version='1.0' encoding='UTF-8' ?>\n";

    /** Establece la etiqueta de abertura del svg */
    protected String SVG_OPEN_TAG = "<svg>\n";
    
    /** Estabelece la etquieta de cerradura del svg */
    protected String SVG_CLOSE_TAG = "</svg>\n";
    
    /** Establece la etiqueta de abertura de g */
    protected String G_OPEN_TAG = "<g>\n";
    
    /** Establece la etiqueta de cerradura de g */
    protected String G_CLOSE_TAG = "</g>\n";
    

    /** Establece la {@link Lista} de los elementos (enteros) pasados por el usuario */
    protected Lista<T> elementos;

    /**
     * Método abstracto que es implementado por las clases concretas 
     * (las cuales dibujan una estructura persé) el cual genera el SVG de la 
     * figura en cuestión.
     * @return String Cadena con el SVG de la estructura de datos.
    */
    public abstract String dibuja();

    /**
     * Genera el encabezado completo obligatorio de cualquier SVG, en particular 
     * las medidas del recuadro donde se encuentra la estructura de datos dibujada.
     * @param altura altura del recuadro.
     * @param ancho ancho del recuadro.
     * @return String Cadena con el encabezado definido del SVG.
     */
    public String empiezaSVG(double altura, double ancho){
        String s = XML_PROLOG;
        return s += String.format("<svg height='%f' width='%f' xmlns='http://www.w3.org/2000/svg' fill-rule='evenodd' clip-rule='evenodd'>\n",altura, ancho);
    }
}

