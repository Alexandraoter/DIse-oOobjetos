public class Operario implements Trabajador { //implements es como una absorción 
// implements se usa porque es una interfaz 
    public final int VALOR_HORA = 9000;
    private double horas;

    public Operario(double horas){
        this.horas = horas;
    }

    public double getHoras(){
        return horas;
    }

    public double pagar(){
            return horas * VALOR_HORA;
    }
}