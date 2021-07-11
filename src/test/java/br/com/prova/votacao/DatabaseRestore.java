package br.com.prova.votacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class DatabaseRestore {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JdbcTemplate jdbcTemplate;

    public DatabaseRestore(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void reset() throws SQLException {
        logger.debug("Iniciado restore da base de dados");
        limparTabelas();
        carregarDados();
        logger.debug("Finalizado restore da base de dados");
    }

    private void carregarDados() {
        logger.debug("Carregando dados em tabelas...");
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        DatabasePopulator populator = new ResourceDatabasePopulator(resourceLoader.getResource("classpath:data.sql"));
        DatabasePopulatorUtils.execute(populator, requireNonNull(jdbcTemplate.getDataSource()));
    }

    private void limparTabelas() throws SQLException {
        logger.debug("Limpando tabelas");
        List<String> tables = tables();
        JdbcTestUtils.deleteFromTables(jdbcTemplate, tables.toArray(new String[0]));
    }

    private List<String> tables() throws SQLException {
        List<String> tabelas = new ArrayList<>();
        try(Connection conn = requireNonNull(jdbcTemplate.getDataSource()).getConnection()){
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            extrairNomeTabelas(tabelas, databaseMetaData);
        }
        return tabelas;
    }

    private void extrairNomeTabelas(List<String> tabelas, DatabaseMetaData databaseMetaData) throws SQLException {
        try(ResultSet rs = databaseMetaData.getTables("", "votacao_db", "%", new String[]{"TABLE"})) {
            tabelas.addAll(toList(rs));
        }
    }

    private List<String> toList(ResultSet rs) throws SQLException {
        List<String> tables = new ArrayList<>();
        while (rs.next()) { tables.add("votacao_db." + rs.getString("TABLE_NAME")); }
        return tables;
    }

}
