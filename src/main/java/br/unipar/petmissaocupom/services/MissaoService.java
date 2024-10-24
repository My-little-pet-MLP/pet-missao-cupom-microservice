package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.dtos.MissaoDTO;
import br.unipar.petmissaocupom.enuns.TipoMissao;
import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.repositories.MissaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository missaoRepository;

    public Missao createMissao(MissaoDTO missaoDTO) {
        Missao novaMissao = new Missao();
        novaMissao.setId(UUID.randomUUID());
        novaMissao.setDescricao(missaoDTO.getDescricao());
        novaMissao.setDataGerada(new Date());
        novaMissao.setUserId(missaoDTO.getUserId());
        novaMissao.setTipo(missaoDTO.getTipo());
        novaMissao.setConcluido(false);

        if (missaoDTO.getTipo() == TipoMissao.TEMPO) {
            novaMissao.setTempoLimite(missaoDTO.getTempoLimite());
            novaMissao.setTemporizadorAtivado(missaoDTO.isTemporizadorAtivado());
        } else if (missaoDTO.getTipo() == TipoMissao.ARQUIVO) {
            novaMissao.setArquivoUrl(missaoDTO.getArquivoUrl());
        }

        return missaoRepository.save(novaMissao);
    }

    public List<Missao> gerarMissoesDiariasParaUsuario(String userId) {
        Date hoje = new Date();

        List<Missao> missoesDoDia = missaoRepository.findByUserIdAndDataGerada(userId, hoje);

        if (missoesDoDia.isEmpty()) {
            List<Missao> todasMissoes = missaoRepository.findAll();

            Random random = new Random();
            missoesDoDia = random.ints(0, todasMissoes.size())
                    .distinct()
                    .limit(5)
                    .mapToObj(todasMissoes::get)
                    .peek(missao -> {
                        missao.setUserId(userId);
                        missao.setDataGerada(hoje);
                        missao.setConcluido(false);
                    })
                    .collect(Collectors.toList());

            missaoRepository.saveAll(missoesDoDia);
        }

        return missoesDoDia;
    }

    public List<Missao> listarMissoesDoUsuario(String userId) {
        return missaoRepository.findTop5ByUserIdOrderByDataGeradaDesc(userId);
    }

    public boolean todasMissoesConcluidas(String userId) {
        List<Missao> missoesDoUsuario = missaoRepository.findByUserId(userId);

        if (missoesDoUsuario.size() == 5) {
            return missoesDoUsuario.stream().allMatch(Missao::isConcluido);
        }
        return false;
    }

    public Missao concluirMissao(UUID missaoId, String userId) {
        Missao missao = missaoRepository.findById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada com o id: " + missaoId));

        if (!missao.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Usuário não autorizado a concluir esta missão.");
        }
        if (missao.isConcluido()) {
            throw new IllegalStateException("Missão já está concluída.");
        }
        if (missao.getTipo() == TipoMissao.ARQUIVO) {
            if (missao.getArquivoUrl() == null || missao.getArquivoUrl().isEmpty()) {
                throw new IllegalStateException("Arquivo não encontrado para a missão de arquivo.");
            }
        }
        if (missao.getTipo() == TipoMissao.TEMPO) {
            long tempoAtual = System.currentTimeMillis();
            if (tempoAtual < (missao.getDataGerada().getTime() + missao.getTempoLimite())) {
                throw new IllegalStateException("O tempo da missão não expirou.");
            }
        }

        missao.setConcluido(true);
        return missaoRepository.save(missao);
    }

}
