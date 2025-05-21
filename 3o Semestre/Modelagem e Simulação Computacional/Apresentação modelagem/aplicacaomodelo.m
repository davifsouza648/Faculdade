% Modelo SIR simples em MATLAB

% Parâmetros
beta  = 0.3;   % taxa de transmissão (por dia)
gamma = 0.1;   % taxa de recuperação (por dia)

% População e condições iniciais
N  = 200000000;     % total de pessoas
S0 = 199999999;      % suscetíveis iniciais
I0 = 1;        % infectados iniciais
R0 = 0;        % recuperados iniciais

% Tempo de simulação (dias)
t = 0:1:160;   % de 0 a 160 dias, passo de 1 dia

% Prealocações para velocidade
S = zeros(size(t));
I = zeros(size(t));
R = zeros(size(t));

% Valores iniciais
S(1) = S0;
I(1) = I0;
R(1) = R0;

% Loop de Euler Explícito
for k = 1:length(t)-1
    dS = -beta * S(k) * I(k) / N;
    dI =  beta * S(k) * I(k) / N - gamma * I(k);
    dR =  gamma * I(k);
    
    S(k+1) = S(k) + dS;   
    I(k+1) = I(k) + dI;  
    R(k+1) = R(k) + dR;   
end

% Plot
plot(t, S, '-b', t, I, '-r', t, R, '-g', 'LineWidth', 2)
xlabel('Dias')
ylabel('Número de pessoas')
legend('Suscetíveis','Infectados','Recuperados')
title('Modelo SIR (Euler Explícito)')
grid on
