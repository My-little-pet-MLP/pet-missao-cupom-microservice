package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.repositories.CupomRepository;
import br.unipar.petmissaocupom.repositories.MissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CupomService {

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private MissaoRepository missaoRepository;

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

    public Cupom inserirUserIdNoCupom(UUID cupomId, String userId) {
        List<Missao> missoesDoUsuario = missaoRepository.findByUserId(userId);

        if (missoesDoUsuario.size() != 5) {
            throw new IllegalStateException("O usuário precisa ter exatamente 5 missões.");
        }

        boolean todasConcluidas = missoesDoUsuario.stream().allMatch(Missao::isConcluido);

        if (!todasConcluidas) {
            throw new IllegalStateException("O usuário ainda não concluiu todas as missões.");
        }

        Optional<Cupom> optionalCupom = cupomRepository.findById(cupomId);

        if (optionalCupom.isPresent()) {
            Cupom cupom = optionalCupom.get();

            Date dataGerada = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dataGerada);
            cal.add(Calendar.DAY_OF_MONTH, 30);
            Date dataVencimento = cal.getTime();

            cupom.setDataGerado(dataGerada);
            cupom.setUserId(userId);
            cupom.setDataVencimento(dataVencimento);

            return cupomRepository.save(cupom);
        }
        throw new EntityNotFoundException("Cupom não encontrado");
    }

    public List<Cupom> listarTodosCupons() {
        return cupomRepository.findAll();
    }

    public List<Cupom> listarCuponsPorUsuario(String userId) {
        return cupomRepository.findByUserId(userId);
    }

    public Cupom desativarCupom(UUID id) {
        Cupom cupom = cupomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado com o id: " + id));

        if (!cupom.isUtilizado()) {
            throw new IllegalStateException("O cupom já está desativado.");
        }

        // Verifica se o cupom já está vencido
//        Date hoje = new Date();
//        if (hoje.after(cupom.getDataVencimento())) {
//            throw new IllegalStateException("O cupom já venceu e não pode ser usado.");
//        }

        cupom.setUtilizado(false);
        return cupomRepository.save(cupom);
    }

    public Cupom findById(UUID id) {
        return cupomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado com o id: " + id));
    }

}
