public class Ejecutar{

    public static void main(String[] args){

        Trabajador[] t = new Trabajador[2];
        t[ 0] = new Asesor(10000);
        t[ 1] = new Operario(10);

        for(Trabajador trabajador: t){
            System.out.println(trabajador.pagar()); //con esto puedo pagar una nomina de muchas personas
        }
// polimorfismo la capacidad que tiene un metodo de comportarse segun quien lo llame

    }
}