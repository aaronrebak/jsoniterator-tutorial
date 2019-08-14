package aaronrebak.gitlab.io.jsoniter.repository;

import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@EnableAutoConfiguration
@ContextConfiguration(classes = CrudRepository.class)
@DisplayName("JsonModelRepositoryIntegrationTest")
class JsonModelRepositoryIntegrationTest {

    private static final String JSON_ID = UUID.randomUUID().toString();
    private static final JsonModel JSON_MODEL = new JsonModel(JSON_ID);

    @Test
    @DisplayName("Will Save a JsonModel to the Database")
    void willSaveAJsonModelToADatabase(@Autowired JsonModelRepository jsonModelRepository, @Autowired CrudRepository<JsonModel, String> crudRepository) {
        jsonModelRepository.save(JSON_MODEL);
        assertThat(crudRepository.findAll()).containsExactlyInAnyOrder(JSON_MODEL);
    }
}