package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;
import mx.unam.ciencias.edd.proyecto3.estructuras_svg.*;

/**
 * Clase que extiende a {@link HtmlUtil} y que genera el index.html. 
 * 
 * @author Julián Rosas Scull
 */
public class IndexHTML extends HtmlUtil{

    /* Lista de archivos para ser redirigidos */
    private Lista<ArchivoHTML> archivos;

    /**
     * Constructir único que procesa los archivos pasados.
     * @param archivos
     */
    public IndexHTML(Lista<ArchivoHTML> archivos){
        this.archivos = archivos;
    }
    
    /**
     * Metodo que genera el index.html
     * @return index.html
     */
    public String dibujaIndex(){
        String s = "";

        s += empiezaHTML();
        s += enlacesHtml();
        s += "\n\n";

        s += "<h2 class='w3-wide w3-center'>Gráfica</h2>";
        s += "\n\n";
        s += creaGrafica();

        s += "\n<br>\n<br>\n</div>\n</body>\n</html>";

        return s;
    }

    /**
     * Generador de enlaces HTML. 
     * @return string - enlaces.
     */
    private String enlacesHtml(){
        String s = "";

        s += "<ul>\n";
        for(ArchivoHTML a : archivos){
            s += "<li><a href= '"+ a.getNombreHtml()+"' style='color:gray;'>" + a.getNombreOriginal() + "</a> con un total de <br>" + a.getElementos() + "</br> palabras </li>"; 
        }
        s += "</ul>\n";
        return s;
    }
    
    /**
     * Metodo auxliar que genera una grafica SVG con la grafica correspondiente entre archivos
     * en la cual, dos archivos estan conectados si y solo si ambos archivos comparten palabras de al menos
     * 7 caracteres. 
     * @return svg de la grafica
     */
    private String creaGrafica(){
        String s = "";

        DibujaGrafica<String> dibujaGrafica;
        Grafica<String> grafica;

        grafica = new Grafica<>();

        for(ArchivoHTML a : archivos){
            grafica.agrega(a.getNombreOriginal());
        }

        for(int i = 0; i < archivos.getElementos();i++){
            for(int j = i+1; j < archivos.getElementos();j++){
                if(archivos.get(i).compareTo(archivos.get(j)) == 1){
                    grafica.conecta(archivos.get(i).getNombreOriginal(), archivos.get(j).getNombreOriginal());
                }
            }
        }        
        
        dibujaGrafica = new DibujaGrafica<>(grafica);
            
        s += dibujaGrafica.dibuja();



        return s;

        
    }
}
