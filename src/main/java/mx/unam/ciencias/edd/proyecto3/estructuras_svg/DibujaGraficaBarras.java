package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.proyecto3.*;
import mx.unam.ciencias.edd.*;

/**
 * Clase genérica que extiende a {@link DibujaEstructuras} que establece como se va a dibujar una
 * gráfica de barras en SVG.
 * 
 * @author Julián Rosas Scull.
 */
public class DibujaGraficaBarras<T extends Comparable<T>> extends DibujaEstructuras<T>{

    /**
     * Clase auxiliar que envuelve una palabra como punto en una grafica de barras.
     */
    private class Punto{
        /*coordenada x de un punto */
        private double x;

        /* coordenada y de un punto */
        private double y;

        /* elemento de un punto */
        private String elemento;

        /* porcentaje de un punto */
        private double porcentaje;

        /* altura de la barra de un punto */
        private double altura;
        /**
         * Constructor de un punto
         * @param x coordenada x del punto.
         * @param y coordenada y del punto
         * @param elemento elemento del punto.
         * @param porcentaje porcentaje del punto.
         * @param altura altura del punto (de su barra).
         */
        public Punto(double x, double y, String elemento, double porcentaje, double altura){
            this.x = x;
            this.y = y;
            this.elemento = elemento;
            this.porcentaje = porcentaje;
            this.altura = altura;
        }
    }

    /* Lista de palabras de la grafica */
    private Lista<Palabra> listaElementos;

    /* total de palabras */
    private int totalPalabras;

    /* lista de puntos */
    private Lista<Punto> puntos;

    /* coordenada x inicial donde inicia la grafica  */
    private final int xIni = 20;

    /* coordenada y inicial donde inicia la grafica */
    private final int yIni = 350;

    /* longitud maxima de altura de una barra */
    private final int MAX_ALTURA_BARRAS = 1200;

    /* longitud maxima de ancho de una barra */
    private final int MAX_ANCHO_BARRAS = 800;

    /* ancho de una barra. */
    private double anchoCuadrado = 0;


    /**
     * Constructor único de una gráfica de barras 
     * @param listaElementos lista de palabras que va a contener la grafica
     * @param totalPalabras total de palabaras
     */   
    public DibujaGraficaBarras(Lista<Palabra> listaElementos, int totalPalabras){
        this.listaElementos = listaElementos;
        this.totalPalabras = totalPalabras;

        puntos = new Lista<>();

        double x , altura, porcentaje, y;

        x = xIni;
        y = yIni;
        altura = 0; 
        porcentaje = 0;
        anchoCuadrado = MAX_ANCHO_BARRAS/listaElementos.getElementos();

        for(Palabra p : this.listaElementos){
            porcentaje = (p.getApariciones()*100)/this.totalPalabras;

            altura = (p.getApariciones() * MAX_ALTURA_BARRAS)/totalPalabras;

            puntos.agrega(new Punto(x, y, p.getPalabra(), porcentaje, altura));
            x += anchoCuadrado;
        }
    }

    /**
     * Metodo auxiliar que crea un rectangulo que representa la barra.
     * @param posX coordenada X de una barra. 
     * @param posY coordenada Y de una barra.
     * @param altura altura de la barra.
     * @param ancho anchura de una barra.
     * @param color color de una barra.
     * @return svg de una barra.
     */
    private String dibujaCuadrado(double posX, double posY, double altura, double ancho, String color){
        String s = "";
        double coorY = this.yIni - altura;

        s += String.format(
                "<rect x='%f' y='%f' width='%f' height='%f' style='fill:%s;stroke-width:1;stroke:black' />\n", posX,
                coorY, ancho, altura, color);

        return s;
    }

    /**
     * Metodo auxiliar que dibuja la etiqueta de cada barra y establece sus dimensiones. 
     * @param p punto 
     * @return svg de la etiqueta de una barra.
     */
    private String dibujaEtiqueta(Punto p){
        double x = p.x + (anchoCuadrado)/2;
        double y = yIni + 15;
        double aux = yIni + 30;
        String tam = "";

        if(p.elemento.length() > 10){
            tam = "10px";
        }else{
            tam = "12px";
        }

        String porcentajeEtiqueta = "";

        porcentajeEtiqueta += "<text x= '" + x + "' y= '" + y
                + "' text-anchor='middle' fill='black' font-size='12px' font-family='sans-serif' dy='0.5em'>" +
                p.porcentaje + "%" + "</text>\n";

        return porcentajeEtiqueta + "<text x= '" + x + "' y= '" + aux
                + "' text-anchor='middle' fill='black' font-size='" + tam + "' font-family='sans-serif' dy='0.5em'>" +
                p.elemento + "</text>\n";
    }

    /**
     * Implementacion concreta del metodo que dibuja, esta vez, una grafica de pastel.
     */
    @Override
    public String dibuja(){
        String s  = "";

        s += empiezaSVG(500, 900);

        s += G_OPEN_TAG;
        
        String[] colores = { "gray","red","khaki","cyan","green", "blue"};

        s += dibujaCuadrado(xIni, yIni + 40, MAX_ALTURA_BARRAS,MAX_ANCHO_BARRAS,"gray");

        int i = 1;
        for(Punto p : puntos){
            s += dibujaCuadrado(p.x, p.y, p.altura, anchoCuadrado, colores[i]);
            s += dibujaEtiqueta(p);
            if(i == colores.length - 1){
                i = 0;
            }
            i++;
        }


        s += G_CLOSE_TAG;

        s += SVG_CLOSE_TAG;


        return s;
    } 
}
