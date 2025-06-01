from sympy import simplify,symbols,factorial, lambdify, pprint
import numpy as np
import matplotlib.pyplot as plt
import os
import tabulate
import time

def interpolacao_lagrange(valores_x, valores_y):
    
    if len(valores_x) != len(valores_y):
        return "N/A"

    x = symbols('x')
    expressao = 0

    for i in range(len(valores_y)):
        Li = 1
        for j in range(len(valores_x)):
            if(j != i):
                Li*=(x-valores_x[j])/(valores_x[i]-valores_x[j])

        expressao += valores_y[i] * Li
    
    return expressao

def interpolacao_newton(valores_x, valores_y):
    if len(valores_x) != len(valores_y):
        return "N/A"

    n = len(valores_x)
    x = symbols('x')

    matriz = np.zeros((n, n), dtype=float)

    for i in range(n):
        matriz[i][0] = valores_y[i]

    for j in range(1, n):
        for i in range(n - j):
            matriz[i][j] = (matriz[i + 1][j - 1] - matriz[i][j - 1]) / (valores_x[i + j] - valores_x[i])

    expressao = matriz[0][0]
    termo = 1
    for i in range(1, n):
        termo *= (x - valores_x[i - 1])
        expressao += matriz[0][i] * termo

    return expressao


def interpolacao_newton_gregory_progressiva(valores_x, valores_y):
    if len(valores_x) != len(valores_y):
        return "N/A"

    n = len(valores_x)
    h = valores_x[1] - valores_x[0]

    for i in range(1, n - 1):
        if not np.isclose(valores_x[i + 1] - valores_x[i], h):
            return "Erro: os valores de x não são uniformemente espaçados."

    x = symbols('x')
    u = (x - valores_x[0]) / h

    tabela = np.zeros((n, n), dtype=float)
    for i in range(n):
        tabela[i][0] = valores_y[i]

    for j in range(1, n):
        for i in range(n - j):
            tabela[i][j] = tabela[i + 1][j - 1] - tabela[i][j - 1]

    expressao = tabela[0][0]
    termo = 1
    for i in range(1, n):
        termo *= (u - (i - 1))
        expressao += (tabela[0][i] * termo) / factorial(i)

    return expressao

    
    

def main():
    os.system("chcp 65001")
    valores_x = [0, 1, 2, 3, 4, 5]
    valores_y = [2.1, 3.8, 5.2, 6.1, 7.8, 8.9]

    x = symbols('x')

    tempo_lagrange = time.time()
    polinomio_lagrange = interpolacao_lagrange(valores_x, valores_y)
    tempo_lagrange = time.time() - tempo_lagrange

    tempo_newton = time.time()
    polinomio_newton = interpolacao_newton(valores_x, valores_y)
    tempo_newton = time.time() - tempo_newton

    tempo_newton_gregory = time.time()
    polinomio_newton_gregory = interpolacao_newton_gregory_progressiva(valores_x, valores_y)
    tempo_newton_gregory = time.time() - tempo_newton_gregory

    erro_lagrange_newton = abs(polinomio_lagrange.subs(x,2.5) - polinomio_newton.subs(x,2.5))
    erro_lagrange_newton_gregory = abs(polinomio_lagrange.subs(x,2.5) - polinomio_newton_gregory.subs(x,2.5))
    erro_newton_newton_gregoy = abs(polinomio_newton.subs(x,2.5) - polinomio_newton_gregory.subs(x,2.5))

    rodando = True

    while(rodando):
        os.system("cls" if os.name == "nt" else "clear")

        print("Problema analisado:")
        tabela = list(zip(valores_x, valores_y))
        print(tabulate.tabulate(tabela, headers=["x", "f(x)"]))
        
        print("1 - Executar métodos")
        print("2 - Comparar a a eficiência dos métodos")
        print("3 - Gerar gráficos de comparação")
        print("4 - Sair\n")

        escolha =  int(input("Insira sua escolha: "))

        match escolha:
            case 1:
                os.system("cls" if os.name == "nt" else "clear")

                print("1 - Interpolação de Lagrange")
                print("2 - Interpolação de Newton")
                print("3 - Interpolação de Newton-Gregory\n")

                escolha2 = int(input("insira sua escolha: "))

                match escolha2:
                    case 1:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Polinômio de Lagrange Expandido:\n")
                        pprint(polinomio_lagrange)
                        
                        print(f"\nValor interpolado em x = 2.5: {polinomio_lagrange.subs(x,2.5)}")    
                        print(f"Tempo de execução: {tempo_lagrange} segundos")

                        input("Pressione enter para continuar: ")

                    case 2:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Polinômio de Newton Expandido: ")

                        pprint(polinomio_newton)
                        
                        print(f"\nValor interpolado em x = 2.5: {polinomio_newton.subs(x,2.5)}")                            
                        print(f"Tempo de execução: {tempo_newton} segundos")

                        input("Pressione enter para continuar: ")

                    case 3:
                        os.system("cls" if os.name == "nt" else "clear")
                        print("Polinômio de Newton-Gregory Expandido: ")

                        pprint(polinomio_newton_gregory)
                        
                        print(f"\nValor interpolado em x = 2.5: {polinomio_newton_gregory.subs(x,2.5)}")                        
                        print(f"Tempo de execução: {tempo_newton_gregory} segundos")

                        input("Pressione enter para continuar: ")
            
            case 2:
                os.system("cls" if os.name == "nt" else "clear")
                
                tabela_comparativa = [["Interpolação de Lagrange", tempo_lagrange, polinomio_lagrange.subs(x,2.5)],
                                      ["Interpolação de Newton", tempo_newton, polinomio_newton.subs(x,2.5)],
                                      ["Interpolação de Newton-Gregory", tempo_newton_gregory, polinomio_newton_gregory.subs(x,2.5)]]
                
                tabela_comparativa2 = [["Lagrange e Newton", erro_lagrange_newton],
                                       ["Lagrange e Newton-Gregory", erro_lagrange_newton_gregory],
                                       ["Newton e Newton_Gregory", erro_newton_newton_gregoy]]
                
                print("Comparação entre os métodos: ")
                
                print(tabulate.tabulate(tabela_comparativa, headers = ["Método", "Tempo de execução(segundos)", "Valor em x = 2.5"], tablefmt="heavy_outline"))

                print(tabulate.tabulate(tabela_comparativa2, headers = ["Erro entre métodos", "Erro"], tablefmt="heavy_outline"))

                input("Pressione enter para continuar: ")

            case 3:
                os.system("cls" if os.name == "nt" else "clear")

                # Converter os polinômios em funções numéricas
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
                plt.savefig('grafico_polinomios.png')

                # Gráfico de barras com tempo de execução
                metodos = ['Lagrange', 'Newton', 'Newton-Gregory']
                tempos = [tempo_lagrange, tempo_newton, tempo_newton_gregory]

                plt.figure(figsize=(8, 5))
                plt.bar(metodos, tempos, color=['blue', 'green', 'red'])
                plt.title('Tempo de Execução dos Métodos')
                plt.ylabel('Tempo (segundos)')
                plt.grid(axis='y')
                plt.tight_layout()
                plt.savefig('grafico_comparacao_tempo.png')

                input("Pressione enter para continuar: ")

            case 4:
                rodando = False



if __name__ == "__main__":
    main()

