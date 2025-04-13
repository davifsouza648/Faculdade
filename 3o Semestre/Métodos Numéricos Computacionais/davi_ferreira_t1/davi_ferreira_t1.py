#Davi Ferreira de Souza
#Programa para analisar 5 problemas de diferentes áreas e buscar zeros de funções reais.
#13/04/2025


import matplotlib.pyplot as mpl
import os
import time
import math

iteracoes_bicessao = 0
iteracoes_falsa_posicao = 0
iteracoes_newton = 0

epsilon = 0
sigma = 0

def funcao_problema_1(x):
    return 5 * x - 100

def funcao_problema_2(x):
    return 1e-12 * (math.exp(x / 0.025) - 1) - 1e-2

def funcao_problema_3(x):
    if(0.1-x) < 1e-12:
        return float('inf')
    return (x**2)/(0.1-x) - 1e-5

def funcao_problema_4(x):
    return 24 * epsilon * (2 * (sigma/x)**13 - (sigma/x)**7)

def funcao_problema_5(x):
    return x - 1 - math.exp(-x)

def plot_graficos(valores_fx, valores_tempo, valores_iteracoes, numproblema):
    
    metodos = ["Bisseção", "Falsa posição", "Newton"]

    #1 - Plot do módulo da função nas raizes encontradas
    mpl.figure(figsize = (10,6))
    mpl.bar(metodos, valores_fx, color = "skyblue")
    mpl.ylabel("|f(x)| na raiz encontrada")
    mpl.title("Precisão dos métodos (quanto menor, melhor [se -1 não convergiu]):")
    mpl.grid(True, axis = 'y')
    mpl.savefig(f"grafico_erro_problema{numproblema}.png")
    mpl.close()

    #2 - Plot do tempo de execução dos métodos

    mpl.figure(figsize = (10,6))
    mpl.bar(metodos, valores_tempo, color = "skyblue")
    mpl.ylabel("Tempo de execução do método em segundos")
    mpl.title("Tempo de execução dos métodos (quanto menor, melhor)")
    mpl.grid(True, axis = 'y')
    mpl.savefig(f"grafico_tempo_problema{numproblema}.png")
    mpl.close()

    #3 - Plot do número de iterações

    mpl.figure(figsize = (10,6))
    mpl.bar(metodos, valores_iteracoes, color = "skyblue")
    mpl.ylabel("Número de iterações")
    mpl.title("Número de iterações dos métodos (quanto menor, melhor)")
    mpl.grid(True, axis = 'y')
    mpl.savefig(f"grafico_iteracoes_problema{numproblema}.png")
    mpl.close()






def derivada(func,x):
    h = 1e-10

    return (func(x+h)-func(x-h)) / (2*h)

def metodo_bissecao(f, a, b, erro = 1e-6, max_iter = 1000):
    global iteracoes_bicessao
    iteracoes = 1

    if(f(a) * f(b)>0):
        return None
    
    xi = (a + b)/2

    while ((abs(f(xi)) > erro or abs(b-a) > erro) and iteracoes < max_iter):
        iteracoes += 1
        xi = (a + b) / 2

        if(f(a) * f(xi) == 0):
            return xi
        elif(f(a) * f(xi) < 0):
            b = xi
        else:
            a = xi

    iteracoes_bicessao = iteracoes
    return xi if iteracoes < max_iter else None 

def metodo_falsa_posicao(f, a, b, erro = 1e-6, max_iter = 1000):
    global iteracoes_falsa_posicao
    iteracoes = 1

    if(f(a) * f(b)>0):
        return None

    xi = (a * f(b) - b * f(a)) / (f(b) - f(a))

    while (abs(f(xi)) > erro and abs(b-a) > erro and iteracoes < max_iter):
        iteracoes += 1
        denominador = f(b)-f(a)
        if(denominador < 1e-12):
            return None
        xi = (a * f(b) - b * f(a)) / (f(b) - f(a))

        if(f(a) * f(xi) == 0):
            return xi
        elif(f(a) * f(xi) < 0):
            b = xi
        else:
            a = xi

    iteracoes_falsa_posicao = iteracoes
    return xi if iteracoes < max_iter else None  

