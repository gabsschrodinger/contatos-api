## Manual de instalação

1. Clone o repositório: insira o seguinte comando no terminal GIT
    ```sh
    git clone https://github.com/gabsschrodinger/contatos-api.git
    ```

2. Inicalize o docker

3. Abre um terminal na pasta clonada e insira o seguinte comando:
    ```sh
    docker-compose up -d --build
    ```

4. Liste os containers criados e inicalizados no docker com o seguinte comando:
    ```sh
    docker ps
    ```

5. Verifique a funcionalidade da API:
    ```sh
    docker logs (insira aqui o ID correspondente ao container da API contatos)
    ```



## Utilizando a API

1. Cadastro de contatos: o cadastro é feito no endpoint http://localhost:9000/contatos/cadastrar com o método POST. O método passa por uma validação de e-mail, a fim de verificar se a string está no formato correto (string@string.string), e por uma validação de telefones, a fim de verificar se há, no mínimo, 1 número de telefone. Para o cadastro deve ser inserido um JSON com o modelo abaixo
    ```json
    {
        "primeiroNome": "string",
        "ultimoNome": "string",
        "email": "string",
        "telefones": [
            "string"
        ]
    }
    ```

2. Edição de contatos: a edição é feita no endpoint http://localhost:9000/contatos/editar com o método PUT. O método passa pelas mesmas validações de e-mail e telefones que o cadastro, e deve ser inserido um objeto json no formato abaixo
    ```json
    {
        "id": integer,
        "primeiroNome": "string",
        "ultimoNome": "string",
        "email": "string",
        "telefones": [
            "string"
        ]
    }
    ```

3. Deletar contatos: para deletar algum contato, deve ser feita uma requisição no endpoint http://localhost:9000/contatos/deletar/{id do contato a ser excluído} com o método DELETE

4. Ver todos os contatos: deve ser feita uma requisição no endpoint http://localhost:9000/contatos com o método GET

5. Ver contato específico por id: deve ser feita uma requisição no endpoint http://localhost:9000/contatos/id/{id do contato a ser visualizado} com o método GET

6. Ver contatos filtrados por nome, sobrenome e e-mail: deve ser feita uma requisição no endpoint http://localhost:9000/contatos/filtrar com o método GET e com os filtros desejados. Por exemplo, para filtrar os contatos com nome Gabriel, pode ser feita uma requisição de GET no endpoint http://localhost:9000/contatos/filtrar?nome=gabriel. Para buscar um contato de nome Gabriel e e-mail de gabriel@email.com, pode ser feita uma requisição GET no endpoint http://localhost:9000/contatos/filtrar?nome=gabriel&email=gabriel@email.com. De forma geral, após o endpoint http://localhost:9000/contatos/filtrar deve ser colocado um ponto de interrogação ("?") e os filtros desejados, separados por & em caso de múltiplos filtros simultâneos.