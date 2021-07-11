package br.com.prova.votacao;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

@SpringBootTest
//@ActiveProfiles("test")
@AutoConfigureMockMvc
public class IntegracaoTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void reiniciarEstado() throws SQLException {
        new DatabaseRestore(jdbcTemplate).reset();
    }

}
