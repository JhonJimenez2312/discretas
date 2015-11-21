package discretas;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author Jhon Jimenez
 */
public class SeriesDeFourier {

//n tambien llamado medida de paso para el metodo del trapecio compuesto entre mas grande sea mas exacto es el metodo
    public static int n = 10000;

    public static double funcion(int k, double x, double periodo, String cadena) {

        //ESTABLECEMOS EL VALOR DE LA FUNCION QUE QUEREMOS EVALUAR:
        //recuerde cambiar si es necesario los intervalos (Limites) en el metodo main
        //la funcion de ejemplo es x^2 en el intervalo de -Π a Π
        //como ejemplo tambien se usara x+Math.PI para no tener que cambiar de intervalo 
        double funcion = (Math.pow(x, 2));
        //definimos r en 0.0 ya que sera nuestra variable de resultado
        double r = 0.0;
        //funcion para a0
        if (cadena.equals("a0")) {
            //aux= 2/T = 2/periodo
            double aux = Double.parseDouble(divisionDecimalExacta(2.0, periodo).toString());

            r = (aux) * funcion;
        } else {
            if (cadena.equals("an")) {
                //aux= 2/T = 2/periodo
                double aux = Double.parseDouble(divisionDecimalExacta(2.0, periodo).toString());
                //aux2=(2*K*Π*t)/T=(2*K*Π*t)/Periodo
                //t es remplazada por x para facilitar su interpretacion
                double aux2 = Double.parseDouble(divisionDecimalExacta((2 * k * Math.PI * x), periodo).toString());

                r = (aux) * funcion * Math.cos(aux2);

            } else {
                if (cadena.equals("bn")) {
                    //aux= 2/T = 2/periodo
                    double aux = Double.parseDouble(divisionDecimalExacta(2.0, periodo).toString());
                    //aux2=(2*K*Π*t)/T=(2*K*Π*t)/Periodo
                    //t es remplazada por x para facilitar su interpretacion
                    double aux2 = Double.parseDouble(divisionDecimalExacta((2 * k * Math.PI * x), periodo).toString());

                    r = (aux) * funcion * Math.sin(aux2);
                }

            }

        }
        return r;
    }

    public static String trapecio(int k, double Lsuperior, double Linferior, String cadena) {

        //calculamos el periodo correctamente llamando a la funcion periodo()
        double periodo = periodo(Linferior, Lsuperior);

        Lsuperior = Double.parseDouble(divisionDecimalExacta((double) periodo, 2.0).toString());
        Linferior = Double.parseDouble(divisionDecimalExacta((double) -1 * periodo, 2.0).toString());
        //h=(Limite superior - Limite inferior)/n
        //esta es el valor de h para el metodo del trapecio
        double h = Double.parseDouble(divisionDecimalExacta((Lsuperior - Linferior), (double) n).toString());
        //vector para almacenar los valores de la sumatoria del metodo del trapecio
        double vector[] = new double[n + 1];
        //delta de la formula de los trapecios (Ls-Li/n)
        String DeltaAux = divisionDecimalExacta((double) (Lsuperior - Linferior), (double) n).toString();
        double Delta = Double.parseDouble(DeltaAux);
        //Calculo de los valores inicial y final de la sumatoria del metodo del trapecio...
        //Esto se hace porque al comienzo y al final de la sumatoria se evalua la funcion en sus limites
        vector[0] = funcion(k, Linferior, periodo, cadena);
        vector[vector.length - 1] = funcion(k, Lsuperior, periodo, cadena);
        //para calcular todos los demas valores de la sumatoria del metodo del trapecio
        for (int i = 1; i < n; i++) {
            vector[i] = 2.0 * funcion(k, (Linferior + (i * h)), periodo, cadena);
        }
        double res = 0.0;
        //Aqui realizamos la sumatoria del metodo del trapecio
        for (int i = 0; i < vector.length; i++) {
            res = res + vector[i];

        }
        // calculo de h/2 necesaria en la formula de los trapecios
        double hEntre2 = Double.parseDouble(divisionDecimalExacta(h, 2.0).toString());
        res = res * hEntre2;
        cadena = "" + res;
        return cadena;
    }

//Este metodo sirve para truncar a n decimas un numero real y retorna el decimal truncado
    public static double truncarDecimas(int numeroDecimales, double decimal) {
        decimal = decimal * (java.lang.Math.pow(10, numeroDecimales));
        decimal = java.lang.Math.round(decimal);
        decimal = decimal / java.lang.Math.pow(10, numeroDecimales);
        return decimal;
    }

    //Este metodo ayuda a dividir numeros reales de forma mas precisa
    //Esto es devido a que java en algunos casos se come las decimas en las divisiones
    public static BigDecimal divisionDecimalExacta(double a, double b) {
        BigDecimal ba = new BigDecimal("" + a);
        BigDecimal bb = new BigDecimal("" + b);
        BigDecimal resultado = ba.divide(bb, MathContext.DECIMAL128);
        return resultado;
    }

    //Este metodo nos retorna el valor correspondiente en Π de un numero decimal Ej; 3.141516.. sera 1 Π
    public static double enPi(double n) {
        double r = 0.0;
        r = Double.parseDouble(divisionDecimalExacta(n, Math.PI).toString());
        return r;
    }

