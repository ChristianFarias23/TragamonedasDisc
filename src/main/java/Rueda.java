public class Rueda {

    private int valor;

    public Rueda(){
        this.valor = (int)(Math.random()*10);
    }

    public void girarRueda(){
        this.valor = (int)(Math.random()*10);
    }

    public int obtenerValor(){
        return this.valor;
    }

}
