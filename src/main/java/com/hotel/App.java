package com.hotel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Preu total temporal
    public static float preu_total = 0;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();
            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
       //TODO:
       switch (opcio) {
           case 1:
               reservarHabitacio();
               break;
           case 2:
               alliberarHabitacio();
               break;
           case 3:
               consultarDisponibilitat();
               break;
           case 4:
               obtindreReservaPerTipus();
               break;
           case 5:
               obtindreReserva();
               break;
           case 6:
               // Eixir
               break;
           default:
               System.out.println("Opció no vàlida. Torneu a intentar-ho.");
       }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */

    public static void reservarHabitacio() {

        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        //TODO:
        preu_total = 0;
        String tipus_habitacio = seleccionarTipusHabitacioDisponible();
        disponibilitatHabitacions.put(tipus_habitacio, disponibilitatHabitacions.get(tipus_habitacio) - 1);
        ArrayList<String> serveis_seleccionats = seleccionarServeis();
        calcularPreuTotal(tipus_habitacio, serveis_seleccionats);
        int codi_reserva = generarCodiReserva();

        //NO ENTIENDO ESTO
        ArrayList<String> dadesReserva = new ArrayList<>();
        dadesReserva.add(tipus_habitacio);
        dadesReserva.add(Float.toString(preu_total));
        dadesReserva.addAll(serveis_seleccionats);

        reserves.put(codi_reserva, dadesReserva);

        System.out.println("Codi de reserva generat: " + codi_reserva);
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        //TODO:
        String tipus_habitacio = "";
        int opcio_hab = llegirEnter("Seleccione tipus d'habitacio: ");
        switch (opcio_hab) {
            case 1:
                tipus_habitacio = TIPUS_ESTANDARD;
                break;
            case 2:
                tipus_habitacio = TIPUS_SUITE;
                break;
            case 3:
                tipus_habitacio = TIPUS_DELUXE;
                break;
        
            default:
                System.out.println("Opció no vàlida. Torneu a intentar-ho.");
        }
        return tipus_habitacio;
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {
        //TODO:
        System.out.println("\nTipus d'habitació disponibles:");
        mostrarInfoTipus(TIPUS_ESTANDARD);
        mostrarInfoTipus(TIPUS_SUITE);
        mostrarInfoTipus(TIPUS_DELUXE);
        String tipus_seleccionat = seleccionarTipusHabitacio();
        if (disponibilitatHabitacions.get(tipus_seleccionat) > 0) {
            return tipus_seleccionat;
        }else {
            System.out.println("No hi ha habitacions disponibles d'aquest tipus.");
            return null;   
        }
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        //TODO:
        System.out.println("Serveis addicionals (0-4):");
        System.out.println("0.Finalitzar selecció de serveis");
        System.out.println("1.Esmorzar - "+preusServeis.get(SERVEI_ESMORZAR)+"€");
        System.out.println("2.Gimnàs - "+preusServeis.get(SERVEI_GIMNAS)+"€");
        System.out.println("3.Spa - "+preusServeis.get(SERVEI_SPA)+"€");
        System.out.println("4.Piscina - "+preusServeis.get(SERVEI_PISCINA)+"€");
        /*Generamos un HashMap de int para almacenar las opciones 
        de servicios seleccionados y evitar repeticiones
        */
        ArrayList<String> serveis_seleccionats = new ArrayList<>();
        char servei_sn = 's';
        do{
            System.out.print("Vol afegir un servei?(s/n): ");
            servei_sn = sc.next().charAt(0);
            if (servei_sn == 'n') {
                System.out.println("Finalitzant selecció de serveis.");
                System.out.println("Calculem el total...");
            }if (servei_sn == 's'){
            int opcio_servei = llegirEnter("Seleccione servei: ");
            
                switch (opcio_servei) {
                case 1:
                    if (!serveis_seleccionats.contains(SERVEI_ESMORZAR)) {
                        serveis_seleccionats.add(SERVEI_ESMORZAR);
                        preu_total += preusServeis.get(SERVEI_ESMORZAR);
                        System.out.println("Servei afegit: Esmorzar");
                    } else {
                        System.out.println("Servei ja seleccionat.");
                    }
                    break;

                case 2:
                    if (!serveis_seleccionats.contains(SERVEI_GIMNAS)) {
                        serveis_seleccionats.add(SERVEI_GIMNAS);
                        preu_total += preusServeis.get(SERVEI_GIMNAS);
                        System.out.println("Servei afegit: Gimnàs");
                    } else {
                        System.out.println("Servei ja seleccionat.");
                    }
                    break;

                case 3:
                    if (!serveis_seleccionats.contains(SERVEI_SPA)) {
                        serveis_seleccionats.add(SERVEI_SPA);
                        preu_total += preusServeis.get(SERVEI_SPA);
                        System.out.println("Servei afegit: Spa");
                    } else {
                        System.out.println("Servei ja seleccionat.");
                    }
                    break;

                case 4:
                    if (!serveis_seleccionats.contains(SERVEI_PISCINA)) {
                        serveis_seleccionats.add(SERVEI_PISCINA);
                        preu_total += preusServeis.get(SERVEI_PISCINA);
                        System.out.println("Servei afegit: Piscina");
                    } else {
                        System.out.println("Servei ja seleccionat.");
                    }
                    break;

                    case 0 :
                        System.out.println("Finalitzant selecció de serveis.");
                        System.out.println("Calculem el total...");
                        servei_sn = 'n';
                        break;
                
                    default:
                        System.out.println("Opció no vàlida. Torneu a intentar-ho.");
                }
            }else {
                System.out.println("Opció no vàlida. Torneu a intentar-ho.");
            }

        } while (servei_sn != 'n');
        
        return serveis_seleccionats;
        }
    

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveis_seleccionats) {
        //TODO:
        System.out.println("Calculant preu total...");
        System.out.println("Preu habitació: " + preusHabitacions.get(tipusHabitacio) + "€");
        System.out.println("Preu serveis seleccionats: " + preu_total + "€");
        preu_total += preusHabitacions.get(tipusHabitacio);
        float IVA_amount = preu_total * IVA;
        System.out.println("IVA (21%): " + IVA_amount + "€");
        preu_total += preu_total * IVA;
        System.out.println("Preu total: " + preu_total + "€");
        return 0;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        //TODO:

        int codi = random.nextInt(900) + 100;
        if(reserves.containsKey(codi)){
            return generarCodiReserva();
        }

        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
         // TODO: Demanar codi, tornar habitació i eliminar reserva
         int codi = llegirEnter("Introdueix el codi de reserva: ");
         if (reserves.containsKey(codi)) {
             String tipus_habitacio = reserves.get(codi).get(0);
             disponibilitatHabitacions.put(tipus_habitacio, disponibilitatHabitacions.get(tipus_habitacio) + 1);
             reserves.remove(codi);
             System.out.println("Habitació alliberada correctament.");
             System.out.println("Disponibilitat actualitzada.");
         } else {
             System.out.println("Codi de reserva no vàlid.");
         }
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades
        
        System.out.println("\n===== DISPONIBILITAT HABITACIONS =====");
        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);
    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
         // TODO: Implementar recursivitat
    }

    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta
        int codi = llegirEnter("Introdueix el codi de reserva: ");
        if (reserves.containsKey(codi)) {
            mostrarDadesReserva(codi);
        } else {
            System.out.println("No s'ha trobat cap reserva amb aquest codi.");
        }
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus
            System.out.println("Seleccione tipus:");
            System.out.println("1. Estàndard");
            System.out.println("2. Suite");
            System.out.println("3. Deluxe");
             int opcio = llegirEnter("Opció: ");
             
             String tipus_seleccionat = "";
             switch (opcio) {
                 case 1:
                     tipus_seleccionat = TIPUS_ESTANDARD;
                     break;
                 case 2:
                     tipus_seleccionat = TIPUS_SUITE;
                     break;
                 case 3:
                     tipus_seleccionat = TIPUS_DELUXE;
                     break;
                 default:
                     System.out.println("Opció no vàlida.");
                     return;
             }
             
             System.out.println("Reserves de tipus " + tipus_seleccionat + ":");
             boolean trobada = false;
             //Foeach per recórrer les reserves
             for (int codi : reserves.keySet()) {
                 if (reserves.get(codi).get(0).equals(tipus_seleccionat)) {
                     System.out.println("Codi: " + codi);
                     mostrarDadesReserva(codi);
                     System.out.println();
                     trobada = true;
                 }
             }
             if (!trobada) {
                 System.out.println("No hi ha reserves d'aquest tipus.");
             }
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
       // TODO: Imprimir tota la informació d'una reserva
        String tipus_habitacio = reserves.get(codi).get(0);
        String preu_total_str = reserves.get(codi).get(1);
        System.out.println("Tipus d'habitació: " + tipus_habitacio);
        System.out.println("Preu total: " + preu_total_str + "€");
        System.out.println("Serveis addicionals:");
        
        for (int i = 2; i < reserves.get(codi).size(); i++) {
            System.out.println("- " + reserves.get(codi).get(i));
        }

    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
                System.out.print(missatge);
                valor = sc.nextInt();
                correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
    }
}