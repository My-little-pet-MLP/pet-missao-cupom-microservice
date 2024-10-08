package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class CupomService {

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private MissaoService missaoService;

    private List<Cupom> cuponsDisponiveis;

    public Cupom insert(Cupom cupom) {
        return cupomRepository.save(cupom);
    }

    public void armazenarCupons() {
        List<Cupom> cuponsDisponiveis = Arrays.asList(
                new Cupom(UUID.randomUUID(), "DESCONTO10", 10.0, LocalDate.now(), LocalDate.now().plusDays(30), false, null),
                new Cupom(UUID.randomUUID(), "DESCONTO20", 20.0, LocalDate.now(), LocalDate.now().plusDays(30), false, null),
                new Cupom(UUID.randomUUID(), "DESCONTO5", 5.0, LocalDate.now(), LocalDate.now().plusDays(30), false, null)
        );

        cupomRepository.saveAll(cuponsDisponiveis);
    }

    public Cupom gerarCupomSeTodasMissoesCompletas(String userId) {
        if (missaoService.verificarTodasMissoesCompletas(userId)) {

            if (cuponsDisponiveis == null || cuponsDisponiveis.isEmpty()) {
                armazenarCupons();
            }

            Random random = new Random();
            Cupom cupomSorteado = cuponsDisponiveis.get(random.nextInt(cuponsDisponiveis.size()));

            Cupom cupomGerado = new Cupom();
            cupomGerado.setCodigo(cupomSorteado.getCodigo());
            cupomGerado.setPorcentagem(cupomSorteado.getPorcentagem());
            cupomGerado.setDataGerado(LocalDate.now());
            cupomGerado.setDataVencimento(cupomSorteado.getDataVencimento());
            cupomGerado.setUtilizado(false);
            cupomGerado.setUserId(userId);

            return cupomRepository.save(cupomGerado);
        }
        throw new RuntimeException("Missões não completadas.");
    }

    public List<Cupom> armazenarVariosCupons(List<Cupom> cupons) {
        return cupomRepository.saveAll(cupons);
    }

    public List<Cupom> listarTodosCupons() {
        return cupomRepository.findAll();
    }

    public List<Cupom> listarCuponsPorUsuario(String userId) {
        return cupomRepository.findByUserId(userId);
    }

    public void usarCupom(UUID id) {
        Optional<Cupom> cupomOpt = cupomRepository.findById(id);
        if (cupomOpt.isPresent()) {
            Cupom cupom = cupomOpt.get();
            cupom.setUtilizado(true);
            cupomRepository.save(cupom);
        } else {
            throw new RuntimeException("Cupom não encontrado.");
        }
    }

}
