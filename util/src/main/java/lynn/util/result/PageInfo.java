package lynn.util.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Map;

/**
 * @description：分页实体类,com.baomidou.mybatisplus.plugins.Page 已经够用, 但是某些框架需要返回的字段名需要确定
 * 采用的是Jackson用@JsonIgnore来忽略某字段(com.fasterxml.jackson.annotation.JsonIgnore)  
 * 采用的是fastjson用@JSONField来忽略某字段(com.alibaba.fastjson.annotation.JSONField)
 * 采用的是gson用@Expose来忽略某字段(com.google.gson.annotations.Expose)
 *
 */
public class PageInfo {
	
    private final static int PAGESIZE = 10; //默认显示的记录数 
    public final static String ORDERBY_ASC = "asc"; //默认显示的记录数 

    private int total; // 总记录 
    private List<?> rows; //显示的记录  

    private int from;
    private int pages; //总页数
    @JsonIgnore
    private int size;
    
    private int pageIndex; // 当前页 
    
    private int pageSize; // 每页显示的记录数 
    @JsonIgnore
    private Map<String, Object> condition; //查询条件

    @JsonIgnore
    private String sort = "seq";// 排序字段
    @JsonIgnore
    private String order = ORDERBY_ASC;// asc，desc mybatis Order 关键字

    public PageInfo() {}

    //构造方法
    public PageInfo(int pageIndex, int pageSize) {
        //计算当前页  
        if (pageIndex < 0) {
            this.pageIndex = 1;
        } else {
            //当前页
            this.pageIndex = pageIndex;
        }
        //记录每页显示的记录数  
        if (pageSize < 0) {
            this.pageSize = PAGESIZE;
        } else {
            this.pageSize = pageSize;
        }
        //计算开始的记录和结束的记录  
        this.from = (this.pageIndex - 1) * this.pageSize;
        this.size = this.pageSize;
    }

    // 构造方法
    public PageInfo(int pageIndex, int pageSize, String sort, String order) {
        this(pageIndex, pageSize) ;
        // 排序字段，正序还是反序
        this.sort = sort;
        this.order = order;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

//    public List getRows() {
//        return rows;
//    }
//
//    public void setRows(List rows) {
//        this.rows = rows;
//    }

    public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getCondition() {
        return condition;
    }

    public void setCondition(Map<String, Object> condition) {
        this.condition = condition;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
