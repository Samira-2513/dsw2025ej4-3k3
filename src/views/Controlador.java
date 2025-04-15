package views;

import data.Persistencia;
import domain.*;

import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controlador {
    private static final ArrayList<Mamifero> animales = new ArrayList<>();
    private static final ArrayList<Sector> sectores = Persistencia.getSectores();

    public static TipoAlimentacion[] getTiposAlimentacion(){
        return  TipoAlimentacion.values();
    }
 
    public static double getTotalComida(TipoAlimentacion tipoAlimentacion) {
        double total = 0;
        for(Mamifero animal : animales){
            total += animal.TieneAlimentacion(tipoAlimentacion) ? animal.calcularCantidadDeComida() : 0;
        }
        return total;
    }
    
    public static ArrayList<Mamifero> getAnimalesMamifero() {
        return animales;
    }
    
    public static ArrayList<AnimalViewModel> getAnimalesView(){
        ArrayList<AnimalViewModel> animales = new ArrayList<>();
        for(Mamifero animal : getAnimalesMamifero()){
            animales.add(new AnimalViewModel(animal));
        }
        return animales;
    }
    
    public static ComidaViewModel  calcularComida(){
        double totalCarnivoros = getTotalComida(TipoAlimentacion.CARNIVORO);
        double totalHerbivoros = getTotalComida(TipoAlimentacion.HERBIVORO);
        return new ComidaViewModel(totalCarnivoros, totalHerbivoros);
    }

    static void agregarAnimalNuevo(JTextField nombreText, JTextField paisText, JTextField pesoText, 
            JComboBox<String> tipoAlimen, int sector,JTextField extra,JTextField edad,JTextField codIso, double valorFijo) {
        try {
            String pais = paisText.getText().trim();
            if (pais.isEmpty()) throw new IllegalArgumentException("El campo 'pais' está vacio.");

            String cod = codIso.getText().trim();
            if (cod.isEmpty()) throw new IllegalArgumentException("El campo 'codigo ISO' esta vacio.");

            String n = nombreText.getText().trim();
            if (n.isEmpty()) throw new IllegalArgumentException("El campo 'nombre' esta vacio.");

            int ed;
            try {
                ed = Integer.parseInt(edad.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("La edad debe ser un numero entero.");
            }

            double p;
            try {
                p = Double.parseDouble(pesoText.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El peso debe ser un numero valido (puede tener decimales).");
            }

            double ex;
            try {
                ex = Double.parseDouble(extra.getText().trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("El valor extra debe ser un numero valido (puede tener decimales).");
            }
            

            if (tipoAlimen.getSelectedItem().equals("Carnivoro")) {
                agregarAnimalCarnivoro(n, ed, p, ex, sector, pais, cod);
            } else {
                agregarAnimalHerbivoro(n, ed, p, ex, sector, pais, cod,valorFijo);
            }

            JOptionPane.showMessageDialog(null, "Animal agregado correctamente.");

        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Datos invalidos", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error inesperado. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void agregarAnimalHerbivoro(String n,int ed, double p, double ex, int s,
            String pais,String cod,double val) throws InvalidPropertiesFormatException {
        
        Pais paisAnim= new Pais(pais,cod);
        Especie esp= new Especie(n,TipoAlimentacion.HERBIVORO,ex);
        Herbivoro h=new Herbivoro(ed,p,esp,sectores.get(s-1),val,paisAnim);
        animales.add(h);
    }

    private static void agregarAnimalCarnivoro(String n,int ed, double p, double ex, int s,
            String pais, String cod) throws InvalidPropertiesFormatException {
        Especie esp= new Especie(n,TipoAlimentacion.CARNIVORO,ex);
        Pais paisAnim=new Pais(pais,cod);
        Carnivoro car= new Carnivoro(ed,p,esp,sectores.get(s-1),paisAnim);
        animales.add(car);
    }


}
