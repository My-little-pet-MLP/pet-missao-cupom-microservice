package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.models.Pet;
import br.unipar.petmissaocupom.services.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
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
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPet);
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
        try {
            List<Pet> pets = petService.listPetByUserId(userId);
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Obtém um pet pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/pet/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable UUID id) {
        try {
            Optional<Pet> pet = petService.getPetById(id);
            return pet.map(ResponseEntity::ok)
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Desativar pet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pet desativado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "404", description = "Pet não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/desativar/{petId}")
    public ResponseEntity<Pet> desativarPet(@PathVariable UUID petId) {
        Pet updatedPet = petService.desativarPet(petId);
        return ResponseEntity.ok(updatedPet);
    }

}
