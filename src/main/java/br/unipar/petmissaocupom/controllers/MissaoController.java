package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.models.MissaoArquivo;
import br.unipar.petmissaocupom.models.MissaoTempo;
import br.unipar.petmissaocupom.services.MissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/missoes")
public class MissaoController {

    @Autowired
    private MissaoService missaoService;

    @Operation(summary = "Criar uma nova missão com temporizador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missao criada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping("/tempo")
    public ResponseEntity<Missao> criarMissaoDeTempo(@RequestBody MissaoTempo missaoDeTempo) {
        Missao missao = missaoService.salvarMissao(missaoDeTempo);
        return new ResponseEntity<>(missao, HttpStatus.CREATED);
    }

    @Operation(summary = "Criar uma nova missão com add arquivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missao criada com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping("/arquivo")
    public ResponseEntity<Missao> criarMissaoDeArquivo(@RequestBody MissaoArquivo missaoDeArquivo) {
        Missao missao = missaoService.salvarMissao(missaoDeArquivo);
        return new ResponseEntity<>(missao, HttpStatus.CREATED);
    }

    @Operation(summary = "Lista todas as 5 missoes do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missoes listadas com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/listar/{userId}")
    public ResponseEntity<List<Missao>> listarMissoes(@PathVariable String userId) {
        List<Missao> missoes = missaoService.listarMissoesDoUsuario(userId);
        return new ResponseEntity<>(missoes, HttpStatus.OK);
    }

    @Operation(summary = "Gera missões diárias para um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missões diárias geradas com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping("/gerar/{userId}")
    public ResponseEntity<List<Missao>> gerarMissoesDiarias(@PathVariable String userId) {
        List<Missao> missoes = missaoService.gerarMissoesDiariasParaUsuario(userId);
        return new ResponseEntity<>(missoes, HttpStatus.OK);
    }

    @Operation(summary = "Verificar se todas as missoes do usuário estão concluidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verificação realizada com " +
                    "sucesso, retornando se todas as missões estão concluídas",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/verificar-concluidas/{userId}")
    public ResponseEntity<Boolean> verificarMissoesConcluidas(@PathVariable String userId) {
        boolean todasConcluidas = missaoService.todasMissoesConcluidas(userId);
        return new ResponseEntity<>(todasConcluidas, HttpStatus.OK);
    }

    @Operation(summary = "Marca uma missão como concluída")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão marcada como concluída com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/concluir/{userId}/{missaoId}")
    public ResponseEntity<Missao> concluirMissao(@PathVariable UUID missaoId, @PathVariable String userId) {
        Missao missaoConcluida = missaoService.concluirMissao(missaoId, userId);
        return new ResponseEntity<>(missaoConcluida, HttpStatus.OK);
    }

}
