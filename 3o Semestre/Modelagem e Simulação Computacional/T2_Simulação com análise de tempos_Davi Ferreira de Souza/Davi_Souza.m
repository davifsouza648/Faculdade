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
temposMediosPorPessoa = [];
mediasTemposAteAtendido = [];
maioresTemposAteAtendido = [];
menoresTemposAteAtendido = [];


for iteracao = numeroIteracoes
    fila = []; %Reinicializa as variáveis para cada iteração
    temposFila = [];
    historico = [];
    temposAteAtendido = [];
    tempo = 0;
    tempoOcupado = 0;
    tempoDesocupado = 0;
    atendimentos = 0;

    for minuto = t
        if mod(minuto,2)
            qtd = poissrnd(1); %Adicione clientes em ticks impares (média 1 pessoa por chamada).
            fila = [fila, ones(1, qtd)]; %Adiciona 1s equivalentes a quantidade de pessoas que chegam
            temposFila = [temposFila, zeros(1,qtd)]; %Adiciona zeros para manter conta do tempo de espera do cliente na fila
        end

        historico = [historico, length(fila)]; %Mantém o histórico da fila
        temposFila = temposFila + 0.5; %Adiciona 0.5 minutos em cada tique em cada iteração para cada cliente não atendido.
            
        if tempo == 0 && isempty(fila) %Se não há atendimentos ocorrendo e a fila esta vazia então o caixa esta livre.
            tempoDesocupado = tempoDesocupado + 0.5; %Adiciona tempoDesocupado para cada vez que o tempo for zero e a fila estiver vazia.
        end

        if tempo == 0 && ~isempty(fila)
            fila(1) = [];  % atende uma pessoa
            atendimentos = atendimentos + 1;
            tempo = randi(4) / 2; % tempo de atendimento aleatório
            temposAteAtendido = [temposAteAtendido, temposFila(1)]; %Adiciona nos temposAteAtendido o tempo que demorou para o cliente ser atendido
            temposFila(1) = []; %Remove o tempo de atendimento do cliente ja atendido
        end

        if tempo > 0
            tempo = tempo - 0.5; % Subtrai o tempo do atendimento
            tempoOcupado = tempoOcupado + 0.5; %Se esta atendendo, aumenta o tempo ocupado do servidor.
        end
    end
    
    tempoMedioPorPessoa = tempoOcupado / atendimentos;
    temposMediosPorPessoa = [temposMediosPorPessoa, tempoMedioPorPessoa];
    
    mediasTsAleatorio = [mediasTsAleatorio, mean(historico)]; %Adiciona nos vetores que serão plotados os valores dessa iteração
    maioresTsAleatorio = [maioresTsAleatorio, max(historico)];
    restosTsAleatorio = [restosTsAleatorio, length(fila)];
    temposOcupado = [temposOcupado, tempoOcupado];
    temposDesocupado = [temposDesocupado, tempoDesocupado];
    mediasTemposAteAtendido = [mediasTemposAteAtendido, mean(temposAteAtendido)];
    maioresTemposAteAtendido = [maioresTemposAteAtendido, max(temposAteAtendido)];
    menoresTemposAteAtendido = [menoresTemposAteAtendido, min(temposAteAtendido)];

end

% ANÁLISE:
fprintf("RELATÓRIO DO TRABALHO 2\n\n");

fprintf("***************************************************\n");

fprintf("ANÁLISE DE TAMANHO DE FILA:\n"); %Relatório Fila
fprintf("Tamanho médio da fila: %.2f\n", mean(mediasTsAleatorio));
fprintf("Desvio padrão: %.2f\n", std(mediasTsAleatorio));
fprintf("Coeficiente de Variação: %.2f %%\n", std(mediasTsAleatorio)/mean(mediasTsAleatorio)*100);
fprintf("Maior valor absoluto da fila: %d\n", max(maioresTsAleatorio));
fprintf("Maior tamanho de fila no encerramento: %d\n\n", max(restosTsAleatorio));

fprintf("***************************************************\n");

%Adição de análises
fprintf("ANÁLISE DO TEMPO DE USUÁRIOS NA FILA\n"); %Análise de usuários na fila
fprintf("Tempo médio em fila: %.2f minutos\n", mean(mediasTemposAteAtendido));
fprintf("Desvio padrão: %.2f\n", std(mediasTemposAteAtendido));
fprintf("Coeficiente de Variação: %.2f %%\n", std(mediasTemposAteAtendido)/mean(mediasTemposAteAtendido)*100);
fprintf("Maior valor absoluto: %.2f minutos\n", max(maioresTemposAteAtendido));
fprintf("Menor valor absoluto: %.2f minutos\n\n", min(menoresTemposAteAtendido));

fprintf("***************************************************\n");

fprintf("ANÁLISE DO TEMPO DE OCUPAÇÃO DO SERVIDOR\n");
fprintf("Tempo médio de ocupação do servidor: %.2f minutos\n", mean(temposOcupado));
fprintf("Desvio padrão: %.2f\n", std(temposOcupado));
fprintf("Coeficiente de variação: %.2f %%\n", std(temposOcupado)/mean(temposOcupado)*100);
fprintf("Tempo médio que o servidor usou por pessoa: %.2f minutos\n", mean(temposMediosPorPessoa));
fprintf("Desvio padrão: %.2f\n", std(temposMediosPorPessoa));
fprintf("Coeficiente de Variação: %.2f %%\n", std(temposMediosPorPessoa)/mean(temposMediosPorPessoa)*100);
fprintf("Tempo médio do servidor livre: %.2f minutos\n",mean(temposDesocupado));
fprintf("Desvio padrão: %.2f\n", std(temposDesocupado));
fprintf("Coeficiente de variação: %.2f %%\n", std(temposDesocupado)/mean(temposDesocupado)*100);
fprintf("Maior tempo com o servidor ocioso: %.2f minutos\n\n", max(temposDesocupado));


%Plot e salvamento dos gráficos
fig = figure('Visible', 'off');
bar(mediasTsAleatorio);
title('Tamanho Médio das filas');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_media_fila.png');
close(fig);

fig = figure('Visible', 'off');
bar(maioresTsAleatorio);
title('Maior Tamanho Absoluto da Fila');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_maior_fila.png');
close(fig);

fig = figure('Visible', 'off');
bar(restosTsAleatorio);
title('Tamanho Absoluto da Fila no Encerramento');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_resto_fila.png');
close(fig);

fig = figure('Visible', 'off');
bar(temposOcupado);
title('Ocupação do Servidor');
xlabel('Número do Experimento');
ylabel('Tempo ocupado (em minutos)');
saveas(gcf, 'grafico_tempo_ocupacao.png');
close(fig);

fig = figure('Visible', 'off');
bar(temposDesocupado);
title('Desocupação do Servidor');
xlabel('Número do experimento');
ylabel('Tempo desocupado (em minutos)');
saveas(gcf, 'grafico_tempo_desocupado.png');
close(fig);

fig = figure('Visible', 'off');
bar(mediasTemposAteAtendido);
title('Tempo médio até ser atendido');
xlabel('Número do experimento');
ylabel('Tempo médio até ser atendido (em minutos)');
saveas(gcf, 'grafico_tempo_ate_atendido.png');
close(fig);
