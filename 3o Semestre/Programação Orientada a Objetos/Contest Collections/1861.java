import java.io.IOException;
import java.util.*;
public class Main {
 
    public static void main(String[] args) throws IOException 
    {
        Scanner scan = new Scanner(System.in);
        HashMap<String, Integer> numero_assassinados = new HashMap<String, Integer>();
        ArrayList<String> lista_assassinados = new ArrayList<>();

        while(scan.hasNext())
        {
            String assassino;
            String assassinado;


            assassino = scan.next();
            assassinado = scan.next();

            lista_assassinados.add(assassinado);
            
            if(numero_assassinados.containsKey(assassino))
            {
                numero_assassinados.put(assassino, numero_assassinados.get(assassino) + 1);
            }
            else
            {
                numero_assassinados.put(assassino, 1);
            }

        }
        System.out.println("HALL OF MURDERERS");
        List<String> nomes = new ArrayList<>(numero_assassinados.keySet());
        Collections.sort(nomes);
        
        for(String a : nomes) 
        {
            if(!lista_assassinados.contains(a)) {
                System.out.println(a + " " + numero_assassinados.get(a));
            }
        }



 
    }
 
}
