package com.tensquare.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * 文章实体类
 */
//SpringDataElasticsearch的文档标识（自动扫描，映射索引库）
@Document(
        //映射的索引的名字
        indexName="tensquare_article"
        //类型，一般和实体类型一样
        ,type="article")
public class Article implements Serializable {
    //SpringDataElasticsearch的主键标识，自动映射es中的“_id”
    @Id
    private String id;//ID
    //字段配置
    @Field(
            //类型
            type = FieldType.Text,
            //是否生成索引,默认是true，如果是false，则保存的时候，不生成索引词条
            index=true,
            //存储文档的时候，生成索引词条所使用的分词器
            analyzer="ik_max_word",
            //进行字段分词搜索的时候，使用的分词器
            searchAnalyzer="ik_max_word"
    )
    private String title;//标题
    @Field(type = FieldType.Text,index= true,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String content;//文章正文
    //字段配置，如果不配置，默认字段相关属性映射都是自动猜的。
    private String state;//审核状态
 
//getter and setter ......    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}