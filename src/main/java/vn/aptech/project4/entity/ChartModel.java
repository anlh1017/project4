package vn.aptech.project4.entity;

public class ChartModel {

	String month;
	int total;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ChartModel(String month, int total) {
		super();
		this.month = month;
		this.total = total;
	}
	public ChartModel() {
		super();
	}
	
}
