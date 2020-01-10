package com.example.myFirstSpringApp.payload.request;

import javax.validation.constraints.NotNull;

public class VoteRequest {
    @NotNull
    private Long choiceId;

    public void setChoiceId(Long choiceId){
        this.choiceId=choiceId;
    }

    public Long getChoiceId(){
        return choiceId;
    }
}
