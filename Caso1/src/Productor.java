import java.util.ArrayList;

public class Productor extends Thread{
    private int numProductos;
    private int id;
    private int contador;
    private Bodega bodega;
    private ArrayList<Producto> productos;


    //constructor
    public Productor(int id, int numProductos, Bodega bodega){
        this.id = id;
        this.numProductos = numProductos;
        this.contador = 0;
        this.bodega = bodega;
        this.productos = new ArrayList<Producto>(numProductos);
    }

    //Metodo generarProducto
    public Producto generarProducto(Producto anterior){
        while (bodega.getProductos().size() == bodega.getCapacidad() || anterior.getEntregado() == false){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Producto producto = new Producto(id);
        productos.add(producto);
        contador++;
        return producto;
    }

    //MetodoPrimerProducto
    public Producto generarPrimero(){
        while (bodega.getProductos().size() == bodega.getCapacidad()){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Producto producto = new Producto(id);
        productos.add(producto);
        contador++;
        return producto;

    }



    //metodo run
    public void run(){

        while(contador < numProductos){
            if (contador==0){
                Producto producto = generarPrimero();
                bodega.agregarProducto(producto);
                System.out.println("Productor " + id + " agrego el producto " + contador + " a la bodega.");
            }
            else{

                Producto producto = generarProducto(productos.get(contador-1));
                bodega.agregarProducto(producto);
                System.out.println("Productor " + id + " agrego el producto " + contador + " a la bodega.");
            }
        }
    }
}
