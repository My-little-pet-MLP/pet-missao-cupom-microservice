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

    // Método que verifica as missões e atribui o userId ao cupom já existente
    public Cupom gerarCupomParaUsuarioSeMissoesConcluidas(String userId) {
        // Verifica se todas as missões estão concluídas para o userId
        boolean missoesConcluidas = missaoService.todasMissoesConcluidas(userId);

        if (missoesConcluidas) {
            // Busca um cupom disponível (que ainda não tem um userId)
            Cupom cupom = cupomRepository.findFirstByUserIdIsNull();

            if (cupom != null) {
                // Atribui o userId ao cupom e salva
                cupom.setUserId(userId);
                cupom.setDataVencimento(new Date(System.currentTimeMillis() + 30L * 24 * 60 * 60 * 1000)); // 30 dias de validade
                return cupomRepository.save(cupom);
            }
        }
        return null;  // Retorna null se missões não concluídas ou cupom não encontrado
    }


//    public Cupom inserirUserIdNoCupom(UUID cupomId, String userId) {
//        List<Missao> missoesDoUsuario = missaoRepository.findByUserId(userId);
//
//        if (missoesDoUsuario.size() != 5) {
//            throw new IllegalStateException("O usuário precisa ter exatamente 5 missões.");
//        }
//
//        boolean todasConcluidas = missoesDoUsuario.stream().allMatch(Missao::isConcluido);
//
//        if (!todasConcluidas) {
//            throw new IllegalStateException("O usuário ainda não concluiu todas as missões.");
//        }
//
//        Optional<Cupom> optionalCupom = cupomRepository.findById(cupomId);
//
//        if (optionalCupom.isPresent()) {
//            Cupom cupom = optionalCupom.get();
//
//            Date dataGerada = new Date();
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(dataGerada);
//            cal.add(Calendar.DAY_OF_MONTH, 30);
//            Date dataVencimento = cal.getTime();
//
//            cupom.setDataGerado(dataGerada);
//            cupom.setUserId(userId);
//            cupom.setDataVencimento(dataVencimento);
//
//            return cupomRepository.save(cupom);
//        }
//        throw new EntityNotFoundException("Cupom não encontrado");
//    }
//
//    public List<Cupom> listarTodosCupons() {
//        return cupomRepository.findAll();
//    }
//
//    public List<Cupom> listarCuponsPorUsuario(String userId) {
//        return cupomRepository.findByUserId(userId);
//    }
//
//    public Cupom desativarCupom(UUID id) {
//        Cupom cupom = cupomRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado com o id: " + id));
//
//        if (!cupom.isUtilizado()) {
//            throw new IllegalStateException("O cupom já está desativado.");
//        }
//
//        cupom.setUtilizado(false);
//        return cupomRepository.save(cupom);
//    }
//
//    public Cupom findById(UUID id) {
//        return cupomRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado com o id: " + id));
//    }
//
//    public Cupom findCupomByUserId(UUID cupomId, String userId) {
//        Cupom cupom = cupomRepository.findByIdAndUserId(cupomId, userId)
//                .orElseThrow(() -> new EntityNotFoundException("Cupom não encontrado para o usuário com id: " + userId));
//
//        return cupom;
//    }

}
