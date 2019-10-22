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

import static aaronrebak.gitlab.io.jsoniter.model.JsonModel.aJsonModel;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("The JsonIngestorService")
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@SpringBootTest
class JsonIterServiceSystemTest {

    private static final String EXTERNAL_ENDPOINT = "/api/json/get/41OOrUK5L";
    private static final String JSON_CONTROLLER_ENDPOINT = "/jsoniter/stream-json";

    @Test
    @DisplayName("Will Ingest Json to a Database when the Json Controller Endpoint is Triggered")
    void endpointTriggersIngestToDatabase(@Autowired MockMvc mockMvc, @Autowired CrudRepository<JsonModel, String> crudRepository) throws Exception {
        givenThat(
                WireMock.get(urlEqualTo(EXTERNAL_ENDPOINT))
                        .willReturn(
                                aResponse()
                                        .withBodyFile("serialisedJson.json")
                                        .withHeader("Content-Type", "application/octet-stream")
                        )
        );

        mockMvc.perform(MockMvcRequestBuilders.get(JSON_CONTROLLER_ENDPOINT)).andReturn();

        then(crudRepository.findAll()).containsExactlyInAnyOrder(
                aJsonModel("id1"),
                aJsonModel("id2"),
                aJsonModel("id3"),
                aJsonModel("id4"),
                aJsonModel("id5")
        );
    }
}