package clases;

import java.util.*;

import clases.TablaDispersa;
import clases.Equipo;

public class Liga {
    private TablaDispersa equipos;
    private Set<String> partidosJugadosIda;
    private Set<String> partidosJugadosVuelta;

    public Liga() {
        this.equipos = new TablaDispersa(); 
        this.partidosJugadosIda = new HashSet<>();
        this.partidosJugadosVuelta = new HashSet<>();
    }

    public boolean agregarEquipo(String id, String nombre) {
        // Comprobamos si ya existe el equipo en la tabla dispersa
        if (equipos.buscar(id) != null) {
            return false; // El equipo ya existe
        }
        Equipo nuevoEquipo = new Equipo(nombre);
        equipos.insertar(nuevoEquipo); // Insertamos el nuevo equipo en la tabla dispersa
        return true;
    }

    public void simularLiga() {
        List<Equipo> listaEquipos = obtenerEquipos();
        int numEquipos = listaEquipos.size();

        // Si el número de equipos es impar, añadimos un equipo ficticio ("Descanso")
        boolean tieneEquipoImpar = (numEquipos % 2 != 0);
        if (tieneEquipoImpar) {
            listaEquipos.add(new Equipo("Descanso")); // Equipo ficticio
            numEquipos++;
        }

        // Simulamos primero las jornadas de ida
        System.out.println("\n--- Fase de Ida ---");
        generarJornadas(listaEquipos, numEquipos, true);

        // Barajamos los equipos para empezar la fase de vuelta con un orden aleatorio
        System.out.println("\n--- Fase de Vuelta ---");
        Collections.shuffle(listaEquipos); // Barajar equipos aleatoriamente
        generarJornadas(listaEquipos, numEquipos, false);

        // Quitamos el equipo ficticio "Descanso" si fue añadido
        if (tieneEquipoImpar) {
            listaEquipos.remove(listaEquipos.size() - 1);
        }
    }

    private List<Equipo> obtenerEquipos() {
        List<Equipo> listaEquipos = new ArrayList<>();
        Equipo[] equiposArray = equipos.getEquipos();

        for (Equipo equipo : equiposArray) {
            if (equipo != null) {
                listaEquipos.add(equipo);
            }
        }
        return listaEquipos;
    }

    private void generarJornadas(List<Equipo> listaEquipos, int numEquipos, boolean esIda) {
        List<Equipo> rotacion = new ArrayList<>(listaEquipos);
        boolean invertirLocalVisitante = false; // Alternancia de local y visitante

        for (int jornada = 1; jornada < numEquipos; jornada++) {
            System.out.println("\nJornada " + jornada + (esIda ? " (Ida)" : " (Vuelta)"));

            for (int i = 0; i < numEquipos / 2; i++) {
                Equipo local = rotacion.get(i);
                Equipo visitante = rotacion.get(numEquipos - 1 - i);

                // Ignoramos partidos donde participe "Descanso"
                if (local.getNombre().equals("Descanso") || visitante.getNombre().equals("Descanso")) {
                    continue;
                }

                // Alternancia de local y visitante cada jornada
                if (invertirLocalVisitante) {
                    Equipo temp = local;
                    local = visitante;
                    visitante = temp;
                }

                // Generamos goles aleatorios
                Random random = new Random();
                int golesLocal = random.nextInt(8);
                int golesVisitante = random.nextInt(8);

                // Mostrar el partido y actualizar los resultados
                actualizarResultados(local, visitante, golesLocal, golesVisitante);
                System.out.println(local.getNombre() + " " + golesLocal + " - " + golesVisitante + " " + visitante.getNombre());
            }

            // Rotamos los equipos para la próxima jornada
            rotarEquipos(rotacion);

            // Cambiamos la alternancia de local y visitante para la próxima jornada
            invertirLocalVisitante = !invertirLocalVisitante;
        }
    }

    // Método para rotar los equipos (manteniendo el primer equipo fijo)
    private void rotarEquipos(List<Equipo> equipos) {
        if (equipos.size() <= 2) return;
        Equipo fijo = equipos.get(0); // El primer equipo queda fijo
        Equipo ultimo = equipos.remove(equipos.size() - 1); // Extraemos el último equipo
        equipos.add(1, ultimo); // Lo colocamos después del primero
    }

    private void actualizarResultados(Equipo local, Equipo visitante, int golesLocal, int golesVisitante) {
        local.setGolesFavor(local.getGolesFavor() + golesLocal);
        local.setGolesContra(local.getGolesContra() + golesVisitante);

        visitante.setGolesFavor(visitante.getGolesFavor() + golesVisitante);
        visitante.setGolesContra(visitante.getGolesContra() + golesLocal);

        if (golesLocal > golesVisitante) {
            local.setPuntos(local.getPuntos() + 3);
            local.incrementarVictorias();
        } else if (golesLocal < golesVisitante) {
            visitante.setPuntos(visitante.getPuntos() + 3);
            visitante.incrementarVictorias();
        } else {
            local.setPuntos(local.getPuntos() + 1);
            visitante.setPuntos(visitante.getPuntos() + 1);
        }
    }

    public List<Equipo> obtenerClasificacion() {
        List<Equipo> listaEquipos = obtenerEquipos();
        listaEquipos.sort((e1, e2) -> {
            if (e1.getPuntos() != e2.getPuntos()) {
                return Integer.compare(e2.getPuntos(), e1.getPuntos());
            } else if (e1.getDiferenciaGoles() != e2.getDiferenciaGoles()) {
                return Integer.compare(e2.getDiferenciaGoles(), e1.getDiferenciaGoles());
            } else {
                return Integer.compare(e2.getGolesFavor(), e1.getGolesFavor());
            }
        });
        for (Equipo equipo : listaEquipos) {
            System.out.printf(
                "%-20s %-10d %-15d %-15d %-15d %-15d\n",
                equipo.getNombre(),
                equipo.getPuntos(),
                equipo.getGolesFavor(),
                equipo.getGolesContra(),
                equipo.getDiferenciaGoles(),
                equipo.getVictorias()
            );
        }

        return listaEquipos;
    }

    public TablaDispersa getEquipos() {
        return equipos;
    }
}
