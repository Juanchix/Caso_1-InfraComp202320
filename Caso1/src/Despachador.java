import java.util.ArrayList;
import java.util.List;

public class Despachador extends Thread{
    private Bodega bodega;
    private Bodega despacho;

    private int totalProductos;
    private int totalRepartidos;

    private List<Repartidor> repartidores;

    private Boolean termino;
    //constructor
    public Despachador(int numRepartidores, Bodega bodega, Bodega despacho, int totalProductos){
        this.bodega = bodega;
        this.despacho = despacho;
        this.totalProductos=totalProductos;
        this.repartidores=new ArrayList<Repartidor>(numRepartidores);
        this.termino = false;
    }
    //Getter
    public int getTotalProductos()
    {
        return this.totalProductos;
    }
    public List<Repartidor> getRepartidores()
    {
        return this.repartidores;
    }
    public Boolean getTerminado()
    {
        return this.termino;
    }

    //Calcular repartidos
    public int getNumRepartidos()
    {
        int repartidos=0;
        for (int i=0; i<getRepartidores().size();i++)
        {
            repartidos += getRepartidores().get(i).getDespachados();
        }
        return repartidos;
    }

    //Agregar repartidores
    public void agregarRepartidor(Repartidor repartidor)
    {
        this.repartidores.add(repartidor);
    } 

    //Metodo estoyEsperando
    public void estoyEsperando(){
        System.out.println("La bodega esta vacía");
        System.out.println("Estoy esperando a que se agreguen productos");
        System.out.println("Ya vuelvo");
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
        System.out.println("Producto "+producto.getID()+" retirado de bodega del productor "+producto.getPadre());
        return producto;
    } 

    //Metodo agregarProductoDespacho
    public synchronized void agregarProductoDespacho(Producto producto){
        despacho.agregarProducto(producto);
        //System.out.println("El despachador ha agregado un producto del productor "+producto.getPadre()+" en el despacho");
        System.out.println("Producto "+producto.getID()+" agregado a despacho del productor "+producto.getPadre());
    }

    //Metodo run
    public void run(){
        int trasladados = 0;
        System.out.println("----Thread despachador iniciado");
        while(trasladados<totalProductos || getNumRepartidos()<totalProductos) //thread despacho a todos Y todos los productos fueron repartidos
        {
            if(trasladados<totalProductos)
            {
                Producto producto = sacarProductoBodega();
                agregarProductoDespacho(producto);
                trasladados++;
            }
            if(getNumRepartidos()==totalProductos)
            {
                break;
            }

            System.out.println(trasladados + " < " + totalProductos+" && "+getNumRepartidos()+" < "+totalProductos);
            System.out.println("1 "+ getRepartidores().get(0).getDespachados());
            System.out.println("2 "+getRepartidores().get(1).getDespachados());
            System.out.println("EL metodo imprime:"+getNumRepartidos());
            System.out.println(" BODEGA: "+bodega.getProductos().size());
            System.out.println(" DESPACHO: "+despacho.getProductos().size());

        }
        System.out.println("---Thread despachador acabado");
        System.out.println("TOTALLLLLLL despachados es: "+getNumRepartidos());
        this.termino=true;
    }
}
