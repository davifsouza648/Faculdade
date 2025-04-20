%Davi Ferreira de Souza
%Esse programa simula uma fila de banco, são feitas 30 iterações, em que
%cada uma é observada por 6 horas. Neste terceiro trabalho, utilizamos a
%resolução de 1 segundo para análise. Trabalho realizado para a disciplina
%de Modelagem e Simulação Computacional.
%20/04/2025

rand('twister',sum(100*clock)); %Aleatorizar melhor.

numeroIteracoes = 1:30;
t = 1:21600; %tempo de 6 horas em segundos



mediasTamanhoFila = []; %Inicializa as variáveis que guardam as informações por iteração
tamanhosFilaEncerramento = [];
maioresTamanhoFila = [];
temposMedioOcupacaoPessoa = [];
temposDesocupado = [];
mediasEsperasFila = [];
maioresTempoEsperaFila = [];
temposAbertoAposEncerramento = [];



for iteracao = numeroIteracoes
    fila = []; %Reinicializa as variáveis para cada iteração
    temposFila = [];
    historico = [];
    temposAteAtendido = [];
    tempo = 0;
    tempoDesocupado = 0;
    tempoOcupado = 0;
    atendimentos = 0;
    qtdPessoasChegando = 0; %Inicialização da variável qtdPessoasChegando

    for segundo = t
        i = rand(1); %Gera um número aleatorio entre 0 e 1;
        switch true %Switch case para determinar a quantidade de pessoas chegando levando em conta a função poisscdf com valores obtidos entre 0 e 7
            case i <= 0.006131324019524
                qtdPessoasChegando = 0;
            case i >= 0.006131324019524 && i <= 0.012262648039048
                qtdPessoasChegando = 1;
            case i >= 0.012262648039048 && i <= 0.015328310048810
                qtdPessoasChegando = 2;
            case i >= 0.015328310048810 && i <= 0.016350197385397
                qtdPessoasChegando = 3;
            case i >= 0.016350197385397 && i <= 0.016605669219544
                qtdPessoasChegando = 4;
            case i >= 0.016605669219544 && i <= 0.016656763586374
                qtdPessoasChegando = 5;
            case i >= 0.016656763586374 && i <= 0.016665279314179
                qtdPessoasChegando = 6;
            case i >= 0.016665279314179 && i <= 0.016666495846722
                qtdPessoasChegando = 7;
            otherwise
                qtdPessoasChegando = 0;
        end

        fila = [fila, ones(1,qtdPessoasChegando)]; %Aumenta a quantidade de elementos na fila considerando a quantidade de pessoas que chegam
        temposFila = [temposFila, zeros(1,qtdPessoasChegando)]; %Adiciona zeros em uma fila para manter conta do tempo de atendimento por pessoa
        historico = [historico, length(fila)]; %Guarda o histórico da fila a cada segundo

        if tempo == 0 && isempty(fila) %Se não esta em serviço e a fila esta vazia então o caixa esta livre
            tempoDesocupado = tempoDesocupado + 1; %Incrementa um segundo no tempoDesocupado
        end

        if tempo == 0 && ~isempty(fila) %Se não esta em serviço e a fila tem pessoas inicia atendimento
            fila(1) = []; %Remove primeiro elemento da fila
            atendimentos = atendimentos + 1; %Aumenta o número de atendimentos
            tempo = max(1, round(normrnd(90, 10))); %Determina o tempo de atendimento usando uma distribuição normal com média = 90 e variação = 10, mas impede de ser menor que 1.
            temposAteAtendido = [temposAteAtendido, temposFila(1)]; %Guarda o tempo até ser atendido da pessoa atendida
            temposFila(1) = []; %Limpa o primeiro elemento do vetor de tempos na fila.
        end

        temposFila = temposFila + 1; %Incrementa um segundo em todos os elementos do vetor tempos na fila.

        if tempo > 0 %Se esta em serviço decrementa o tempo de serviço gerado
            tempoOcupado = tempoOcupado + 1; %Incrementa o tempo ocupado em 1 segundo.
            tempo = tempo - 1; 
        end
    end


    tempoAbertoEncerramento = 0; %Inicializa a variável para contar quando tempo o banco ficará aberto após o encerramento
    tamanhosFilaEncerramento = [tamanhosFilaEncerramento, length(fila)]; %Guarda o tamanho da fila no encerramento

    while ~isempty(fila) %Continua atendendo enquanto a fila não estiver vazia
        tempoAbertoEncerramento = tempoAbertoEncerramento + 1; %Incrementa o tempo de serviço após encerramento
        if tempo == 0 && ~isempty(fila) %Se não esta em serviço, atende nova pessoa
            fila(1) = []; %Mesma lógica de atendimento do loop acima.
            atendimentos = atendimentos + 1; 
            tempo = max(1, round(normrnd(90, 10)));
            temposAteAtendido = [temposAteAtendido, temposFila(1)];
            temposFila(1) = [];
        end
        if tempo > 0 %Decrementa o tempo de serviço
            tempoOcupado = tempoOcupado + 1; %Novamente adiciona 1 segundo no tempo ocupado
            tempo = tempo - 1;
        end

        temposFila = temposFila + 1;
    end

    tempoMedioPorPessoa = tempoOcupado / atendimentos; %Determina o tempo de atendimento médio por pessoa considerando o número de atendimentos.
    temposMedioOcupacaoPessoa = [temposMedioOcupacaoPessoa, tempoMedioPorPessoa]; %Guarda a média por pessoa da iteração numa variável
    mediasTamanhoFila = [mediasTamanhoFila, mean(historico)]; %Guarda o tamanho médio da fila na iteração
    maioresTamanhoFila = [maioresTamanhoFila, max(historico)]; %Guarda o maior tamanho da fila na iteração
    temposDesocupado = [temposDesocupado, tempoDesocupado]; %Guarda o tempo desocupado do servidor na iteração
    mediasEsperasFila = [mediasEsperasFila, mean(temposAteAtendido)]; %Guarda o tempo médio de espera na iteração
    maioresTempoEsperaFila = [maioresTempoEsperaFila, max(temposAteAtendido)]; %Guarda o maior tempo de espera na iteração
    temposAbertoAposEncerramento = [temposAbertoAposEncerramento, tempoAbertoEncerramento]; %Guarda o tempo aberto após encerramento do banco.
