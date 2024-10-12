package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.services.CupomService;
import br.unipar.petmissaocupom.services.MissaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cupom")
public class CupomController {

    @Autowired
    private CupomService cupomService;

    @Autowired
    private MissaoService missaoService;

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
        try {
            Cupom saveCupom = cupomService.createCupom(cupom);
            return ResponseEntity.ok(saveCupom);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Verifica se todas as missões do usuário estão concluídas e insere o userId no cupom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "UserId inserido no cupom com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Cupom.class))),
            @ApiResponse(responseCode = "404", description = "Cupom ou missões não encontrados",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro no servidor",
                    content = @Content)
    })
    @PostMapping("/verificarEInserir/{userId}/{cupomId}")
    public ResponseEntity<Cupom> verificarEInserirUserIdNoCupom(
            @PathVariable String userId,
            @PathVariable UUID cupomId) {
        try {
            boolean todasConcluidas = missaoService.todasMissoesConcluidas(userId);
            if (todasConcluidas) {
                Cupom cupomAtualizado = cupomService.inserirUserIdNoCupom(cupomId, userId);
                return ResponseEntity.ok(cupomAtualizado);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try {
            Cupom cupomAtualizado = cupomService.inserirUserIdNoCupom(cupomId, userId);
            return ResponseEntity.ok(cupomAtualizado);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Lista todos os cupons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons listados com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Cupom>> listarCupons() {
        try {
            List<Cupom> cupons = cupomService.listarTodosCupons();
            return new ResponseEntity<>(cupons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Lista todos os cupons do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons listados com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Servidor não encontrou o usuário especificado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content)
    })
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Cupom>> listarCuponsPorUsuario(@PathVariable String userId) {
        try {
            List<Cupom> cupons = cupomService.listarCuponsPorUsuario(userId);
            return new ResponseEntity<>(cupons, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try {
            Cupom updateCupom = cupomService.desativarCupom(id);
            return ResponseEntity.ok(updateCupom);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        try {
            Cupom cupom = cupomService.findById(cupomId);
            return new ResponseEntity<>(cupom, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Busca um cupom por ID e UserId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom encontrado com sucesso",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content)
    })
    @GetMapping("/{cupomId}/user/{userId}")
    public ResponseEntity<Cupom> findCupomByUserId(@PathVariable UUID cupomId, @PathVariable String userId) {
        try {
            Cupom cupom = cupomService.findCupomByUserId(cupomId, userId);
            return new ResponseEntity<>(cupom, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
