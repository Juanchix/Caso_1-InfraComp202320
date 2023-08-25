import java.util.HashMap;

public class Despachador extends Thread{
    private HashMap<String, Integer> PPP;
    private int numRepartidores;
    private Bodega bodega;
    private Bodega despacho;

    //constructor
    public Despachador(int numRepartidores, Bodega bodega, Bodega despacho){
        this.numRepartidores = numRepartidores;
        this.bodega = bodega;
        this.despacho = despacho;
        this.PPP = new HashMap<String, Integer>();
    }

    //Metodo estoyEsperando
    public void estoyEsperando(){
        System.out.println("La bodega esta vacía");
        System.out.println("Estoy esperando a que se agreguen productos");
        System.out.println("Ya vuelvo");
    }

    //Metodo SacarProductoBodega
    public synchronized Producto sacarProductoBodega(){
        while(bodega.getProductos().size() == 0 && despacho.getProductos().size() != 0){
            estoyEsperando();
        }
        Producto producto = bodega.retirarProducto();
        return producto;
    } 

    //Metodo agregarProductoDespacho
    public synchronized void agregarProductoDespacho(Producto producto){
        despacho.agregarProducto(producto);
    }

    //Metodo run
    public void run(){
        Producto producto = sacarProductoBodega();
        agregarProductoDespacho(producto);
    }
}
