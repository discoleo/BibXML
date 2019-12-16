package data;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class AffiliationObjMap extends TreeMap<AffiliationObj, Integer> {
	
	protected final AffiliationMap mapAffiliations;
	protected final InstitutionObjMap mapInstitutionObj;
	
	// next available ID
	protected int iLastID = 0;

	public AffiliationObjMap(final AffiliationMap mapAffiliations) {
		super(new ComparatorAffil());
		
		this.mapAffiliations = mapAffiliations;
		this.mapInstitutionObj = new InstitutionObjMap(mapAffiliations);
		
		int iMaxID = 0;
		for(final Map.Entry<Integer, AffiliationObj> entry : mapAffiliations.entrySet()) {
			this.put(entry.getValue(), entry.getKey());
			if(iMaxID < entry.getKey()) {
				iMaxID = entry.getKey();
			}
			// System.out.println("Map: " + entry.getValue().sInstitution);
		}
		iLastID = iMaxID + 1;
	}
	
	// +++++++ MEMBER FUNCTIONS ++++++

	public int NewID() {
		return iLastID;
	}
	protected void NextID() {
		iLastID ++;
	}
	public Integer GetInstitution(final String sInstitution) {
		final Integer idInstitution = mapInstitutionObj.get(sInstitution);
		
		return idInstitution;
	}
	public Integer GetOrSetInstitution(final String sInstitution) {
		final Integer idInstitution = mapInstitutionObj.AddInstitution(sInstitution);
		
		return idInstitution;
	}
	
	public Integer Put(final AffiliationObj affiliation) {
		final Integer idAffiliation;
		if(affiliation.idAffil == null) {
			idAffiliation = iLastID;
			this.NextID();
			affiliation.idAffil = idAffiliation;
		} else {
			idAffiliation = affiliation.idAffil;
		}
		this.put(affiliation, idAffiliation);
		return idAffiliation;
	}
	
	// ++++ Helper classes
	
	public static class ComparatorAffil implements Comparator<AffiliationObj> {

		@Override
		public int compare(final AffiliationObj affil1, final AffiliationObj affil2) {
			if(affil1 == null) {
				if(affil2 == null) {
					return 0;
				}
				return 1;
			}
			
			// TODO: check for null
			final int cmpInstitution = affil1.sInstitution.compareToIgnoreCase(affil2.sInstitution);
			if(cmpInstitution != 0) {
				return cmpInstitution;
			}
			
			final int cmpDepartment = affil1.sDepartment.compareToIgnoreCase(affil2.sDepartment);
			return cmpDepartment;
		}
		
	}
}
