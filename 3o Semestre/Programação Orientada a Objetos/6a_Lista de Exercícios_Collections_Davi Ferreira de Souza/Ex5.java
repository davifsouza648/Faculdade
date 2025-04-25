import java.util.*;

public class Ex5 
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int p, a, r;

        while (true)
        {
            p = scan.nextInt();
            a = scan.nextInt();
            r = scan.nextInt();
            if(scan.hasNextLine()) scan.nextLine(); 

            if (p == 0 && a == 0 && r == 0)
            {
                break;
            }

            TreeSet<String> perolas = new TreeSet<>();
            HashMap<String, Integer> alunos_perolas = new HashMap<>();

            for (int i = 0; i < p; i++)
            {
                perolas.add(scan.nextLine());
            }

            for (int i = 0; i < a; i++)
            {
                String nomeAluno = scan.nextLine();
                int soma_perolas = 0;

                for (int j = 0; j < r; j++)
                {
                    String perola = scan.nextLine();
                    if (perolas.contains(perola))
                    {
                        soma_perolas++;
                    }
                }

                alunos_perolas.put(nomeAluno, soma_perolas);
            }

            int maxPerolas = Collections.max(alunos_perolas.values());
            List<String> melhoresAlunos = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : alunos_perolas.entrySet())
            {
                if (entry.getValue() == maxPerolas)
                {
                    melhoresAlunos.add(entry.getKey());
                }
            }

            Collections.sort(melhoresAlunos);
            System.out.println(String.join(", ", melhoresAlunos));
        }

        scan.close();
    }
}
