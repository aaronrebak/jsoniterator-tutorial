package aaronrebak.gitlab.io.jsoniter.service;

import aaronrebak.gitlab.io.jsoniter.client.JsonClient;
import aaronrebak.gitlab.io.jsoniter.iterator.JsonIteratorCreator;
import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import aaronrebak.gitlab.io.jsoniter.repository.JsonModelRepository;
import com.jsoniter.JsonIterator;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@DisplayName("JsonIngesterUnitTest")
class JsonIngesterUnitTest {

    private JsonClient jsonClient;
    private JsonIteratorCreator jsonIteratorCreator;
    private JsonModelRepository jsonModelRepository;
    private JsonIterator jsonIterator;

    @InjectMocks
    private JsonIngester jsonIngester;

    JsonIngesterUnitTest(@Mock JsonClient jsonClient, @Mock JsonIteratorCreator jsonIteratorCreator, @Mock JsonModelRepository jsonModelRepository, @Mock JsonIterator jsonIterator) {
        this.jsonClient = jsonClient;
        this.jsonIteratorCreator = jsonIteratorCreator;
        this.jsonModelRepository = jsonModelRepository;
        this.jsonIterator = jsonIterator;
    }

    @BeforeEach
    void setupMocks(@Mock Response response, @Mock Response.Body body, @Mock InputStream inputStream) throws Exception {
        given(this.jsonClient.getJson()).willReturn(response);
        given(response.body()).willReturn(body);
        given(body.asInputStream()).willReturn(inputStream);
        given(this.jsonIteratorCreator.create(inputStream, 1024)).willReturn(this.jsonIterator);
    }

    @Test
    @DisplayName("Should Ingest Json and Persist to Database Successfully")
    void shouldIngestAndPersistToDatabaseSuccessfully(@Mock JsonModel jsonModel) throws Exception {
        given(this.jsonIterator.readArray()).willReturn(true).willReturn(false);
        given(this.jsonIterator.read(JsonModel.class)).willReturn(jsonModel);
        this.jsonIngester.ingestJson();
        then(jsonModelRepository).should(times(1)).save(jsonModel);
    }

}