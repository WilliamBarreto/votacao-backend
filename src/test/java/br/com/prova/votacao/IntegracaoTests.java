package br.com.prova.votacao;

import org.junit.jupiter.api.AfterEach;
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
    private DatabaseRestore databaseRestore;

    @BeforeEach
    public void reiniciarEstado() throws SQLException {
        databaseRestore.reset();
    }

    @AfterEach
    public void limparEstado() throws SQLException {
        databaseRestore.limparTabelas();
    }
}
