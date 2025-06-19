package clases;

public class Equipo {
    private String id;    
    private String nombre;
    private int puntos;
    private int golesFavor;
    private int golesContra;
    private int victorias;

    // Constructor que acepta solo el nombre 
    public Equipo(String nombre) {
        this.id = generarIdUnico();  
        this.nombre = nombre;
        this.puntos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.victorias = 0;
    }

    // Constructor que acepta id y nombre 
    public Equipo(String id, String nombre) {
        this.id = id;  
        this.nombre = nombre;
        this.puntos = 0;
        this.golesFavor = 0;
        this.golesContra = 0;
        this.victorias = 0;
    }

    // Método para generar un ID único si no se proporciona uno
    private String generarIdUnico() {
        return "ID-" + System.nanoTime();  // Generar un ID basado en el tiempo actual (como ejemplo)
    }

    // Getters y setters
    public String getId() {
        return id;
    }


    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public void setGolesFavor(int golesFavor) {
        this.golesFavor = golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public void setGolesContra(int golesContra) {
        this.golesContra = golesContra;
    }

    public int getVictorias() {
        return victorias;
    }

    public void incrementarVictorias() {
        this.victorias++;
    }

    // Calcula la diferencia de goles
    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }

    @Override
    public String toString() {
        return nombre + ", " + puntos + ", " + getDiferenciaGoles();
    }
}

