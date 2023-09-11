public class Despachador extends Thread{
    private Bodega bodega;
    private Bodega despacho;
    private int totalProductos;

    //constructor
    public Despachador(int numRepartidores, Bodega bodega, Bodega despacho, int totalProductos){
        this.bodega = bodega;
        this.despacho = despacho;
        this.totalProductos=totalProductos;
    }
    //Getter
    public int getTotalProductos()
    {
        return this.totalProductos;
    }

    //Metodo estoyEsperando
    public void estoyEsperando(){
        System.out.println("-     La bodega esta vacía");
        System.out.println("-     Estoy esperando a que se agreguen productos");
        System.out.println("-     Ya vuelvo");
    }

    //Metodo SacarProductoBodega
    public synchronized Producto sacarProductoBodega(){
        
        int contador = 0;
        while(bodega.getProductos().size()==0 && contador>3){ //IF vs WHILE(ciclo infinito)
            estoyEsperando();
            contador ++;
        }
        Producto producto = bodega.retirarProducto();
        //System.out.println("El despachador ha retirado un producto del productor "+producto.getPadre()+" que estaba en la bodega");
        System.out.println("║       Producto retirado de bodega");

        return producto;
    } 

    //Metodo agregarProductoDespacho
    public synchronized void agregarProductoDespacho(Producto producto){
        despacho.agregarProducto(producto);
        //System.out.println("El despachador ha agregado un producto del productor "+producto.getPadre()+" en el despacho");
        System.out.println("║       Producto agregado a despacho");
    }

    //Metodo run
    public void run(){
        int total = totalProductos;
        System.out.println(">> Thread despachador iniciado");
        while(total!=0)
        {
            Producto producto = sacarProductoBodega();
            agregarProductoDespacho(producto);
            total--;
        }
        System.out.println(">> Thread despachador acabado");
    }
}
