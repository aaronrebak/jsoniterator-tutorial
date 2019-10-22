package aaronrebak.gitlab.io.jsoniter.iterator;

import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import com.jsoniter.JsonIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class JsonModelArrayIteratorUnitTest {

    private JsonIterator jsonIterator;

    @InjectMocks
    private JsonModelArrayIterator jsonModelArrayIterator;

    JsonModelArrayIteratorUnitTest(@Mock JsonIterator jsonIterator) {
        this.jsonIterator = jsonIterator;
    }


    @DisplayName("Will notify if able to iterate through array")
    @ParameterizedTest(name = "when readArray is: {0}")
    @CsvSource({"true", "false"})
    void willNotifyIfAbleToIterateThroughArray(final boolean value) throws Exception {
        given(this.jsonIterator.readArray()).willReturn(value);
        assertThat(this.jsonModelArrayIterator.hasNext()).isEqualTo(value);
    }

    @Test
    @DisplayName("Will return a JsonModel while iterating")
    void willReturnJsonModelWhileIterating(@Mock JsonModel jsonModel) throws Exception {
        given(this.jsonIterator.read(JsonModel.class)).willReturn(jsonModel);
        assertThat(this.jsonModelArrayIterator.next()).isEqualTo(jsonModel);
    }

    @Test
    @DisplayName("Will close the iterator on close")
    void willCloseTheIteratorOnClose() throws Exception {
        this.jsonModelArrayIterator.close();
        then(this.jsonIterator).should().close();
    }
}