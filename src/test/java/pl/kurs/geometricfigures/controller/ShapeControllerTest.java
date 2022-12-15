package pl.kurs.geometricfigures.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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
        String response = postman.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=Circle&radiusFrom=1.9&radiusTo=2.1")
                .with(httpBasic("creatorUser", "123")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(response).get(0);
        node.get("type").equals("CIRCLE");
        node.get("id").equals(9);
        node.get("radius").equals(2.0);
        node.get("version").equals(0);
        node.get("createdAt").equals("2022-12-14 23:58:56.147539");
        node.get("createdBy").equals("creatorUser");
        node.get("lastModifiedAt").equals("2022-12-14 23:58:56.147539");
        node.get("lastModifiedBy").equals("creatorUser");
    }

    @Test
    void shouldReturnSquareWithRadius2() throws Exception {
        String response = postman.perform(MockMvcRequestBuilders.get("/api/v1/shapes/parameters?type=Circle&radiusFrom=1.9&radiusTo=2.1")
                .with(httpBasic("creatorUser", "123")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(response).get(0);
        node.get("type").equals("CIRCLE");
        node.get("id").equals(9);
        node.get("radius").equals(2.0);
        node.get("version").equals(0);
        node.get("createdAt").equals("2022-12-14 23:58:56.147539");
        node.get("createdBy").equals("creatorUser");
        node.get("lastModifiedAt").equals("2022-12-14 23:58:56.147539");
        node.get("lastModifiedBy").equals("creatorUser");
        node.get("area").equals(Math.PI * 2 * 2);
        node.get("perimeter").equals(Math.PI * 2 * 2);
    }

    @Test
    void shouldSave8x9RectangleInDatabase() throws Exception {
        Double[] params = new Double[2];
        params[0] = 8.0;
        params[1] = 9.0;
        CreateShapeCommand command = new CreateShapeCommand("RECTANGLE", params);
        JsonNode node = objectMapper.convertValue(command, JsonNode.class);

        String response = postman.perform(MockMvcRequestBuilders.post("/api/v1/shapes")
                .with(httpBasic("creatorUser", "123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(node.toPrettyString()))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode responseNode = objectMapper.readTree(response).get(0);
//        responseNode.get("type").equals("CIRCLE");

    }


}