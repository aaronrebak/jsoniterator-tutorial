package aaronrebak.gitlab.io.jsoniter.controller;

import aaronrebak.gitlab.io.jsoniter.service.JsonIngester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/jsoniter")
public class JsonController {

    private JsonIngester jsonIngester;

    @Autowired
    public JsonController(JsonIngester jsonIngester) {
        this.jsonIngester = jsonIngester;
    }

    @GetMapping(value = "/stream-json")
    @ResponseBody public void streamJson() throws IOException {
       this.jsonIngester.ingestJson();
    }
}
