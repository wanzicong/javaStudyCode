package com.tensquare.article.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 */
public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    /*文章的审核*/
    @Modifying
    @Query(value = "update tb_article set state = 1 where id = ?", nativeQuery = true)
     void updateState(String id);

    /*文章的点赞*/
    @Modifying
    @Query(value = "update tb_article set thumbup = thumbup+ 1 where id = ?", nativeQuery = true)
     void updateThumbup(String id);
}
