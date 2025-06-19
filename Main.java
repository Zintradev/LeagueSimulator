package main;
import clases.Liga;
import java.util.Scanner;

public class Main {

    private Liga liga;
    private Scanner scanner;

    public Main() {
        this.liga = new Liga();
        this.scanner = new Scanner(System.in);
        // Cargar equipos de ejemplo al iniciar
        cargarEquiposDeEjemplo();
    }

    private void cargarEquiposDeEjemplo() {
        // Agregar 6 equipos de ejemplo para no tener que hacer la prueba manualmente en cada prueba
        liga.agregarEquipo("E1", "Real Madrid");
        liga.agregarEquipo("E2", "Barcelona");
        liga.agregarEquipo("E3", "Atlético Madrid");
        liga.agregarEquipo("E4", "Sevilla");
        liga.agregarEquipo("E5", "Valencia");
        liga.agregarEquipo("E6", "Betis");
        liga.agregarEquipo("E7", "Murcia");
        liga.agregarEquipo("E8", "Malaga");
        
    }

    public void mostrarMenu() {
        int opcion;

        do {
            System.out.println("\n--- Menú de Opciones ---");
            System.out.println("1. Insertar equipo");
            System.out.println("2. Simulación");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            if (opcion == 1) {
                insertarEquipo();
            } else if (opcion == 2) {
                simulacion();
                break;
            } else {
                System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (true);
    }

    private void insertarEquipo() {
        System.out.print("Ingrese el identificador del equipo: ");
        String id = scanner.nextLine();
        System.out.print("Ingrese el nombre del equipo: ");
        String nombre = scanner.nextLine();

        if (liga.agregarEquipo(id, nombre)) {
            System.out.println("Equipo registrado exitosamente.");
        } else {
            System.out.println("Error: El equipo ya está registrado.");
        }
    }

    private void simulacion() {
        if (liga.getEquipos().size() < 2) {
            System.out.println("Error: Se necesitan al menos dos equipos para iniciar la simulación.");
            return;
        }

        liga.simularLiga();
        mostrarClasificacion();
    }

    private void mostrarClasificacion() {
        System.out.println("\n--- Clasificación Final ---");
        System.out.printf("%-20s %-10s %-15s %-15s %-15s %-15s\n", "Equipo", "Puntos", "Goles a Favor", "Goles en Contra", "Diferencia de Goles", "Victorias");

        liga.obtenerClasificacion().forEach(equipo -> System.out.printf(
            "%-20s %-10d %-15d %-15d %-15d %-15d\n",
            equipo.getNombre(),
            equipo.getPuntos(),
            equipo.getGolesFavor(),
            equipo.getGolesContra(),
            equipo.getDiferenciaGoles(),
            equipo.getVictorias()
        ));
    }


    public static void main(String[] args) {
        Main liga = new Main();
        liga.mostrarMenu();
    }
}
