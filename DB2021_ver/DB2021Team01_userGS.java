package DB2021Team01;

public class DB2021Team01_userGS {

	private String ID, PASSWORD, NAME, GENDER, EMAIL, BIRTHDAY;
	
	public String getID() {
		return ID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public String getNAME() {
		return NAME;
	}
	public String getGENDER() {
		return GENDER;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public String getBIRTHDAY() {
		return BIRTHDAY;
	}
	
	public void setID(String ID) {
		this.ID = ID;
	}
	public void setPASSWORD(String PASSWORD) {
		this.PASSWORD = PASSWORD;
	}
	public void setNAME(String NAME) {
		this.NAME = NAME;
	}
	public void setGENDER(String GENDER) {
		this.GENDER = GENDER;
	}
	public void setEMAIL(String EMAIL) {
		this.EMAIL = EMAIL;
	}
	public void setBIRTHDAY(String BIRTHDAY) {
		this.BIRTHDAY = BIRTHDAY;
	}
	@Override
	public String toString() {
		return "DB2021Team01userGS [ID=" + ID + ", PASSWORD=" + PASSWORD + ", NAME=" + NAME + ", GENDER=" + GENDER
				+ ", EMAIL=" + EMAIL + ", BIRTHDAY=" + BIRTHDAY + "]";
	}
	
	
}
