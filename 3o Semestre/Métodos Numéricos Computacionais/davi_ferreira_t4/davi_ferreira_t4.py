from sympy import symbols, factorial, lambdify, pprint, preview, simplify
import numpy as np
import matplotlib.pyplot as plt
import os
import tabulate
import time

def interpolacao_lagrange(valores_x, valores_y): #Função para implementar a interpolação de lagrange
    
    if len(valores_x) != len(valores_y): #Checa se o tamanho dos dois vetores é o mesmo
        return "N/A"

    x = symbols('x') #Inicia x como uma variável simbolica 'x' do sympy
    expressao = 0 #Inicializa o polinômio como 0

    for i in range(len(valores_y)): #Percorre todos os valores de y
        Li = 1 #Inicializa o Li(x) da iteração atual
        for j in range(len(valores_x)): #Percorre todos os valores de x
            if(j != i): #Checa se j != i para evitar divisão por 0
                Li*=(x-valores_x[j])/(valores_x[i]-valores_x[j])#Realiza a multiplicação para gerar o Li(x)

        expressao += valores_y[i] * Li #Soma o Li(x) multiplicando o valor correspondente de y
    
    return expressao #Retorna o polinômio

def interpolacao_newton(valores_x, valores_y): #Função para implementar a interpolação de Newton
    
    if len(valores_x) != len(valores_y): #Checa se o tamanho dos dois vetores é o mesmo
        return "N/A"

    n = len(valores_x) 
    x = symbols('x')

    matriz = np.zeros((n, n), dtype=float) # Cria matriz de diferenças divididas

    for i in range(n): # Inicializa a primeira coluna com os valores de y
        matriz[i][0] = valores_y[i]

    for j in range(1, n): # Calcula as diferenças divididas
        for i in range(n - j):
            matriz[i][j] = (matriz[i + 1][j - 1] - matriz[i][j - 1]) / (valores_x[i + j] - valores_x[i])

    expressao = matriz[0][0] # Inicializa o polinômio com f[x0]
    termo = 1 # Termo acumulador (x - x0)(x - x1)...
    for i in range(1, n):
        termo *= (x - valores_x[i - 1])
        expressao += matriz[0][i] * termo # Soma os termos com coeficientes de diferenças divididas

    return expressao #Retorna o polinômio


def interpolacao_newton_gregory_progressiva(valores_x, valores_y): #Função para implementar a interpolação de Newton-Gregory
    
    if len(valores_x) != len(valores_y):
        return "N/A"

    n = len(valores_x)
    h = valores_x[1] - valores_x[0] # Calcula o espaçamento h

    for i in range(1, n - 1): # Verifica se os pontos são uniformemente espaçados
        if not np.isclose(valores_x[i + 1] - valores_x[i], h):
            return "N/A"

    x = symbols('x')
    s = (x - valores_x[0]) / h # Define a variável s para facilitar a fórmula

    tabela = np.zeros((n, n), dtype=float) # Tabela de diferenças finitas
    for i in range(n):
        tabela[i][0] = valores_y[i] # Primeira coluna com f(x)

    for j in range(1, n): # Cálculo das diferenças finitas progressivas
        for i in range(n - j):
            tabela[i][j] = tabela[i + 1][j - 1] - tabela[i][j - 1]

    expressao = tabela[0][0] # Inicializa o polinômio com f[x0]
    termo = 1
    for i in range(1, n):
        termo *= (s - (i - 1)) # Calcula o termo s(s-1)...(s - i + 1)
        expressao += (tabela[0][i] * termo) / factorial(i) # Soma o termo ao polinômio

    return expressao #Retorna o polinômio

    
    

