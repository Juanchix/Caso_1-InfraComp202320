import java.util.ArrayList;

public class Bodega {
    private ArrayList<Producto> productos;
    private int capacidad;

    //constructor
    public Bodega(int capacidad){
        this.capacidad = capacidad;
        this.productos = new ArrayList<Producto>();
    }

    //Metodos getters
    public int getCapacidad(){
        return capacidad;
    }
    public ArrayList<Producto> getProductos(){
        return productos;
    }

    //Metodo agregarProducto
    public synchronized void agregarProducto(Producto producto){
        while (this.getProductos().size() == this.getCapacidad()){
            try{
                this.wait(); //dormir sobre la bodega
            }catch(InterruptedException e){e.printStackTrace();}
        }
        productos.add(producto);
        this.notify();//notificar que se puede sacar un producto si estuviera en espera pasiva
        System.out.println("*     El producto "+ (producto.getID())+" del productor " + (producto.getPadre()) + " se agregó a la bodega.");
    }

    //Metodo retirarProducto
    public synchronized Producto retirarProducto(){
        while(this.getProductos().size()==0)
        {
            try {
                this.wait();
            } catch (InterruptedException e) {e.printStackTrace();}
        }
        Producto producto = productos.remove(0);
        this.notify(); //despertar sobre bodega porque hay un cupo libre
        System.out.println("*     El producto "+(producto.getID()) +" del productor " + (producto.getPadre()) + " ha sido retirado de la bodega.");
        return producto;
    }
}
