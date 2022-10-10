package gzb.tools;

import java.util.ArrayList;
import java.util.List;
/**
 * 分页工具类
 * */
public class ListPage<T> {
	private int count;
	private int page;
	private int limit;
	private List<T>list;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	} 
	@Override
	public String toString() {
		return toJson();
	}
	public String toJson() { 
		return toJson("ok");
	}
	public String toJson(String message){
		return Tools.json(1, message, list, null,page,limit,count,null);
	}
	public String toJson(Object state,Object message){
		return Tools.json(state, message, list, null,page,limit,count,null);
	}
	public void limitList(List<T> list,int page, int limit) {
		List<T> list_ret = new ArrayList<>();
		int k=(page-1) * limit;
		for (int i = k; i < list.size(); i++) {
			list_ret.add(list.get(i));
			if (list_ret.size() >= limit){
				break;
			}
		}
		setCount(list.size());
		setPage(page);
		setLimit(limit);
		setList(list_ret);
	}
}
