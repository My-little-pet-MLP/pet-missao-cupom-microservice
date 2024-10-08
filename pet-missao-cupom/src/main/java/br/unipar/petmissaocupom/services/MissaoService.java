package br.unipar.petmissaocupom.services;

import br.unipar.petmissaocupom.enuns.TipoMissao;
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

    public Missao insert(Missao missao) {
        missao.setConcluido(false);
        return missaoRepository.save(missao);
    }

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
                new Missao(UUID.randomUUID(), "Bricar por 20 minutos", false, null, TipoMissao.TEMPO,null),
                new Missao(UUID.randomUUID(), "Trocar água", false, null, TipoMissao.ARQUIVO,null),
                new Missao(UUID.randomUUID(), "Passear por 40 minutos", false, null, TipoMissao.TEMPO,null),
                new Missao(UUID.randomUUID(), "Dar a patinha", false, null, TipoMissao.ARQUIVO,null),
                new Missao(UUID.randomUUID(), "Ensinar o comando - senta", false, null, TipoMissao.ARQUIVO,null),
                new Missao(UUID.randomUUID(), "Brincar de pegar bolinha por 15 minutos", false, null, TipoMissao.TEMPO,null),
                new Missao(UUID.randomUUID(), "Faça uma sessão de fotos com alguma temática", false, null, TipoMissao.ARQUIVO,null),
                new Missao(UUID.randomUUID(), "Ensinar o comanda - fica", false, null, TipoMissao.ARQUIVO,null),
                new Missao(UUID.randomUUID(), "Esconda petiscos pela casa e deixe o seu animalzinho achar", false, null, TipoMissao.ARQUIVO,null),
                new Missao(UUID.randomUUID(), "Dar um petisco", false, null, TipoMissao.ARQUIVO,null)
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