    public static void serieDefourier(int k, double LimInferior, double LimSuperior) {
        System.out.println("----------------------------------------------------");
        System.out.println("USANDO N= "+n+" EN EL METODO DEL TRAPECIO");
        System.out.println("PARA K: " + k);
        System.out.println("FORMULA USANDO VALORES EXACTOS:");
        //aqui volvemos a calcular el periodo para no pasarlo como parametro
        double periodo = periodo(LimInferior, LimSuperior);
        double a0 = Double.parseDouble(trapecio(k, LimSuperior, LimInferior, "a0"));
        double an = Double.parseDouble(trapecio(k, LimSuperior, LimInferior, "an"));
        double bn = Double.parseDouble(trapecio(k, LimSuperior, LimInferior, "bn"));
        System.out.println("En el intervalo: " + enPi(LimInferior) + "*Π a " + enPi(LimSuperior) + "*Π");
        a0 = enPi(a0);
        System.out.println("a0: " + a0 + "*Π");
        System.out.println("an: " + an);
        System.out.println("bn: " + bn);

        System.out.println("T: " + enPi(periodo) + " *Π");
        //a02 = a0/2
        double a02 = Double.parseDouble(divisionDecimalExacta(a0, 2.0).toString());

       
        System.out.println("(a0/2): " + a02 + " *Π");
        System.out.println("\nFormula:(a0/2) + Σ(an*(Cos(2*K*Π*t/T))+bn*(Sen(2*K*Π*t/T)))");
        System.out.println("(" + a02 + " *Π) + Σ(an*(Cos((2*" + k + "*Π*t)/(" + enPi(periodo) + "*Π)))+bn*(Sen(2*" + k + "*Π*t)/(" + enPi(periodo) + "*Π))))");
        System.out.println("R: (" + a02 + " *Π) + Σ((" + an + ")*(Cos((2*" + k + "*Π*t)/(" + enPi(periodo) + "*Π)))+(" + bn + ")*(Sen(2*" + k + "*Π*t)/(" + enPi(periodo) + "*Π))))");

        //MOSTRAR DATOS TRUNCADOS
        //tomamos los valores en nuevos auxiliares de las variables para luego truncarlas sin cambiar su valor
        double auxA0 = a0;//valor de a0
        double auxAn = an;//valor de an
        double auxBn = bn;//valor de bn
        double periodoAux = periodo;//valor del periodo
        double auxA02 = a02;//valor de (a0/2) que es para la formula

        //lo truncamos a 6 datos despues del punto para poder definir si es par o no y para facilitar el remplazo en la formula
        auxA0 = truncarDecimas(6, auxA0);
        auxAn = truncarDecimas(6, auxAn);
        auxBn = truncarDecimas(6, auxBn);
        periodoAux = enPi(periodoAux);
        periodoAux = truncarDecimas(6, periodoAux);
        auxA02 = truncarDecimas(6, a02);

        //MISMA FORMULA CON LOS VALORES TRUNCADOS
        System.out.println("\n**FORMULA CON VALORES TRUNCADOS (Mas practica)**:");
        System.out.println("En el intervalo: " + enPi(LimInferior) + "*Π a " + enPi(LimSuperior) + "*Π");
        System.out.println("a0: " + auxA0 + "*Π");
        System.out.println("an: " + auxAn);
        System.out.println("bn: " + auxBn);
        System.out.println("T: " + periodoAux + "*Π");
        System.out.println("(a0/2): " + auxA02 + " *Π");
        //
        System.out.println("\nFormula:(a0/2) + Σ(an*(Cos(2*K*Π*t/T))+bn*(Sen(2*K*Π*t/T)))");
        System.out.println("R: (" + auxA02 + "*Π) + Σ((" + auxAn + ")*(Cos((2*" + k + "*Π*t)/(" + periodoAux + "*Π)))+(" + auxBn + ")*(Sen(2*" + k + "*Π*t)/(" + periodoAux + "*Π))))");
        //Paca saber si la funcion es par, impar o ninguna de estas
        if (auxAn == 0.0) {
            System.out.println("La funcion es: Impar");
        } else {
            if (auxBn == 0.0) {
                System.out.println("La funcion es: Par");
            } else {
                if (auxAn != 0.0 && auxBn != 0.0) {
                    System.out.println("La funcion no es par ni tampoco impar");
                }

            }

        }

    }

    //Metodo para calcular correctamente el periodo apartir de los intervalos ingresados
    public static double periodo(double li, double ls) {
        //calcular correctamente el tamaño del periodo apartir del intervalo
        double pr = 0.0;
        //cuando ambos son positivos
        if (ls >= 0 && li >= 0) {
            pr = Math.abs((ls - li));
        } else {
            //cuando uno de los 2 es negativo
            if ((ls >= 0 && li < 0) || (li >= 0 && ls < 0)) {
                pr = Math.abs((ls)) + Math.abs((li));
            } else {
                //cuando ambos son negativos
                if ((ls < 0 && li < 0)) {
                    pr = Math.abs((ls + li));
                }

            }

        }
        return pr;
    }
//metodo main (principal) desde aqui ejecutamos el programa

    public static void main(String[] args) {

        // K representa hasta que numero queremos evaluar la serie 
        //K=n pero no se usa n para no confundir con el termino de el metodo de los trapecios
        for (int k = 1; k <= 1; k++) {
            //PARA USAR EL METODO serieDeFourier() TENGA EN CUENTA
            //la funcion debe definirse en el metodo funcion() como ejemplo esta x^2 de 0 a Pi
            //PARAMETROS :(K=n) contante,Limite inferior,Limite Superior
            serieDefourier(k, -Math.PI, Math.PI);

        }

    }

}
