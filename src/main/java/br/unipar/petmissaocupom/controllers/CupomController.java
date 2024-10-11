package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.services.CupomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cupom")
public class CupomController {

    @Autowired
    private CupomService cupomService;

    @Operation(summary = "Inserir cupom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<Cupom> createCupom(@RequestBody Cupom cupom) {
        Cupom saveCupom = cupomService.createCupom(cupom);
        return ResponseEntity.ok(saveCupom);
    }

    @Operation(summary = "Gerar o cupom para o usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom gerado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/atualizar/{cupomId}")
    public ResponseEntity<Cupom> inserirUserIdNoCupom(
            @PathVariable UUID cupomId,
            @RequestParam String userId) {
        Cupom cupomAtualizado = cupomService.inserirUserIdNoCupom(cupomId, userId);
        return ResponseEntity.ok(cupomAtualizado);
    }

    @Operation(summary = "Lista todos os cupons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons listados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping
    public List<Cupom> listarCupons() {
        return cupomService.listarTodosCupons();
    }

    @Operation(summary = "Lista todos os cupons do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons listados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Servidor não encontrou o usuário especificado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping("/usuario/{userId}")
    public List<Cupom> listarCuponsPorUsuario(@PathVariable String userId) {
        return cupomService.listarCuponsPorUsuario(userId);
    }

    @Operation(summary = "Desativa um cupom alterando isUtilizado para false")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom desativado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PutMapping("/desativar/{id}")
    public ResponseEntity<Cupom> desativarCupom(@PathVariable UUID id) {
        Cupom updateCupom = cupomService.desativarCupom(id);
        return ResponseEntity.ok(updateCupom);
    }

    @Operation(summary = "Busca um cupom por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @GetMapping("/{cupomId}")
    public ResponseEntity<Cupom> findById(@PathVariable UUID cupomId) {
        Cupom cupom = cupomService.findById(cupomId);
        return ResponseEntity.ok(cupom);
    }

}
