import java.util.Scanner;

public class Ex3
{

    public static void bubbleSort(int[] vetor)
    {
        boolean ordenado = false;
        int temp;

        while(!ordenado)
        {
            ordenado = true;

            for(int i = 0; i < vetor.length - 1; i++)
            {
                if(vetor[i] > vetor[i+1])
                {
                    temp = vetor[i+1];
                    vetor[i+1] = vetor[i];
                    vetor[i] = temp;
                    ordenado = false;
                }

            }
        }
    }

    public static void main(String[] args)
    {
        int n;

        Scanner scan = new Scanner(System.in);

        System.out.print("Insira o número de elementos do vetor: ");
        n = scan.nextInt();

        int vetor[] = new int[n];

        for(int i = 0; i < n; i++)
        {
            System.out.println("Vetor[" + (i+1) + "] =");
            vetor[i] = scan.nextInt();
        }

        bubbleSort(vetor);

        for(int i = 0; i < n; i++)
        {
            System.out.print("Vetor[" + (i + 1) + "] = " + vetor[i] + "\n");
        }


    }
}
