package aaronrebak.gitlab.io.jsoniter.service;

import aaronrebak.gitlab.io.jsoniter.client.JsonClient;
import aaronrebak.gitlab.io.jsoniter.iterator.JsonModelArrayIterator;
import aaronrebak.gitlab.io.jsoniter.iterator.JsonModelIteratorFactory;
import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import aaronrebak.gitlab.io.jsoniter.repository.JsonModelRepository;
import feign.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.function.Consumer;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atMostOnce;

@ExtendWith(MockitoExtension.class)
@DisplayName("JsonIngesterUnitTest")
class JsonIngesterUnitTest {

    private JsonClient jsonClient;
    private JsonModelIteratorFactory jsonModelIteratorFactory;
    private JsonModelArrayIterator jsonModelArrayIterator;
    private JsonModelRepository jsonModelRepository;

    @InjectMocks
    private JsonIngester jsonIngester;

    JsonIngesterUnitTest(@Mock JsonClient jsonClient, @Mock JsonModelIteratorFactory jsonModelIteratorFactory, @Mock JsonModelArrayIterator jsonModelArrayIterator, @Mock JsonModelRepository jsonModelRepository) {
        this.jsonClient = jsonClient;
        this.jsonModelIteratorFactory = jsonModelIteratorFactory;
        this.jsonModelArrayIterator = jsonModelArrayIterator;
        this.jsonModelRepository = jsonModelRepository;
    }

    @BeforeEach
    void setupMocks(@Mock Response response, @Mock Response.Body body, @Mock InputStream inputStream) throws Exception {
        given(this.jsonClient.getJson()).willReturn(response);
        given(response.body()).willReturn(body);
        given(this.jsonModelIteratorFactory.create(body)).willReturn(this.jsonModelArrayIterator);
    }

    @Test
    @DisplayName("Should Ingest Json and Persist to Database Successfully")
    void shouldIngestAndPersistToDatabaseSuccessfully(@Mock JsonModel jsonModel) throws Exception {
        final ArgumentCaptor<Consumer> consumerArgumentCaptor = ArgumentCaptor.forClass(Consumer.class);
        this.jsonIngester.ingestJson();

        then(this.jsonModelArrayIterator).should().forEachRemaining(consumerArgumentCaptor.capture());
        consumerArgumentCaptor.getValue().accept(jsonModel);
        then(jsonModelRepository).should(atMostOnce()).save(jsonModel);
        then(this.jsonModelArrayIterator).should().close();
        then(this.jsonModelArrayIterator).shouldHaveNoMoreInteractions();
    }

}