%Davi Ferreira de Souza
%Esse programa simula uma fila de banco com resolução de 1 segundo num
%período de 6 horas. Foi feito uma modificação sobre o trabalho 3 para ser
%possível adicionar novos servidores de maneira escalável para análise.
%30/04/2025

rand('twister',sum(100*clock)); %Aleatorizar melhor.

numeroIteracoes = 1:30;
t = 1:21600; %tempo de 6 horas em segundos

numeroServidores = 3; %Quantidade de servidores (escalável)

tamanhosMediosFila = []; %Inicialização de variáveis básicas.
tamanhosFilaEncerramento = [];
maioresTamanhosFila = [];
mediasOcupacaoPessoa = zeros(numeroServidores, numeroIteracoes(end)); %Gera uma matriz para dar conta da media de ocupação do servidor para cada pessoa
temposDesocupadosIteracao = zeros(numeroServidores, numeroIteracoes(end)); %Gera uma matriz para dar conta dos tempos desocupado de cada servidor em cada iteração
maioresValoresAbsolutosTempoDesocupado = zeros(1,numeroServidores); %Gera um vetor com o número de servidores e a cada iteração é testado se o tempodesocupado atual é maior que o previamente definido, inicialmente é 0.
temposMedioEsperaFila = [];
maioresValoresEsperaFila = [];
temposAbertoAposEncerramento = [];


