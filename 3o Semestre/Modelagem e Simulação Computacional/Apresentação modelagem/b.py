import numpy as np
from scipy.integrate import odeint
import matplotlib.pyplot as plt


# Parâmetros
beta = 0.3
gamma = 0.1
N = 1000  # população total

# Condições iniciais
S0 = 990
I0 = 10
R0 = 0
y0 = [S0, I0, R0]

# Equações diferenciais (com divisão por N)
def deriv(y, t, beta, gamma, N):
    S, I, R = y
    dSdt = -beta * S * I / N
    dIdt = beta * S * I / N - gamma * I
    dRdt = gamma * I
    return [dSdt, dIdt, dRdt]

# Tempo
t = np.linspace(0, 160, 160)

# Integração
sol = odeint(deriv, y0, t, args=(beta, gamma, N))
S, I, R = sol.T

# Plot
plt.plot(t, S, label='S (pessoas)')
plt.plot(t, I, label='I (pessoas)')
plt.plot(t, R, label='R (pessoas)')
plt.xlabel('Tempo (dias)')
plt.ylabel('Número de pessoas')
plt.legend()
plt.title('SIR - Modelo com População Absoluta (com N)')
plt.grid()
plt.show()
