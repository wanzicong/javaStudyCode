package com.tensquare.qa.dao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;
/**
 * 数据访问接口
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    /*最新的问题列表查询*/
    @Query(value = "select * from tb_problem ,tb_pl where id = problemid and labelid=? order by replytime desc ", nativeQuery = true)
    Page<Problem> newlist(String labelid, Pageable pageable);

    /*最火的问题的查询*/
    @Query(value = "select * from tb_problem ,tb_pl where id = problemid and labelid=? order by reply desc ", nativeQuery = true)
    Page<Problem> hotlist(String labelid, Pageable pageable);

    /*等待回答的问题*/
    @Query(value = "select * from tb_problem ,tb_pl where id = problemid and labelid=? and  reply = 0 order by createtime desc ", nativeQuery = true)
    Page<Problem> waitlist(String labelid, Pageable pageable);

}
