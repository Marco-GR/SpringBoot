package com.example.myFirstSpringApp.util;

import com.example.myFirstSpringApp.model.Poll;
import com.example.myFirstSpringApp.model.User;
import com.example.myFirstSpringApp.payload.response.ChoiceResponse;
import com.example.myFirstSpringApp.payload.response.PollResponse;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static PollResponse mapPollToPollResponse(Poll poll, Map<Long,Long> choiceVotesMap, User creator, Long userVote){
    PollResponse pollResponse = new PollResponse();
    pollResponse.setPollId(poll.getPollId());
    pollResponse.setQuestion(poll.getQuestion());
    pollResponse.setCreationDateTime(poll.getCreatedAt());
    pollResponse.setExpirationDateTime(poll.getExpirationTime());
    pollResponse.setExpired(poll.getExpirationTime().isBefore(Instant.now()));

        List<ChoiceResponse> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponse choiceResponse = new ChoiceResponse();
            choiceResponse.setId(choice.getChoiceId());
            choiceResponse.setText(choice.getChoiceText());

            if (choiceVotesMap.containsKey(choice.getChoiceId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getChoiceId()));
            }else{
                choiceResponse.setVoteCount(0);
            }

            return choiceResponse;
        }).collect(Collectors.toList());

        if (userVote!=null){
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoiceResponses().stream().mapToLong(ChoiceResponse::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

    return pollResponse;
    }
}