end

%Gera o relatório
fprintf("RELATÓRIO DO TRABALHO 2\n\n");

fprintf("***************************************************\n");

fprintf("ANÁLISE DE TAMANHO DE FILA:\n");
fprintf("Tamanho médio da fila: %.2f\n", mean(mediasTamanhoFila));
fprintf("Desvio padrão: %.2f\n", std(mediasTamanhoFila));
fprintf("Coeficiente de Variação: %.2f %%\n\n", std(mediasTamanhoFila)/mean(mediasTamanhoFila)*100);

fprintf("***************************************************\n");

fprintf("ANÁLISE DE TAMANHO FILA NO ENCERRAMENTO:\n");
fprintf("Tamanho médio da fila no encerramento: %.2f\n", mean(tamanhosFilaEncerramento));
fprintf("Desvio padrão: %.2f\n", std(tamanhosFilaEncerramento));
fprintf("Coeficiente de variação: %.2f %%\n", std(tamanhosFilaEncerramento)/mean(tamanhosFilaEncerramento)*100);
fprintf("Maior tamanho da fila no encerramento: %d\n", max(tamanhosFilaEncerramento));
fprintf("Maior tamanho absoluto da fila: %d\n\n", max(maioresTamanhoFila));

fprintf("***************************************************\n");