for iteracao = numeroIteracoes
    fila = []; %Inicialização de variáveis para cada iteração
    historico = [];
    temposAteAtendido = [];
    temposAtendimento = [zeros(1,numeroServidores)]; %Iniciliza as varíaveis de acordo com a quantidade de servidores para análise separada de cada um.
    temposDesocupado = [zeros(1,numeroServidores)]; 
    temposOcupado = [zeros(1,numeroServidores)];
    atendimentos = [zeros(1,numeroServidores)];
    
    temposFila = [];
    qtdPessoasChegando = 0;
    

    for segundo = t
        i = rand(1); %Gera um número aleatório entre 0 e 1.
        
        switch true
            case i <= 0.983471453821617  %Checa por meio da função cumulativa de poisson com lambda = 1/60 a quantidade de pessoas que chegam.
                qtdPessoasChegando = 0;
            case i <= 0.999880675596995  
                qtdPessoasChegando = 1;
            case i <= 0.999937690703957  
                qtdPessoasChegando = 2;
            case i <= 0.999937763156063  
                qtdPessoasChegando = 3;
            case i <= 0.999937763158332  
                qtdPessoasChegando = 4;
            case i <= 0.999937763158333  
                qtdPessoasChegando = 5;
            otherwise                    
                qtdPessoasChegando = 0;  
        end

        fila = [fila, ones(1,qtdPessoasChegando)]; %Aumenta a quantidade de elementos na fila considerando a quantidade de pessoas que chegam
        temposFila = [temposFila, zeros(1,qtdPessoasChegando)]; %Adiciona zeros em uma fila para manter conta do tempo de atendimento por pessoa
        historico = [historico, length(fila)]; %Guarda o histórico da fila a cada segundo

        %Checagem de desocupação:
        
        for caixa = 1:numeroServidores
            if temposAtendimento(caixa) == 0 && isempty(fila) %Se o caixa não esta atendendo ninguém e a fila está vazia, então o caixa especifico está livre
                temposDesocupado(caixa) = temposDesocupado(caixa) + 1; % Correto %Adiciona 1 segundo no tempo desocupado.
            end
        end

        for caixa = 1:numeroServidores
            if(temposAtendimento(caixa) == 0 && ~isempty(fila))
                fila(1) = []; %Remove primeiro elemento da fila
                atendimentos(caixa) = atendimentos(caixa) + 1; %Adiciona um atendimento no caixa específico
                temposAtendimento(caixa) = max(1, round(normrnd(90, 10))); % Determina o tempo de atendimento para o caixa específico
                temposAteAtendido = [temposAteAtendido, temposFila(1)]; % Guarda o tempo que a pessoa demorou para ser atendida.
                temposFila(1) = []; %Limpa o primeiro elemento do tempos fila.
            end
        end


        temposFila = temposFila + 1; %Aumenta o tempo de espera de todos os usuários da fila que não foram atendidos.

        for caixa = 1:numeroServidores %Itera por todos os servidores para aumentar o tempo ocupado e diminuir o tempo de serviço se estiver ocupado.
            if temposAtendimento(caixa) > 0
                temposOcupado(caixa) = temposOcupado(caixa) + 1;
                temposAtendimento(caixa) = temposAtendimento(caixa) - 1;
            end
        end


    end

    tempoAbertoEncerramento = 0; %Inicializa a variável para contar quando tempo o banco ficará aberto após o encerramento
    tamanhosFilaEncerramento = [tamanhosFilaEncerramento, length(fila)]; %Guarda o tamanho da fila no encerramento

    while ~isempty(fila) %Continua atendendo enquanto a fila não esta vázia
        tempoAbertoEncerramento = tempoAbertoEncerramento + 1; %Incrementa o tempo de serviço após encerramento
        for caixa = 1:numeroServidores
            if temposAtendimento(caixa) == 0 && ~isempty(fila) %Se caixa está livre atende alguém.
                fila(1) = []; %Remove primeiro elemento da fila
                atendimentos(caixa) = atendimentos(caixa) + 1; %Adiciona um atendimento no caixa específico
                temposAtendimento(caixa) = max(1, round(normrnd(90, 10))); % Determina o tempo de atendimento para o caixa específico
                temposAteAtendido = [temposAteAtendido, temposFila(1)]; % Guarda o tempo que a pessoa demorou para ser atendida.
                temposFila(1) = []; %Limpa o primeiro elemento do tempos fila.
            end
        end

        for caixa = 1: numeroServidores %Itera por todos os servidores para aumentar o tempo ocupado e diminuir o tempo de serviço se estiver ocupado.
            if temposAtendimento(caixa) > 0 
                temposOcupado(caixa) = temposOcupado(caixa) + 1; 
                temposAtendimento(caixa) = temposAtendimento(caixa) - 1;
            end
        end

        temposFila = temposFila + 1; %Aumenta o tempo de espera de todos os usuários da fila que não foram atendidos.
    end

    for caixa = 1:numeroServidores %Itera por todos os caixas para guardar as estatísticas da iteração atual nos vetores correspondentes
        mediasOcupacaoPessoa(caixa,iteracao) = temposOcupado(caixa) / max(1, atendimentos(caixa)); %Guarda a ocupação média por pessoa da iteração atual
        temposDesocupadosIteracao(caixa,iteracao) = temposDesocupado(caixa); %Guarda o tempo de desocupação do caixa na iteração atual.
        if(temposDesocupado(caixa) > maioresValoresAbsolutosTempoDesocupado(caixa)) %Checa se o tempo de desocupação dessa iteração é maior que a definida previamente
            maioresValoresAbsolutosTempoDesocupado(caixa) = temposDesocupado(caixa); %Define o novo maior tempo de desocupação do caixa.
        end
    end

    tamanhosMediosFila = [tamanhosMediosFila, mean(historico)]; %Guarda o tamanho médio da fila na iteração
    maioresTamanhosFila = [maioresTamanhosFila, max(historico)];
    temposMedioEsperaFila = [temposMedioEsperaFila, mean(temposAteAtendido)];
    maioresValoresEsperaFila = [maioresValoresEsperaFila, max(temposAteAtendido)];
    temposAbertoAposEncerramento = [temposAbertoAposEncerramento, tempoAbertoEncerramento];
end


%Gera o relatório
fprintf("RELATÓRIO DO TRABALHO 4\n\n");

fprintf("***************************************************\n");

fprintf("\nANÁLISE DE TAMANHO DE FILA:\n");
fprintf("Tamanho médio da fila: %.2f\n", mean(tamanhosMediosFila));
fprintf("Desvio padrão: %.2f pessoas\n", std(tamanhosMediosFila));
fprintf("Coeficiente de Variação: %.2f %%\n\n", std(tamanhosMediosFila)/mean(tamanhosMediosFila)*100);

fprintf("***************************************************\n");

