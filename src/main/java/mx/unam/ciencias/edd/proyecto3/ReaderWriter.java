package mx.unam.ciencias.edd.proyecto3;
import mx.unam.ciencias.edd.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;
import java.io.BufferedReader;
import java.io.File;

/**
 * Clase que se encarga de la lectura y escritura de archivos
 * @author Julián Rosas Scull
 */
public class ReaderWriter {
    
    /** Constructor único para evitar instanciación */
    public ReaderWriter(){}

    /**
     * Metodo que lee un archivo para asi contar sus palabras y devolver un {@link Diccionario}
     * donde cada entrada del mismo sera una palabra y el numero de veces que esa palabra 
     * se encuentra en el archivo
     * @param archivo archivo a leer.
     * @return Diccionario con palabras y sus apariciones.
     */
    public static Diccionario<String, Integer> leerArchivo(String archivo){
        BufferedReader buf = null;
        FileReader fr = null;
        String linea = "";
        Diccionario<String, Integer> diccionario = new Diccionario<>();


        try{
            fr = new FileReader(archivo);
            buf = new BufferedReader(fr);
            linea = buf.readLine();
            String[] arr;
            
            while(linea != null){
                arr = linea.split(" ");
                
                for(int i = 0; i < arr.length; i++){
                    String palabra = Normalizer.normalize(arr[i].replaceAll("\\P{L}+", "").toLowerCase(), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
                
                    if(palabra.length() > 0){
                
                        if(diccionario.contiene(palabra)){
                            diccionario.agrega(palabra, diccionario.get(palabra)+1);
                        }else{
                            diccionario.agrega(palabra, 1);
                        }
             
                    }
                }

                linea = buf.readLine();

            }
        }catch(IOException io){
            System.err.println("Error de lectura del archivo");
            System.exit(1);
        }finally{
            try{
                if(buf != null){
                    buf.close();
                }

                if(fr != null){
                    fr.close();
                }
            }catch(IOException io){
                System.out.println(io);
            }
        }

        return diccionario;
    }


    /**
     * Metodo para escribir una archivo con un contendio pasado en un directorio especifico.
     * @param archivo archivo a escribir
     * @param contenido contenido del archivo que se quiere escribir
     * @param directorio directorio del archivo a escribir.
     */
    public static void escribirArchivo(String archivo, String contenido, File directorio){
        FileWriter fr = null;
        try{

            if(!directorio.exists()){
                directorio.mkdirs();
            }else{
                if(!directorio.isDirectory()){
                    System.err.println("El directorio de salida es un archivo");
                    System.exit(1);
                }
            }

            fr = new FileWriter(new File(directorio, archivo));
            fr.write(contenido);

        }catch(IOException io){
            System.err.println("Error de escritura");
            System.exit(1);
        }catch(SecurityException se){
            System.err.println("Error de seguridad de lectura del archivo");
            System.exit(1);
        }finally{

            try{
                if(fr != null){
                    fr.close();
                }
            }catch(IOException io){
                System.out.println(io);
            }

        }
    }

}
