import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class Main {
 
    public static void main(String[] args) throws IOException {
 
        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNext())
        {
            String str = scanner.next();
            Stack <Character> parenteses = new Stack<>();
            boolean ok = true;

            for(int i = 0; i < str.length(); i++)
            {
                if(str.charAt(i) == ')')
                {
                    if(parenteses.isEmpty())
                    {
                        ok = false;
                        break;
                    }

                    if(parenteses.peek() != '(')
                    {
                        ok = false;
                        break;
                    }
                    else
                    {
                        parenteses.pop();
                    }
                    
                }

                if(str.charAt(i) == '('){parenteses.add('(');}


            }

            if(ok && parenteses.isEmpty())
            {
                System.out.println("correct");
            }
            else
            {
                System.out.println("incorrect");
            }

        }

    }
 
}