def metodo_newton(f, x0, erro = 1e-6, max_iter = 1000):
    iteracoes = 1
    global iteracoes_newton
    df = derivada(f, x0)
    if abs(df) < 1e-12:
        return None

    xi = x0 - (f(x0) / derivada(f, x0))

    while (abs(f(xi)) > erro and iteracoes < max_iter):
        iteracoes += 1
        xiantes = xi
        df = derivada(f, xi)
    
        if abs(df) < 1e-12:
            return None
        
        if(xi == float('nan')):
            return None
        
        xi = xi - (f(xi) / df)

        if (abs(xi - xiantes) / max(xi, 1) < erro):
            return xi

    iteracoes_newton = iteracoes
    return xi if iteracoes < max_iter else None  




def problema_1():

    os.system("clear");
    print("Problema 1 – Economia: Equilíbrio de Oferta e Demanda\n\n")
    print("Função analisada: f(p) = 5p - 100")
    
    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6
    
    print(f"Erro = {erro}\n\n")

    print("Resultados obtidos:\n")
    print("----------Raizes----------")

    tempo_bissecao = time.time();
    raiz_metodo_bissecao = metodo_bissecao(funcao_problema_1,a0,b0,erro)
    print(f"Método da bisseção (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_bissecao == None else raiz_metodo_bissecao}")
    tempo_bissecao = time.time() - tempo_bissecao

    tempo_metodo_falsa_posicao = time.time()
    raiz_metodo_falsa_posicao = metodo_falsa_posicao(funcao_problema_1,a0,b0,erro)
    print(f"Método da falsa posição (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_falsa_posicao == None else raiz_metodo_falsa_posicao}")
    tempo_metodo_falsa_posicao = time.time() - tempo_metodo_falsa_posicao

    tempo_metodo_newton = time.time()
    raiz_metodo_newton = metodo_newton(funcao_problema_1, x0, erro)
    print(f"Método de Newton (x0 = {x0}): {"Não convergiu" if raiz_metodo_newton == None else raiz_metodo_newton}")
    tempo_metodo_newton = time.time() - tempo_metodo_newton

    print("\n----------Tempos de execução----------");
    print(f"Método da bisseção: {tempo_bissecao} segundos")
    print(f"Método da falsa posição: {tempo_metodo_falsa_posicao} segundos")
    print(f"Método de Newton: {tempo_metodo_newton} segundos")

    print("\n----------Número de iterações----------")
    print(f"Método da bisseção: {iteracoes_bicessao} iterações")
    print(f"Método da falsa posição: {iteracoes_falsa_posicao} iterações")
    print(f"Método de Newton: {iteracoes_newton} iterações")

    print("\n----------Erros finais----------")
    print(f"Método da bisseção: {abs(funcao_problema_1(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else "Não convergiu"}")
    print(f"Método da falsa posição: {abs(funcao_problema_1(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else "Não convergiu"}")
    print(f"Método de Newton: {abs(funcao_problema_1(raiz_metodo_newton)) if raiz_metodo_newton is not None else "Não convergiu"}")

    #Plotar gráficos
    valores_fx = [abs(funcao_problema_1(raiz_metodo_bissecao)),
                  abs(funcao_problema_1(raiz_metodo_falsa_posicao)),
                  abs(funcao_problema_1(raiz_metodo_newton))]
    valores_tempo = [tempo_bissecao, tempo_metodo_falsa_posicao, tempo_metodo_newton]
    valores_iteracoes = [iteracoes_bicessao, iteracoes_falsa_posicao, iteracoes_newton]

    plot_graficos(valores_fx, valores_tempo, valores_iteracoes, 1)

    continuar = input("\n\nAperte enter para continuar: ")
    main()

   
