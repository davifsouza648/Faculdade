#include <queue>
#include <stack>
#include <sstream>
#include <iostream>

using namespace std;

int prioridade(char c) 
{
    if (c == '^') return 4;
    if (c == '*' || c == '/') return 3;
    if (c == '+' || c == '-') return 2;
    return 0;
}

bool e_operando(const string& str) 
{
    string operadores = "+-*/^()";
    return operadores.find(str) == string::npos;
}

string infixa_pos_fixa(string expressao) 
{
    string retorno = "";
    string operadores_string = "^*/+-";
    stack<string> operadores;
    queue<string> output;

    stringstream ss(expressao);
    string token;
    vector<string> tokens;

    while (ss >> token) 
    {
        tokens.push_back(token);
    }

    for (string str : tokens) 
    {
        if (e_operando(str)) 
        {
            output.push(str);
        } 
        else if (operadores_string.find(str) != string::npos) 
        {
            while (!operadores.empty() &&
                   prioridade(operadores.top()[0]) >= prioridade(str[0]) &&
                   operadores.top() != "(") 
            {
                output.push(operadores.top());
                operadores.pop();
            }
            operadores.push(str);
        } 
        else if (str == "(") 
        {
            operadores.push(str);
        } 
        else if (str == ")") 
        {
            while (!operadores.empty() && operadores.top() != "(") 
            {
                output.push(operadores.top());
                operadores.pop();
            }
            if (!operadores.empty()) operadores.pop();
        }
    }

    while (!operadores.empty()) 
    {
        output.push(operadores.top());
        operadores.pop();
    }

    while (!output.empty()) {
        retorno += output.front() + " ";
        output.pop();
    }

    return retorno;
}

int main() 
{
    string expressao;
    getline(cin, expressao);

    cout << infixa_pos_fixa(expressao) << endl;

    
    return 0;
}
