package inheritance;

import java.util.HashMap;
import java.util.Map;

public class LicenseManager {
	
	Map<LicenseType, String> licenseMap = new HashMap<LicenseType, String>();
	
	public LicenseManager(){
		LicenseType type = new LicenseType();
		type.setType("demo-locense-key");	// hard-coded for demo purposes
		licenseMap.put(type, "ABC-DEF-PQR-ZYX"); 
	}
	
	public Object getLicenseKey(LicenseType licenseType){
		return licenseMap.get(licenseType);
	}
	
	public void setLicenseKey(LicenseType licenseType, String licenseKey){
		licenseMap.put(licenseType, licenseKey);
	}
}
