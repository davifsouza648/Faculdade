//Davi Ferreira de Souza e Mário Masao Mukai
//RA: 241024676 e 241022321
//16/04/2025

#include <stack>
#include <string>
#include <iostream>
#include <cctype>

using namespace std;

int prioridade(char c) 
{
    if (c == '^') return 6;
    if (c == '*' || c == '/') return 5;
    if (c == '+' || c == '-') return 4;
    if (c == '>' || c == '<' || c == '=' || c == '#') return 3;
    if (c == '.') return 2;
    if (c == '|') return 1;
    return 0;
}

bool is_operador(char c) 
{
    return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || 
            c == '>' || c == '<' || c == '=' || c == '#' || c == '.' || c == '|');
}

bool is_operando(char c) 
{
    return (isalpha(c) || isdigit(c));
}

bool is_valido(char c) 
{
    return (is_operando(c) || is_operador(c) || c == '(' || c == ')');
}

string infixa_posfixa(string str) 
{
    string output;
    stack<char> operadores;
    bool lexicalerror = false;
    bool syntaxerror = false;


    for (size_t i = 0; i < str.size(); ++i) 
    {
        if (!is_valido(str[i])) 
        {
            lexicalerror = true;
            break;
        }
    }

    if (lexicalerror) 
    {
        return "Lexical Error!";
    }

    for (size_t i = 0; i < str.size() && !syntaxerror; ++i) {
        char c = str[i];

        if (is_operando(c)) 
        {
            output += c;
            if (i + 1 < str.size() && is_operando(str[i + 1])) 
            {
                syntaxerror = true;
            }
        }
        else if (is_operador(c)) {
            if (i == 0 || i == str.size() - 1) 
            {
                syntaxerror = true;
            }
            else if (!is_operando(str[i - 1]) && str[i - 1] != ')') 
            {
                syntaxerror = true;
            }
            else if (!is_operando(str[i + 1]) && str[i + 1] != '(') 
            {
                syntaxerror = true;
            }

            while (!operadores.empty() && operadores.top() != '(' && 
                   prioridade(operadores.top()) >= prioridade(c)) 
            {
                output += operadores.top();
                operadores.pop();
            }
            operadores.push(c);
        }
        else if (c == '(') 
        {
            if (i > 0 && (is_operando(str[i - 1]) || str[i - 1] == ')')) 
            {
                syntaxerror = true;
            }
            operadores.push(c);
        }
        else if (c == ')') 
        {
            if (i > 0 && str[i - 1] == '(') 
            {
                syntaxerror = true;
            }
            bool matching_parenthesis = false;
            while (!operadores.empty() && operadores.top() != '(') 
            {
                output += operadores.top();
                operadores.pop();
            }
            if (operadores.empty()) 
            {
                syntaxerror = true;  
            } 
            else 
            {
                operadores.pop();  
            }
            if (i + 1 < str.size() && is_operando(str[i + 1])) {
                syntaxerror = true;
            }
        }
    }

    while (!operadores.empty()) 
    {
        if (operadores.top() == '(') 
        {
            syntaxerror = true;
        }
        output += operadores.top();
        operadores.pop();
    }

    if (syntaxerror) 
    {
        return "Syntax Error!";
    }

    return output;
}

int main() 
{
    string expressao;
    while (getline(cin, expressao)) 
    {
        if (expressao.empty()) continue;
        cout << infixa_posfixa(expressao) << endl;
    }
    return 0;
}
