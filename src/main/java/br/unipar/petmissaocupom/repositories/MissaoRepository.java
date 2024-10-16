package br.unipar.petmissaocupom.repositories;

import br.unipar.petmissaocupom.models.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, UUID> {

    List<Missao> findByUserIdAndDataGerada(String userId, Date dataGerada);
    List<Missao> findByUserId(String userId);
    List<Missao> findTop5ByUserIdOrderByDataGeradaDesc(String userId);
}
