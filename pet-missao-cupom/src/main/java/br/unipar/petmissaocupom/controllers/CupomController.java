package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.services.CupomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cupom")
public class CupomController {

    @Autowired
    private CupomService cupomService;

    @PostMapping("/armazenar")
    public List<Cupom> armazenarVariosCupons(@RequestBody List<Cupom> cupons) {
        return cupomService.armazenarVariosCupons(cupons);
    }

    @GetMapping
    public List<Cupom> listarCupons() {
        return cupomService.listarTodosCupons();
    }

}
