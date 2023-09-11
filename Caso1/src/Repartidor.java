public class Repartidor extends Thread {
    private Bodega despacho;
    private Despachador despachador;
    //constructor
    public Repartidor(Bodega despacho, Despachador despachador){
        this.despacho = despacho;
        this.despachador = despachador;
    }

    //MetodoRetirardeDespacho
    public synchronized Producto retirarDeDespacho(Bodega despacho){
        while(despacho.getProductos().size() == 0){
            try{
                this.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        Producto producto = despacho.retirarProducto();
        //System.out.println("El repartidor ha retirado un producto del productor "+producto.getPadre()+" del despacho");
        System.out.println("+   Producto recogido por repartidor");
        
        return producto;
    }

    //Metodo repartir: se demora en repartir entre 3 y 10 segundos
    public synchronized void repartir(Producto producto){
        try{
            int tiempo = (int) (Math.random() * 8000) + 3000;
            sleep(tiempo);
            producto.entregar();
            producto.notify();
            //notifiy para despertar en producto
            //System.out.println("El repartidor ha repartido un producto del prodcutor "+producto.getPadre() + " en " + tiempo / 1000 + " segundos.");
            System.out.println("*     Producto repartido");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    } 

    //Metodo run
    public void run(){
        System.out.println(">> Thread repartidor iniciado");
        int num_despachados = 0;
        System.out.println("║       El total de productos a producir es: "+despachador.getTotalProductos());
        while(num_despachados < despachador.getTotalProductos())
        {

            System.out.println("*     Tamaño del despacho: "+despacho.getProductos().size());
            Producto producto = retirarDeDespacho(despacho);
            System.out.println("*     Tamaño del despacho: "+despacho.getProductos().size());
            repartir(producto);
            /* 
            if(producto.getEntregado())
            {
                producto.notify();
            }
            */
            //producto.notify(); SI SOLO SE PRODUCE UN PRODUCTO HAY ERROR
            //producto.notify();
            num_despachados++;
            System.out.println("║       El número de productos despachados es: "+ num_despachados);

        }
        System.out.println(">> Thread repartidor acabado");
    }
}
