import java.util.*;

public class Ex2 
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        
        TreeSet<String> alunos = new TreeSet<>();

        System.out.print("Insira o número de alunos: ");
        int n =  scan.nextInt();
        scan.nextLine();

        for(int i = 0; i < n; i++)
        {
            alunos.add(scan.nextLine());
        }

        System.out.println("Alunos ordenados: ");
        System.out.print(alunos.toString());

        

    }
    
}
