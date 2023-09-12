import java.util.ArrayList;

public class Productor extends Thread{
    private int numProductos;
    private int id;
    private int hijos;
    private Bodega bodega;
    private ArrayList<Producto> productos;

    //constructor
    public Productor(int id, int numProductos, Bodega bodega){
        this.id = id;
        this.numProductos = numProductos;
        this.hijos = 0;
        this.bodega = bodega;
        this.productos = new ArrayList<Producto>();
    }

    //Metodo generarCualquierProducto
    public Producto generarProducto(int id_producto, int id_padre){
        Producto producto = new Producto(id_producto,id_padre);
        productos.add(producto);
        System.out.println("+   El productor "+(id_padre)+" ha creado el producto "+(id_producto));
        return producto;
    }

    //metodo run
    public void run(){
        System.out.println(">> Thread del productor "+ id+" iniciado");
        while(hijos < numProductos){
            //Genero un primer producto siempre 
            Producto producto = generarProducto(this.hijos+1,this.id);
            bodega.agregarProducto(producto); //puedo dormir sobre la bodega
            //El producto está en el ciclo de producción, entonces debo dormir sobre el producto
            //ya que no puedo tener 2 o más productos en un ciclo de producción
            System.out.println("*     El producto "+producto.getID()+" del productor "+producto.getPadre()+" ha sido agregado a la bodega");
            synchronized (producto)
            {
                try {
                    System.out.println("*     El productor "+producto.getPadre()+" está durmiendo sobre el producto "+producto.getID());
                    producto.wait();
                } catch (InterruptedException e) {e.printStackTrace();}
            }
            hijos++; //debería generar lo que me digan por consola para cada instancia de thread

            /*
            if(hijos==0)//evaluar el primer elemento creado
            {
                bodega.agregarProducto(producto); //puede dormir sobre bodega    
            }
            else
            {
                Producto anterior = productos.get(hijos-1);   
                while(!anterior.getEntregado())
                {
                    try {
                        anterior.wait(); //dormir sobre producto
                    } catch (Exception e) {}
                }
                bodega.agregarProducto(producto); //puede dormir sobre bodega
            }
            hijos++;
             */
        }
        System.out.println("║     El thread"+id+ "produjo y agregó "+this.hijos+" productos");
        System.out.println(">> Thread "+ id +" productor acabado");
    }
}