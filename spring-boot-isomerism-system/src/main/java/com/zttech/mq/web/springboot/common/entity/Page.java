package com.zttech.mq.web.springboot.common.entity;
/**
 * @author 061308
 *
 */
public class Page {
	private int pageNum = 0;
	
	private int pageSize = 5;
	
	private int total;
	
	private String selKeyProperty = "id";
	
	private String selKey = "";
	
	private enum Sort{
		
		asc,
		
		desc;	
	}
	private Sort sort = Sort.asc;
	
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public String getSelKey() {
		return selKey;
	}
	
	public void setSelKey(String selKey) {
		this.selKey = selKey;
	}
	
	public Sort getSort() {
		return sort;
	}
	
	public void setSort(Sort sort) {
		this.sort = sort;
	}
	
	public String getSelKeyProperty() {
		return selKeyProperty;
	}
	
	public void setSelKeyProperty(String selKeyProperty) {
		this.selKeyProperty = selKeyProperty;
	}	
	
}
