package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.repositories.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository missaoRepository;

    // Gera as missões diárias para o usuário
    public List<Missao> gerarMissoesDiariasParaUsuario(String userId) {
        LocalDate hoje = LocalDate.now();
        List<Missao> missoesDoDia = missaoRepository.findByUserIdAndDataGerada(userId, hoje);

        if (missoesDoDia.isEmpty()) {
            // Gerar 5 novas missões para o usuário
            Missao missao1 = new Missao(UUID.randomUUID(), "Colete 100 moedas", false, hoje, userId);
            Missao missao2 = new Missao(UUID.randomUUID(), "Derrote 10 inimigos", false, hoje, userId);
            Missao missao3 = new Missao(UUID.randomUUID(), "Complete uma fase", false, hoje, userId);
            Missao missao4 = new Missao(UUID.randomUUID(), "Conquiste 500 pontos", false, hoje, userId);
            Missao missao5 = new Missao(UUID.randomUUID(), "Jogue por 30 minutos", false, hoje, userId);

            missaoRepository.saveAll(Arrays.asList(missao1, missao2, missao3, missao4, missao5));
            missoesDoDia = Arrays.asList(missao1, missao2, missao3, missao4, missao5);
        }

        return missoesDoDia;
    }

    // Lista as missões do dia para um usuário
    public List<Missao> listarMissoesDoDia(String userId) {
        return missaoRepository.findByUserIdAndDataGerada(userId, LocalDate.now());
    }

    // Verifica se todas as missões de um usuário estão completas
    public boolean verificarTodasMissoesCompletas(String userId) {
        List<Missao> missoes = missaoRepository.findByUserIdAndDataGerada(userId, LocalDate.now());
        return missoes.stream().allMatch(Missao::isConcluido);
    }

}
