package availty_csv_filter;
//Class outlining the "Client Object" which stores every line processed from csv files.
public class Client {

	private String userID; 
	private String fullName;
	private int version;
	private String insuranceCompany;

	public Client(String userID, String fullName, int version, String insuranceCompany) 
	{
		this.userID = userID; 
		this.fullName = fullName;
		this.version = version;
		this.insuranceCompany = insuranceCompany;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getInsuranceCompany() {
		return insuranceCompany;
	}

	public void setInsuranceCompany(String insuranceCompany) {
		this.insuranceCompany = insuranceCompany;
	}

}