def problema_2():
    os.system("clear");

    print("Problema 2 – Engenharia: Equação Não-Linear de um Diodo")
    print("Função analisada: f(V) = 1e-12 * (e^(V / 0.025) - 1) - 1e-2")
    
    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6
    
    print(f"Erro = {erro}\n\n")

    print("Resultados obtidos:\n")
    print("----------Raizes----------")
    
    tempo_bissecao = time.time();
    raiz_metodo_bissecao = metodo_bissecao(funcao_problema_2,a0,b0,erro)
    print(f"Método da bisseção (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_bissecao == None else raiz_metodo_bissecao}")
    tempo_bissecao = time.time() - tempo_bissecao

    tempo_metodo_falsa_posicao = time.time()
    raiz_metodo_falsa_posicao = metodo_falsa_posicao(funcao_problema_2,a0,b0,erro)
    print(f"Método da falsa posição (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_falsa_posicao == None else raiz_metodo_falsa_posicao}")
    tempo_metodo_falsa_posicao = time.time() - tempo_metodo_falsa_posicao

    tempo_metodo_newton = time.time()
    raiz_metodo_newton = metodo_newton(funcao_problema_2, x0, erro)
    print(f"Método de Newton (x0 = {x0}): {"Não convergiu" if raiz_metodo_newton == None else raiz_metodo_newton}")
    tempo_metodo_newton = time.time() - tempo_metodo_newton

    print("\n----------Tempos de execução----------");
    print(f"Método da bisseção: {tempo_bissecao} segundos")
    print(f"Método da falsa posição: {tempo_metodo_falsa_posicao} segundos")
    print(f"Método de Newton: {tempo_metodo_newton} segundos")

    print("\n----------Número de iterações----------")
    print(f"Método da bisseção: {iteracoes_bicessao} iterações")
    print(f"Método da falsa posição: {iteracoes_falsa_posicao} iterações")
    print(f"Método de Newton: {iteracoes_newton} iterações")

    print("\n----------Erros finais----------")
    print(f"Método da bisseção: {abs(funcao_problema_2(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else "Não convergiu"}")
    print(f"Método da falsa posição: {abs(funcao_problema_2(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else "Não convergiu"}")
    print(f"Método de Newton: {abs(funcao_problema_2(raiz_metodo_newton)) if raiz_metodo_newton is not None else "Não convergiu"}")

    valores_fx = [abs(funcao_problema_2(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else -1,
                  abs(funcao_problema_2(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else -1,
                  abs(funcao_problema_2(raiz_metodo_newton)) if raiz_metodo_newton is not None else -1
        ]
    valores_tempo = [tempo_bissecao, tempo_metodo_falsa_posicao, tempo_metodo_newton]
    valores_iteracoes = [iteracoes_bicessao, iteracoes_falsa_posicao, iteracoes_newton]

    plot_graficos(valores_fx, valores_tempo, valores_iteracoes, 2)

    continuar = input("\n\nAperte enter para continuar: ")
    main()

def problema_3():
    os.system("clear");

    print("Problema 3 – Química: Equilíbrio de Dissociação de um Ácido Fraco")
    print("Função analisada: f(x) = (x^2)/(0.1-x) - 1e-5")
    
    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6
    
    print(f"Erro = {erro}\n\n")

    print("Resultados obtidos:\n")
    print("----------Raizes----------")

    tempo_bissecao = time.time();
    raiz_metodo_bissecao = metodo_bissecao(funcao_problema_3,a0,b0,erro)
    print(f"Método da bisseção (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_bissecao == None else raiz_metodo_bissecao}")
    tempo_bissecao = time.time() - tempo_bissecao

    tempo_metodo_falsa_posicao = time.time()
    raiz_metodo_falsa_posicao = metodo_falsa_posicao(funcao_problema_3,a0,b0,erro)
    print(f"Método da falsa posição (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_falsa_posicao == None else raiz_metodo_falsa_posicao}")
    tempo_metodo_falsa_posicao = time.time() - tempo_metodo_falsa_posicao

    tempo_metodo_newton = time.time()
    raiz_metodo_newton = metodo_newton(funcao_problema_3, x0, erro)
    print(f"Método de Newton (x0 = {x0}): {"Não convergiu" if raiz_metodo_newton == None else raiz_metodo_newton}")
    tempo_metodo_newton = time.time() - tempo_metodo_newton

    print("\n----------Tempos de execução----------");
    print(f"Método da bisseção: {tempo_bissecao} segundos")
    print(f"Método da falsa posição: {tempo_metodo_falsa_posicao} segundos")
    print(f"Método de Newton: {tempo_metodo_newton} segundos")

    print("\n----------Número de iterações----------")
    print(f"Método da bisseção: {iteracoes_bicessao} iterações")
    print(f"Método da falsa posição: {iteracoes_falsa_posicao} iterações")
    print(f"Método de Newton: {iteracoes_newton} iterações")

    print("\n----------Erros finais----------")
    print(f"Método da bisseção: {abs(funcao_problema_3(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else "Não convergiu"}")
    print(f"Método da falsa posição: {abs(funcao_problema_3(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else "Não convergiu"}")
    print(f"Método de Newton: {abs(funcao_problema_3(raiz_metodo_newton)) if raiz_metodo_newton is not None else "Não convergiu"}")

    valores_fx = [abs(funcao_problema_3(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else -1,
                  abs(funcao_problema_3(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else -1,
                  abs(funcao_problema_3(raiz_metodo_newton)) if raiz_metodo_newton is not None else -1
        ]
    valores_tempo = [tempo_bissecao, tempo_metodo_falsa_posicao, tempo_metodo_newton]
    valores_iteracoes = [iteracoes_bicessao, iteracoes_falsa_posicao, iteracoes_newton]

    plot_graficos(valores_fx, valores_tempo, valores_iteracoes, 3)

    continuar = input("\n\nAperte enter para continuar: ")
    main()

def problema_4():
    global epsilon
    global sigma

    print("Problema 4 – Física: Ponto de Equilíbrio no Potencial de Lennard-Jones")
    print("Função analisada: f(x) = 24ε [(2(σ/x)¹³) - (σ/x)⁷]")

    epsilon = float(input("Informe o valor de ε (profundidade do poço de potencial): "))
    sigma = float(input("Informe o valor de σ (distância na qual o potencial é zero): "))

    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6
    
    print(f"Erro = {erro}\n\n")
    print("Resultados obtidos:\n")
    print("----------Raizes----------")

    tempo_bissecao = time.time();
    raiz_metodo_bissecao = metodo_bissecao(funcao_problema_4,a0,b0,erro)
    print(f"Método da bisseção (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_bissecao == None else raiz_metodo_bissecao}")
    tempo_bissecao = time.time() - tempo_bissecao

    tempo_metodo_falsa_posicao = time.time()
    raiz_metodo_falsa_posicao = metodo_falsa_posicao(funcao_problema_4,a0,b0,erro)
    print(f"Método da falsa posição (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_falsa_posicao == None else raiz_metodo_falsa_posicao}")
    tempo_metodo_falsa_posicao = time.time() - tempo_metodo_falsa_posicao

    tempo_metodo_newton = time.time()
    raiz_metodo_newton = metodo_newton(funcao_problema_4, x0, erro)
    print(f"Método de Newton (x0 = {x0}): {"Não convergiu" if raiz_metodo_newton == None else raiz_metodo_newton}")
    tempo_metodo_newton = time.time() - tempo_metodo_newton

    print("\n----------Tempos de execução----------");
    print(f"Método da bisseção: {tempo_bissecao} segundos")
    print(f"Método da falsa posição: {tempo_metodo_falsa_posicao} segundos")
    print(f"Método de Newton: {tempo_metodo_newton} segundos")

    print("\n----------Número de iterações----------")
    print(f"Método da bisseção: {iteracoes_bicessao} iterações")
    print(f"Método da falsa posição: {iteracoes_falsa_posicao} iterações")
    print(f"Método de Newton: {iteracoes_newton} iterações")

    print("\n----------Erros finais----------")
    print(f"Método da bisseção: {abs(funcao_problema_4(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else "Não convergiu"}")
    print(f"Método da falsa posição: {abs(funcao_problema_4(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else "Não convergiu"}")
    print(f"Método de Newton: {abs(funcao_problema_4(raiz_metodo_newton)) if raiz_metodo_newton is not None else "Não convergiu"}")

    valores_fx = [abs(funcao_problema_4(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else -1,
                  abs(funcao_problema_4(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else -1,
                  abs(funcao_problema_4(raiz_metodo_newton)) if raiz_metodo_newton is not None else -1
        ]
    valores_tempo = [tempo_bissecao, tempo_metodo_falsa_posicao, tempo_metodo_newton]
    valores_iteracoes = [iteracoes_bicessao, iteracoes_falsa_posicao, iteracoes_newton]

    plot_graficos(valores_fx, valores_tempo, valores_iteracoes, 4)

    continuar = input("\n\nAperte enter para continuar: ")
    main()


def problema_5():
    os.system("clear")
    print("Problema 5 – Engenharia (Controle): Ganho Crítico para Estabilidade de um Sistema de Realimentação")
    print("Função analisada: f(x) = x − 1 − e^x")


    a0 = float(input("Insira o valor de a0 para os métodos da bisseção e falsa posição: "))
    b0 = float(input("Insira o valor de b0 para os métodos da bisseção e falsa posição: "))
    x0 = float(input("Insira o valor de x0 para o método de newton: "))
    erro = float(input("Insira o erro para a execução: "))

    if erro <= 0:
        erro = 1e-6


    print(f"Erro = {erro}\n\n")
    print("Resultados obtidos:\n")
    print("----------Raizes----------")

    tempo_bissecao = time.time();
    raiz_metodo_bissecao = metodo_bissecao(funcao_problema_5,a0,b0,erro)
    print(f"Método da bisseção (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_bissecao == None else raiz_metodo_bissecao}")
    tempo_bissecao = time.time() - tempo_bissecao

    tempo_metodo_falsa_posicao = time.time()
    raiz_metodo_falsa_posicao = metodo_falsa_posicao(funcao_problema_5,a0,b0,erro)
    print(f"Método da falsa posição (a0 = {a0} b0 = {b0}): {"Não convergiu" if raiz_metodo_falsa_posicao == None else raiz_metodo_falsa_posicao}")
    tempo_metodo_falsa_posicao = time.time() - tempo_metodo_falsa_posicao

    tempo_metodo_newton = time.time()
    raiz_metodo_newton = metodo_newton(funcao_problema_5, x0, erro)
    print(f"Método de Newton (x0 = {x0}): {"Não convergiu" if raiz_metodo_newton == None else raiz_metodo_newton}")
    tempo_metodo_newton = time.time() - tempo_metodo_newton

    print("\n----------Tempos de execução----------");
    print(f"Método da bisseção: {tempo_bissecao} segundos")
    print(f"Método da falsa posição: {tempo_metodo_falsa_posicao} segundos")
    print(f"Método de Newton: {tempo_metodo_newton} segundos")

    print("\n----------Número de iterações----------")
    print(f"Método da bisseção: {iteracoes_bicessao} iterações")
    print(f"Método da falsa posição: {iteracoes_falsa_posicao} iterações")
    print(f"Método de Newton: {iteracoes_newton} iterações")

    print("\n----------Erros finais----------")
    print(f"Método da bisseção: {abs(funcao_problema_5(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else "Não convergiu"}")
    print(f"Método da falsa posição: {abs(funcao_problema_5(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else "Não convergiu"}")
    print(f"Método de Newton: {abs(funcao_problema_5(raiz_metodo_newton)) if raiz_metodo_newton is not None else "Não convergiu"}")

    valores_fx = [abs(funcao_problema_5(raiz_metodo_bissecao)) if raiz_metodo_bissecao is not None else -1,
                  abs(funcao_problema_5(raiz_metodo_falsa_posicao)) if raiz_metodo_falsa_posicao is not None else -1,
                  abs(funcao_problema_5(raiz_metodo_newton)) if raiz_metodo_newton is not None else -1
        ]
    valores_tempo = [tempo_bissecao, tempo_metodo_falsa_posicao, tempo_metodo_newton]
    valores_iteracoes = [iteracoes_bicessao, iteracoes_falsa_posicao, iteracoes_newton]

    plot_graficos(valores_fx, valores_tempo, valores_iteracoes, 5)

    continuar = input("\n\nAperte enter para continuar: ")
    main()



def main():
    os.system("chcp 65001")
    os.system("clear");    
    print("Selecione o problema para analisar: ")
    print("1 - Problema 1 – Economia: Equilíbrio de Oferta e Demanda")
    print("2 - Problema 2 – Engenharia: Equação Não-Linear de um Diodo")
    print("3 - Problema 3 – Química: Equilíbrio de Dissociação de um Ácido Fraco")
    print("4 - Problema 4 – Física: Ponto de Equilíbrio no Potencial de Lennard-Jones")
    print("5 - Problema 5 – Engenharia (Controle): Ganho Crítico para Estabilidade de um Sistema de Realimentação")
    print("6 - Sair\n")

    escolha = int(input("Insira sua escolha: "))

    while escolha < 1 or escolha > 6:
        print("Escolha inválida.")
        escolha = int(input("Insira a escolha novamente: "))

    match escolha:
        case 1:
            problema_1()
        case 2:
            problema_2()
        case 3:
            problema_3()
        case 4:
            problema_4()
        case 5:
            problema_5()
        case 6:
            quit()
        


if __name__ == "__main__":
    main()
