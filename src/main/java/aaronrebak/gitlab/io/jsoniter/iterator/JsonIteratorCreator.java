package aaronrebak.gitlab.io.jsoniter.iterator;

import com.jsoniter.JsonIterator;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class JsonIteratorCreator {

    public JsonIteratorCreator() {
    }

    public JsonIterator create(final InputStream inputStream, final int bufferSize) {
        return JsonIterator.parse(inputStream, bufferSize);
    }
}
