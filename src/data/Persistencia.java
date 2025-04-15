package data;

import domain.*;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;

public class Persistencia {
    private static ArrayList<Sector> sectores = new ArrayList<>();
    private static String [] sectoresHerbivoro = {"1","3"};
    private static String [] sectoresCarnivoro = {"2","4"};

    public static String[] getSectoresHerbivoro() {
        return sectoresHerbivoro;
    }

    public static void setSectoresHerbivoro(String[] sectoresHerbivoro) {
        Persistencia.sectoresHerbivoro = sectoresHerbivoro;
    }

    public static String[] getSectoresCarnivoro() {
        return sectoresCarnivoro;
    }

    public static void setSectoresCarnivoro(String[] sectoresCarnivoro) {
        Persistencia.sectoresCarnivoro = sectoresCarnivoro;
    }

    private static void inicializarSectores() {
        Empleado raul = new Empleado("Raul A", "20111222", "Tucumán" );
        Empleado maria = new Empleado("Maria B", "30111222", "Tucumán" );
        sectores.add(new Sector(1, -26.250724, -65.522827, 10, TipoAlimentacion.HERBIVORO, raul));
        sectores.add(new Sector(2, -26.252359, -65.521511, 10, TipoAlimentacion.CARNIVORO, maria));
        sectores.add(new Sector(3, -26.254661, -65.522726, 10, TipoAlimentacion.HERBIVORO, maria));
        sectores.add(new Sector(4, -26.257250, -65.523514, 10, TipoAlimentacion.CARNIVORO, raul));
    }
    

    public static void inicializar() throws InvalidPropertiesFormatException{
        inicializarSectores();
    }


    public static ArrayList<Sector> getSectores() {
        return sectores;
    }

}
