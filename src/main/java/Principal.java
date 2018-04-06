import java.util.Scanner;

/*
* Clase principal.
* Desde aqui se crea la maquina Tragamonedas y se llaman sus respectivas funciones.
*
* @author Christian Farias Aguila.
* Horas Hombre: ~2.3
*/

public class Principal {

    private static final int NUM_RUEDAS = 3;
    private static final int SALDO = 1000;

    public static void main(String[] args) {

        //Si no se cumplen estos requerimientos minimos, el juego no puede empezar.
        if (NUM_RUEDAS < 3 || SALDO <= 0)
            return;

        Scanner entrada = new Scanner(System.in);

        Tragamonedas tragamonedas = new Tragamonedas(NUM_RUEDAS, SALDO);

        while (tragamonedas.obtenerEstado() == Tragamonedas.Estado.ENCENDIDO) {

            tragamonedas.mostrarSaldoPedirApuesta();
            try {
                tragamonedas.ingresarApuesta(entrada.nextInt());

            } catch (Exception e) {   //En caso de Input Mismatch.
                tragamonedas.mostrarError();
                entrada.next();
            }

        }

    }
}


