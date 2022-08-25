package model;

public class AddCartDTO {

	// 멤버변수
	private String addSel1Name;
	private int addSel1Price;
	private String addSel2Name;
	private int addSel2Price;
	private String addSel3Name;
	private int addSel3Price;
	private String addSel4Name;
	private int addSel4Price;
	private String addSel5Name;
	private int addSel5Price;
	private String addSel6Name;
	private int addSel6Price;
	private int addSumPrice;


	public AddCartDTO() { }

	
	public AddCartDTO(String addSel1Name, int addSel1Price, String addSel2Name, int addSel2Price, String addSel3Name, int addSel3Price, String addSel4Name, int addSel4Price, String addSel5Name, int addSel5Price, String addSel6Name, int addSel6Price, int addSumPrice) {
		this.addSel1Name = addSel1Name;
		this.addSel1Price = addSel1Price;
		this.addSel2Name = addSel2Name;
		this.addSel2Price = addSel2Price;
		this.addSel3Name = addSel3Name;
		this.addSel3Price = addSel3Price;
		this.addSel4Name = addSel4Name;
		this.addSel4Price = addSel4Price;
		this.addSel5Name = addSel5Name;
		this.addSel5Price = addSel5Price;
		this.addSel6Name = addSel6Name;
		this.addSel6Price = addSel6Price;
		this.addSumPrice = addSumPrice;
	}



	public String getAddSel1Name() {
		return addSel1Name;
	}
	public void setAddSel1Name(String addSel1Name) {
		this.addSel1Name = addSel1Name;
	}
	public int getAddSel1Price() {
		return addSel1Price;
	}
	public void setAddSel1Price(int addSel1Price) {
		this.addSel1Price = addSel1Price;
	}
	public String getAddSel2Name() {
		return addSel2Name;
	}
	public void setAddSel2Name(String addSel2Name) {
		this.addSel2Name = addSel2Name;
	}
	public int getAddSel2Price() {
		return addSel2Price;
	}
	public void setAddSel2Price(int addSel2Price) {
		this.addSel2Price = addSel2Price;
	}
	public String getAddSel3Name() {
		return addSel3Name;
	}
	public void setAddSel3Name(String addSel3Name) {
		this.addSel3Name = addSel3Name;
	}
	public int getAddSel3Price() {
		return addSel3Price;
	}
	public void setAddSel3Price(int addSel3Price) {
		this.addSel3Price = addSel3Price;
	}
	public String getAddSel4Name() {
		return addSel4Name;
	}
	public void setAddSel4Name(String addSel4Name) {
		this.addSel4Name = addSel4Name;
	}
	public int getAddSel4Price() {
		return addSel4Price;
	}
	public void setAddSel4Price(int addSel4Price) {
		this.addSel4Price = addSel4Price;
	}
	public String getAddSel5Name() {
		return addSel5Name;
	}
	public void setAddSel5Name(String addSel5Name) {
		this.addSel5Name = addSel5Name;
	}
	public int getAddSel5Price() {
		return addSel5Price;
	}
	public void setAddSel5Price(int addSel5Price) {
		this.addSel5Price = addSel5Price;
	}
	public String getAddSel6Name() {
		return addSel6Name;
	}
	public void setAddSel6Name(String addSel6Name) {
		this.addSel6Name = addSel6Name;
	}
	public int getAddSel6Price() {
		return addSel6Price;
	}
	public void setAddSel6Price(int addSel6Price) {
		this.addSel6Price = addSel6Price;
	}
	public int getAddSumPrice() {
		return addSumPrice;
	}
	public void setAddSumPrice(int addSumPrice) {
		this.addSumPrice = addSumPrice;
	}
}
