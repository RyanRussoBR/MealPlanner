# Verificação das telas

Revisão estática do código em 10/11/2026. A execução no dispositivo e a compilação ainda precisam ser confirmadas.

| Parte | Resultado da revisão |
|---|---|
| 1. Planejador | Observa as refeições do Room, ordena por dia e tipo de refeição, agrupa por dia e abre os detalhes ao tocar. Permite remover um agendamento. |
| 2. Receitas | Observa as receitas cadastradas, abre os detalhes e oferece o botão Nova receita. |
| 3. Cadastro/edição | Usa o mesmo formulário para criar e editar, carrega a receita pelo ID, valida os campos e salva pelo repositório. |
| 4. Detalhes | Exibe ingredientes e preparo, permite editar, confirma a exclusão, agenda por dia/tipo e envia os ingredientes para o mesmo repositório usado pela lista de compras. |
| 5. Compras | Interface completada com exclusão individual, limpeza dos comprados, contador, texto riscado e estado vazio. Mantidos adicionar e marcar/desmarcar, usando o ViewModel existente. |

## Ressalvas das partes 1 a 4

- Em `RecipeDetailScreen`, o estado nulo representa tanto carregamento quanto receita inexistente. Um ID inexistente mantém o indicador de carregamento indefinidamente.
- Os avisos de sucesso ao agendar e enviar ingredientes são exibidos sem aguardar a conclusão da gravação. Os ViewModels não apresentam erros de persistência; no formulário, uma falha ao salvar também deixa `isSaving` ativo.
- Esses pontos não foram alterados nesta implementação da tela 5. A presença dos fluxos no código não substitui o teste em dispositivo.

## Validação realizada

- Revisados navegação, ViewModels, repositórios, entidades e DAOs das cinco telas.
- Confirmado no código que a limpeza usa `DELETE FROM shopping_items WHERE checked = 1`, preservando itens pendentes; os ingredientes enviados pelas receitas entram na mesma tabela.
- `git diff --check`: sem erros.
- Tentativa de `gradlew.bat :app:assembleDebug :app:lintDebug --no-daemon`: interrompida no download da distribuição Gradle 9.5.0 com `java.net.SocketException: Permission denied: getsockopt`. Compilação e lint não chegaram a executar.

## Roteiro de teste no Android Studio

1. Criar uma receita com dois ingredientes, conferir a lista e abrir os detalhes.
2. Editar a receita e verificar as alterações nos detalhes e na lista.
3. Agendar em dias diferentes e verificar a ordem dos dias no planejador.
4. Enviar ingredientes para compras e conferir os dois itens na aba Compras.
5. Adicionar um item manualmente pelo botão e outro pela ação do teclado; conferir que espaços em branco não habilitam Adicionar.
6. Marcar e desmarcar um item; conferir contador e texto riscado.
7. Excluir um item individualmente, incluindo um nome repetido; conferir que apenas o item escolhido foi excluído.
8. Deixar itens comprados e pendentes, tocar em Limpar comprados e conferir que os pendentes permanecem. Sem comprados, o botão deve ficar desabilitado.
9. Fechar e reabrir o aplicativo para conferir persistência. Girar o dispositivo com texto no campo Novo item para conferir a preservação do rascunho.
10. Excluir todos os itens para conferir o estado vazio. Excluir uma receita agendada e conferir a remoção dos respectivos agendamentos.
