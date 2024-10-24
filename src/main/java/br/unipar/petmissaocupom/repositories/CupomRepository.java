package br.unipar.petmissaocupom.repositories;

import br.unipar.petmissaocupom.models.Cupom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CupomRepository extends JpaRepository<Cupom, UUID> {

    List<Cupom> findByUserId(String userId);
    Optional<Cupom> findByIdAndUserId(UUID cupomId, String userId);
    Cupom findFirstByUserIdIsNull();

}
