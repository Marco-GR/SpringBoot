package com.example.myFirstSpringApp.repository;

import com.example.myFirstSpringApp.model.Poll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {

    Optional<Poll> findById(Long pollId);

    Page<Poll> findByCreatedBy(Long UserId, Pageable pageable);

    long countByCreatedBy(Long userId);

    List<Poll> findByPollIdIn(List<Long> pollIds);

    List<Poll> findByPollIdIn(List<Long> pollIds, Sort sort);
}
