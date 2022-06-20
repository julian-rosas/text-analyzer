package mx.unam.ciencias.edd.proyecto3;

/**
 * Clase que implementa a {@link Comparable} que envuelve una entrada de un diccionario, de tal manera que 
 * que cada palabra contiene un string (la palabra) y un Integer que representa sus 
 * apariciones.
 *
 * @author Julián Rosas Scull. 
 */
public class Palabra implements Comparable<Palabra> {

    /* string que representa la palabra */
    private String palabra;

    /* entero que representa el numero de apariciones de la palabra */
    private Integer aparciones;

    /**
     * Constructor único que genera una palabracon sus respectivas apariciones.
     * @param palabra string que representa la palabra.
     * @param aparciones apariciones de dicha palabra.
     */
    public Palabra(String palabra, Integer aparciones){
        this.palabra = palabra;
        this.aparciones = aparciones;
    }

    /**
     * Metodo que regresa el string de la palabra.
     * @return palabra.
     */
    public String getPalabra(){
        return this.palabra;
    }

    /**
     * Metodo que regresa las apariciones de una palabra.
     * @return apariciones de la palabra.
     */
    public int getApariciones(){
        return this.aparciones;
    } 
    
    @Override 
    public String toString(){
        return this.palabra.toString();
    }

    /**
     * Metodo auxiliar que regresa una representacion en cadena de una palabra con sus
     * respectivas apariciones.
     * @return
     */
    public String toFile(){
        return String.format("<li><b>'%s'</b> tiene <b>'%d'</b> apariciones</li>", this.palabra, this.aparciones);
    }


    /**
     * Metodo que decide que como se va a comparar una palabra con otra.
     */
    @Override
    public int compareTo(Palabra palabra){
        return this.aparciones.compareTo(palabra.aparciones);
    }


}
