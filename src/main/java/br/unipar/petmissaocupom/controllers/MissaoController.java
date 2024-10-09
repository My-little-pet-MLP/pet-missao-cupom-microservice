package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.services.CupomService;
import br.unipar.petmissaocupom.services.MissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService missaoService;

    @Autowired
    private CupomService cupomService;

    @Operation(summary = "Insere uma nova missão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Missão criada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<Missao> insert(@RequestBody Missao missao) {
        try {
            Missao saveMissao = missaoService.insert(missao);
            return new ResponseEntity<>(saveMissao, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Lista as missões diárias de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missões retornadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/diarias/{userId}")
    public List<Missao> getMissoesDiarias(@PathVariable String userId) {
        return missaoService.listarMissoesDoDia(userId);
    }

    @Operation(summary = "Gera missões diárias para um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missões geradas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/gerar/{userId}")
    public List<Missao> gerarMissoesDiarias(@PathVariable String userId) {
        return missaoService.gerarMissoesDiariasParaUsuario(userId);
    }

    @Operation(summary = "Valida todas as missões diárias de um usuário e gera um cupom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom gerado com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/completar/{userId}")
    public Cupom completarMissoesDiarias(@PathVariable String userId) {
        return cupomService.gerarCupomSeTodasMissoesCompletas(userId);
    }

    @Operation(summary = "Armazena todas as missões no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missões armazenadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/armazenar")
    public void armazenarTodasMissoes() {
        missaoService.armazenarTodasMissoes();
    }

}
