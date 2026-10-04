# Memorial Técnico de Desenvolvimento — CIT-Ticket

## 1. Stack Tecnológica

* **Backend:** Java, Spring Boot, PostgreSQL, JUnit 5, Mockito, Docker.
* **Frontend:** React, TypeScript, Vite, React Router, Axios.

---

## 2. Justificativa Técnica e Arquitetural

A escolha por **Java com Spring Boot** no backend e **TypeScript com React** no frontend foi guiada pela **natureza opinativa** de ambas as tecnologias.

* **O fator "opinativo" e a organização:** Diferente de ferramentas totalmente livres que permitem estruturar o código de qualquer maneira — o que frequentemente gera desorganização e débito técnico em curto prazo —, o ecossistema Spring (com sua inversão de controle e arquitetura em camadas bem delimitada) e o TypeScript forçam o desenvolvedor a seguir um padrão rígido e padronizado. 
* **Impacto na manutenção e legibilidade:** Como as tecnologias impõem essa disciplina estrutural (separação clara entre Controller, Service, DTOs e tipagem estática rigorosa), a base de código se mantém limpa, previsível e altamente legível. Para a avaliação técnica, isso demonstra maturidade arquitetural, reduzindo a margem para desvios de design e facilitando a leitura do fluxo de dados.
* **Consistência de Contratos:** O uso estrito de tipagem no frontend via TypeScript espelha os Enums e DTOs definidos no backend (`TicketCategoryEnum`, `TicketStatusEnum`), eliminando erros de integração e garantindo que o contrato da API REST seja cumprido de ponta a ponta sem surpresas em tempo de execução.

---

## 3. Análise Crítica e Visão de Produção

* **Escopo atual:** O projeto foca cirurgicamente nos fluxos essenciais exigidos (métricas do dashboard, listagem, tratamento de status e regras essenciais de negócio), priorizando a qualidade da entrega principal em detrimento de CRUDs genéricos.
* **Limitações reais:** Em um ambiente corporativo de produção real com alto volume de acessos, a arquitetura monolítica atual precisaria evoluir para incluir paginação otimizada no banco de dados, estratégias de cache com Redis para o dashboard e uma camada de autenticação robusta baseada em tokens (JWT/OAuth2). 
* **Conclusão:** A escolha das ferramentas garantiu que a aplicação fosse entregue com alta coesão estrutural, testabilidade isolada via JUnit/Mockito e containerização pronta via Docker, cumprindo o objetivo de demonstrar engenharia de software limpa e defensiva.
