public class Producto {
    private boolean entregado;
    private int productor;

    //constructor
    public Producto(int productor){
        this.entregado = false;
        this.productor = productor;
    }

    //Metodo entregar
    public void entregar(){
        this.entregado = true;
    }
    //Metodo getEntregado
    public boolean getEntregado(){
        return entregado;
    }
    
}
