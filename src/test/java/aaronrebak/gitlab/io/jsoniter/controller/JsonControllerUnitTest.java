package aaronrebak.gitlab.io.jsoniter.controller;

import aaronrebak.gitlab.io.jsoniter.service.JsonIngester;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
@DisplayName("JsonControllerUnitTest")
class JsonControllerUnitTest {

    private JsonIngester jsonIngester;

    @InjectMocks
    private JsonController jsonController;

    JsonControllerUnitTest(@Mock JsonIngester jsonIngester) {
        this.jsonIngester = jsonIngester;
    }


    @Test
    @DisplayName("Endpoint call should result in json being ingested")
    void endpointTriggerShouldResultInJsonBeingIngested() throws Exception {
        this.jsonController.streamJson();
        then(this.jsonIngester).should(times(1)).ingestJson();
    }
}