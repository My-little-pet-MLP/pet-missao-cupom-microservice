package br.unipar.petmissaocupom.component;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        try {
            // Adiciona a coluna com valor temporário
            String sqlAddColumn = "ALTER TABLE missoes ADD COLUMN tipo_missao VARCHAR(31)";
            jdbcTemplate.execute(sqlAddColumn);

            // Atualiza a nova coluna com um valor padrão para as linhas existentes
            String sqlUpdateColumn = "UPDATE missoes SET tipo_missao = 'default_missao' WHERE tipo_missao IS NULL";
            jdbcTemplate.execute(sqlUpdateColumn);

            // Altera a coluna para NOT NULL
            String sqlAlterColumn = "ALTER TABLE missoes ALTER COLUMN tipo_missao SET NOT NULL";
            jdbcTemplate.execute(sqlAlterColumn);
        } catch (Exception e) {
            e.printStackTrace();
            // Trate a exceção conforme necessário
        }
    }

}
