import java.util.*;


class OperacaoMatriz extends Thread
{
    private int [][] A,B,matriz_soma;
    private int inicioLinha, inicioColuna, finalLinha, finalColuna;

    public OperacaoMatriz(int[][] A, int[][] B, int[][] matriz_soma, int inicioLinha, int inicioColuna, int finalLinha, int finalColuna)
    {
        this.A = A;
        this.B = B;
        this.matriz_soma = matriz_soma;
        this.inicioLinha = inicioLinha;
        this.inicioColuna = inicioColuna;
        this.finalLinha = finalLinha;
        this.finalColuna = finalColuna;
    }

    @Override
    public void run()
    {
        for(int i = inicioLinha; i < finalLinha; i++)
        {
            for(int j = inicioColuna; j < finalColuna; j++)
            {
                this.matriz_soma[i][j] = this.A[i][j] + this.B[i][j];
            }
        }
    }
}

public class Ex2
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        System.out.print("Insira o tamanho da matriz: ");
        int n = scan.nextInt();


        int[][] A = new int[n][n];
        int[][] B = new int[n][n];
        int[][] matriz_soma = new int[n][n];

        System.out.println("Insira os elementos da matriz A:");

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                A[i][j] = scan.nextInt();
            }
        }

        System.out.println("Insira os elementos da matriz B:");

        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                B[i][j] = scan.nextInt();
            }
        }

        int meio = n/2;

        Thread t1 = new OperacaoMatriz(A, B, matriz_soma, 0,   0,   meio, meio);
        Thread t2 = new OperacaoMatriz(A, B, matriz_soma, 0,   meio, meio, n);
        Thread t3 = new OperacaoMatriz(A, B, matriz_soma, meio, 0,   n,   meio);
        Thread t4 = new OperacaoMatriz(A, B, matriz_soma, meio, meio, n,   n);

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try
        {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("Matriz soma: ");
        for(int i = 0; i < n; i++)
        {
            for(int j = 0; j < n; j++)
            {
                System.out.print(matriz_soma[i][j] + " ");
            }
            System.out.println("");
        }



        

        scan.close();
    }
}
