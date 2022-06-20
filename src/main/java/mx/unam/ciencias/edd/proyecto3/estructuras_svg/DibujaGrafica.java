package mx.unam.ciencias.edd.proyecto3.estructuras_svg;
import mx.unam.ciencias.edd.*;


/**
 * Clase que decide como se ve una {@link Grafica} en SVG.
 * 
 * @author Julián Rosas Scull
 */
public class DibujaGrafica<T extends Comparable<T>> extends DibujaEstructuras<T>{


    /**
     * Clase interna privada que representa a los vértices 
     * como puntos en un plano en R2.
     */
    private class Punto{
        
        /** Elemento del punto*/
        public T elemento;
        
        /** Coordenada en el eje X del punto */
        public double x;
        
        /** Coordenada en el eje Y del punto */
        public double y;

        /**
         * Constructor único que genera un punto.
         * @param elemento elemento del punto 
         * @param x coordenada X del punto
         * @param y coordenada Y del punto
         */
        public Punto(T elemento, double x, double y){
            this.elemento = elemento;
            this.x = x;
            this.y = y;
        }

    }


    /** Gráfica auxiliar que representará la gráfica asociada a la que pasa el usuario */
    private Grafica<T> grafica;

    /** Lista de puntos que van a representar los vértices de la gráfica pasada */
    private Lista<Punto> puntos = new Lista<>();

    /**
     * Constructor único que genera una gráfica.
     * @param grafica
     */
    public DibujaGrafica(Grafica<T> grafica){
        this.grafica = grafica;
    }

    /**
     * Método que decide como se va a ver la etiqueta de un vértice (junto con su elemento).
     * @param text Elemento del vértice.
     * @param posX Posición en el eje X del vértice.
     * @param posY Posición en el eje Y del vértice.
     * @return String de la etiqueta del vértice.
     */
    private String dibujaVerticeGrafica(String text, double posX, double posY){
        String circulo = String.format("<circle cx='%f' cy='%f' r='%d' fill='black' stroke='middle' stroke-width='3'/>\n", 
        posX,
        posY,
        25);

        String texto = String.format("<text x='%f' y='%f' fill='white' font-size='8' text-anchor='middle'>%s</text>\n", 
        posX,
        posY + 5,
        text);

        return circulo + texto;
    }


    /**
     * Método que define como se va a ver una arista en SVG de una gráfica.
     * @param x1 Posición del primer extremo de la gráfica en el eje X.
     * @param y1 Posición del primer extremo de la gráfica en el eje Y.
     * @param x2 Posición del segundo extremo de la gráfica en el eje X.
     * @param y2 Posición del segundo extremo de la gráfica en el eje Y.
     * @return String con la etiqueta de una arista entre vértices.
     */
    private String dibujaArista(double x1, double y1, double x2, double y2) {
        return String.format("<line x1='%f' y1='%f' x2='%f' y2='%f' stroke='blue' stroke-width='3'/>\n", x1, y1, x2, y2);
    }

    /**
     * Método que envuelve a los vértices como puntos, de tal manera que contengan coordenadas
     * en el eje X y en el eje Y.
     * @param partes Partes en la que se dividira la gráfica
     * @param mitad Mitad del SVG para que así se centre en cada iteración de los vértices.
     */
    private void generaPuntos(int partes, double mitad){
        double angulo = 0;
        double x = 0, y = 0;
        
        int i = 0;

        for(T e : grafica){
            if(grafica.getElementos() == 1)
                puntos.agrega(new Punto(e, mitad, mitad));
            else
                angulo = i * (360 / partes);
                x = 110 * Math.cos(Math.toRadians(angulo));
                y = 110 * Math.sin(Math.toRadians(angulo));
                puntos.agrega(new Punto(e, x + mitad, y + mitad));
                i++;
        }
    }

    /**
     * Método que genera las coordenadas de los vértices ya representados como puntos
     * auxiliandose de {@link #dibujaVerticeGrafica(String, double, double)}.
     * @return Cadena con la etiqueta e cada vértice.
     */
    private String generaCoordenadasVertices(){
        String s = "";
        
        for(Punto p : puntos){
            s += dibujaVerticeGrafica(p.elemento.toString(), p.x, p.y);
        }
        
        return s;
    }

    /**
     * Método que genera las coordenadas de las aristas ya representadas como etiquetas,
     * auxiliandose de los vértices ya convertidos a puntos y de {@link #dibujaArista(double, double, double, double)}.
     * @return etiqueta de cada arista.
     */
    private String generCoordenadasAristas(){
        String s = "";
        
        for(int i = 0; i < puntos.getElementos(); i++){
        
            Punto a = puntos.get(i);
        
            for(int j = 0; j < grafica.getElementos(); j++){
        
                Punto b = puntos.get(j);
        
                if(grafica.sonVecinos(a.elemento, b.elemento)){
                    s += dibujaArista(a.x, a.y, b.x, b.y);
                }
            }
        }

        return s;
    }

    /**
     * Implementación concreta que dibuja, esta vez, gráficas.
     */
    @Override
    public String dibuja(){
        String s = "";

        double radioCircunferencia = (2*(2*grafica.getElementos()) * (2*20))/Math.PI;

        double altura = (2*20) + (2*radioCircunferencia) + 20;

        s += empiezaSVG(altura, altura);
        
        s += G_OPEN_TAG;
        
        generaPuntos(grafica.getElementos(), altura/2);

        if(grafica.getElementos() > 1)
            s += generCoordenadasAristas();
            
        s += generaCoordenadasVertices();

        s += G_CLOSE_TAG;

        s += SVG_CLOSE_TAG;

        return s;
    }


}
