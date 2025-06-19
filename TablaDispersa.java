package clases;

import clases.Equipo;

public class TablaDispersa {
	//Tamaño de tabla 29 porque si el maximo de equipos es21, 21/29=0.72, cogemos 29 porque es preferible que sea primo
    static final int TAMTABLA = 29;
    private int numElementos;
    private double factorCarga;
    private Equipo[] tabla;

    public TablaDispersa() {
        // Inicializa la tabla y sus elementos a NULL
        tabla = new Equipo[TAMTABLA];
        for (int i = 0; i < TAMTABLA; i++) {
            tabla[i] = null;
        }
        // Inicializa los atributos a 0
        numElementos = 0;
        factorCarga = 0;
    }

    // Devuelve la posición o índice de la tabla libre para insertar
    public int hash(String clave) {
        int i = 0, p;
        long d;
        // Convierte la clave de dispersión a un valor entero
        d = transformaCadena(clave);

        // Aplica aritmética modular para obtener la dirección base
        p = (int) (d % TAMTABLA);

        // Bucle de exploración cuadrática
        while (tabla[p] != null && !tabla[p].getId().equals(clave)) {
            i++;
            p = p + i * i;
            p = p % TAMTABLA; // Considera el array como circular
        }
        return p;
    }

    private long transformaCadena(String clave) {
        // Método de multiplicación para realizar la transformación
        long d = 0;
        for (int j = 0; j < Math.min(10, clave.length()); j++) {
            d = d * 29 + (int) clave.charAt(j);
        }

        if (d < 0) d = -d;
        return d;
    }

    public void insertar(Equipo equipo) {
        int posicion = hash(equipo.getId());
        tabla[posicion] = equipo;
        numElementos++;
        factorCarga = (double) (numElementos) / TAMTABLA;
        if (factorCarga > 0.8) {
            System.out.println("\n#### EL FACTOR DE CARGA SUPERA EL 80%, conviene aumentar el tamaño");
        }
    }

    // Devuelve una referencia a un equipo si lo encuentra en la tabla y NULL si no lo encuentra
    public Equipo buscar(String clave) {
        int posicion = hash(clave);
        Equipo equipo = tabla[posicion];
        return equipo;
    }

    // Para eliminar, se sigue el mismo proceso que buscar y se establece el objeto a null
    public void eliminar(String clave) {
        int posicion = hash(clave);
        if (tabla[posicion] != null) {
            tabla[posicion] = null;
            numElementos--;
            factorCarga = (double) (numElementos) / TAMTABLA;
        }
    }

    public Equipo[] getEquipos() {
        return tabla;
    }

    // Método size que devuelve el número de elementos en la tabla
    public int size() {
        return numElementos;  // Retorna el número de elementos en la tabla dispersa
    }
}
