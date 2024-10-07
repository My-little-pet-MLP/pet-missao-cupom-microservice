package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.repositories.MissaoRepository;
import br.unipar.petmissaocupom.services.CupomService;
import br.unipar.petmissaocupom.services.MissaoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService missaoService;

    @Autowired
    private CupomService cupomService;

    @GetMapping("/diarias/{userId}")
    public List<Missao> getMissoesDiarias(@PathVariable String userId) {
        return missaoService.listarMissoesDoDia(userId);
    }

    @PostMapping("/gerar/{userId}")
    public List<Missao> gerarMissoesDiarias(@PathVariable String userId) {
        return missaoService.gerarMissoesDiariasParaUsuario(userId);
    }

    @PostMapping("/completar/{userId}")
    public Cupom completarMissoesDiarias(@PathVariable String userId) {
        return cupomService.gerarCupomSeTodasMissoesCompletas(userId);
    }

    @PostMapping("/armazenar")
    public void armazenarTodasMissoes() {
        missaoService.armazenarTodasMissoes();
    }

}
