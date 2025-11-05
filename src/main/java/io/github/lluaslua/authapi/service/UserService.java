package io.github.lluaslua.authapi.service;

import io.github.lluaslua.authapi.model.User;
import io.github.lluaslua.authapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //Atributo
    private final UserRepository repository;

    //Contrutor(já que não estamos usando o @AutoWired do lombock
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    //Método para salvar usuário no  e recebe como parâmetro um user para salvà-lo no banco
    public void saveUser(User user){
        repository.saveAndFlush(user); //save and flush salva e fecha a conexão com o banco
    }


    //Buscando por email, e não por ID(mas poderia sim). Pois, o email é unico também nesse
    //A partir daqui, precisamos ir no UserRepository CRIAR o findByEmail
    public User getUserByEmail(String email){

        //Como foi um option lá no Repository, voc~e precisa colocar algum aviso.
        //orElseThrow() sozinho não faz nada e só estoura a exceção
        //orElse() Você pode colocar um new User() e ele retornará um usuário vazio.
        //orElseThrow( () -> new RunTimeException("Valor personalizado")) para colocar algo personalizado.
        return repository.findByEmail(email).orElseThrow(
                () -> new RuntimeException("Email not found")
        );
    }

    public void deleteUserByEmail(String email){
        repository.deleteByEmail(email);
    }

    // -------------------------- Explicação do update abaixo --------------------------

    //Qual a situação abaixo?
    //OBS: Estude o builder para entender melhor
    //OBS2: Estude mais também sobre isso aqui abaixo para entender melhor.
    //Agora voltando: Existem outros métodos para chegar a este mesmo resultado. Porém foi feito este.
    //Nesse caso, queremos atualizar um usuário(adicionando um novo com as informações a serem mudadas, sendo as que não serão, espaços nulos)
    //e vamos utilizar o ID para identificá-lo(o pk no db)

    //Para não acontecer de atualizar TUDO e deixar vazio, etc. O que foi feito?

    //1 - criamos um usuário2 que é o MESMO que é desejável encontrar(ou seja, quero atualizar o usuário ID 400,
    //  então copiamos as informações do usuário salvas no db dentro do userEntity(o usuário2)
    //2 - atualizamos as informações que queremos atualizar (os que não estão nulos dentro do User user do parâmetro).
    //  o que primeiro vai pro email. Caso o parâmetro user tenha o atributo email vazio(ou seja, não seja requisitado a alteração dele),
    //  o código irá preencher com o que já tem presente no db. A partir daí se repete para os outros, menos para o ID, já que é a PY.

    public void updateUserById(Long id, User user){
        User userEntity = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        User updatedUser = User.builder()
                .email(user.getEmail()!= null ? user.getEmail() : userEntity.getEmail())
                .name(user.getName()!= null ? user.getName() : userEntity.getName())
                .password(user.getPassword()!= null ? user.getPassword() : userEntity.getPassword())
                .id(userEntity.getId())
                .build();
        repository.saveAndFlush(updatedUser); //salvando no repositório e fechando a  conexão
    }
}
