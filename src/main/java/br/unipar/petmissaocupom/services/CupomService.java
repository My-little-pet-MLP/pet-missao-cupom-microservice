package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.repositories.CupomRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CupomService {

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private MissaoService missaoService;

    public Cupom createCupom(Cupom cupom) {
        cupom.setUtilizado(false);

        if (cupom.getCodigo() == null || cupom.getCodigo().isEmpty()) {
            throw new ConstraintViolationException("codigo não pode ser nulo ou vazio", null);
        }
        if (cupom.getPorcentagem() <= 0) {
            throw new IllegalArgumentException("O valor da porcentagem não pode ser negativo.");
        }

        return cupomRepository.save(cupom);
    }

    public List<Cupom> listarCuponsPorUsuario(String userId) {
        return cupomRepository.findByUserId(userId);
    }

    public Cupom findCupomByIdAndUserId(UUID cupomId, String userId) {
        return cupomRepository.findByIdAndUserId(cupomId, userId).orElse(null);
    }

    public Cupom desativarCupom(UUID cupomId) {
        Cupom cupom = cupomRepository.findById(cupomId).orElse(null);
        if (cupom != null && !cupom.isUtilizado()) {
            cupom.setUtilizado(true);
            return cupomRepository.save(cupom);
        }
        return null;
    }

    public Cupom buscarCupomPorId(UUID id) {
        return cupomRepository.findById(id).orElse(null);
    }

    public Cupom atualizarCupom(UUID cupomId, String userId) {
        Cupom cupom = buscarCupomPorId(cupomId);
        if (cupom != null) {
            cupom.setUserId(userId);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date()); // Data atual
            calendar.add(Calendar.DAY_OF_MONTH, 30);
            cupom.setDataVencimento(calendar.getTime());

            return cupomRepository.save(cupom);
        }
        return null;
    }

}
