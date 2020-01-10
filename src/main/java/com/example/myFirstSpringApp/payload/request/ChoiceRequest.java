package com.example.myFirstSpringApp.payload.request;

import com.example.myFirstSpringApp.model.Poll;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChoiceRequest {

    @NotBlank
    @Size(max = 40)
    private String choiceText;

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }
}
