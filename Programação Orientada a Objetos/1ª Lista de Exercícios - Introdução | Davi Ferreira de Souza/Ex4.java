import java.util.Scanner;

public class Ex4
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int n;

        System.out.println("Insira a dimensão das matrizes: ");
        n = scan.nextInt();

        int[][] m1 = new int[n][n];
        int[][] m2 = new int[n][n];

        System.out.println("Insira os elementos da matriz 1:");

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                m1[i][j] = scan.nextInt();
            }
        }

        System.out.println("Insira os elementos da matriz 2:");

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                m2[i][j] = scan.nextInt();
            }
        }

        System.out.println("Matriz 1 + Matriz 2 = \n");

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print((m1[i][j] + m2[i][j]) + " ");
            }
            System.out.println();
        }
    }
}
