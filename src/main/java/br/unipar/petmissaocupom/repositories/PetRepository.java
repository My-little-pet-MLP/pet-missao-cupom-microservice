package br.unipar.petmissaocupom.repositories;

import br.unipar.petmissaocupom.models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {

    List<Pet> findByIsActiveTrue();

}
