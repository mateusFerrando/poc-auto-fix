package br.com.mateus.ferrando;

import io.agroal.api.AgroalDataSource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.PathParam;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@Path("/hello")
public class GreetingResource {

    private final AgroalDataSource dataSource;

    public GreetingResource(AgroalDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/users/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers(@PathParam("name") String name) throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE name = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    users.add(new User(rs.getString("name"), rs.getString("email")));
                }
            }
        }

        return users;
    }

    public static class User {
        public String name;
        public String email;

        public User(String name, String email) {
            this.name = name;
            this.email = email;
        }
    }
}
