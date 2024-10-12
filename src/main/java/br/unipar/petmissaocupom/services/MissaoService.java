package br.unipar.petmissaocupom.services;

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

    public Missao createMissao(Missao missao) {
        missao.setConcluido(false);
        return missaoRepository.save(missao);
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

        missao.setConcluido(true);
        return missaoRepository.save(missao);
    }

}