def main():
    os.system("chcp 65001")
    valores_x = [0, 1, 2, 3, 4, 5] #Inicializa os valores de x e y
    valores_y = [2.1, 3.8, 5.2, 6.1, 7.8, 8.9]
 
    x = symbols('x') #Inicializa a variável simbólica x

    tempo_lagrange = time.time() #Calcula o tempo de execução e o polinômio gerado por cada método
    polinomio_lagrange = interpolacao_lagrange(valores_x, valores_y)
    tempo_lagrange = time.time() - tempo_lagrange

    tempo_newton = time.time()
    polinomio_newton = interpolacao_newton(valores_x, valores_y)
    tempo_newton = time.time() - tempo_newton

    tempo_newton_gregory = time.time()
    polinomio_newton_gregory = interpolacao_newton_gregory_progressiva(valores_x, valores_y)
    tempo_newton_gregory = time.time() - tempo_newton_gregory

    erro_lagrange_newton = abs(polinomio_lagrange.subs(x,2.5) - polinomio_newton.subs(x,2.5)) #Calcula os erros entre os métodos
    erro_lagrange_newton_gregory = abs(polinomio_lagrange.subs(x,2.5) - polinomio_newton_gregory.subs(x,2.5))
    erro_newton_newton_gregoy = abs(polinomio_newton.subs(x,2.5) - polinomio_newton_gregory.subs(x,2.5))

    rodando = True

    while(rodando): #Loop de execução do menu
        os.system("cls" if os.name == "nt" else "clear") #Limpa a tela

        print("Problema analisado:")
        tabela = list(zip(valores_x, valores_y))
        print(tabulate.tabulate(tabela, headers=["x", "f(x)"], tablefmt = "heavy_outline")) #Cria a tabela para mostrar os valores de x e y
        
        print("1 - Executar métodos") #Mostra as opções do menu principal
        print("2 - Comparar a a eficiência dos métodos")
        print("3 - Gerar gráficos de comparação")
        print("4 - Sair\n")

        escolha =  int(input("Insira sua escolha: ")) #Lê a escolha do usuário

        match escolha:
            case 1:
                os.system("cls" if os.name == "nt" else "clear")

                print("1 - Interpolação de Lagrange") #Mostra as opções de métodos para serem aplicados
                print("2 - Interpolação de Newton")
                print("3 - Interpolação de Newton-Gregory\n")

                escolha2 = int(input("insira sua escolha: ")) #Lê a escolha do usuário

                match escolha2:
                    case 1:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Polinômio de Lagrange:\n")
                        
                        pprint(polinomio_lagrange) #Mostra o polinômio sem expandir gerado pelo método
                        
                        print(f"\nValor interpolado em x = 2.5: {polinomio_lagrange.subs(x,2.5)}") #Avalia f(2.5) no polinômio gerado  
                        print(f"Tempo de execução: {tempo_lagrange} segundos") #Mostra o tempo de execução do método

                        input("Pressione enter para continuar: ") #Enter para continuar

                    case 2:
                        os.system("cls" if os.name == "nt" else "clear") #Mesmos comentários do caso 1
                        print("Polinômio de Newton: ")

                        pprint(polinomio_newton)
                        
                        print(f"\nValor interpolado em x = 2.5: {polinomio_newton.subs(x,2.5)}")                            
                        print(f"Tempo de execução: {tempo_newton} segundos")

                        input("Pressione enter para continuar: ")

                    case 3:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Polinômio de Newton-Gregory: ")

                        pprint(polinomio_newton_gregory)
                        
                        print(f"\nValor interpolado em x = 2.5: {polinomio_newton_gregory.subs(x,2.5)}")                        
                        print(f"Tempo de execução: {tempo_newton_gregory} segundos")

                        input("Pressione enter para continuar: ")
            
            case 2:
                os.system("cls" if os.name == "nt" else "clear")
                
                tabela_comparativa = [["Interpolação de Lagrange", tempo_lagrange, polinomio_lagrange.subs(x,2.5)],
                                      ["Interpolação de Newton", tempo_newton, polinomio_newton.subs(x,2.5)],
                                      ["Interpolação de Newton-Gregory", tempo_newton_gregory, polinomio_newton_gregory.subs(x,2.5)]] #Cria a tabela mostrando o nome do método, tempo de execução e o valor gerado em x = 2.5
                
                tabela_comparativa2 = [["Lagrange e Newton", erro_lagrange_newton], #Monta uma segunda tabela mostrando os erros entre métodos
                                       ["Lagrange e Newton-Gregory", erro_lagrange_newton_gregory],
                                       ["Newton e Newton_Gregory", erro_newton_newton_gregoy]]
                
                print("Comparação entre os métodos: ")
                
                print(tabulate.tabulate(tabela_comparativa, headers = ["Método", "Tempo de execução(segundos)", "Valor em x = 2.5"], tablefmt="heavy_outline")) #Printa as duas tabelas com seus respectivos headers e estilos

                print(tabulate.tabulate(tabela_comparativa2, headers = ["Erro entre métodos", "Erro"], tablefmt="heavy_outline"))

                input("Pressione enter para continuar: ") #Enter para continuar

            case 3:
                os.system("cls" if os.name == "nt" else "clear")

                # Geração de gráficos de comparação entre os polinômios
                f_lagrange = lambdify(x, polinomio_lagrange, modules='numpy')
                f_newton = lambdify(x, polinomio_newton, modules='numpy')
                f_gregory = lambdify(x, polinomio_newton_gregory, modules='numpy')

                x_vals = np.linspace(min(valores_x), max(valores_x), 400)
                y_lagrange = f_lagrange(x_vals)
                y_newton = f_newton(x_vals)
                y_gregory = f_gregory(x_vals)

                # Gráfico dos polinômios
                plt.figure(figsize=(10, 6))
                plt.plot(x_vals, y_lagrange, label='Lagrange', color='blue')
                plt.plot(x_vals, y_newton, label='Newton', linestyle='--', color='green')
                plt.plot(x_vals, y_gregory, label='Newton-Gregory', linestyle='-.', color='red')
                plt.scatter(valores_x, valores_y, color='black', label='Pontos experimentais', zorder=5)
                plt.title('Interpolação dos Pontos')
                plt.xlabel('x')
                plt.ylabel('f(x)')
                plt.grid(True)
                plt.legend()
                plt.tight_layout()
                plt.savefig('grafico_polinomios.png')  # Salva o gráfico dos polinômios

                # Gráfico de barras com tempo de execução
                metodos = ['Lagrange', 'Newton', 'Newton-Gregory']
                tempos = [tempo_lagrange, tempo_newton, tempo_newton_gregory]

                plt.figure(figsize=(8, 5))
                plt.bar(metodos, tempos, color=['blue', 'green', 'red'])
                plt.title('Tempo de Execução dos Métodos')
                plt.ylabel('Tempo (segundos)')
                plt.grid(axis='y')
                plt.tight_layout()
                plt.savefig('grafico_comparacao_tempo.png')  # Salva o gráfico de barras

                input("Pressione enter para continuar: ")

            case 4:
                rodando = False # Sai do loop



if __name__ == "__main__":
    main()

