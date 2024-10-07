package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CupomService {

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private MissaoService missaoService;

    public Cupom gerarCupomSeTodasMissoesCompletas(String userId) {
        if (missaoService.verificarTodasMissoesCompletas(userId)) {
            Cupom cupom = new Cupom();
            cupom.setCodigo("DESCONTO10"); // Gere um código dinamicamente se preferir
            cupom.setUtilizado(false);
            cupom.setDataGerado(LocalDate.now());
            cupom.setUserId(userId);
            return cupomRepository.save(cupom);
        }

        throw new RuntimeException("Missões não completadas.");
    }

}
