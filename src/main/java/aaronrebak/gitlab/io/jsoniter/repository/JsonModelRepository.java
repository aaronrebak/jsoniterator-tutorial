package aaronrebak.gitlab.io.jsoniter.repository;

import aaronrebak.gitlab.io.jsoniter.model.JsonModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JsonModelRepository extends CrudRepository<JsonModel, String> {

}
