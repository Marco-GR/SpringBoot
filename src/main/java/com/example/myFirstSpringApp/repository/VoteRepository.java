package com.example.myFirstSpringApp.repository;

import com.example.myFirstSpringApp.model.ChoiceVoteCount;
import com.example.myFirstSpringApp.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query("SELECT NEW com.example.myFirstSpringApp.model.ChoiceVoteCount(v.choice.choiceId, count(v.poll.pollId)) FROM Vote v WHERE v.poll.pollId in :pollIds GROUP BY v.choice.choiceId")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    @Query("SELECT NEW com.example.myFirstSpringApp.model.ChoiceVoteCount(v.choice.choiceId, count(v.poll.pollId)) FROM Vote v WHERE v.poll.pollId = :pollId GROUP BY v.choice.choiceId")
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

    @Query("SELECT v FROM Vote v WHERE v.user.userId = :userId AND v.user.userId IN :pollIds")
    List<Vote> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("SELECT v FROM Vote v WHERE v.user.userId = :userId AND v.poll.pollId = :pollId")
    Vote findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("SELECT count(v.user.userId) FROM Vote v WHERE v.user.userId = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.poll.pollId FROM Vote v WHERE v.user.userId = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
