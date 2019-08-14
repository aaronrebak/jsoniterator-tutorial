package aaronrebak.gitlab.io.jsoniter.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.BDDAssertions.then;

@DisplayName("JsonModelUnitTest")
class JsonModelUnitTest {
    private static final String JSON_ID = "jsonId";

    private JsonModel jsonModel;

    @BeforeEach
    void beforeEach() {
        this.jsonModel = new JsonModel(JSON_ID);
    }

    @Test
    @DisplayName("That A JsonModel is contructed correclty")
    void jsonModelIsConstructedCorrectly() {
       then(jsonModel.get_id()).isEqualTo(JSON_ID);
    }

}