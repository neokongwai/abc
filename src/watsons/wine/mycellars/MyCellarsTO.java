package watsons.wine.mycellars;

public class MyCellarsTO {
	
	int id;
	String wineName;
	String region;
	String vintage;
	String grape;
	String colour;
	String body;
	String sweetness;
	String size;
	double price;
	int quantity;
	String note;
	int rating;
	String tasting_date;
	String occasion;
	String instock;
	String wineImage;
	String up_to_cms;
	String serverId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getWineName() {
		return wineName;
	}
	public void setWineName(String wineName) {
		this.wineName = wineName;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getVintage() {
		return vintage;
	}
	public void setVintage(String vintage) {
		this.vintage = vintage;
	}
	public String getGrape() {
		return grape;
	}
	public void setGrape(String grape) {
		this.grape = grape;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSweetness() {
		return sweetness;
	}
	public void setSweetness(String sweetness) {
		this.sweetness = sweetness;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getTasting_date() {
		return tasting_date;
	}
	public void setTasting_date(String tasting_date) {
		this.tasting_date = tasting_date;
	}
	public String getOccasion() {
		return occasion;
	}
	public void setOccasion(String occasion) {
		this.occasion = occasion;
	}
	public String getInstock() {
		return instock;
	}
	public void setInstock(String instock) {
		this.instock = instock;
	}
	public String getWineImage() {
		return wineImage;
	}
	public void setWineImage(String wineImage) {
		this.wineImage = wineImage;
	}
	public String getUp_to_cms() {
		return up_to_cms;
	}
	public void setUp_to_cms(String up_to_cms) {
		this.up_to_cms = up_to_cms;
	}
	
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public void initMyCellarsTO(){
		this.wineName = "-";
		this.region = "-";
		this.vintage = "-";
		this.grape = "-";
		this.colour = "-";
		this.body = "-";
		this.sweetness = "-";
		this.size = "-";
		this.price = 0.00;
		this.quantity = 0;
		this.note = "-";
		this.rating = 0;
		this.tasting_date = "-";
		this.occasion = "-";
		this.instock = "-";
		this.wineImage = "-";
		this.up_to_cms = "-";
		this.serverId = "-1";
	}
	
}