import java.util.*;

public class Ex1 
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int n;

        System.out.print("Insira o número de alunos: ");
        n = scan.nextInt();
        
        scan.nextLine();

        Vector<String> nomeAlunos = new Vector<>();

        for(int i = 0; i < n; i++)
        {
            nomeAlunos.add(scan.nextLine());
        }

        nomeAlunos.sort(null);

        System.out.println("Nome dos alunos ordenados: ");
        System.out.print(nomeAlunos.toString());


    }   
}
