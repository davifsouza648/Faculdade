import java.util.Scanner;

public class Ex2
{

    public static int fibonnaci(int n)
    {
        if(n == 0) return 0;
        if(n == 1) return 1;

        return fibonnaci(n - 1) + fibonnaci(n - 2);
    }

    public static void main(String[] args)
    {
        int n;
        Scanner scan = new Scanner(System.in);

        System.out.println("Insira o número para obter o n-ésimo termo de fibonnaci: ");
        n = scan.nextInt();

        System.out.println("Fibonnaci(n) = " + fibonnaci(n) + "\n");
    }
}
