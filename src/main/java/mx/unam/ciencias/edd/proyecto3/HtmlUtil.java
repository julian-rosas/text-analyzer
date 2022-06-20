package mx.unam.ciencias.edd.proyecto3;

/**
 * Clase abstracta que establece las utilidades básicas de un HTML.
 * 
 * @author Julián Rosas Scull
 */
public abstract class HtmlUtil {
    /**
    * Metodo que establece el encabezado de un HTML.
    * @return encabezado del html.
    */
    public static String empiezaHTML(){
        String s = "";

        s += "<!DOCTYPE html>\n<html lang='en'>\n<title>Archivo</title>\n<meta charset='UTF-8'>\n" 
                +"<head>\n"+
                "\n<link rel='stylesheet' href='https://www.w3schools.com/w3css/4/w3.css'>" +
                "\n<link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Lato'>" +
                "\n<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css'>"
                +
                "\n<body>\n"
                +
                "\n<div class='w3-content' style='max-width:2000px;'><div class='w3-container w3-content w3-center w3-padding-64' style='max-width:1000px' id='band'>"
                +
                "\n<h2 class='w3-wide'><a href='index.html'>Analizador de Palabras</a></h2>\n <h3 class='w3-wide'> Julián Rosas Scull</h3>\n <h3 class='w3-opacity'><i>Estructuras de Datos</i></h3>\n <h3 class='w3-opacity'><i>Proyecto 3<i></h3>\n"+ "</head>\n";
        
        return s;
    }




}
