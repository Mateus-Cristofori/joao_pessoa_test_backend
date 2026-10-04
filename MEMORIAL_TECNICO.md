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

## 3. Análise Crítica

### Limitações da Solução Implementada
* O escopo atual focou nos fluxos essenciais (dashboard, listagem, regras de negócio centrais e tratamento de status), deixando funcionalidades corporativas avançadas para fases futuras.
* Ausência de mecanismos nativos de auditoria detalhada de alterações e de proteção avançada contra ataques de força bruta no endpoint de autenticação.

### Melhorias Futuras e Requisitos a Aperfeiçoar
* **Gestão de Perfil:** Criação de uma tela dedicada para gerenciamento de perfil, permitindo que o usuário atualize suas informações cadastrais, e-mail e senha com validações seguras.
* **Central de Ajuda e Suporte:** Implementação de uma tela de suporte para que os usuários possam abrir chamados de dúvidas, reportar falhas na aplicação e interagir com a equipe de atendimento.

### Decisões Diferentes em um Ambiente Corporativo de Produção
* **Controle de Acessos:** Evolução do backend para suportar papéis (`roles`) por usuário, restrição estrita para que apenas perfis com função de administrador possam cadastrar novos funcionários, e criação de uma tabela/página dedicada para o gerenciamento dinâmico (criação e exclusão) de categorias de solicitações.
* **Auditoria e Histórico:** Desenvolvimento de uma tela de histórico restrita a administradores para rastreabilidade completa das alterações de status de todos os tickets (registrando quem alterou, o timestamp da modificação e os estados de origem e destino).
* **Segurança de Autenticação:** Aplicação de mecanismo de *rate limit* no endpoint de login para bloqueio temporário após três tentativas consecutivas inválidas (seja por e-mail ou senha incorretos).
* **Métricas e Relatórios Analíticos:** Implementação de relatórios gerenciais da plataforma calculando o tempo médio de resolução e transição de status dos chamados (tempo médio até entrar em atendimento e até a conclusão).
* **Infraestrutura e Observabilidade:** Adição de camadas de cache (Redis) e ferramentas de monitoramento de performance (APM/Prometheus).
