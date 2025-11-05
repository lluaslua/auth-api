package io.github.lluaslua.authapi.repository;

import io.github.lluaslua.authapi.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //Optional evita o null pointer exception. O que nos obriga a criar uma exceção ou alternativa caso o email não exista.
    //findBy é regra, Email PRECISA corresponder ao nome do campo, porém em camelCase.
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    @Transactional
    void deleteByEmail(String email);
}
