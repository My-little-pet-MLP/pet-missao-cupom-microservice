package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Pet;
import br.unipar.petmissaocupom.services.PetService;
import br.unipar.petmissaocupom.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @Operation(summary = "Insere um novo pet para o usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pet criado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<Pet> insert(@PathVariable String userId, @RequestBody Pet pet) {
        // Valida o userId com a API de usuários
        if (!userService.isUserValid(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(null); // Pode também retornar uma mensagem explicando o erro
        }

        pet.setUserId(userId); // Certifique-se de que o userId no pet seja o correto
        Pet savedPet = petService.insert(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
    }


    @Operation(summary = "Lista todos os pets de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pets retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado com o ID do usuário especificado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> listByUserId(@PathVariable String userId) {
        try {
            List<Pet> pets = petService.listByUserId(userId);
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtém um pet pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/pet/{id}")
    public ResponseEntity<Pet> getById(@PathVariable UUID id) {
        try {
            Optional<Pet> pet = petService.getById(id);
            return pet.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Operation(summary = "Desativar pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet desativado"),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public void desativarPet(@PathVariable UUID id) {
        petService.desativarPet(id);
    }

}
