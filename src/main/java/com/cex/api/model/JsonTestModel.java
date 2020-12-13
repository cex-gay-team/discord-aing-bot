package com.cex.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonTestModel {
    @JsonProperty
    private String testa;
    @JsonProperty
    private String testb;

    public String getTesta() {
        return testa;
    }

    public void setTesta(String testa) {
        this.testa = testa;
    }

    public String getTestb() {
        return testb;
    }

    public void setTestb(String testb) {
        this.testb = testb;
    }
}
