%Davi Ferreira de Souza
%09/05/2025
%Trabalho 5 - Modelagem e Simulação Computacional - Realiza a simulação de uma rede de interconexão modelo shared Bus com protocolo TDM

pkg load statistics %Carrega o pacote statistics para usar a função poissrnd

rand('twister',sum(100*clock));

tempo_simulacao = 18000; %Tempo total de simulação
numero_iteracoes = 30; %Número de iterações

medias_p1_iteracao = []; #Inicialização dos vetores correspondentes a cada nó
valores_maximos_p1_iteracao = [];

medias_p2_iteracao = [];
valores_maximos_p2_iteracao = [];

medias_p3_iteracao = [];
valores_maximos_p3_iteracao = [];

tic %Inicializa a contagem de tempo

for iteracao = 1:numero_iteracoes %Loop de iterações

   Nop1 = []; %Inicialização de variáveis para cada iteração
   Nop2 = [];
   Nop3 = [];

   historico_p1 = zeros(1, tempo_simulacao);
   historico_p2 = zeros(1, tempo_simulacao);
   historico_p3 = zeros(1, tempo_simulacao);


   CT = 1;

  for segundo = 1:tempo_simulacao %Loop de tempo
    if(mod(segundo, 5) == 0 || segundo == 1) %Checa se é o primeiro tique ou se é multiplo de 5 para gerar pacotes
      Nop1 = [Nop1, ones(1, poissrnd(2))]; %Popula os vetores com quantidades de pacotes conforme distribuições de Poisson com λ = 2 (p1), λ = 1 (p2 e p3)

      Nop2 = [Nop2, ones(1, poissrnd(1))];
      Nop3 = [Nop3, ones(1, poissrnd(1))];
    endif

    if(CT == 4) %Volta CT para 1 quando passa pelo 3
      CT = 1;
    endif

    if(CT == 1) %Checa para cada valor possível de CT para tratar o nó correspondente
      if ~isempty(Nop1) %Checa se a fila está vazia
        Nop1(1) = []; %Se não estiver vazia, trata o primeiro pacotes
      endif
    elseif(CT == 2)
      if ~isempty(Nop2)
        Nop2(1) = [];
      endif
    elseif(CT == 3)
      if ~isempty(Nop3)
        Nop3(1) = [];
      endif
    endif

    CT = CT + 1; %Incrementa o CT para percorrer os nós


     historico_p1(segundo) = length(Nop1); %Guarda o histórico correspondente a cada vetor (tamanho da fila)
     historico_p2(segundo) = length(Nop2);
     historico_p3(segundo) = length(Nop3);

  endfor

  medias_p1_iteracao = [medias_p1_iteracao, mean(historico_p1)]; %Guarda a média da iteração atual no vetor de médias
  valores_maximos_p1_iteracao = [valores_maximos_p1_iteracao, max(historico_p1)]; %Guarda o valor máximo encontrado na iteração atual no vetor de valores máximos

  medias_p2_iteracao = [medias_p2_iteracao, mean(historico_p2)];
  valores_maximos_p2_iteracao = [valores_maximos_p2_iteracao, max(historico_p2)];

  medias_p3_iteracao = [medias_p3_iteracao, mean(historico_p3)];
  valores_maximos_p3_iteracao = [valores_maximos_p3_iteracao, max(historico_p3)];

endfor

tempo_execucao = toc; %Termina a contagem de tempo e guarda o tempo passado na variável

printf("Relatório com a média das médias dos 30 experimentos:\n\n"); %Gerar relatório


printf("Fila 1: Média: %.2f, Desvio padrão: %.2f, Cv = %.2f %%, Máximo absoluto = %d\n\n", mean(medias_p1_iteracao), std(medias_p1_iteracao), std(medias_p1_iteracao)/mean(medias_p1_iteracao)*100, max(valores_maximos_p1_iteracao));


printf("Fila 2: Média: %.2f, Desvio padrão: %.2f, Cv = %.2f %%, Máximo absoluto = %d\n\n", mean(medias_p2_iteracao), std(medias_p2_iteracao), std(medias_p2_iteracao)/mean(medias_p2_iteracao)*100, max(valores_maximos_p2_iteracao));


printf("Fila 3: Média: %.2f, Desvio padrão: %.2f, Cv = %.2f %%, Máximo absoluto = %d\n\n", mean(medias_p3_iteracao), std(medias_p3_iteracao), std(medias_p3_iteracao)/mean(medias_p3_iteracao)*100, max(valores_maximos_p3_iteracao));

printf("Tempo de processamento: %.2f (segundos)\n", tempo_execucao);


