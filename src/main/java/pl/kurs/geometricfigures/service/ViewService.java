package pl.kurs.geometricfigures.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ViewService {
    private final JdbcTemplate jdbcTemplate;


    public ViewService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct
    public void createView() {
        String createViewSql = "CREATE OR REPLACE VIEW shape_view AS " +
                "SELECT 'square' AS type, id, width, NULL as height, NULL as radius, width * width AS area, 4 * width AS perimeter, created_at, created_by_id, last_modified_at, last_modified_by_id, version " +
                "FROM square " +
                "UNION " +
                "SELECT 'circle' AS type, id,  NULL as width ,NULL as height, radius, PI() * radius * radius AS area, 2 * PI() * radius AS perimeter, created_at, created_by_id, last_modified_at, last_modified_by_id, version " +
                "FROM circle " +
                "UNION " +
                "SELECT 'rectangle' AS type, id, width, height, NULL as radius, width * height AS area, 2 * (width + height) AS perimeter, created_at, created_by_id, last_modified_at, last_modified_by_id, version " +
                "FROM rectangle;";
        jdbcTemplate.execute(createViewSql);

    }
}
