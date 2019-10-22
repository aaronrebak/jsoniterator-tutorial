package aaronrebak.gitlab.io.jsoniter.iterator;

import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import com.jsoniter.JsonIterator;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsonModelIteratorFactory {

    private StreamingSupporter streamingSupporter;

    @Autowired
    public JsonModelIteratorFactory(final StreamingSupporter streamingSupporter) {
        this.streamingSupporter = streamingSupporter;
    }

    public CloseableIterator<JsonModel> create(final Response.Body body) throws IOException {
        final JsonIterator jsonIterator = this.streamingSupporter.create(body);
        return new JsonModelArrayIterator(jsonIterator);
    }

}
