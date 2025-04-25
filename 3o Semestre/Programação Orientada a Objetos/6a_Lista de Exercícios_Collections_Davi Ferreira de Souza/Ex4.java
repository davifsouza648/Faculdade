import java.util.*;

public class Ex4 
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);

        int n;

        n = scan.nextInt();
        scan.nextLine();

        for(int i = 0; i < n; i++)
        {
            Stack<Character> diamantes = new Stack<>();
            int soma = 0;
            String entrada;

            entrada = scan.nextLine();

            for(int j = 0; j < entrada.length(); j++)
            {
                if(entrada.charAt(j) == '<'){
                    diamantes.push('<');
                }
                if(entrada.charAt(j) == '>')
                {
                    if(!diamantes.isEmpty())
                    {
                        if(diamantes.peek() == '<')
                        {
                            diamantes.pop();
                            soma = soma + 1;
                        }     
                    }

                }
            }

            System.out.println(soma);
        }

    }    
}
