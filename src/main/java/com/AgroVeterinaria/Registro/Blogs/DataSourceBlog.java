package com.AgroVeterinaria.Registro.Blogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.AgroVeterinaria.DTO.Blog;
import com.AgroVeterinaria.Herramientas.Constantes;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DataSourceBlog {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DataSourceBlog(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // RowMapper para convertir filas de la tabla blog en objetos Blog
    private static class BlogRowMapper implements RowMapper<Blog> {
        @Override
        public Blog mapRow(ResultSet rs, int rowNum) throws SQLException {
            Blog blog = new Blog();
            blog.setOrigen(rs.getString("origen"));
            blog.setEmail(rs.getString("email"));
            blog.setFecha(rs.getString("fecha"));
            blog.setHora(rs.getString("hora"));
            blog.setComentario(rs.getString("comentario"));
            blog.setEstatus(rs.getInt("estatus"));
            return blog;
        }
    }

    // 1. Obtener últimos 10 por origen y estatus
    public List<Blog> findUltimosDiezByOrigen(String origen, int estatus) {
        String sql = "SELECT * FROM registro.blog " +
                     "WHERE origen = ? AND estatus = ? " +
                     "ORDER BY fecha, hora DESC LIMIT 10";
        return jdbcTemplate.query(sql, new BlogRowMapper(), origen, estatus);
    }

    // 2. Insertar un nuevo blog
    @Transactional
    public Integer newBlog(String origen, String email, String fecha, String hora, String comentario, Integer estatus) {
        String sql = "INSERT INTO registro.blog (origen, email, fecha, hora, comentario, estatus) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        int filasAfectadas = jdbcTemplate.update(sql, origen, email, fecha, hora, comentario, estatus);
        // Devuelve el mismo valor que la implementación original (Constantes.INT_ESTATUS_UNO)
        // aunque podrías devolver filasAfectadas si prefieres.
        return Constantes.INT_ESTATUS_UNO;
    }

    // 3. Actualizar el estatus de un blog específico
    @Transactional
    public Integer updateBlogEstatus(String origen, String email, String fecha, String hora, Integer estatus) {
        String sql = "UPDATE registro.blog SET estatus = ? " +
                     "WHERE origen = ? AND email = ? AND fecha = ? AND hora = ?";
        return jdbcTemplate.update(sql, estatus, origen, email, fecha, hora);
    }

    // 4. Buscar un blog por sus claves (excluyendo estatus = 3)
    public Optional<Blog> findOneByOrigenAndEmailAndFechaAndHora(String origen, String email, String fecha, String hora) {
        String sql = "SELECT * FROM registro.blog " +
                     "WHERE origen = ? AND email = ? AND fecha = ? AND hora = ? " +
                     "AND estatus <> 3 " +
                     "LIMIT 1";
        List<Blog> resultados = jdbcTemplate.query(sql, new BlogRowMapper(), origen, email, fecha, hora);
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }

    // 5. Búsqueda paginada por estatus (similar a Page<Blog> findByEstatus)
    public Page<Blog> findByEstatus(int estatus, Pageable pageable) {
        // Consulta para obtener los registros de la página actual
        String sqlData = "SELECT * FROM registro.blog WHERE estatus = ? " +
                         "ORDER BY fecha DESC, hora DESC " +  // orden razonable para paginación
                         "LIMIT ? OFFSET ?";
        List<Blog> blogs = jdbcTemplate.query(sqlData, new BlogRowMapper(),
                estatus, pageable.getPageSize(), pageable.getOffset());

        // Consulta para contar el total de registros con ese estatus
        String sqlCount = "SELECT COUNT(*) FROM registro.blog WHERE estatus = ?";
        Long total = jdbcTemplate.queryForObject(sqlCount, Long.class, estatus);

        return new PageImpl<>(blogs, pageable, total);
    }
    
 // ... dentro de CrudBlogJdbc

    @Transactional
    public Blog save(Blog blog) {
        // Verificar si ya existe un registro con la misma clave primaria
        Optional<Blog> existente = findOneByOrigenAndEmailAndFechaAndHora(
                blog.getOrigen(),
                blog.getEmail(),
                blog.getFecha(),
                blog.getHora()
        );
        
        if (existente.isPresent()) {
            // Actualizar el registro existente (todos los campos que permitimos modificar)
            String sqlUpdate = "UPDATE registro.blog SET comentario = ?, estatus = ? " +
                               "WHERE origen = ? AND email = ? AND fecha = ? AND hora = ?";
            jdbcTemplate.update(sqlUpdate,
                    blog.getComentario(),
                    blog.getEstatus(),
                    blog.getOrigen(),
                    blog.getEmail(),
                    blog.getFecha(),
                    blog.getHora());
        } else {
            // Insertar nuevo registro
            String sqlInsert = "INSERT INTO registro.blog (origen, email, fecha, hora, comentario, estatus) " +
                               "VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sqlInsert,
                    blog.getOrigen(),
                    blog.getEmail(),
                    blog.getFecha(),
                    blog.getHora(),
                    blog.getComentario(),
                    blog.getEstatus());
        }
        return blog;
    }
    
    
    public Optional<Blog> find(String origen, String clave, String fecha, String hora) {
        String sql = "SELECT * FROM registro.blog " +
                     "WHERE origen = ? AND email = ? AND fecha = ? AND hora = ? " +
                     "AND estatus <> 3 " +
                     "LIMIT 1";
        List<Blog> resultados = jdbcTemplate.query(sql, new BlogRowMapper(), origen, clave, fecha, hora);
        return resultados.isEmpty() ? Optional.empty() : Optional.of(resultados.get(0));
    }
    
    public Optional<Blog> findOneByEmail(String email) {
        String sql = "SELECT * FROM registro.blog " +
                     "WHERE email = ? " +
                     "AND estatus <> 3 " +
                     "LIMIT 1";
        List<Blog> resultados = jdbcTemplate.query(sql, new BlogRowMapper(), email);
        // Convertimos la lista a stream y tomamos el primero si existe
        return resultados.stream().findFirst();
    }
}