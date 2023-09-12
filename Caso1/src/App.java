import java.util.Scanner;

public class App{
    
    //Atributos static 
    public static Bodega bodega;
    public static Bodega despacho;

    public static void main(String[] args){ 
        Scanner scanner = new Scanner(System.in);

        System.out.println("****************************************************************");
        System.out.println("***        Manejo de concurrencia - Caso 1 InfraComp         ***");
        System.out.println("****************************************************************");

        System.out.print("\n║ Ingrese el número de productores -->  ");
        int N = scanner.nextInt();

        System.out.print("\n║ Ingrese el número de repartidores -->  ");
        int M = scanner.nextInt();

        System.out.print("\n║ Ingrese la capacidad de la bodega -->  ");
        int TAM = scanner.nextInt();

        // Instancias de clases
        
        //Crear buffers 
        bodega = new Bodega(TAM);
        despacho = new Bodega(1);

        //System.out.println("");
        //System.out.println("El tamaño de la bodega es: "+bodega.getProductos().size());
        //System.out.println("El tamaño de la despacho es: "+despacho.getProductos().size());
        //System.out.println("");
        
        //Crear Productores
        int totalProductos=0;
        for (int i = 0; i<N; i++)
        {
            int cantidadProductos;
            System.out.println("\n║ Ingrese el número total de productos que creará el productor "+(i+1)+" -->  ");
            cantidadProductos= scanner.nextInt();
            Productor productor = new Productor((i+1), cantidadProductos, bodega);
            System.out.println("+   El productor "+(i+1)+" ha sido creado");
            productor.start();
            
            totalProductos+=cantidadProductos;
        }
        
        //System.out.println("");
        //System.out.println("El tamaño de la bodega es: "+bodega.getProductos().size());
        //System.out.println("El tamaño de la despacho es: "+despacho.getProductos().size());
        //System.out.println("");

        //Crear Despachador
        Despachador despachador = new Despachador(M, bodega, despacho, totalProductos);
        System.out.println("+   El despachador ha sido creado");
        despachador.start();

        //System.out.println("");
        //System.out.println("El tamaño de la bodega es: "+bodega.getProductos().size());
        //System.out.println("El tamaño de la despacho es: "+despacho.getProductos().size());
        //System.out.println("");
        
        //Crear Repartidores
        for(int i=0; i<M; i++)
        {
            Repartidor repartidor = new Repartidor((i+1),despacho, despachador);
            System.out.println("+   El repartidor "+(i+1)+" ha sido creado");
            despachador.agregarRepartidor(repartidor);
            repartidor.start();
        }
        //System.out.println("");
        //System.out.println("El tamaño de la bodega es: "+bodega.getProductos().size());
        //System.out.println("El tamaño de la despacho es: "+despacho.getProductos().size());
        //System.out.println("");
        
        scanner.close();
    }
}

