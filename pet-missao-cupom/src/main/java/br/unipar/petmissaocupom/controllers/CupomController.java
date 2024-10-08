package br.unipar.petmissaocupom.controllers;

import br.unipar.petmissaocupom.models.Cupom;
import br.unipar.petmissaocupom.services.CupomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Inserir um cupom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons salvos com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<Cupom> insert(@RequestBody Cupom cupom) {
        try {
            Cupom savedCupom = cupomService.insert(cupom);
            return new ResponseEntity<>(savedCupom, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Armazena vários cupons")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons armazenados com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/armazenarLista")
    public List<Cupom> armazenarVariosCupons(@RequestBody List<Cupom> cupons) {
        return cupomService.armazenarVariosCupons(cupons);
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

    @Operation(summary = "Armazena todos os cupons no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupons armazenadas com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping("/armazenar")
    public void armazenarCupons() {
        cupomService.armazenarCupons();
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

    @Operation(summary = "Desativar cupom usado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupom desativado"),
            @ApiResponse(responseCode = "404", description = "Cupom não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping("/{id}")
    public void desativarCupom(@PathVariable UUID id) {
        cupomService.usarCupom(id);
    }

}
