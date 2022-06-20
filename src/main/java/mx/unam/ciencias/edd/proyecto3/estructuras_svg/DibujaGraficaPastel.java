package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto3.*;

/**
 * Clase genérica que extiende a {@link DibujaEstructuras} y establece como se va a dibujar una
 * grafica de pastel en SVG.
 * 
 * @author Julián Rosas Scull
 */
public class DibujaGraficaPastel<T extends Comparable<T>> extends DibujaEstructuras<T> {

    /**
     * Clase que envuelve una palabra como un punto en una grafica de pastel, el cual representara una
     * rebanada de la misma.
     */
    private class Punto{

        /* Coordenada X del punto */
        public double x;
        
        /* Coordenada Y del punto */
        public double y;
        
        /* Porcentaje del punto respecto al total de elementos */
        public double porcentaje;

        /* Elemento del punto */
        public String elemento; 
        
        /* Posicion en el eje X de la etiqueta del punto */
        public double posXetiqueta;
        
        /* Posicion en el eje Y de la etiqueta del punto */
        public double posYetiqueta;

        /**
         * Constrictor único de un punto.
         * @param x coordenada x del punto 
         * @param y coordenada y del punto
         * @param elemento elemento del punto
         * @param porcentaje porcentaje del punto 
         * @param posXetiqueta posicion en el eje X de la etiqueta del punto
         * @param posYetiqueta posicion en el eje Y de la etiqueta del punto
         */
        public Punto(double x, double y, String elemento, double porcentaje, double posXetiqueta, double posYetiqueta){
            this.x = x;
            this.y = y;
            this.porcentaje = porcentaje;
            this.elemento = elemento;
            this.posXetiqueta = posXetiqueta;
            this.posYetiqueta = posYetiqueta;
        }
    }
    
    /* Lista de palabras de una grafica de pastel  */
    private Lista<Palabra> listaElementos;

    /* Total de palabras en una grafica de pastel */
    private int totalElementos;

    /* Lista de puntos, los cuales se graficaran para hacer las rebanadas */
    private Lista<Punto> puntos;

    /* Radio del circulo */
    private final int RADIO = 200;

    /* El numero maximo de elementos en una grafica de pastel */
    private final int MAXIMO_ELEMENTOS = 10;
    
    /* Posicion inicial en el eje X de una grafica de pastel (el centro en el eje X de la grafica de pastel)*/
    private int xIni = 270;

    /* Posicion inicial en el eje Y de una grafica de pastel (el centro en el eje Y de la grafica de pastel)*/
    private int yIni = 270;


    /**
     * Constructor único de la gráfica de pastel que procesa una lista de palabras para ser graficadas.
     * @param listaElementos lista de palabras.
     * @param totalElementos total de elementos.
     */
    public DibujaGraficaPastel(Lista<Palabra> listaElementos, int totalElementos){
        this.totalElementos = totalElementos;
        puntos = new Lista<>();
        this.listaElementos = new Lista<>();

        Lista<Palabra> aux = Lista.mergeSort(listaElementos).reversa();

        if(aux.getLongitud() >= MAXIMO_ELEMENTOS){
            for(int i = 0; i < MAXIMO_ELEMENTOS; i++){
                this.listaElementos.agrega(aux.get(i));
            }
        }else{
            for(int i = 0; i < aux.getElementos(); i++){
                this.listaElementos.agrega(aux.get(i));
            }
        }


        double x = 0,y = 0, angulo = 0, angulo_aux = 0, angulo_etiqueta = 0, xEt = 0,yEt = 0, porcentaje = 0;

        for(Palabra e : this.listaElementos){
            angulo += (e.getApariciones()*360)/totalElementos;
            x = RADIO * Math.cos(Math.toRadians(angulo)) + xIni;
            y = RADIO * Math.cos(Math.toRadians(angulo)) + yIni;
            angulo_etiqueta = Math.ceil(angulo_aux) + (Math.ceil(angulo) - Math.floor(angulo_aux))/2;
            xEt = RADIO * Math.cos(Math.toRadians(angulo_etiqueta)) + xIni;
            yEt = RADIO * Math.sin(Math.toRadians(angulo_etiqueta)) + yIni;
            porcentaje = ((double)e.getApariciones()/totalElementos) * 100;
            puntos.agrega(new Punto(x, y, e.getPalabra(), Math.floor(porcentaje), xEt, yEt));
            angulo_aux = angulo;
        }
    }

    
    
    /**
     * Metodo auxiliar que dibuja las divisiones , de acuerdo a los porcentajes, de una grafica de 
     * pastel.
     * @param x coordenada x de la division
     * @param y coordenada y de la division
     * @return string - division
     */
    
    private String dibujaDivisiones(double x, double y){
        String s = "";

        s += String.format("<line x1='%d' y1='%d' x2='%f' y2='%f' style='stroke:white; stroke-width:5;'></line>\n", xIni, yIni, x, y);

        return s;
    }

    /**
     * Metodo auxiliar que genera la circunferencia 
     * @return string - circunferencia total sin divisiones
     */
    private String dibujaCircunferencia(){
        String s = "";
        s += String.format("<circle cx='%d' cy='%d' r='%d' stroke='black' stroke-width='3' fill='black'/>\n", xIni, yIni, RADIO);

        return s;
    }

    /**
     * Metodo auxliar que dibuja por cada punto, su etiqueta respectiva.
     * @param p punto 
     * @param color color del texto 
     * @return string - etiqueta 
     */
    private String dibujaEtiqueta(Punto p, String color, int i){
        double x = p.posXetiqueta;
        double y = p.posYetiqueta;

        String tam = "";
        if(p.elemento.length() > 9){
            tam = "10px";
        }else{
            tam = "15px";
        }
        String s = "";

        s += "<text x= '" + x + "' y= '" + y + "' text-anchor='middle' fill='" + color + "' font-size='" + tam
                + "' font-family='sans-serif' dy='.3em'>" +
                p.elemento + " - " + p.porcentaje + "% " + "</text>\n";

        return s;
            
    }

    /**
     * Impelementacion concreta del metodo que dibuja ,esta vez, una grafica 
     * de pastel. 
     */

    @Override 
    public String dibuja(){
        String s = "";

        s += empiezaSVG(500, 560);

        s += G_OPEN_TAG;
        s += dibujaCircunferencia();
        
        String[] colores = {"red", "green", "blue", "gray", "khaki"};
        int j = 0, i = 0;
        
        for(Punto p : puntos){
                s += dibujaDivisiones(p.posXetiqueta, p.posYetiqueta);
                s += dibujaEtiqueta(p, colores[j],i);
                if(j == colores.length - 1){
                    j = 0;
                }
                j++;
                i++;
        }

        s += G_CLOSE_TAG;
         
        s += SVG_CLOSE_TAG;

        return s;

    }
    
}
