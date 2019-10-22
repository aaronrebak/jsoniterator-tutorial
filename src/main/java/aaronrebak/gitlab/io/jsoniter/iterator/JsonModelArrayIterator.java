package aaronrebak.gitlab.io.jsoniter.iterator;

import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import com.jsoniter.JsonIterator;
import com.jsoniter.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;

import java.io.IOException;

@EqualsAndHashCode
public class JsonModelArrayIterator implements CloseableIterator<JsonModel> {

    private JsonIterator jsonIterator;

    static CloseableIterator<JsonModel> aJsonModelIterator(final JsonIterator jsonIterator) {
        return new JsonModelArrayIterator(jsonIterator);
    }

    JsonModelArrayIterator(final JsonIterator jsonIterator) {
        this.jsonIterator = jsonIterator;
    }

    @Override
    public boolean hasNext() {
        try {
            return jsonIterator.readArray();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonModel next() {
        try {
            return this.jsonIterator.read(JsonModel.class);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws IOException {
        this.jsonIterator.close();
    }
}
