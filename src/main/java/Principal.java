import java.util.Scanner;

/*
* Clase principal.
*
* @author Christian Farias Aguila.
* Horas Hombre: ~2.3
*/

public class Principal {

    private static final int numRuedas = 3;
    private static final int saldo = 1000;

    public static void main(String[] args) {

        if (numRuedas < 3 || saldo <= 0)
            return;

        Scanner entrada = new Scanner(System.in);

        Tragamonedas tragamonedas = new Tragamonedas(numRuedas, saldo);

        while (tragamonedas.obtenerEstado() == Tragamonedas.Estado.ENCENDIDO) {
            while (true) {

                tragamonedas.mostrarSaldoPedirApuesta();
                try {
                    tragamonedas.ingresarApuesta(entrada.nextInt());
                    break;
                } catch (Exception e) {   //En caso de Input Mismatch.
                    tragamonedas.mostrarError();
                    entrada.next();
                }
            }
        }

    }
}


