package io.github.lluaslua.authapi.controller;


import io.github.lluaslua.authapi.model.User;
import io.github.lluaslua.authapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//sinalizar que é um controller REST
@RestController

// Determinando que será o padrão para as requisições daqui.
@RequestMapping("/user")

//para injeção de dependencias. Não precisamos de construtor se tiver isso.
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //ao injetar, é aqui que começamos com os 4 verbos.

    //gravar dados
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<User> getUserByEmail(@RequestParam String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserByEmail(@RequestParam String email) {
        userService.deleteUserByEmail(email);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUserById(@RequestParam Long id,
                                               @RequestBody User user){
        userService.updateUserById(id, user);
        return ResponseEntity.ok().build();

    }

}
