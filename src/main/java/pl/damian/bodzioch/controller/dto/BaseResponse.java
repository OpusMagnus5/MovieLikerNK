package pl.damian.bodzioch.controller.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class BaseResponse implements Serializable {

    private String error;
}
