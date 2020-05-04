package com.tensquare.base.dao;

import com.tensquare.base.po.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 标签管理的dao接口
 */
public interface LabelDao extends JpaRepository<Label,String> ,JpaSpecificationExecutor<Label> {
}
