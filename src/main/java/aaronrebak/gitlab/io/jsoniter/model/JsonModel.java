package aaronrebak.gitlab.io.jsoniter.model;

import com.jsoniter.annotation.JsonCreator;
import com.jsoniter.annotation.JsonProperty;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Document
public class JsonModel {
    @Id
    private String id;

    public static JsonModel aJsonModel(final String id) {
        return new JsonModel(id);
    }

    @JsonCreator
    public JsonModel(@JsonProperty("_id") final String id) {
        this.id = id;
    }
}