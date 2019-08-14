package aaronrebak.gitlab.io.jsoniter.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "jsonClient", url = "${json-client.scheme}://${json-client.host}:${json-client.port}")
@RequestMapping("/api/json")
public interface JsonClient {

    @GetMapping(value = "/get/41OOrUK5L")
    Response getJson();
}
