package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.models.Missao;
import br.unipar.petmissaocupom.repositories.MissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class MissaoService {

    @Autowired
    private MissaoRepository missaoRepository;

    public List<Missao> gerarMissoesDiariasParaUsuario(String userId) {
        LocalDate hoje = LocalDate.now();
        List<Missao> missoesDoDia = missaoRepository.findByUserIdAndDataGerada(userId, hoje);

        if (missoesDoDia.isEmpty()) {
            List<Missao> todasMissoes = missaoRepository.findAll();
            Random random = new Random();
            missoesDoDia = random.ints(0, todasMissoes.size())
                    .distinct()
                    .limit(5)
                    .mapToObj(todasMissoes::get)
                    .peek(m -> m.setUserId(userId)) // Define o userId
                    .peek(m -> m.setDataGerada(hoje)) // Define a data gerada
                    .toList();

            missaoRepository.saveAll(missoesDoDia);
        }

        return missoesDoDia;
    }

    public void armazenarTodasMissoes() {
        List<Missao> missoes = Arrays.asList(
                new Missao(UUID.randomUUID(), "Colete 100 moedas", false, null, null),
                new Missao(UUID.randomUUID(), "Derrote 10 inimigos", false, null, null),
                new Missao(UUID.randomUUID(), "Complete uma fase", false, null, null),
                new Missao(UUID.randomUUID(), "Conquiste 500 pontos", false, null, null),
                new Missao(UUID.randomUUID(), "Jogue por 30 minutos", false, null, null),
                new Missao(UUID.randomUUID(), "Passeio do Dia", false, null, null),
                new Missao(UUID.randomUUID(), "Hora do Banho", false, null, null),
                new Missao(UUID.randomUUID(), "Treino de Obediência", false, null, null),
                new Missao(UUID.randomUUID(), "Brincadeira de Busca", false, null, null),
                new Missao(UUID.randomUUID(), "Hora da Alimentação", false, null, null)
        );

        missaoRepository.saveAll(missoes);
    }

    public List<Missao> listarMissoesDoDia(String userId) {
        return missaoRepository.findByUserIdAndDataGerada(userId, LocalDate.now());
    }

    public boolean verificarTodasMissoesCompletas(String userId) {
        List<Missao> missoes = missaoRepository.findByUserIdAndDataGerada(userId, LocalDate.now());
        return missoes.stream().allMatch(Missao::isConcluido);
    }

}
