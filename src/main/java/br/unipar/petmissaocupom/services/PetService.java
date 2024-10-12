package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Pet;
import br.unipar.petmissaocupom.repositories.PetRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet createPet(Pet pet) {
        if (pet.getNome() == null || pet.getNome().isEmpty()) {
            throw new ConstraintViolationException("Nome não pode ser nulo ou vazio", null);
        }
        if (pet.getRaca() == null || pet.getRaca().isEmpty()) {
            throw new ConstraintViolationException("Raça não pode ser nulo ou vazio", null);
        }
        if (pet.getPorte() == null) {
            throw new ConstraintViolationException("Porte não pode ser nulo ou vazio", null);
        }
        if (pet.getIdade() < 0) {
            throw new IllegalArgumentException("A idade não pode ser negativo.");
        }
        if (pet.getDataNascimento() == null) {
            throw new ConstraintViolationException("Data de nascimento não pode ser nulo ou vazio", null);
        }
        if (pet.getUserId() == null || pet.getUserId().isEmpty()) {
            throw new ConstraintViolationException("UserId não pode ser nulo ou vazio", null);
        }
        return petRepository.save(pet);
    }

    public List<Pet> listPetByUserId(String userId) {
        List<Pet> pets = petRepository.findByUserId(userId);
        if(pets.isEmpty()){
            throw new EntityNotFoundException("Nenhum pet ativo encontrado.");
        }
        return pets;
    }

    public Optional<Pet> getPetById(UUID id) {
        return petRepository.findById(id);
    }

    public Pet desativarPet(UUID id) {
        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pet não encontrado com o id: " + id));
        if (!pet.isAtivo()) {
            throw new IllegalStateException("O pet já está desativado.");
        }
        pet.setAtivo(false);
        return petRepository.save(pet);
    }

}
