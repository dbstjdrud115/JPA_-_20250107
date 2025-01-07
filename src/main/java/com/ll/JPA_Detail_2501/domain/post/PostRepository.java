package com.ll.JPA_Detail_2501.domain.post;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUsername(String userName);

    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Post> findWithShareLockById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Post> findWithWriteLockById(Long id);

}
