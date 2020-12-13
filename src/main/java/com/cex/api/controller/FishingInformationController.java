package com.cex.api.controller;

import com.cex.api.model.JsonTestModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FishingInformationController {
    @GetMapping("/test")
    @ResponseBody
    public JsonTestModel test() {
        JsonTestModel testModel = new JsonTestModel();
        testModel.setTesta("abc");
        testModel.setTestb("cde");
        return testModel;
    }
}
