package aaronrebak.gitlab.io.jsoniter.service;

import aaronrebak.gitlab.io.jsoniter.client.JsonClient;
import aaronrebak.gitlab.io.jsoniter.iterator.JsonIteratorCreator;
import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import aaronrebak.gitlab.io.jsoniter.repository.JsonModelRepository;
import com.jsoniter.JsonIterator;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class JsonIngester {

    private static final int BUFFER_SIZE = 1024;

    private JsonClient jsonClient;
    private JsonIteratorCreator jsonIteratorCreator;
    private JsonModelRepository jsonModelRepository;

    @Autowired
    public JsonIngester(final JsonClient jsonClient, final JsonIteratorCreator jsonIteratorCreator, JsonModelRepository jsonModelRepository) {
        this.jsonClient = jsonClient;
        this.jsonIteratorCreator = jsonIteratorCreator;
        this.jsonModelRepository = jsonModelRepository;
    }

    public void ingestJson() throws IOException {
        try (final Response response = this.jsonClient.getJson();
             final Response.Body body = response.body();
             final InputStream inputStream = body.asInputStream();
             final JsonIterator jsonIterator = this.jsonIteratorCreator.create(inputStream, BUFFER_SIZE)) {

            while (jsonIterator.readArray()) {
                final JsonModel jsonModel = jsonIterator.read(JsonModel.class);
                this.jsonModelRepository.save(jsonModel);
            }
        }
    }
}
