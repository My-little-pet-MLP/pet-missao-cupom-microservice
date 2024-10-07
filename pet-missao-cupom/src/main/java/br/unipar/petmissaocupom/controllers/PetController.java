package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Pet;
import br.unipar.petmissaocupom.services.PetService;
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

    @PostMapping("/user/{userId}")
    public ResponseEntity<Pet> insert(@PathVariable String userId, @RequestBody Pet pet) {
        try {
            pet.setUserId(userId);
            Pet savedPet = petService.insert(pet);
            return new ResponseEntity<>(savedPet, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Pet>> listByUserId(@PathVariable String userId) {
        try {
            List<Pet> pets = petService.listByUserId(userId);
            return new ResponseEntity<>(pets, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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

}
