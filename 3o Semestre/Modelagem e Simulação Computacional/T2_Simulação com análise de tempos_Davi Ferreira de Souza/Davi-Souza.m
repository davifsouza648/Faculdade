%Davi Ferreira de Souza
%Esse programa simula uma fila de banco, são feitas 30 iterações, em que cada é observada por 6 horas. Neste segundo trabalho, dobramos 
% a resolução do problema. Trabalho realizado para a disciplina de Modelagem e Simulação Computacional.
%11/04/2025


numeroIteracoes = 1:30;
t = 1:720;

rand('twister',sum(100*clock)); %Aleatorizar melhor.


mediasTsAleatorio = []; %Inicializa as variavéis.
maioresTsAleatorio = [];
restosTsAleatorio = [];
temposOcupado = [];
temposDesocupado = [];
mediasTemposAteAtendido = [];


for iteracao = numeroIteracoes
    fila = []; %Reinicializa as variáveis para cada iteração
    temposFila = [];
    historico = [];
    temposAteAtendido = [];
    tempo = 0;
    tempoOcupado = 0;
    tempoDesocupado = 0;

    for minuto = t
        if mod(minuto,2)
            qtd = poissrnd(1); %Adicione clientes em ticks impares.
            fila = [fila, ones(1, qtd)];
            temposFila = [temposFila, zeros(1,qtd)];
        end

        historico = [historico, length(fila)];
        temposFila = temposFila + 0.5; %Adiciona 0.5 minutos em cada tique em cada iteração para cada cliente não atendido.
            
        if tempo == 0 && isempty(fila)
            tempoDesocupado = tempoDesocupado + 0.5; %Adiciona tempoDesocupado para cada vez que o tempo for zero e a fila estiver vazia.
        end

        if tempo == 0 && ~isempty(fila)
            fila(1) = [];          % atende uma pessoa
            tempo = randi(4) / 2; % tempo de atendimento aleatório
            temposAteAtendido = [temposAteAtendido, temposFila(1)]; %Adiciona nos temposAteAtendido o tempo que demorou para o cliente ser atendido
            temposFila(1) = []; %Remove o tempo de atendimento do cliente ja atendido
        end

        if tempo > 0
            tempo = tempo - 0.5; % Subtrai o tempo do atendimento
            tempoOcupado = tempoOcupado + 0.5; %Se esta atendendo, aumenta o tempo ocupado do servidor.
        end
    end

    mediasTsAleatorio = [mediasTsAleatorio, mean(historico)]; %Adiciona nos vetores que serão plotados os valores dessa iteração
    maioresTsAleatorio = [maioresTsAleatorio, max(historico)];
    restosTsAleatorio = [restosTsAleatorio, length(fila)];
    temposOcupado = [temposOcupado, tempoOcupado];
    temposDesocupado = [temposDesocupado, tempoDesocupado];
    mediasTemposAteAtendido = [mediasTemposAteAtendido, mean(temposAteAtendido)];

end

% ANÁLISE:
fprintf("\nRelatório:\n\n");
fprintf("Análise da fila:\n");
fprintf("Média das médias do tamanho da fila: %.2f\n", mean(mediasTsAleatorio));
fprintf("Desvio padrão: %.2f\n", std(mediasTsAleatorio));
fprintf("Coeficiente de variação: %.2f %%\n", std(mediasTsAleatorio)/mean(mediasTsAleatorio)*100);
fprintf("Maior valor absoluto encontrado: %d\n", max(maioresTsAleatorio));
fprintf("Maior resto de fila no encerramento: %d\n\n", max(restosTsAleatorio));

%Adição de análises
fprintf("Análise de ocupação do servidor:\n"); %Ocupação do servidor
fprintf("Média de tempo de ocupação do servidor: %.2f\n minutos", mean(temposOcupado));
fprintf("Desvio padrão: %.2f\n", std(temposOcupado));
fprintf("Coeficiente de variação: %.2f %%\n\n", std(temposOcupado)/mean(temposOcupado)*100);

fprintf("Análise de desocupação do servidor:\n"); %Desocupação do servidor
fprintf("Média de tempo de descupação do servidor: %.2f minutos\n", mean(temposDesocupado));
fprintf("Desvio padrão: %.2f\n", std(temposDesocupado));
fprintf("Coeficiente de variação: %.2f %%\n\n", std(temposDesocupado)/mean(temposDesocupado)*100);

fprintf("Análise de tempo de atendimento por cliente:\n"); %Tempo de atendimento por cliente
fprintf("Média de tempo até ser atendido por pessoa: %.2f minutos\n", mean(temposAteAtendido));
fprintf("Desvio padrão: %.2f\n", std(temposAteAtendido));
fprintf("Coeficiente de variação: %.2f %%\n\n", std(temposAteAtendido)/mean(temposAteAtendido)*100);


%Plot e salvamento dos gráficos
figure;
bar(mediasTsAleatorio);
title('Tamanho Médio das filas');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_media_fila.png');

figure;
bar(maioresTsAleatorio);
title('Maior Tamanho Absoluto da Fila');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_maior_fila.png');

figure;
bar(restosTsAleatorio);
title('Tamanho Absoluto da Fila no Encerramento');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_resto_fila.png');

figure;
bar(temposOcupado);
title('Ocupação do Servidor');
xlabel('Número do Experimento');
ylabel('Tempo ocupado (em minutos)');
saveas(gcf, 'grafico_tempo_ocupacao.png');

figure;
bar(temposDesocupado);
title('Desocupação do Servidor');
xlabel('Número do experimento');
ylabel('Tempo desocupado (em minutos)');
saveas(gcf, 'grafico_tempo_desocupado.png');

figure;
bar(mediasTemposAteAtendido);
title('Tempo médio até ser atendido');
xlabel('Número do experimento');
ylabel('Tempo médio até ser atendido (em minutos)');
saveas(gcf, 'grafico_tempo_ate_atendido.png');