fprintf("\nANÁLISE DE TAMANHO FILA NO ENCERRAMENTO:\n");
fprintf("Tamanho médio da fila no encerramento: %.2f\n", mean(tamanhosFilaEncerramento));
fprintf("Desvio padrão: %.2f pessoas\n", std(tamanhosFilaEncerramento));
fprintf("Coeficiente de variação: %.2f %%\n", std(tamanhosFilaEncerramento)/mean(tamanhosFilaEncerramento)*100);
fprintf("Maior tamanho da fila no encerramento: %d\n", max(tamanhosFilaEncerramento));
fprintf("Maior tamanho absoluto da fila: %d\n\n", max(maioresTamanhosFila));

fprintf("***************************************************\n");

fprintf("Análise dos servidores:\n\n");

for caixa = 1:numeroServidores
    fprintf("\nSERVIDOR %d:\n\n", caixa);

    fprintf("ANÁLISE DE TEMPO DE OCUPAÇÃO DO SERVIDOR %d POR PESSOA:\n", caixa);
    fprintf("Média de tempo de ocupação do servidor por pessoa: %.2f minutos\n", mean(mediasOcupacaoPessoa(caixa,:)) / 60);
    fprintf("Desvio Padrão: %.2f minutos\n", std(mediasOcupacaoPessoa(caixa,:)) / 60);
    fprintf("Coeficiente de variação: %.2f %%\n\n", std(mediasOcupacaoPessoa(caixa,:))/mean(mediasOcupacaoPessoa(caixa,:))*100);

    fprintf("ANÁLISE DE DESOCUPAÇÃO DO SERVIDOR %d:\n", caixa);
    fprintf("Tempo médio de desocupação do servidor: %.2f minutos\n", mean(temposDesocupadosIteracao(caixa))/60);
    fprintf("Desvio padrão: %.2f minutos\n", std(temposDesocupadosIteracao(caixa,:)) / 60);
    fprintf("Coeficiente de variação: %.2f %%\n", std(temposDesocupadosIteracao(caixa,:))/mean(temposDesocupadosIteracao(caixa,:))*100);
    fprintf("Maior tempo absoluto de desocupação do servidor: %.2f minutos\n\n", maioresValoresAbsolutosTempoDesocupado(caixa) / 60);

end

fprintf("***************************************************\n");

fprintf("ANÁLISE DO TEMPO DE USUÁRIOS NA FILA:\n");
fprintf("Tempo médio em fila: %.2f minutos\n", mean(temposMedioEsperaFila) / 60);
fprintf("Desvio padrão: %.2f minutos\n", std(temposMedioEsperaFila) / 60);
fprintf("Coeficiente de variação: %.2f %%\n", std(temposMedioEsperaFila)/mean(temposMedioEsperaFila)*100);
fprintf("Maior tempo absoluto que alguém ficou na fila: %.2f minutos\n\n", max(temposMedioEsperaFila)/60);

fprintf("***************************************************\n");

fprintf("ANÁLISE DE TEMPO DE SERVIÇO APÓS ENCERRAMENTO DO BANCO:\n");
fprintf("Média de tempo de serviço após encerramento do banco: %.2f minutos\n", mean(temposAbertoAposEncerramento)/60);
fprintf("Desvio padrão: %.2f minutos\n", std(temposAbertoAposEncerramento) / 60);
fprintf("Coeficiente de variação: %.2f %%\n", std(temposAbertoAposEncerramento)/mean(temposAbertoAposEncerramento)*100);
fprintf("Maior tempo absoluto de serviço após encerramento: %.2f minutos\n", max(temposAbertoAposEncerramento)/60);


%Geração de gráficos
fig = figure('Visible', 'off');
bar(tamanhosMediosFila);
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


for caixa = 1:numeroServidores
    fig = figure('Visible', 'off');
    
    % Dados corretos (todas iterações para o servidor atual)
    tempos_desocupados_min = temposDesocupadosIteracao(caixa,:) / 60;
    
    bar(tempos_desocupados_min);
    title(['Tempo de desocupação do servidor ' num2str(caixa)]);
    xlabel('Número do Experimento');
    ylabel('Tempo de desocupação (minutos)');
    grid on; % Adiciona grid para melhor visualização
    
    saveas(gcf, sprintf('grafico_tempos_desocupacao_caixa_%d.png', caixa));
    close(fig);
end



temposMedioEsperaFila = temposMedioEsperaFila / 60;

fig = figure('Visible', 'off');
bar(temposMedioEsperaFila);
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
