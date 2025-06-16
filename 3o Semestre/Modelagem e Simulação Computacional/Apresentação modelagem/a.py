import numpy as np
from scipy.integrate import odeint
import matplotlib.pyplot as plt

# Parâmetros
beta = 0.3   # taxa de infecção
gamma = 0.1  # taxa de recuperação

# População normalizada (frações)
S0 = 0.99    # 99% suscetíveis
I0 = 0.01    # 1% infectados
R0 = 0.0     # 0% recuperados
y0 = [S0, I0, R0]

# Equações diferenciais (sem dividir por N)
def deriv(y, t, beta, gamma):
    S, I, R = y
    dSdt = -beta * S * I
    dIdt = beta * S * I - gamma * I
    dRdt = gamma * I
    return [dSdt, dIdt, dRdt]

# Tempo
t = np.linspace(0, 160, 160)

# Integração
sol = odeint(deriv, y0, t, args=(beta, gamma))
S, I, R = sol.T

# Plot
plt.plot(t, S, label='S (fração)')
plt.plot(t, I, label='I (fração)')
plt.plot(t, R, label='R (fração)')
plt.xlabel('Tempo (dias)')
plt.ylabel('Proporção')
plt.legend()
plt.title('SIR - Modelo com Frações (sem N)')
plt.grid()
plt.show()
