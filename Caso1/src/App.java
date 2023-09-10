import java.util.Scanner;

public class App{
    
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de productores: ");
        int N = scanner.nextInt();

        System.out.print("Ingrese el número de repartidores: ");
        int M = scanner.nextInt();

        System.out.print("Ingrese la capacidad de la bodega: ");
        int TAM = scanner.nextInt();

        System.out.print("Ingrese el número total de productos a producir: ");
        int cantidadProductos = scanner.nextInt();

        scanner.close();

        // Instancias de clases
        Bodega bodega = new Bodega(TAM);
        Bodega despacho = new Bodega(1);

        // Crear e iniciar Threads de productores
        for (int i = 0; i < N; i++) {
            Productor productor = new Productor(i, cantidadProductos, bodega);
            productor.start(); 
        }

        // Crear e iniciar Threads de despachadores
        for (int i = 0; i < M; i++) {
            Despachador despachador = new Despachador(M, bodega, despacho);
            despachador.start();
        }

        // Crear e iniciar Threads de repartidores
        for (int i = 0; i < M; i++) {
            Repartidor repartidor = new Repartidor(despacho);
            repartidor.start();
        }
    }
}
