package br.com.lunaticmc.economy.database;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.ConfigurationSection;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Getter
public class SQL {

    private Connection connection;
    private final ExecutorService executor;
    private final String host, user, password, database;
    private final int port;
    @Getter
    public static SQL instance;

    @SneakyThrows
    public SQL(ConfigurationSection section) {
        instance = this;
        this.host = section.getString("host");
        this.user = section.getString("user");
        this.password = section.getString("password");
        this.database = section.getString("database");
        this.port = section.getInt("port");
        this.executor = Executors.newCachedThreadPool();
        load();
    }

    public void load() throws SQLException, ClassNotFoundException {
        openConnection();
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
    }

    @SneakyThrows
    public void update(String sql, Object... vars) {
        PreparedStatement ps = prepareStatement(sql, vars);
        ps.execute();
        ps.close();
    }

    public void execute(String sql, Object... vars) {
        executor.execute(() -> update(sql, vars));
    }

    @SneakyThrows
    public PreparedStatement prepareStatement(String query, Object... vars) {
        PreparedStatement ps = getConnection().prepareStatement(query);
        for (int i = 0; i < vars.length; i++)
            ps.setObject(i + 1, vars[i]);
        return ps;
    }

    @SneakyThrows
    public CachedRowSet query(String query, Object... vars) {
        CachedRowSet rowSet = null;
        Future<CachedRowSet> future = executor.submit(() -> {
            PreparedStatement ps = prepareStatement(query, vars);

            ResultSet rs = ps.executeQuery();
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            rs.close();
            ps.close();

            if (crs.next())
                return crs;

            return null;
        });

        if (future.get() != null) rowSet = future.get();

        return rowSet;
    }

    public void createTable(String table, String columns) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            Statement stm = connection.createStatement();
            stm.execute("CREATE TABLE IF NOT EXISTS " + table + " (" + columns + ");");
        }
    }

}