package aaronrebak.gitlab.io.jsoniter.service;

import aaronrebak.gitlab.io.jsoniter.client.JsonClient;
import aaronrebak.gitlab.io.jsoniter.iterator.CloseableIterator;
import aaronrebak.gitlab.io.jsoniter.iterator.JsonModelIteratorFactory;
import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import aaronrebak.gitlab.io.jsoniter.repository.JsonModelRepository;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JsonIngester {

    private JsonClient jsonClient;
    private JsonModelIteratorFactory jsonModelIteratorFactory;
    private JsonModelRepository jsonModelRepository;

    @Autowired
    public JsonIngester(final JsonClient jsonClient, final JsonModelIteratorFactory jsonModelIteratorFactory, JsonModelRepository jsonModelRepository) {
        this.jsonClient = jsonClient;
        this.jsonModelIteratorFactory = jsonModelIteratorFactory;
        this.jsonModelRepository = jsonModelRepository;
    }

    public void ingestJson() throws IOException {
        try (final Response response = this.jsonClient.getJson();
             final Response.Body body = response.body();
             final CloseableIterator<JsonModel> closeableIterator = this.jsonModelIteratorFactory.create(body)) {
            closeableIterator.forEachRemaining(this.jsonModelRepository::save);
        }
    }
}
