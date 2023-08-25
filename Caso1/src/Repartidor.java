public class Repartidor extends Thread {
    private boolean termino;
    private Bodega despacho;


    //constructor
    public Repartidor(Bodega despacho){
        this.termino = false;
        this.despacho = despacho;
        
    }

    //MetodoRetirardeDespacho
    public synchronized Producto retirarDeDespacho(Bodega despacho){
        while(despacho.getProductos().size() == 0){
            try{
                wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        Producto producto = despacho.retirarProducto();
        return producto;
    }

    //Metodo repartir: se demora en repartir entre 3 y 10 segundos
    public void repartir(Producto producto){
        try{
            int tiempo = (int) (Math.random() * 8000) + 3000;
            Thread.sleep(tiempo);
            producto.entregar();
            notifyAll();
            System.out.println("Reparto de " + producto + " completado en " + tiempo / 1000 + " segundos.");

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    } 

    //Metodo run
    public void run(){
        Producto producto = retirarDeDespacho(despacho);
        repartir(producto);
        termino = true;
    }

}
