import java.util.Scanner;

public class App{
    
    //Atributos static 
    public static Bodega bodega;
    public static Bodega despacho;

    public static void main(String[] args){ 
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de productores: ");
        int N = scanner.nextInt();

        System.out.print("Ingrese el número de repartidores: ");
        int M = scanner.nextInt();

        System.out.print("Ingrese la capacidad de la bodega: ");
        int TAM = scanner.nextInt();

        // Instancias de clases
        
        //Crear buffers 
        bodega = new Bodega(TAM);
        despacho = new Bodega(1);
        
        //Crear Productores
        int totalProductos=0;
        for (int i = 0; i<N; i++)
        {
            int cantidadProductos;
            System.out.println("Ingrese el número total de productos que creara el productor "+(i+1)+": ");
            cantidadProductos= scanner.nextInt();
            Productor productor = new Productor((i+1), cantidadProductos, bodega);
            System.out.println("Productor "+(i+1)+" ha sido creado");
            productor.start();
            
            totalProductos+=cantidadProductos;
        }

        //Crear Despachador
        Despachador despachador = new Despachador(M, bodega, despacho, totalProductos);
        System.out.println("Despachador ha sido creado");
        despachador.start();
        
        //Crear Repartidores
        for(int i=0; i<M; i++)
        {
            Repartidor repartidor = new Repartidor((i+1),despacho, despachador);
            System.out.println("Repartidor "+(i+1)+" ha sido creado");
            despachador.agregarRepartidor(repartidor);
            repartidor.start();
        }
        scanner.close();
    }
}

