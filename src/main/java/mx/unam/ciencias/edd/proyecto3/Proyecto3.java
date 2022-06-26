package mx.unam.ciencias.edd.proyecto3;
/**
 * Clase que se maneja como principal o interface, la cual manda a llamar al gestor. 
 * @author Julián Rosas Scull. 
 */
public class Proyecto3 {
    public static void main(String[] args) {
        Gestor gestor = new Gestor(args);

        gestor.analiza();
    }
}
