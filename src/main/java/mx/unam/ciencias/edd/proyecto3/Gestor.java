package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;
import java.io.File;

/**
 * Clase que recibe los argumentos del usuario y los procesa para crear los 
 * archuvos HTML respectivos.
 * 
 * @author Julián Rosas Scull.
 */
public class Gestor {

    /* Lista que representa los argumentos del usuario pasados */
    private Lista<String> argumentos;

    /**
     * Metodo auxliar que establece un error de directorio.
     */
    public void usoDirectorio(){
        System.err.println("No se especifico un directorio.\n");
        System.exit(1);
    }


    /**
     * Metodo auxiliar que establece un error de archivo.
     */
    public void usoArchivos(){
        System.out.println("No hay archivos para leer.\n");
        System.exit(1);
    }


    /**
     * Metodo auxilar que se encarga de devolver el nombre del archivo sin extension. 
     * @param archivo  archivo a quitar extension.
     * @return string con el nombre del arhivo.
     */

    public String quitaExtension(String archivo){
        String html = "";

        int indice = archivo.indexOf(".");

        html = archivo.substring(0, indice);

        return html;
    }

    /**
     * Constructor único que se encarga de convertir los argumentos a una {@link Lista} 
     * @param args argumentos pasados por el usuario.
     */
    public Gestor(String args[]){

        argumentos = new Lista<>();

        for(String s : args){
            argumentos.agrega(s);
        }


    }

    /**
     * Metodo que analiza los argumentos del usuario.
     */
    public void analiza(){
        if (argumentos.getElementos() == 0) {
            // uso
            usoArchivos();
        }

        int bandera = argumentos.indiceDe("-o");

        try{
            if (bandera == -1) {
                // uso
                usoDirectorio();
            }

            String aux = argumentos.get(bandera + 1);
            File directorio = new File(aux);
            argumentos.elimina("-o");
            argumentos.elimina(aux);

            Lista<ArchivoHTML> archivosAleer = new Lista<>();

            for (String s : argumentos) {
                Diccionario<String, Integer> diccionario = ReaderWriter.leerArchivo(s);

                if(diccionario.getElementos() == 0){
                    System.err.println("El archivo " + s + " esta vacio");
                    System.exit(1);
                }
                
                String archivoHtml = quitaExtension(s);
                
                ArchivoHTML archivo = new ArchivoHTML(archivoHtml + ".html",s, diccionario);
                
                String contenido = archivo.generaArchivoHTML();
                
                ReaderWriter.escribirArchivo(archivoHtml + ".html", contenido, directorio);

                archivosAleer.agrega(archivo);
            }

            IndexHTML index = new IndexHTML(archivosAleer);

            String contenidoIndex = index.dibujaIndex();

            ReaderWriter.escribirArchivo("index.html", contenidoIndex, directorio);

        }catch(ExcepcionIndiceInvalido ei){
            // uso
            usoDirectorio();
        }
     
    }
    
}
