package aaronrebak.gitlab.io.jsoniter.client;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.io.InputStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("JsonClientIntegrationTest")
@EnableFeignClients(clients = JsonClient.class)
@AutoConfigureWireMock(port = 0)
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, EmbeddedMongoAutoConfiguration.class})
@SpringBootTest(classes = JsonClient.class)
class JsonClientIntegrationTest {

    private static final String REQUEST = "/api/json/get/41OOrUK5L";
    private static final String EXPECTED_RESPONSE = "externalEndpointResponse";

    @Test
    @DisplayName("Is able to send a GET Request Successfully")
    void test(@Autowired JsonClient jsonClient) throws Exception {
        givenThat(
                WireMock.get(urlEqualTo(REQUEST))
                        .willReturn(
                                aResponse()
                                        .withBody(EXPECTED_RESPONSE)
                        )
        );

        InputStream inputStream = jsonClient.getJson().body().asInputStream();

        verify(getRequestedFor(urlEqualTo(REQUEST)));

        final String actualResponse = IOUtils.toString(inputStream);
        then(actualResponse).isEqualTo(EXPECTED_RESPONSE);
    }
}