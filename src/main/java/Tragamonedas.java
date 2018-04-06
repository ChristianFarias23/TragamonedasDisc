
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Tragamonedas {

    //Estado de la maquina tragamonedas.
    public enum Estado {
        ENCENDIDO,
        SIN_SALDO,
        SALIR
    }

    private Estado estado;
    private int saldo;
    private Rueda[] ruedas;
    private int numRuedas;

    //Constructor de la clase, requiere el numero de ruedas y el saldo inicial que tendra la maquina tragamonedas.
    public Tragamonedas(int numRuedas, int saldo){
        this.numRuedas= numRuedas;
        this.saldo = saldo;
        this.ruedas = new Rueda[numRuedas];
        for (int i = 0; i < numRuedas; i++)
            ruedas[i] = new Rueda();

        this.estado = Estado.ENCENDIDO;
        System.out.println("Bienvenido!");
    }

    //Funcion que muestra por pantalla el saldo actual y luego pide la apuesta.
    public void mostrarSaldoPedirApuesta(){
        System.out.println("\nSu saldo es de $" + this.saldo + ".");
        System.out.println("¿Cuanto desea apostar?");
    }

    //Metodo que obtiene la apuesta del jugador y evalua que hacer en cada caso.
    public void ingresarApuesta(int apuesta){
        if (apuesta <= this.saldo && apuesta > 0){
            this.saldo -= apuesta;
            girarRuedas();
            mostrarRuedas();
            this.saldo += calcularPremio(apuesta);

            if (this.saldo == 0) {
                this.estado = Estado.SIN_SALDO;
                terminar();
            }

        }else if (apuesta == 0){
            this.estado = Estado.SALIR;
            terminar();
        }else {
            System.out.println("Debe ingresar un valor mayor a $0 y menor o igual a $"+ this.saldo + "!");
        }
    }

    //Dependiendo del estado de la maquina tragamonedas, termina el juego y muestra el mensaje correspondiente.
    private void terminar(){

        switch (this.estado){
            case SALIR:

                GregorianCalendar calendario = new GregorianCalendar();

                try {
                    Date inicioDia = new SimpleDateFormat("HH:mm").parse("06:00");
                    Date finDia = new SimpleDateFormat("HH:mm").parse("11:59");
                    Date inicioTarde = new SimpleDateFormat("HH:mm").parse("12:00");
                    Date finTarde = new SimpleDateFormat("HH:mm").parse("19:59");
                    Date inicioNoche = new SimpleDateFormat("HH:mm").parse("20:00");
                    Date finNoche = new SimpleDateFormat("HH:mm").parse("05:59");

                    Date ahora = new SimpleDateFormat("HH:mm").parse(calendario.get(Calendar.HOUR_OF_DAY) + ":" + calendario.get(Calendar.MINUTE));

                    String saludo = "<Saludo>";

                    if (ahora.after(inicioDia) && ahora.before(finDia))
                        saludo = "Buenos dias";
                    else if (ahora.after(inicioTarde) && ahora.before(finTarde))
                        saludo = "Buenas tardes";
                    else if (ahora.after(inicioNoche) || ahora.before(finNoche))
                        saludo = "Buenas noches";

                    System.out.println(saludo + ", gracias por jugar. Su saldo final es de $"+saldo+".");

                } catch (Exception e) {
                    //No deberia ocurrir (ParseException).
                }
                break;

            case SIN_SALDO:
                System.out.println("Muchas gracias por jugar. Mejor suerte la proxima vez!");
                break;
        }

    }

    //Metodo que muestra por pantalla los valores de las ruedas en el formato requerido.
    private void mostrarRuedas(){   //Puede ser publico si se quiere utilizar desde el main.
        String lineaSeparadora = "-";
        String valoresRuedas = "|";

        for (Rueda r : ruedas){
            lineaSeparadora += "----";
            valoresRuedas += " ";

            if (r.obtenerValor() == 0)
                valoresRuedas += "*";
            else
                valoresRuedas += r.obtenerValor();

            valoresRuedas += " |";
        }

        System.out.println(lineaSeparadora);    //-------------
        System.out.println(valoresRuedas);      //| x | y | z |
        System.out.println(lineaSeparadora);    //-------------

    }

    //Metodo que permite actualizar los valores de las ruedas.
    private void girarRuedas(){
        //Puede ser publico, pero, en este caso, es un metodo interno que
        //se realiza solo despues de ingresar la apuesta; Por lo tanto es
        //un metodo privado.

        for (Rueda r : ruedas)
            r.girarRueda();
    }

    //Funcion que calcula y retorna el premio a recibir segun las reglas.
    private int calcularPremio(int apuesta){

        int premio = 0;
        int[] nums = new int[numRuedas];
        int cantidadEstrellas = 0;

        for (int i = 0 ; i < numRuedas ; i++) {
            nums[i] = ruedas[i].obtenerValor();
            if (nums[i] == 0)
                cantidadEstrellas++;
        }

        switch (cantidadEstrellas){
            case 0:
                boolean distinto = false;

                for (int i = 0 ; i < numRuedas - 1; i++){
                    for (int j = i+1 ; j < numRuedas; j++){
                        if (ruedas[i].obtenerValor() != ruedas[j].obtenerValor()) {
                            distinto = true;
                            break;
                        }
                    }
                    if (distinto)
                        break;
                }

                if (!distinto)
                    premio = ruedas[0].obtenerValor()*apuesta;

                break;
            case 1:
                premio = 50;
                break;
            case 2:
                premio = 300;
                break;
            case 3:
                premio = 500;
                break;
        }

        if (premio != 0)
            System.out.println("Usted obtiene $"+premio+".");

        return premio;
    }

    //Muestra el error en caso de ingresar un valor no permitido.
    public void mostrarError(){
        System.out.println("Error, Ingrese un valor valido!");
    }

    //Obtiene el estado de la maquina tragamonedas.
    public Estado obtenerEstado(){
        return this.estado;
    }



}
