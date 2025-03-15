public class Asesor implements Trabajador { //las clases abstractas tienen por lo menos un metodo abstracto

    private double ventas;

    public Asesor(double ventas){
        this.ventas=ventas;
    }

    public  double getVentas(){
        return ventas;
    }
        
    

    public double pagar(){
        return ventas * 1.30;
    }
}