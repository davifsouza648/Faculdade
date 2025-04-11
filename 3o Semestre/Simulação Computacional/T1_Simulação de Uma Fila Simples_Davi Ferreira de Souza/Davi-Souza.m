numeroIteracoes = 1:30;
t = 1:360;

rand('twister',sum(100*clock)); %Aleatorizar melhor.

% ANÁLISE 1:
mediasTs1 = [];
maioresTs1 = [];
restosTs1 = [];

for iteracao = numeroIteracoes
    fila = [];
    historico = [];
    tempo = 0;

    for minuto = t
        qtd = poissrnd(1);
        fila = [fila, ones(1, qtd)]; %adiciona números 1s equivalente a qtd de pessoas que chegam.

        historico = [historico, length(fila)];

        if tempo == 0 && ~isempty(fila)
            fila(1) = []; %remove primeiro elemento da fila
            tempo = 1;    
        end

        if tempo > 0
            tempo = tempo - 1;
        end
    end

    mediasTs1 = [mediasTs1, mean(historico)];
    maioresTs1 = [maioresTs1, max(historico)];
    restosTs1 = [restosTs1, length(fila)];
end

% Mostrar relatório Ts = 1
fprintf("\nRelatório para Ts = 1:\n");
fprintf("Média das médias do tamanho da fila: %.2f\n", mean(mediasTs1));
fprintf("Desvio padrão: %.2f\n", std(mediasTs1));
fprintf("Coeficiente de variação: %.2f %%\n", std(mediasTs1)/mean(mediasTs1)*100);
fprintf("Maior valor absoluto encontrado: %d\n", max(maioresTs1));
fprintf("Maior resto de fila no encerramento: %d\n", max(restosTs1));

% Gráficos Ts = 1
figure;
bar(mediasTs1);
title('Tamanho Médio das filas (Ts = 1)');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_media_Ts1.png');

figure;
bar(maioresTs1);
title('Maior Tamanho Absoluto da Fila (Ts = 1)');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_maior_Ts1.png');

figure;
bar(restosTs1);
title('Tamanho Absoluto da Fila no Encerramento (Ts = 1)');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_resto_Ts1.png');



% Segundo cenário: Ts aleatório entre 1 e 2
mediasTsAleatorio = [];
maioresTsAleatorio = [];
restosTsAleatorio = [];

for iteracao = numeroIteracoes
    fila = [];
    historico = [];
    tempo = 0;

    for minuto = t
        qtd = poissrnd(1);
        fila = [fila, ones(1, qtd)];

        historico = [historico, length(fila)];

        if tempo == 0 && ~isempty(fila)
            fila(1) = [];          % atende uma pessoa
            tempo = randi([1, 2]); % tempo de atendimento aleatório
        end

        if tempo > 0
            tempo = tempo - 1;
        end
    end

    mediasTsAleatorio = [mediasTsAleatorio, mean(historico)];
    maioresTsAleatorio = [maioresTsAleatorio, max(historico)];
    restosTsAleatorio = [restosTsAleatorio, length(fila)];
end

% ANÁLISE 2:
fprintf("\nRelatório para Ts entre 1 e 2:\n");
fprintf("Média das médias do tamanho da fila: %.2f\n", mean(mediasTsAleatorio));
fprintf("Desvio padrão: %.2f\n", std(mediasTsAleatorio));
fprintf("Coeficiente de variação: %.2f %%\n", std(mediasTsAleatorio)/mean(mediasTsAleatorio)*100);
fprintf("Maior valor absoluto encontrado: %d\n", max(maioresTsAleatorio));
fprintf("Maior resto de fila no encerramento: %d\n", max(restosTsAleatorio));

% Gráficos Ts entre 1 e 2
figure;
bar(mediasTsAleatorio);
title('Tamanho Médio das filas (Ts = [1,2])');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_media_Ts1_2.png');

figure;
bar(maioresTsAleatorio);
title('Maior Tamanho Absoluto da Fila (Ts = [1,2])');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_maior_Ts1_2.png');

figure;
bar(restosTsAleatorio);
title('Tamanho Absoluto da Fila no Encerramento (Ts = [1,2])');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_resto_Ts1_2.png');
