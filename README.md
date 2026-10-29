# MealPlanner — Aplicativo de Planejamento de Refeições

Projeto acadêmico Android desenvolvido em **Kotlin**, utilizando **Jetpack Compose, Hilt, Room e arquitetura MVVM**.

O objetivo do aplicativo é permitir que usuários cadastrem receitas, organizem refeições durante a semana e gerenciem uma lista de compras.


## Como executar

1. Instale o Android Studio e configure o Android SDK.
2. Clone o repositório do projeto.
3. Abra a pasta do projeto no Android Studio.
4. Aguarde a sincronização do Gradle.
5. Selecione um emulador Android ou dispositivo físico compatível.
6. Execute o módulo `app`.

## Divisão das telas

| Tela | Arquivo | Responsável |
|---|---|---|
| 1. Planejador semanal | `ui/planner/PlannerScreen.kt` | Integrante 1 — Pronta |
| 2. Lista de receitas | `ui/recipes/RecipesScreen.kt` | Integrante 2 |
| 3. Cadastro e edição | `ui/recipes/RecipeFormScreen.kt` | Integrante 3 |
| 4. Detalhes da receita | `ui/recipes/RecipeDetailScreen.kt` | Integrante 4 |
| 5. Lista de compras | `ui/shopping/ShoppingScreen.kt` | Integrante 5 |

## Estrutura do projeto

```text
br.pucpr.mealplanner/
├── data/             # Banco de dados, entidades e repositórios
├── di/               # Injeção de dependências
└── ui/
    ├── common/       # Componentes e constantes compartilhadas
    ├── navigation/   # Navegação entre telas
    ├── planner/      # Planejador semanal
    ├── recipes/      # Receitas, cadastro e detalhes
    ├── shopping/     # Lista de compras
    ├── theme/        # Tema visual
    ├── MainActivity.kt
    └── MealPlannerApp.kt
```

## Regras para evitar conflitos

- Cada integrante deve desenvolver preferencialmente a interface pela qual é responsável.
- Não alterar a assinatura das funções `@Composable` utilizadas pela navegação sem combinar com o grupo.
- Não modificar os arquivos de navegação, banco de dados ou injeção de dependências sem discussão prévia.
- Preservar os ViewModels e suas funcionalidades existentes.
- Utilizar branches individuais para desenvolver novas funcionalidades.
- Testar o aplicativo antes de solicitar a integração das alterações.
- Evitar alterações simultâneas nos mesmos arquivos.


## Funcionalidades do aplicativo

- Cadastro e edição de receitas.
- Visualização e exclusão de receitas.
- Planejamento semanal de refeições.
- Agendamento de receitas por dia e tipo de refeição.
- Gerenciamento de lista de compras.
- Armazenamento local dos dados.
