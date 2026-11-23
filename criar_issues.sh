#!/usr/bin/env bash
# Cria as labels e as 9 Issues do backlog no repositório atual.
# Pré-requisito: GitHub CLI instalado e logado (gh auth login). Rode dentro da pasta do projeto.
set -e
gh label create "user story" --color BFD4F2 --description "História de usuário" --force
gh label create "prioridade: alta" --color B60205 --force
gh label create "prioridade: média" --color FBCA04 --force
gh label create "prioridade: baixa" --color 0E8A16 --force
gh label create "sprint 1" --color C5DEF5 --force
gh label create "sprint 2" --color C5DEF5 --force

gh issue create --title 'US01 - Cadastrar livro' --label 'user story' --label 'prioridade: alta' --label 'sprint 1' --body '**Como** bibliotecário, **quero** cadastrar livros com ISBN, título, autor e quantidade de exemplares, **para** manter o acervo atualizado.

### Critérios de aceitação
- [ ] ISBN, título e autor são obrigatórios.
- [ ] A quantidade deve ser de pelo menos 1 exemplar.
- [ ] Não é possível cadastrar dois livros com o mesmo ISBN.
- [ ] Após salvar, o livro aparece na pesquisa com todos os exemplares disponíveis.

**Estimativa:** 3 pontos'
gh issue create --title 'US02 - Cadastrar leitor' --label 'user story' --label 'prioridade: alta' --label 'sprint 1' --body '**Como** bibliotecário, **quero** cadastrar leitores com CPF, nome e e-mail, **para** controlar quem pode pegar livros emprestados.

### Critérios de aceitação
- [ ] O CPF deve ter 11 dígitos (aceita com ou sem pontuação).
- [ ] Não é possível cadastrar dois leitores com o mesmo CPF.
- [ ] O e-mail precisa ter formato válido (nome@dominio.com).
- [ ] O leitor começa sem empréstimos ativos.

**Estimativa:** 2 pontos'
gh issue create --title 'US03 - Pesquisar livro' --label 'user story' --label 'prioridade: alta' --label 'sprint 1' --body '**Como** leitor, **quero** pesquisar livros pelo título ou pelo autor, **para** saber se o livro que eu quero está disponível.

### Critérios de aceitação
- [ ] A busca não diferencia maiúsculas de minúsculas.
- [ ] O resultado mostra quantos exemplares estão disponíveis.
- [ ] Quando nada é encontrado, aparece "Nenhum livro encontrado".

**Estimativa:** 2 pontos'
gh issue create --title 'US04 - Registrar empréstimo' --label 'user story' --label 'prioridade: alta' --label 'sprint 1' --body '**Como** bibliotecário, **quero** registrar o empréstimo de um livro para um leitor, **para** controlar a saída dos exemplares.

### Critérios de aceitação
- [ ] Só é possível emprestar se houver exemplar disponível.
- [ ] O prazo de devolução é de 7 dias a partir da data do empréstimo.
- [ ] Um leitor pode ter no máximo 3 empréstimos ativos.
- [ ] Leitor com empréstimo em atraso não pode pegar novo livro.
- [ ] Ao emprestar, a quantidade disponível do livro diminui em 1.

**Estimativa:** 5 pontos'
gh issue create --title 'US05 - Registrar devolução' --label 'user story' --label 'prioridade: alta' --label 'sprint 1' --body '**Como** bibliotecário, **quero** registrar a devolução de um empréstimo, **para** liberar o exemplar para outros leitores.

### Critérios de aceitação
- [ ] A quantidade disponível do livro aumenta em 1.
- [ ] O empréstimo deixa de aparecer como ativo.
- [ ] Não é possível devolver o mesmo empréstimo duas vezes.

**Estimativa:** 3 pontos'
gh issue create --title 'US06 - Calcular multa por atraso' --label 'user story' --label 'prioridade: média' --label 'sprint 1' --body '**Como** leitor, **quero** saber o valor da multa quando devolvo um livro atrasado, **para** entender quanto devo pagar.

### Critérios de aceitação
- [ ] A multa é de R$ 1,00 por dia de atraso.
- [ ] Devolução até a data prevista não gera multa.
- [ ] O valor é exibido no momento da devolução.

**Estimativa:** 2 pontos'
gh issue create --title 'US07 - Consultar meus empréstimos' --label 'user story' --label 'prioridade: média' --label 'sprint 1' --body '**Como** leitor, **quero** ver meus empréstimos ativos e as datas de devolução, **para** não perder o prazo.

### Critérios de aceitação
- [ ] Mostra apenas os empréstimos ainda não devolvidos.
- [ ] Cada linha mostra o título e a data prevista de devolução.
- [ ] Sem empréstimos, aparece "Nenhum empréstimo ativo".

**Estimativa:** 2 pontos'
gh issue create --title 'US08 - Relatório de atrasos' --label 'user story' --label 'prioridade: média' --label 'sprint 1' --body '**Como** bibliotecário, **quero** gerar um relatório dos empréstimos em atraso, **para** cobrar os leitores e recuperar os livros.

### Critérios de aceitação
- [ ] Lista apenas empréstimos ativos com prazo vencido na data informada.
- [ ] Mostra leitor, livro, data de vencimento e multa acumulada até a data.
- [ ] Sem atrasos, aparece "Nenhum empréstimo em atraso".

**Estimativa:** 3 pontos'
gh issue create --title 'US09 - Login de bibliotecários' --label 'user story' --label 'prioridade: baixa' --label 'sprint 2' --body '**Como** administrador, **quero** cadastrar bibliotecários com usuário e senha, **para** que só funcionários autorizados registrem empréstimos.

### Critérios de aceitação
- [ ] O usuário deve ser único.
- [ ] A senha não pode ser guardada em texto puro (usar hash).
- [ ] Usuário ou senha incorretos exibem mensagem de erro sem dizer qual dos dois errou.
- [ ] Somente usuários logados acessam o painel.

**Estimativa:** 5 pontos'
echo "Issues criadas. Agora crie o Project (Board) e adicione as Issues."
