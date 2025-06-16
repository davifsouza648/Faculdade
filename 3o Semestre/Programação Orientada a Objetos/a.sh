#!/bin/bash

mkdir -p pdfs
saida="pdfs/todos_os_codigos_java.pdf"

# Pasta a ignorar (relativa à pasta onde você roda o script)
IGNORAR="./3a_Lista de Exercícios_SistemaDelivery_Davi Ferreira de Souza"

find . \
  -path "$IGNORAR" -prune -o \
  -type f -name "*.java" ! -name "SistemaDelivery.java" -print | sort | while IFS= read -r arquivo; do
    echo "// ===== Arquivo: $arquivo ====="
    cat "$arquivo"
    echo -e "\n\n"
done | enscript -Ejava -B -q -p - | ps2pdf - "$saida"

echo "✅ PDF gerado com sucesso em: $saida"
