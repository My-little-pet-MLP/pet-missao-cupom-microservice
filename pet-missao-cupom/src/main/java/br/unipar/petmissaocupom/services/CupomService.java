package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.repositories.CupomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CupomService {

    @Autowired
    private CupomRepository cupomRepository;

    @Autowired
    private MissaoService missaoService;

    // Lista de cupons disponíveis
    private List<Cupom> cuponsDisponiveis = Arrays.asList(
            new Cupom(UUID.randomUUID(), "DESCONTO10", 10.0, LocalDate.now(), LocalDate.now().plusDays(30), false, null),
            new Cupom(UUID.randomUUID(), "DESCONTO20", 20.0, LocalDate.now(), LocalDate.now().plusDays(30), false, null),
            new Cupom(UUID.randomUUID(), "DESCONTO5", 5.0, LocalDate.now(), LocalDate.now().plusDays(30), false, null)
            // Adicione mais cupons conforme necessário
    );

    public Cupom gerarCupomSeTodasMissoesCompletas(String userId) {
        if (missaoService.verificarTodasMissoesCompletas(userId)) {
            if (cuponsDisponiveis.isEmpty()) {
                throw new RuntimeException("Nenhum cupom disponível.");
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

//    public Cupom gerarCupomSeTodasMissoesCompletas(String userId) {
//        if (missaoService.verificarTodasMissoesCompletas(userId)) {
//            Cupom cupom = new Cupom();
//            cupom.setCodigo("DESCONTO10"); // Gere um código dinamicamente se preferir
//            cupom.setUtilizado(false);
//            cupom.setDataGerado(LocalDate.now());
//            cupom.setUserId(userId);
//            return cupomRepository.save(cupom);
//        }
//
//        throw new RuntimeException("Missões não completadas.");
//    }

    // Armazenar vários cupons
    public List<Cupom> armazenarVariosCupons(List<Cupom> cupons) {
        return cupomRepository.saveAll(cupons);
    }

    // Listar todos os cupons
    public List<Cupom> listarTodosCupons() {
        return cupomRepository.findAll();
    }

}
