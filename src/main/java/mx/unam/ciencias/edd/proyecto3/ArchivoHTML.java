package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto3.estructuras_svg.*;
import java.util.Iterator;

/**
 * Clase que implementa {@link Comparable} y extiende a {@link HtmlUtil} estableciendo los atributos de un archivo HTML
 *
 *  @author Julián Rosas Scull.  
 */
public class ArchivoHTML extends HtmlUtil implements Comparable<ArchivoHTML>{
    
    /*Conjunto que representa las palabras*/
    private Conjunto<String> palabras;

    /* Nombre con extension .html del archivo */
    private String nombreHtml;

    /* Nombre del archivo sin extension*/
    private String nombreOriginal;

    /* Total de palabras en un archivo */
    private int elementos;

    /* Maximo numero de elementos que puede tener un arbol */
    private final int MAXIMO_ARBOLES = 15;
    
    /* Lista de las palabras mas repetidos en el archivo */
    private Lista<Palabra> listaElementosRepetidos;

    /* Lista de todas las palabras del archivo */
    private Lista<Palabra> listaElementosToT;

    /**
     * Contructor único que construrye un archivo HTML.
     * @param nombreHtml nombre del archivo con extension html.
     * @param nombreOriginal nombre del archivo sin extension.
     * @param diccionario diccionario de palabras del archivo.
     */
    public ArchivoHTML(String nombreHtml,String nombreOriginal, Diccionario<String, Integer> diccionario){
        this.nombreHtml = nombreHtml;
        
        this.nombreOriginal = nombreOriginal;

        this.listaElementosRepetidos = new Lista<>();

        this.listaElementosToT = new Lista<>();

        this.palabras = new Conjunto<>();

        Iterator<String> iterador = diccionario.iteradorLlaves();


        if(diccionario.getElementos() <= 15){
            while (iterador.hasNext()) {
                String llave = iterador.next();
                Integer valor = diccionario.get(llave);

                elementos += valor;

                Palabra e = new Palabra(llave, valor);

                palabras.agrega(llave);

                listaElementosRepetidos.agrega(e);
            }

            listaElementosToT = listaElementosRepetidos;
        }else{

            Lista<Palabra> aux = new Lista<>();

            while(iterador.hasNext()){
                String llave = iterador.next();
                Integer valor = diccionario.get(llave);

                elementos += valor;

                Palabra e =  new Palabra(llave, valor);
                palabras.agrega(llave);

                aux.agrega(e);
            }

            aux = Lista.mergeSort(aux).reversa();

            listaElementosToT = aux;
            

            for(int i = 0; i < MAXIMO_ARBOLES; i++){
                listaElementosRepetidos.agrega(aux.get(i));
            }
        }
    }

    
    /**
     * Metodo que regresa el total de los elementos del archivo.
     * @return elementos
     */
    public int getElementos(){
        return this.elementos;
    }
    
    /**
     * Metodo que regresa el nombre con extension HTML del archivo
     * @return
     */
    public String getNombreHtml(){
        return this.nombreHtml;
    }

    /**
     * MEtodo que regresa el nombre sin extension del archivo.
     * @return
     */
    public String getNombreOriginal(){
        return this.nombreOriginal;
    }

    /**
     * Metodo que genera el archivo HTML
     * @return
     */
    public String generaArchivoHTML(){
        String s = "";

        DibujaArbolRojinegro<Palabra> dibujaRojinegro = new DibujaArbolRojinegro<>(listaElementosRepetidos, Estructuras.ARBOL_ROJINEGRO);
        DibujaArbolAVL<Palabra> dibujaAVL = new DibujaArbolAVL<>(listaElementosRepetidos, Estructuras.ARBOL_AVL);
        DibujaGraficaBarras<Palabra> barras = new DibujaGraficaBarras<>(listaElementosRepetidos, elementos);
        DibujaGraficaPastel<Palabra> pastel = new DibujaGraficaPastel<>(listaElementosRepetidos, elementos);

        s += empiezaHTML();

        s += "\n<br>";

        s += "\n</ol>\n</div><div class='w3-cyan' id='tour'>\n<div class='w3-container w3-content w3-padding-64' style='max-width:800px'>"
                +
                "\n<h2 class='w3-wide w3-center'>Análisis de frecuencia de apariciones en <i>" + nombreOriginal + "</i> </h2>" +
                "\n<br>\n";
        s += "<h2 class='w3-wide w3-center'>Árbol Rojinegro</h2>";
        s += "\n\n";
        s += "<div class='w3-wide w3-center'>\n";
        s += dibujaRojinegro.dibuja();
        s += "</div>\n";
        s += "\n\n";
        s += "<h2 class='w3-wide w3-center'>Árbol AVL</h2>";
        s += "\n\n";                
        s += "<div class='w3-wide w3-center'>\n";
        s += dibujaAVL.dibuja();
        s += "</div>\n";
        s += "\n\n";
        s += "<h2 class='w3-wide w3-center'>Gráfica de Barras</h2>";
        s += "\n\n";
        s += "<div class='w3-wide w3-center'>\n";
        s += barras.dibuja();
        s += "</div>\n";
        s += "\n\n";
        s += "<h2 class='w3-wide w3-center'>Gráfica de Pastel</h2>";
        s += "\n\n";
        s += "<div class='w3-wide w3-center'>\n";
        s += pastel.dibuja();
        s += "</div>\n";
        s += "</div>\n</div>";
        s += "<div style='margin-left: 20px; margin-bottom: 30px; margin-right: 20px; border: 3px ;font-family: Garamond'><b> Lista de palabras:</b>";
        
        s += "<ul>\n";
        for(Palabra e : listaElementosToT){
            s += e.toFile() + "\n";
        }
        s += "</ul>\n";
        
        s += "\n<br>\n<br>\n</div>\n</body>\n</html>";

        return s;
    }


    /**
     * Metodo que regresa el conjunto de palabras del archivo.
     * @return
     */
    public Conjunto<String> getConjunto(){
        return this.palabras;
    }

    /**
     * Metodo que establece como se va a comparar un archivo con otro.
     */
    @Override
    public int compareTo(ArchivoHTML archivo){

        Conjunto<String> inter = this.palabras.interseccion(archivo.palabras);

        if(inter.getElementos() > 0){
            for(String s  : inter){
                if(s.length() >= 7){
                    return 1;
                }
            }
        }

        return -1;
    }

}
