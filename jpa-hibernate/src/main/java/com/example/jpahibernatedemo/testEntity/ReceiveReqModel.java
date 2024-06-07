package com.example.jpahibernatedemo.testEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Data
@ToString
public class ReceiveReqModel<T> {
    @JsonProperty("puuId")
    private String puuId;
    @JsonProperty("dataInfo")
    private List<T> dataInfo = new ArrayList<>();

}
