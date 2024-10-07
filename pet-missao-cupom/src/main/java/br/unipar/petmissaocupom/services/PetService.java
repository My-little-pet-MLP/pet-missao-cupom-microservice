package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Pet;
import br.unipar.petmissaocupom.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public Pet insert(Pet pet) {
        return petRepository.save(pet);
    }

    public List<Pet> listByUserId(String userId) {
        return petRepository.findByUserId(userId);
    }

    public Optional<Pet> getById(UUID id) {
        return petRepository.findById(id);
    }

}
