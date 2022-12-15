package pl.kurs.geometricfigures.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.kurs.geometricfigures.GeometricFiguresApplication;
import pl.kurs.geometricfigures.model.command.CreateShapeCommand;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {GeometricFiguresApplication.class}, properties = "src/test/resources/application.properties")
@AutoConfigureMockMvc
@Sql(scripts = "classpath:db_before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:db_after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ShapeControllerTest {
    @Autowired
    private MockMvc postman;
    @Autowired
    ObjectMapper objectMapper;


    @Test
    void shouldReturnCircleWithRadius2() throws Exception {
        postman.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=Circle&radiusFrom=1.9&radiusTo=2.1")
                .with(httpBasic("creatorUser", "123")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("CIRCLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(9))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].radius").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdAt").value("2022-12-14 23:58:56"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastModifiedAt").value("2022-12-14 23:58:56"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdBy").value("creatorUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastModifiedBy").value("creatorUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].area").value(Math.PI * 2 * 2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].perimeter").value(Math.PI * 2 * 2));
    }

    @Test
    void shouldReturnSquareWithWidth2() throws Exception {
        postman.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=Square&widthFrom=1.9&widthTo=2.1")
                .with(httpBasic("creatorUser", "123")))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("SQUARE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(7))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].width").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].version").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdAt").value("2022-12-14 23:58:56"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastModifiedAt").value("2022-12-14 23:58:56"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].createdBy").value("creatorUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastModifiedBy").value("creatorUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].area").value(2 * 2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].perimeter").value(8));
    }

    @Test
    void shouldSave8x9RectangleInDatabase() throws Exception {
        Double[] params = new Double[2];
        params[0] = 8.0;
        params[1] = 9.0;
        CreateShapeCommand command = new CreateShapeCommand("RECTANGLE", params);
        JsonNode node = objectMapper.convertValue(command, JsonNode.class);

        postman.perform(MockMvcRequestBuilders.post("/api/v1/shapes")
                .with(httpBasic("creatorUser", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(node.toPrettyString()))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("RECTANGLE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.width").value(8))
                .andExpect(MockMvcResultMatchers.jsonPath("$.height").value(9))
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy").value("creatorUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastModifiedBy").value("creatorUser"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.area").value(72))
                .andExpect(MockMvcResultMatchers.jsonPath("$.perimeter").value(34));
    }
}