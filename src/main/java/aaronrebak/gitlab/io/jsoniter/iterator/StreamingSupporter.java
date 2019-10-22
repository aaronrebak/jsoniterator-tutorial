package aaronrebak.gitlab.io.jsoniter.iterator;

import com.jsoniter.JsonIterator;
import feign.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class StreamingSupporter {

    private final static int BUFFER_SIZE = 2048;

    StreamingSupporter() {
        JsonIterator.enableStreamingSupport();
    }

    public JsonIterator create(final Response.Body body) throws IOException {
        return JsonIterator.parse(body.asInputStream(), BUFFER_SIZE);
    }
}
