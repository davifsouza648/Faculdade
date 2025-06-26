import os
import math
import time
import numpy as np
import matplotlib.pyplot as plt
from tabulate import tabulate

# Funções matemáticas a serem integradas
def f1(x): return x**2 + 2*x + 1

def f2(x): return math.sin(x)

def f3(x): return math.exp(x)

# Funções vetorizadas para gráficos
def f1_np(x): return x**2 + 2*x + 1

def f2_np(x): return np.sin(x)

def f3_np(x): return np.exp(x)

# Valores exatos das integrais definidas
valores_exatos = {
    'f1': 53.333333333333336,
    'f2': 2.0,
    'f3': math.exp(2) - 1
}

# Método do Trapézio
def trapezio(f, a, b, n):
    h = (b - a) / n
    soma = f(a) + f(b)
    for i in range(1, n):
        soma += 2 * f(a + i*h)
    return (h / 2) * soma

# Método de Simpson 1/3
def simpson_1_3(f, a, b, n):
    if n % 2 != 0:
        raise ValueError("Simpson 1/3 requer número par de subintervalos.")
    h = (b - a) / n
    soma = f(a) + f(b)
    for i in range(1, n):
        coef = 4 if i % 2 != 0 else 2
        soma += coef * f(a + i*h)
    return (h / 3) * soma

# Método de Simpson 3/8
def simpson_3_8(f, a, b, n):
    if n % 3 != 0:
        raise ValueError("Simpson 3/8 requer número múltiplo de 3.")
    h = (b - a) / n
    soma = f(a) + f(b)
    for i in range(1, n):
        coef = 3 if i % 3 != 0 else 2
        soma += coef * f(a + i*h)
    return (3 * h / 8) * soma

# Cálculo de erros
def erro_absoluto(aprox, exato):
    return abs(exato - aprox)

def erro_relativo(aprox, exato):
    return erro_absoluto(aprox, exato) / abs(exato)

# Função para geração de gráficos
def gerar_graficos(f, f_np, a, b, exato, resultados, pasta):
    os.makedirs(pasta, exist_ok=True)

    x_vals = np.linspace(a, b, 1000)
    y_vals = f_np(x_vals)

    # Gráfico de aproximações
    plt.figure()
    plt.plot(x_vals, y_vals, label='f(x)', color='black')
    for metodo, dados in resultados.items():
        if 'aproximacao' in dados:
            plt.axhline(y=dados['aproximacao'], linestyle='--', label=f"{metodo} ≈ {dados['aproximacao']:.4f}")
    plt.axhline(y=exato, color='green', label=f"Valor exato ≈ {exato:.4f}")
    plt.title('Aproximações das integrais')
    plt.legend()
    plt.grid(True)
    plt.savefig(os.path.join(pasta, 'aproximacoes.png'))
    plt.close()

    # Gráfico de erro absoluto
    plt.figure()
    metodos = list(resultados.keys())
    erros_abs = [dados.get('erro_abs', 0) for dados in resultados.values()]
    plt.bar(metodos, erros_abs, color='red')
    plt.title('Erro Absoluto')
    plt.ylabel('Erro')
    plt.grid(axis='y')
    plt.savefig(os.path.join(pasta, 'erro_absoluto.png'))
    plt.close()

    # Gráfico de erro relativo
    plt.figure()
    erros_rel = [dados.get('erro_rel', 0) for dados in resultados.values()]
    plt.bar(metodos, erros_rel, color='blue')
    plt.title('Erro Relativo')
    plt.ylabel('Erro (%)')
    plt.grid(axis='y')
    plt.savefig(os.path.join(pasta, 'erro_relativo.png'))
    plt.close()

# Função principal de execução

def executar_metodos(f, f_np, a, b, exato, nome_funcao, nome_pasta):
    resultados = {}

    metodos = [
        ("Trapézio", trapezio, 10),
        ("Simpson 1/3", simpson_1_3, 10),
        ("Simpson 3/8", simpson_3_8, 6)
    ]

    for nome, metodo, n in metodos:
        try:
            t_inicio = time.time()
            aprox = metodo(f, a, b, n)
            t_fim = time.time()
            resultados[nome] = {
                'aproximacao': aprox,
                'erro_abs': erro_absoluto(aprox, exato),
                'erro_rel': erro_relativo(aprox, exato),
                'tempo': t_fim - t_inicio,
                'subintervalos': n
            }
        except Exception as e:
            resultados[nome] = {'erro': str(e)}

    gerar_graficos(f, f_np, a, b, exato, resultados, nome_pasta)

    print(f"\nResultados para {nome_funcao}:")
    tabela_resultados = []
    for nome, dados in resultados.items():
        if 'erro' in dados:
            tabela_resultados.append([nome, 'Erro', '-', '-', '-', '-'])
        else:
            tabela_resultados.append([
                nome,
                f"{dados['aproximacao']:.8f}",
                f"{dados['erro_abs']:.2e}",
                f"{dados['erro_rel']:.2%}",
                dados['subintervalos'],
                f"{dados['tempo']:.6f} s"
            ])
    print(tabulate(tabela_resultados, headers=["Método", "Aproximação", "Erro Absoluto", "Erro Relativo", "Subintervalos", "Tempo"], tablefmt="heavy_outline"))

# Menu principal

def menu():
    while True:
        os.system("cls" if os.name == "nt" else "clear")
        print("Métodos Numéricos - Integração Definida\n")
        print("1 - f(x) = x² + 2x + 1    [0, 4]")
        print("2 - f(x) = sin(x)         [0, π]")
        print("3 - f(x) = e^x            [0, 2]")
        print("4 - Sair\n")
        opcao = input("Escolha uma opção: ")

        match opcao:
            case '1':
                executar_metodos(f1, f1_np, 0, 4, valores_exatos['f1'], "f(x) = x² + 2x + 1", "saida_f1")
                input("\nPressione enter para continuar...")
            case '2':
                executar_metodos(f2, f2_np, 0, math.pi, valores_exatos['f2'], "f(x) = sin(x)", "saida_f2")
                input("\nPressione enter para continuar...")
            case '3':
                executar_metodos(f3, f3_np, 0, 2, valores_exatos['f3'], "f(x) = e^x", "saida_f3")
                input("\nPressione enter para continuar...")
            case '4':
                break
            case _:
                input("\nOpção inválida. Pressione enter para tentar novamente.")

if __name__ == "__main__":
    menu()
