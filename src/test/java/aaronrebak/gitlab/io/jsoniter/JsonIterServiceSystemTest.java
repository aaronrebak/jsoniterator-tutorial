package aaronrebak.gitlab.io.jsoniter;

import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder.okForJson;
import static com.github.tomakehurst.wiremock.client.WireMock.givenThat;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("The JsonIngestorService")
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@SpringBootTest
class JsonIterServiceSystemTest {

    private static final String JSON_ID = UUID.randomUUID().toString();
    private static final String EXTERNAL_ENDPOINT = "/api/json/get/41OOrUK5L";
    private static final String JSON_CONTROLLER_ENDPOINT = "/jsoniter/stream-json";
    private static final JsonModel JSON_MODEL = new JsonModel(JSON_ID);

    @Test
    @DisplayName("Will Ingest Json to a Database when the Json Controller Endpoint is Triggered")
    void endpointTriggersIngestToDatabase(@Autowired MockMvc mockMvc, @Autowired CrudRepository<JsonModel, String> crudRepository) throws Exception {
        //given wiremock is stubbed with endpoint data
        givenThat(
                WireMock.get(urlEqualTo(EXTERNAL_ENDPOINT))
                        .willReturn(
                                okForJson(Collections.singleton(JSON_MODEL))
                        )
        );

        //when endpoint is triggered
        mockMvc.perform(MockMvcRequestBuilders.get(JSON_CONTROLLER_ENDPOINT)).andReturn();

        //then data is ingested into the database
        then(crudRepository.findAll()).containsExactlyInAnyOrder(JSON_MODEL);
    }
}