fprintf("ANÁLISE DE TEMPO DE OCUPAÇÃO DO SERVIDOR POR PESSOA:\n");
fprintf("Média de tempo de ocupação do servidor por pessoa: %.2f minutos\n", mean(temposMedioOcupacaoPessoa) / 60);
fprintf("Desvio Padrão: %.2f\n", std(temposMedioOcupacaoPessoa));
fprintf("Coeficiente de variação: %.2f %%\n\n", std(temposMedioOcupacaoPessoa)/mean(temposMedioOcupacaoPessoa)*100);

fprintf("***************************************************\n");

fprintf("ANÁLISE DE DESOCUPAÇÃO DO SERVIDOR:\n");
fprintf("Tempo médio de desocupação do servidor: %.2f minutos\n", mean(temposDesocupado)/60);
fprintf("Desvio padrão: %.2f\n", std(temposDesocupado));
fprintf("Coeficiente de variação: %.2f %%\n", std(temposDesocupado)/mean(temposDesocupado)*100);
fprintf("Maior tempo absoluto de desocupação do servidor: %.2f minutos\n\n", max(temposDesocupado)/60);

fprintf("***************************************************\n");

fprintf("ANÁLISE DO TEMPO DE USUÁRIOS NA FILA:\n");
fprintf("Tempo médio em fila: %.2f minutos\n", mean(mediasEsperasFila) / 60);
fprintf("Desvio padrão: %.2f\n", std(mediasEsperasFila));
fprintf("Coeficiente de variação: %.2f %%\n", std(mediasEsperasFila)/mean(mediasEsperasFila)*100);
fprintf("Maior tempo absoluto que alguém ficou na fila: %.2f minutos\n\n", max(mediasEsperasFila)/60);

fprintf("***************************************************\n");

fprintf("ANÁLISE DE TEMPO DE SERVIÇO APÓS ENCERRAMENTO DO BANCO:\n");
fprintf("Média de tempo de serviço após encerramento do banco: %.2f minutos\n", mean(temposAbertoAposEncerramento)/60);
fprintf("Desvio padrão: %.2f\n", std(temposAbertoAposEncerramento));
fprintf("Coeficiente de variação: %.2f %%\n", std(temposAbertoAposEncerramento)/mean(temposAbertoAposEncerramento)*100);
fprintf("Maior tempo absoluto de serviço após encerramento: %.2f minutos\n", max(temposAbertoAposEncerramento)/60);



fig = figure('Visible', 'off');
bar(mediasTamanhoFila);
title('Tamanho Médio das filas');
xlabel('Número do Experimento');
ylabel('Tamanho da Fila');
saveas(gcf, 'grafico_media_fila.png');
close(fig);

fig = figure('Visible', 'off');
bar(tamanhosFilaEncerramento);
title('Tamanho das filas no encerramento do banco');
xlabel('Número do Experimento');
ylabel('Tamanho da fila no encerramento');
saveas(gcf, 'grafico_tamanho_fila_encerramento.png');
close(fig);

temposDesocupado = temposDesocupado / 60;

fig = figure('Visible', 'off');
bar(temposDesocupado);
title('Tempo de desocupação do servidor');
xlabel('Número do Experimento');
ylabel('Tempo de desocupação (em minutos)');
saveas(gcf, 'grafico_tempos_desocupacao.png');
close(fig);

mediasEsperasFila = mediasEsperasFila / 60;

fig = figure('Visible', 'off');
bar(mediasEsperasFila);
title('Tempo médio de espera na fila');
xlabel('Número do experimento');
ylabel('Tempo de espera (em minutos)');
saveas(gcf, 'grafico_tempo_espera.png');
close(fig);

temposAbertoAposEncerramento = temposAbertoAposEncerramento / 60;

fig = figure('Visible', 'off');
bar(temposAbertoAposEncerramento);
title('Tempo que o banco ficou aberto após encerramento');
xlabel('Número do experimento');
ylabel('Tempo de serviço (em minutos)');
saveas(gcf, 'grafico_tempo_aberto_encerramento.png');
close(fig);