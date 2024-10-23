package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Pet;
import br.unipar.petmissaocupom.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
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

    @Operation(summary = "Insere um novo pet para o usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet criado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping("/user/{userId}")
    public ResponseEntity<Pet> createPet(@PathVariable String userId, @RequestBody Pet pet) {
        pet.setUserId(userId);
        pet.setAtivo(true);
        Pet savedPet = petService.createPet(pet);
        return new ResponseEntity<>(savedPet, HttpStatus.CREATED);
    }


    @Operation(summary = "Lista todos os pets de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pets listados com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Servidor não encontrou o usuário especificado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content)
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> listPetByUserId(@PathVariable String userId) {
        List<Pet> pets = petService.listPetByUserId(userId);
        return new ResponseEntity<>(pets, HttpStatus.OK);

    }

    @Operation(summary = "Obtém um pet pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/pet/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable UUID id) {
        Optional<Pet> pet = petService.getPetById(id);
        return pet.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Desativar pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet desativado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Pet ou usuário não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "403", description = "O pet não pertence ao usuário",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/desativar/{userId}/{petId}")
    public ResponseEntity<Pet> desativarPet(@PathVariable UUID petId, @PathVariable String userId) {
       Optional<Pet> optionalPet = petService.getPetById(petId);
       if (optionalPet.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       Pet pet = optionalPet.get();
       if (!pet.getUserId().equals(userId)) {
           return new ResponseEntity<>(HttpStatus.FORBIDDEN);
       }

       Pet updatedPet = petService.desativarPet(petId);
        return new ResponseEntity<>(updatedPet, HttpStatus.OK);
    }

}
