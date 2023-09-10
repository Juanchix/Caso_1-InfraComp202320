public class Producto {
    private boolean entregado;
    private int productor;
    private int padre;

    //constructor
    public Producto(int productor, int padre){
        this.entregado = false;
        this.productor = productor;
        this.padre = padre;
    }

    //Metodos setters
    public void entregar(){
        this.entregado = true;
    }
    //Metodos getters
    public boolean getEntregado(){
        return entregado;
    }
    public int getPadre()
    {
        return this.padre;
    }
        public int getID()
    {
        return this.productor;
    }
}