% Parâmetros
beta  = 0.3;   % taxa de transmissão (por dia)
gamma = 0.1;   % taxa de recuperação (por dia)

% Condições iniciais
N  = 1000;     % total de pessoas
S0 = 999;      % suscetíveis iniciais
I0 = 1;        % infectados iniciais
R0 = 0;        % recuperados iniciais

% Tempo de simulação (dias)
t = 0:1:160;

% Inicialização dos vetores
S = zeros(1,length(t));
I = zeros(1,length(t));
R = zeros(1,length(t));

% Valores iniciais
S(1) = S0;
I(1) = I0;
R(1) = R0;

% Loop para calcular os valores durante o tempo
for k = 1:length(t)-1
    dS = -beta * S(k) * I(k) / N; % Calcula dS/dt
    dI =  beta * S(k) * I(k) / N - gamma * I(k); % Calcula dI/dT
    dR =  gamma * I(k); % Calcula dR/dt
    
    S(k+1) = S(k) + dS;  %Soma valor atual com a derivada para o próximo valor
    I(k+1) = I(k) + dI;  
    R(k+1) = R(k) + dR;   
end

% Plot
plot(t, S, '-r', t, I, '-g', t, R, '-b', 'LineWidth', 2)
xlabel('Dias')
ylabel('Número de pessoas')
legend('Suscetíveis','Infectados','Recuperados')
title('Modelo SIR')
grid on

