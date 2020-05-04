package com.tensquare.base.po;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;


@Entity
@Table(name = "tb_label")
public class Label  implements Serializable {
    @Id
    private String id;//OID编号
    @Column(name = "labelname")
    private String labelname;//标签名称
    @Column(name = "state")
    private String state;//状态
    @Column(name = "count")
    private Long count;//使用数量
    @Column(name = "fans")
    private Long fans;//关注数
    @Column(name = "recommend")
    private String recommend;//是否推荐

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getLabelname() {
        return labelname;
    }
    public void setLabelname(String labelname) {
        this.labelname = labelname;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    public Long getFans() {
        return fans;
    }
    public void setFans(Long fans) {
        this.fans = fans;
    }
    public String getRecommend() {
        return recommend;
    }
    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

}
