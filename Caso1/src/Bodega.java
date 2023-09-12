import java.util.ArrayList;

public class Bodega {
    private ArrayList<Producto> productos;
    private int capacidad;
    private int totalEntregados;
    private int totalRecibidos;
    private boolean despachadorAcaba;

    //constructor
    public Bodega(int capacidad){
        this.capacidad = capacidad;
        this.productos = new ArrayList<Producto>();
        this.totalEntregados=0;
        this.despachadorAcaba=false;
    }
    //Metodos Setter
    public void setDespachadorAcaba(boolean valor)
    {
        this.despachadorAcaba=valor;
    }
    //Metodos getters
    public int getCapacidad(){
        return capacidad;
    }
    public ArrayList<Producto> getProductos(){
        return productos;
    }
    public int getTotalEntregados()
    {
        return totalEntregados;
    }
    public int getTotalRecibidos()
    {
        return totalRecibidos;
    }
    public boolean getDespachadorAcaba()
    {
        return this.despachadorAcaba;
    }

    //Metodo agregarProducto
    public synchronized void agregarProducto(Producto producto){
        while (this.getProductos().size() == this.getCapacidad()){
            try{
                this.wait(); //dormir sobre la bodega
            }catch(InterruptedException e){e.printStackTrace();}
        }
        productos.add(producto);
        this.totalRecibidos++;
        this.notify();//notificar que se puede sacar un producto si estuviera en espera pasiva
        //System.out.println("El producto "+ producto.getID()+" del productor " + producto.getPadre() + " se agrego a la bodega.");
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
        this.totalEntregados++;
        this.notify(); //despertar sobre bodega porque hay un cupo libre
        //System.out.println("El producto "+producto.getID() +" del productor " + producto.getPadre() + " ha sido retirado de la bodega.");
        return producto;
    }
}
