import java.util.Scanner;

public class Ex1 {
    public static void main(String[] args)
    {
        double a,b,c, delta;
        Scanner scan = new Scanner(System.in);

        System.out.println("Insira o coeficiente a: ");
        a = scan.nextDouble();

        System.out.println("Insira o coeficiente b: ");
        b = scan.nextDouble();

        System.out.println("Insira o coeficiente c: ");
        c = scan.nextDouble();

        delta = Math.pow(b,2) - 4 * a * c;

        if(delta > 0)
        {
            double x1 = (-b + Math.sqrt(delta)) / (2 * a);
            double x2 = (-b - Math.sqrt(delta)) / (2 * a);

            System.out.println("x1 = " + x1 + "\nx2 = " + x2 + "\n");

        }
        else if(delta == 0)
        {
            double x = (-b / (2*a));

            System.out.println("x = " + x + "\n");

        }
        else
        {
            System.out.println("Equação não possui raízes reais.\n");
        }





    }
}
