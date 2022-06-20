package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
            this.iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return this.iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            return this.iterador.next().get();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La distancia del vértice. */
        private double distancia;
        /* El índice del vértice. */
        private int indice;
        /* El diccionario de vecinos del vértice. */
        private Diccionario<T, Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Diccionario<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return this.vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return this.vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice = indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
            if(distancia > vertice.distancia)
                return 1;
            else if(distancia < vertice.distancia)
                return -1;
            return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
            this.vecino = vecino;
            this.peso = peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
            return vecino.color;
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return this.vecino.vecinos();
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
    }

    /* Vértices. */
    private Diccionario<T, Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices = new Diccionario<>();
        aristas = 0;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.

        if(elemento == null || contiene(elemento)) throw new IllegalArgumentException();

        Vertice nuevo = new Vertice(elemento);

        vertices.agrega(elemento,nuevo);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
        conecta(a, b, 1.0);
        
        
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
     
        if (!contiene(a) || !contiene(b)) throw new NoSuchElementException();
        if (a == b || sonVecinos(a, b) || peso < 0) throw new IllegalArgumentException();

        Vertice vA = (Vertice) vertice(a);

        Vertice vB = (Vertice) vertice(b);

        vA.vecinos.agrega(b, new Vecino(vB, peso));

        vB.vecinos.agrega(a,new Vecino(vA, peso));

        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        if (!contiene(a) || !contiene(b)) throw new NoSuchElementException();
        if (a == b) throw new IllegalArgumentException();

        Vertice vA = (Vertice)vertice(a);


        Vertice vB = (Vertice)vertice(b);


        vA.vecinos.elimina(b);
        vB.vecinos.elimina(a);

        aristas--;

    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código
        if(elemento == null) throw new IllegalArgumentException();
        Vertice v = getVertice(elemento);
        return v != null;
        
    }

    private Vertice getVertice(T elemento){

        if(elemento == null) throw new IllegalArgumentException();

        Vertice v1 = null;    
        
        for(Vertice v : vertices){
            if(v.elemento.equals(elemento)){
                v1 = v;
            }
        }

        return v1;
        
    }

    private Vecino getVecino(Diccionario<T,Vecino> diccionario, Vertice v){
        Vecino v1 = null;
        
        for(Vecino vec : diccionario){
            if(vec.vecino == v){
                v1 = vec;
            }
        }
        return v1;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        if(!contiene(elemento)) throw new NoSuchElementException();
        Vertice vertice = (Vertice)vertice(elemento);

        int aristasEliminadas = 0;

        for(Vecino v : vertice.vecinos){
            v.vecino.vecinos.elimina(elemento);
            aristasEliminadas++;
        }


        vertices.elimina(elemento);
        this.aristas -= aristasEliminadas;
    }
    

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        if (a == null || b == null) throw new IllegalArgumentException();
        if(!contiene(a) || !contiene(b)) throw new NoSuchElementException();

        Vertice vA = (Vertice) vertice(a);
        Vertice vB = (Vertice) vertice(b);

        return vA.vecinos.contiene(b) && vB.vecinos.contiene(a);
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b)) throw new NoSuchElementException();
        if(!sonVecinos(a, b)) throw new IllegalArgumentException();

        Vertice vA = (Vertice)vertice(a);
        Vertice vB = (Vertice)vertice(b);

        for(Vecino v : vA.vecinos){
            if(v.vecino == vB){
                return v.peso;
            }
        }

        return -1;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b)) throw new NoSuchElementException();
        if(!sonVecinos(a, b) || peso <= 0) throw new IllegalArgumentException();

        Vertice vA = (Vertice)vertice(a);
        Vertice vB = (Vertice)vertice(b);

        Vecino vecA = getVecino(vB.vecinos, vA);
        Vecino vecB = getVecino(vA.vecinos, vB);

        vecA.peso = peso;
        vecB.peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.

        if (elemento == null) throw new IllegalArgumentException();
        
        Vertice v = null;
        
        for (Vertice vertice : vertices) {
            if (vertice.elemento.equals(elemento))
                v = vertice;
        }

        if (v == null) throw new NoSuchElementException();
        
        return v;
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
        
        if(vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class || vertice == null) throw new IllegalArgumentException();
        
        if(vertice.getClass() == Vertice.class){
            Vertice v = (Vertice)vertice;
            if(!vertices.contiene(v.elemento)) throw new IllegalArgumentException();
            v.color = color;
        }
        
        if(vertice.getClass() == Vecino.class){
            Vecino v = (Vecino)vertice;
            v.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
        if(vertices.getElementos() == 0 || vertices.getElementos() == 1) return true;

        Cola<Vertice> cola = new Cola<>();

        for(Vertice v : vertices){
            v.color = Color.NINGUNO;
        }

        Vertice vIni = null;

        for(Vertice v : vertices){
            vIni = v;
            break;
        }
        
        vIni.color = Color.ROJO;
        cola.mete(vIni);
        
        while(!cola.esVacia()){
            Vertice vIter = cola.saca();

            for(Vecino v : vIter.vecinos){
                if(v.vecino.color == Color.NINGUNO){
                    v.vecino.color = Color.ROJO;
                    cola.mete(v.vecino);
                }
            }
        }

        boolean flag = false;

        for(Vertice v : vertices){
            
            if(v.color == Color.ROJO){
                flag = true;
            }else{
                flag = false;
                break;
            }
            
        }

        for(Vertice v : vertices){
            v.color = Color.NINGUNO;
        }

        return flag;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.

        for(Vertice v : vertices){
            accion.actua(v);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        if(!contiene(elemento)) throw new NoSuchElementException();
        recorrido(elemento, accion, new Cola<Vertice>());

    }


    private void recorrido(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> estructura){

        for(Vertice v : vertices){
            v.color = Color.ROJO;
        }

        Vertice v1 = (Vertice)vertice(elemento);
        
        v1.color = Color.NEGRO;

        estructura.mete(v1);

        while(!estructura.esVacia()){
            Vertice v2 = estructura.saca();
            accion.actua(v2);
            for(Vecino v : v2.vecinos){
                if(v.vecino.color == Color.ROJO){
                    v.vecino.color = Color.NEGRO;
                    estructura.mete(v.vecino);
                }
            }
        }

        for(Vertice v : vertices){
            v.color = Color.NINGUNO;
        }

    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        if(!contiene(elemento)) throw new NoSuchElementException();
        recorrido(elemento, accion, new Pila<Vertice>());
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        vertices.limpia();
        aristas = 0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.

         String s = "{";
         String arista = "{";   

        paraCadaVertice(v -> setColor(v, Color.NINGUNO));
        
        for(Vertice v : vertices){
            s += v.elemento + ", "; 
            for(Vecino vecino : v.vecinos){
                if(vecino.vecino.color == Color.NINGUNO){
                    arista += "(" + v.elemento + ", " + vecino.vecino.elemento + "), ";
                }
                v.color = Color.NEGRO;
            }
        }

        paraCadaVertice(v -> setColor(v, Color.NINGUNO));


        return s + "}, " + arista + "}";

    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.

        if(vertices.getElementos() != grafica.vertices.getElementos() || aristas != grafica.aristas) return false;


        for(Vertice v : vertices){
            for(Vecino vec : v.vecinos){
                if(!grafica.contiene(v.elemento) || !grafica.sonVecinos(v.elemento, vec.vecino.elemento)) return false;
            }
        }

        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }



    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        if(origen == null || destino == null) throw new IllegalArgumentException();
        if(!contiene(origen) || !contiene(destino)) throw new NoSuchElementException();
        
        Vertice vorigen = (Vertice)vertice(origen);
        Vertice vdestino = (Vertice)vertice(destino);
        Lista<VerticeGrafica<T>> listaTray = new Lista<>();
        
        if(origen.equals(destino)){
            listaTray.agrega(vorigen);
            return listaTray;
        }

        
        for(Vertice v : vertices){
            v.distancia = Double.POSITIVE_INFINITY;
        }

        vorigen.distancia = 0;

        Cola<Vertice> cola = new Cola<>();
        cola.mete(vorigen);
        
        while(!cola.esVacia()){
            Vertice u = cola.saca();

            for(Vecino v : u.vecinos){
                if(v.vecino.distancia == Double.POSITIVE_INFINITY){
                    v.vecino.distancia = u.distancia + 1;
                    cola.mete(v.vecino);
                }
            }

        }

        if(vdestino.distancia == Double.POSITIVE_INFINITY){
            return listaTray;
        }

        Vertice aux = vdestino;

        listaTray.agregaInicio(aux); 
        
        while(aux != vorigen){
            for(Vecino v : aux.vecinos){
                if(v.vecino.distancia == (aux.distancia - 1)){
                    listaTray.agregaInicio(v.vecino);
                    aux = v.vecino;
                    break;
                }
            }
        }

        return listaTray;
    }

    /**
     * de destino.
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
   
        Lista<VerticeGrafica<T>> listaTray = new Lista<>();
        
        Vertice vorigen = (Vertice) vertice(origen);
        Vertice vdestino = (Vertice) vertice(destino);
          
        if(origen == null || destino == null) throw new IllegalArgumentException();
        if(!contiene(origen) || !contiene(destino)) throw new NoSuchElementException();
         
          
        if(origen == destino){
            listaTray.agrega(vorigen);
            return listaTray;
        }

        for(Vertice v : vertices){
            v.distancia = Double.POSITIVE_INFINITY;
        }       
        
        vorigen.distancia = 0;
         
        MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<>();

        for(Vertice v : vertices){
            monticulo.agrega(v);
        }

        while(!monticulo.esVacia()){
            Vertice u = monticulo.elimina(); 
            for(Vecino v : u.vecinos){
                if (v.vecino.distancia > u.distancia + v.peso) {
                    v.vecino.distancia = u.distancia + v.peso;
                    monticulo.reordena(v.vecino);
                }
            }
        }

        if(vdestino.distancia == Double.POSITIVE_INFINITY){
            return listaTray;
        }
          
        Vertice aux = vdestino;
          
        listaTray.agregaInicio(aux);
          
        while(!aux.elemento.equals(vorigen.elemento)){
            for(Vecino v : aux.vecinos){
                if(v.vecino.distancia == aux.distancia - v.peso){
                    listaTray.agregaInicio(v.vecino);
                    aux = v.vecino;
                    break;
                }
            }
        }
          
        return listaTray;
          
    }
}
