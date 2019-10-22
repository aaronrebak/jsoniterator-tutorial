package aaronrebak.gitlab.io.jsoniter.iterator;

import com.jsoniter.JsonIterator;
import feign.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static aaronrebak.gitlab.io.jsoniter.iterator.JsonModelArrayIterator.aJsonModelIterator;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JsonModelArrayIteratorFactoryUnitTest {

    private StreamingSupporter streamingSupporter;

    @InjectMocks
    private JsonModelIteratorFactory jsonModelIteratorFactory;

    JsonModelArrayIteratorFactoryUnitTest(@Mock StreamingSupporter streamingSupporter) {
        this.streamingSupporter = streamingSupporter;
    }

    @Test
    @DisplayName("Will create an instance of JsonModelArrayIterator")
    void willCreateAnInstanceOfJsonModelIterator(@Mock Response.Body body, @Mock JsonIterator jsonIterator) throws Exception {
        given(this.streamingSupporter.create(body)).willReturn(jsonIterator);
        then(this.jsonModelIteratorFactory.create(body)).isEqualTo(aJsonModelIterator(jsonIterator));
    }
}