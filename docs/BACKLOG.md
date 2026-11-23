# Backlog do BiblioTech

| ID | História | Prioridade | Pontos | Sprint |
|---|---|---|---|---|
| US01 | Cadastrar livro | Alta | 3 | 1 |
| US02 | Cadastrar leitor | Alta | 2 | 1 |
| US03 | Pesquisar livro | Alta | 2 | 1 |
| US04 | Registrar empréstimo | Alta | 5 | 1 |
| US05 | Registrar devolução | Alta | 3 | 1 |
| US06 | Calcular multa por atraso | Média | 2 | 1 |
| US07 | Consultar meus empréstimos | Média | 2 | 1 |
| US08 | Relatório de atrasos | Média | 3 | 1 |
| US09 | Login de bibliotecários | Baixa | 5 | 2 |

## US01 - Cadastrar livro

**Como** bibliotecário, **quero** cadastrar livros com ISBN, título, autor e quantidade de exemplares, **para** manter o acervo atualizado.

**Critérios de aceitação**

- [ ] ISBN, título e autor são obrigatórios.
- [ ] A quantidade deve ser de pelo menos 1 exemplar.
- [ ] Não é possível cadastrar dois livros com o mesmo ISBN.
- [ ] Após salvar, o livro aparece na pesquisa com todos os exemplares disponíveis.

## US02 - Cadastrar leitor

**Como** bibliotecário, **quero** cadastrar leitores com CPF, nome e e-mail, **para** controlar quem pode pegar livros emprestados.

**Critérios de aceitação**

- [ ] O CPF deve ter 11 dígitos (aceita com ou sem pontuação).
- [ ] Não é possível cadastrar dois leitores com o mesmo CPF.
- [ ] O e-mail precisa ter formato válido (nome@dominio.com).
- [ ] O leitor começa sem empréstimos ativos.

## US03 - Pesquisar livro

**Como** leitor, **quero** pesquisar livros pelo título ou pelo autor, **para** saber se o livro que eu quero está disponível.

**Critérios de aceitação**

- [ ] A busca não diferencia maiúsculas de minúsculas.
- [ ] O resultado mostra quantos exemplares estão disponíveis.
- [ ] Quando nada é encontrado, aparece "Nenhum livro encontrado".

## US04 - Registrar empréstimo

**Como** bibliotecário, **quero** registrar o empréstimo de um livro para um leitor, **para** controlar a saída dos exemplares.

**Critérios de aceitação**

- [ ] Só é possível emprestar se houver exemplar disponível.
- [ ] O prazo de devolução é de 7 dias a partir da data do empréstimo.
- [ ] Um leitor pode ter no máximo 3 empréstimos ativos.
- [ ] Leitor com empréstimo em atraso não pode pegar novo livro.
- [ ] Ao emprestar, a quantidade disponível do livro diminui em 1.

## US05 - Registrar devolução

**Como** bibliotecário, **quero** registrar a devolução de um empréstimo, **para** liberar o exemplar para outros leitores.

**Critérios de aceitação**

- [ ] A quantidade disponível do livro aumenta em 1.
- [ ] O empréstimo deixa de aparecer como ativo.
- [ ] Não é possível devolver o mesmo empréstimo duas vezes.

## US06 - Calcular multa por atraso

**Como** leitor, **quero** saber o valor da multa quando devolvo um livro atrasado, **para** entender quanto devo pagar.

**Critérios de aceitação**

- [ ] A multa é de R$ 1,00 por dia de atraso.
- [ ] Devolução até a data prevista não gera multa.
- [ ] O valor é exibido no momento da devolução.

## US07 - Consultar meus empréstimos

**Como** leitor, **quero** ver meus empréstimos ativos e as datas de devolução, **para** não perder o prazo.

**Critérios de aceitação**

- [ ] Mostra apenas os empréstimos ainda não devolvidos.
- [ ] Cada linha mostra o título e a data prevista de devolução.
- [ ] Sem empréstimos, aparece "Nenhum empréstimo ativo".

## US08 - Relatório de atrasos

**Como** bibliotecário, **quero** gerar um relatório dos empréstimos em atraso, **para** cobrar os leitores e recuperar os livros.

**Critérios de aceitação**

- [ ] Lista apenas empréstimos ativos com prazo vencido na data informada.
- [ ] Mostra leitor, livro, data de vencimento e multa acumulada até a data.
- [ ] Sem atrasos, aparece "Nenhum empréstimo em atraso".

## US09 - Login de bibliotecários

**Como** administrador, **quero** cadastrar bibliotecários com usuário e senha, **para** que só funcionários autorizados registrem empréstimos.

**Critérios de aceitação**

- [ ] O usuário deve ser único.
- [ ] A senha não pode ser guardada em texto puro (usar hash).
- [ ] Usuário ou senha incorretos exibem mensagem de erro sem dizer qual dos dois errou.
- [ ] Somente usuários logados acessam o painel.

## Regras de negócio

- **RN01**: Cada leitor pode ter no máximo 3 empréstimos ativos.
- **RN02**: O prazo de devolução é de 7 dias corridos.
- **RN03**: A multa por atraso é de R$ 1,00 por dia.
- **RN04**: Leitor com empréstimo em atraso não pode pegar novos livros.
- **RN05**: O ISBN do livro é único no acervo.
- **RN06**: O CPF do leitor é único no cadastro.
