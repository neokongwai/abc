package watsons.wine.mycellars;

import java.util.ArrayList;

public class MyCellarItemDetails {
	private String id;
	private String wineName;
	private String wineStatus;
	private String winefavourite;
	private String wineImage;
	
	private String wineRegion;
    private String wineVintage;
    private String wineGrape;
    private String wineColour;
    private String wineBody;
    private String wineSweetness;
    private String wineSize;
    private String winePrice;
    private String wineQuantity;
    private String wineNote;
    private String wineTastingDate;
    private String wineOccasion;
    private String wineCreateDate;
    private String wineModifyDate;
    private String wineUpToCms;
    private String serverId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWineName() {
		return wineName;
	}
	public void setWineName(String wineName) {
		this.wineName = wineName;
	}
	public String getWineStatus() {
		return wineStatus;
	}
	public void setWineStatus(String wineStatus) {
		this.wineStatus = wineStatus;
	}
	public String getWinefavourite() {
		return winefavourite;
	}
	public void setWinefavourite(String winefavourite) {
		this.winefavourite = winefavourite;
	}
	public String getWineImage() {
		return wineImage;
	}
	public void setWineImage(String wineImage) {
		this.wineImage = wineImage;
	}
	public String getWineRegion() {
		return wineRegion;
	}
	public void setWineRegion(String wineRegion) {
		this.wineRegion = wineRegion;
	}
	public String getWineVintage() {
		return wineVintage;
	}
	public void setWineVintage(String wineVintage) {
		this.wineVintage = wineVintage;
	}
	public String getWineGrape() {
		return wineGrape;
	}
	public void setWineGrape(String wineGrape) {
		this.wineGrape = wineGrape;
	}
	public String getWineColour() {
		return wineColour;
	}
	public void setWineColour(String wineColour) {
		this.wineColour = wineColour;
	}
	public String getWineBody() {
		return wineBody;
	}
	public void setWineBody(String wineBody) {
		this.wineBody = wineBody;
	}
	public String getWineSweetness() {
		return wineSweetness;
	}
	public void setWineSweetness(String wineSweetness) {
		this.wineSweetness = wineSweetness;
	}
	public String getWineSize() {
		return wineSize;
	}
	public void setWineSize(String wineSize) {
		this.wineSize = wineSize;
	}
	public String getWinePrice() {
		return winePrice;
	}
	public void setWinePrice(String winePrice) {
		this.winePrice = winePrice;
	}
	public String getWineQuantity() {
		return wineQuantity;
	}
	public void setWineQuantity(String wineQuantity) {
		this.wineQuantity = wineQuantity;
	}
	public String getWineNote() {
		return wineNote;
	}
	public void setWineNote(String wineNote) {
		this.wineNote = wineNote;
	}
	public String getWineTastingDate() {
		return wineTastingDate;
	}
	public void setWineTastingDate(String wineTastingDate) {
		this.wineTastingDate = wineTastingDate;
	}
	public String getWineOccasion() {
		return wineOccasion;
	}
	public void setWineOccasion(String wineOccasion) {
		this.wineOccasion = wineOccasion;
	}
	public String getWineCreateDate() {
		return wineCreateDate;
	}
	public void setWineCreateDate(String wineCreateDate) {
		this.wineCreateDate = wineCreateDate;
	}
	public String getWineModifyDate() {
		return wineModifyDate;
	}
	public void setWineModifyDate(String wineModifyDate) {
		this.wineModifyDate = wineModifyDate;
	}
	public String getWineUpToCms() {
		return wineUpToCms;
	}
	public void setWineUpToCms(String wineUpToCms) {
		this.wineUpToCms = wineUpToCms;
	}

	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	
	public ArrayList<String> getAllToStringArray(){
		ArrayList<String> resultString = new ArrayList<String>();
		
		resultString.add(getId());//0
		resultString.add(getWineName());//1
		resultString.add(getWineStatus());//2
		resultString.add(getWinefavourite());//3
		resultString.add(getWineImage());//4
		resultString.add(getWineRegion());//5
		resultString.add(getWineVintage());//6
		resultString.add(getWineGrape());//7
		resultString.add(getWineColour());//8
		resultString.add(getWineBody());//9
		resultString.add(getWineSweetness());//10
		resultString.add(getWineSize());//11
		resultString.add(getWinePrice());//12
		resultString.add(getWineQuantity());//13
		resultString.add(getWineNote());//14
		resultString.add(getWineTastingDate());//15
		resultString.add(getWineOccasion());//16
		resultString.add(getWineCreateDate());//17
		resultString.add(getWineModifyDate());//18
		resultString.add(getWineUpToCms());//19
		resultString.add(getServerId());//20
		for(int i=0; i<resultString.size(); i++){
			if (resultString.get(i) == null){
				resultString.set(i, "-");
			}else if(resultString.get(i).isEmpty()){
				resultString.set(i, "-");
			}
		}
		
		return resultString;
		
	}
	
	
}