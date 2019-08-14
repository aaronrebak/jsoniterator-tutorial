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
    private String _id;

    @JsonCreator
    public JsonModel(@JsonProperty final String _id) {
        this._id = _id;
    }
}