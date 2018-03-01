package com.winway.android.edcollection.project.entity;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * EcLinePart实体类
 * 
 * @author zgq
 * @since 2017/2/19 9:41:33
 */
@Table(name = "ec_line_part")
public class EcLinePartEntity implements Serializable{

        @Id
        @Column(column = "EC_LINE_PART_ID")
        private String ecLinePartId;//电缆段ID
        @Column(column = "EC_LINE_ID")
        private String ecLineId;//电缆ID
        @Column(column = "SECTION")
        private Double section;//SECTION
        @Column(column = "TINDEX1")
        private Double tindex1;//顺序
        @Column(column = "TINDEX2")
        private Double tindex2;//TINDEX2
        @Column(column = "ORGID")
        private String orgid;//ORGID
    
        
        public String getEcLinePartId() {
		    return ecLinePartId;
	    }
        
	    public void setEcLinePartId(String ecLinePartId) {
		    this.ecLinePartId = ecLinePartId;
	    }
        
        public String getEcLineId() {
		    return ecLineId;
	    }
        
	    public void setEcLineId(String ecLineId) {
		    this.ecLineId = ecLineId;
	    }
        
        public Double getSection() {
		    return section;
	    }
        
	    public void setSection(Double section) {
		    this.section = section;
	    }
        
        public Double getTindex1() {
		    return tindex1;
	    }
        
	    public void setTindex1(Double tindex1) {
		    this.tindex1 = tindex1;
	    }
        
        public Double getTindex2() {
		    return tindex2;
	    }
        
	    public void setTindex2(Double tindex2) {
		    this.tindex2 = tindex2;
	    }
        
        public String getOrgid() {
		    return orgid;
	    }
        
	    public void setOrgid(String orgid) {
		    this.orgid = orgid;
	    }
    
}
 
