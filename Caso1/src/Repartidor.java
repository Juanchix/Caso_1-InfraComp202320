public class Repartidor extends Thread {
    private int id;
    private Bodega despacho;
    private Despachador despachador;
    private int num_despachados;

    //constructor
    public Repartidor(int id, Bodega despacho, Despachador despachador){
        this.id = id;
        this.despacho = despacho;
        this.despachador = despachador;
        this.num_despachados= 0;
    }
    //Metodo getter
    public int getDespachados()
    {
        return this.num_despachados;
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
        System.out.println("*     Producto "+ producto.getID() +" del productor "+ producto.getPadre()+" recogido por repartidor");
        
        return producto;
    }

    //Metodo repartir: se demora en repartir entre 3 y 10 segundos
    public synchronized void repartir(Producto producto){
        try{
            int tiempo = (int) (Math.random() * 7000) + 3000;
            sleep(tiempo);
            producto.entregar();//boolean
            synchronized (producto)
            {
                producto.notify();
            }
            //notifiy para despertar en producto
            //System.out.println("El repartidor ha repartido un producto del prodcutor "+producto.getPadre() + " en " + tiempo / 1000 + " segundos.");
            System.out.println("*     Producto "+ producto.getID()+" del productor"+producto.getPadre()+" repartido");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    } 

    //Metodo run
    public void run(){
        System.out.println(">> Thread del repartidor "+ id +" iniciado");
        System.out.println("║     Se producirán en total "+despachador.getTotalProductos()+" productos");
        while(true)
        {
            if(despachador.getTerminado()) //thread despachador acabo
            {
                break;
            }
            //System.out.println("Tamaño del despacho: "+despacho.getProductos().size());
            Producto producto = despacho.retirarProducto();
            //System.out.println("Tamaño del despacho: "+despacho.getProductos().size());
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
            System.out.println("");
            System.out.println("*     El repartidor "+this.id+" ha repartido "+this.num_despachados+" productos");
            System.out.println("");

        }
        
        System.out.println("║      EL número total de productos despachados es: "+ num_despachados);
        System.out.println(">> Thread del repartidor "+ id+" acabado");
    }
}
