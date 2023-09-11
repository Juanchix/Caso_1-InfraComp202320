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
            }catch(InterruptedException e){}
        }
        productos.add(producto);
        this.notifyAll(); //notificar que se puede sacar un producto si estuviera en espera pasiva
        System.out.println("*     El producto "+ (producto.getID()+1)+" del productor " + (producto.getPadre()+1) + " se agregó a la bodega.");
    }

    //Metodo retirarProducto
    public synchronized Producto retirarProducto(){
        while(this.getProductos().size()==0)
        {
            try {
                this.wait();
            } catch (InterruptedException e) {}
        }
        Producto producto = productos.remove(0);
        this.notifyAll(); //despertar sobre bodega porque hay un cupo libre
        System.out.println("*     El producto "+(producto.getID()+1) +" del productor " + (producto.getPadre()+1) + " ha sido retirado de la bodega.");
        return producto;
    }
}
