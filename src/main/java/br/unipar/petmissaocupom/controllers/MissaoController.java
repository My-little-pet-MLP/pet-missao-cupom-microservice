package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.services.MissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService missaoService;

    @Operation(summary = "Criar uma nova missão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missao criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Missao> createMissao(@RequestBody Missao missao) {
        Missao novaMissao = missaoService.createMissao(missao);
        return ResponseEntity.ok(novaMissao);
    }

    @Operation(summary = "Lista todas as 5 missoes do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missoes listadas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/listar/{userId}")
    public ResponseEntity<List<Missao>> listarMissoes(@PathVariable String userId) {
        List<Missao> missoes = missaoService.listarMissoesDoUsuario(userId);
        return ResponseEntity.ok(missoes);
    }

    @Operation(summary = "Gera missões diárias para um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missões diárias geradas com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping("/gerar/{userId}")
    public List<Missao> gerarMissoesDiarias(@PathVariable String userId) {
        return missaoService.gerarMissoesDiariasParaUsuario(userId);
    }

    @Operation(summary = "Verificar se todas as missoes do usuário estão concluidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com sucesso, retornando se todas as missões estão concluídas",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/verificarConcluidas/{userId}")
    public ResponseEntity<Boolean> verificarMissoesConcluidas(@PathVariable String userId) {
        boolean todasConcluidas = missaoService.todasMissoesConcluidas(userId);
        return ResponseEntity.ok(todasConcluidas);
    }

    @Operation(summary = "Marca uma missão como concluída")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão marcada como concluída com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Missao.class))),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/concluir/{missaoId}")
    public ResponseEntity<Missao> concluirMissao(@PathVariable UUID missaoId) {
        Missao missaoConcluida = missaoService.concluirMissao(missaoId);
        return ResponseEntity.ok(missaoConcluida);
    }

}
