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
            //System.out.println("El repartidor ha repartido un producto del prodcutor "+producto.getPadre() + " en " + tiempo / 1000 + " segundos.");
            System.out.println("Producto "+ producto.getID()+" del productor"+producto.getPadre()+" repartido");
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    } 

    //Metodo run
    public void run(){
        System.out.println("----Thread repartidor "+ id +" iniciado");
        while(true)
        {
            if((despacho.getTotalEntregados()==despachador.getTotalProductos()) && (despacho.getTotalRecibidos()==despachador.getTotalProductos()))
            {
                break;
            }
            if(!despachador.getTerminado())
            {
                if(despacho.getDespachadorAcaba())
                {
                    synchronized (despachador)
                    {
                        try {
                            despachador.wait();
                        } catch (InterruptedException e) {}
                    }
                }
                else
                {
                    Producto producto = despacho.retirarProducto();
                    repartir(producto);
                    num_despachados++;
                }
            }
            else
            {
                break;
            }
        }
        System.out.println("EL repartidor "+this.id+" ha repartido "+this.num_despachados);
        System.out.println("----Thread repartidor "+ id+" acabado");
    }
}
