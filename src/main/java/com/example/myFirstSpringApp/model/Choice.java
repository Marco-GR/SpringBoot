package com.example.myFirstSpringApp.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "choices")
public class Choice  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long choiceId;

    @Column(name = "choice_text", length = 40, nullable = false)
    private String choiceText;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name ="poll_id", nullable = false)
     private Poll poll;

    public Choice() {
    }

    public Choice(String choiceText) {
        this.choiceText = choiceText;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Choice choice = (Choice) o;
        return Objects.equals(choiceId, choice.choiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(choiceId);
    }
}
