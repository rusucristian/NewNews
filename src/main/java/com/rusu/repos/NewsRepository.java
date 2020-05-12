package com.rusu.repos;

import com.rusu.domain.News;
import com.rusu.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByTag(String tag);

    List<News> findByAuthor(User user);
}
