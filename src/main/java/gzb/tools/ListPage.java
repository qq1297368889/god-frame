package gzb.tools;

import java.util.ArrayList;
import java.util.List;

public class ListPage { 
	private int count;
	private int page;
	private int limit;
	private List<?>list;
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
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	} 
	@Override
	public String toString() {
		return toJson();
	}
	public String toJson() { 
		return toJson("ok");
	}
	public String toJson(String msg){
		String json="{"
				+ "\"state\":\"1\","
				+ "\"code\":\"0\","
				+ "\"message\":\""+msg+"\","
				+ "\"page\":\""+page+"\","
				+ "\"limit\":\""+limit+"\","
				+ "\"count\":\""+count+"\","
				+ "\"data\":"+list.toString()+""
				+ "}";
		json=json.replaceAll("\r\n", "\\\\r\\\\n")
				.replaceAll("\r", "\\\\r")
				.replaceAll("\n", "\\\\n")
				.replaceAll("\t", "\\\\t");
		return json;
	}
	public void limitList(List<?> list,int page, int limit) {
		List<Object> list_ret = new ArrayList<>();
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
