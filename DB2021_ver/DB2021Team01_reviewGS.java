package DB2021Team01;

public class DB2021Team01_reviewGS {
private String ID, PERFORMANCE, REVIEW;
	
	public String getID() {
		return ID;
	}
	public String getPERFORMANCE() {
		return PERFORMANCE;
	}
	public String getREVIEW() {
		return REVIEW;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	public void setPERFORMANCE(String PERFORMANCE) {
		this.PERFORMANCE = PERFORMANCE;
	}
	public void setREVIEW(String REVIEW) {
		this.REVIEW = REVIEW;
	}
	@Override
	public String toString() {
		return "DB2021Team01_reviewGS [ID=" + ID + ", PERFORMANCE=" + PERFORMANCE + ", REVIEW=" + REVIEW + "]";
	}
	
}
