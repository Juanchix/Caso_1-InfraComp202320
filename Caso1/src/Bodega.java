import java.util.ArrayList;

public class Bodega {
    private ArrayList<Producto> productos;
    private int capacidad;

    //constructor
    public Bodega(int capacidad){
        this.capacidad = capacidad;
        this.productos = new ArrayList<Producto>();
    }

    //GetCapacidad
    public int getCapacidad(){
        return capacidad;
    
    }

    //GetProductos
    public ArrayList<Producto> getProductos(){
        return productos;
    }
    //Metodo agregarProducto
    public synchronized void agregarProducto(Producto producto){
        productos.add(producto);
        notify();
    }

    //Metodo retirarProducto
    public synchronized Producto retirarProducto(){
        Producto producto = productos.remove(0);
        return producto;
    }

}
