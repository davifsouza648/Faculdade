import java.util.*;

public class Ex3 
{
    public static void main(String[] args) 
    {
        Scanner scan = new Scanner(System.in);

        int n = scan.nextInt();
        int[] alunos = new int[n];

        for (int i = 0; i < n; i++) 
        {
            alunos[i] = scan.nextInt();
        }

        Arrays.sort(alunos);

        int soma = 0;
        for (int i = 0; i < n - 1; i += 2) 
        {
            if (alunos[i] != alunos[i+1]) 
            {
                soma += (alunos[i+1] - alunos[i]);
            }
        }

        System.out.println(soma);
    }
}
