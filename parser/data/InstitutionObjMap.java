package data;

import java.util.Map;
import java.util.TreeMap;

public class InstitutionObjMap extends TreeMap<String, Integer> {
	// Dictionary: Institution => ID Institution;
	
	// next available ID
	private int iLastID = 0;
	
	// ++++++++ CONSTRUCTOR +++++++++
	
	public InstitutionObjMap(final AffiliationMap mapAffiliations) {
		int iMaxID = 0;
		for(final Map.Entry<Integer, String> entry : mapAffiliations.GetInstitutions().entrySet()) {
			this.put(entry.getValue(), entry.getKey());
			if(iMaxID < entry.getKey()) {
				iMaxID = entry.getKey();
			}
			// System.out.println("Map: " + entry.getValue());
		}
		iLastID = iMaxID + 1;
	}
	
	// +++++++++ MEMBER FUNCTIONS +++++++++
	
	public int NewID() {
		return iLastID;
	}
	protected void NextID() {
		iLastID ++;
	}
	
	// Add a new Institution without ID
	public Integer AddInstitution(final String sInstitution) {
		final Integer idInstitution = this.get(sInstitution);
		if(idInstitution != null) {
			return idInstitution;
		}
		
		final Integer idNewInstitution = iLastID;
		this.NextID();
		this.put(sInstitution, idNewInstitution);
		return idNewInstitution;
	}
}
