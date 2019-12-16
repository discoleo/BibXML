package dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import data.AffiliationMap;
import data.AffiliationObj;
import data.AffiliationObjMap;
import data.AuthorObj;
import data.AuthorsDict;
import data.GetMapsINTF;
import data.JournalMap;
import sax.BIB_NODES;

public class Updater {
	
	protected final AffiliationObjMap mapAffiliations;
	
	protected final JournalMap mapJournals;
	protected final AuthorsDict mapAuthors;
	
	protected final Document document;
	
	// Affiliations
	protected static final String sXP_INSTITUTIONS = "/BibManagement/Affiliations/Institutions";
	protected static final String sXP_DEPARTMENTS  = "/BibManagement/Affiliations";
	protected static final String sXP_AUTHORS  = "/BibManagement/Authors";
	protected static final String sXP_AUTHORS_AFFIL  = sXP_AUTHORS + "/Author[@idAuthor=$Author]/Affiliations";
	
	public Updater(final Document document, final AuthorsDict mapAuthors,
			final AffiliationMap mapAffiliations, final JournalMap mapJournals) {
		this.document = document;
		this.mapAffiliations = new AffiliationObjMap(mapAffiliations);
		this.mapJournals = mapJournals;
		this.mapAuthors  = mapAuthors;
	}
	public Updater(final Document document, final GetMapsINTF maps) {
		this(document, maps.GetAuthorsDict(), maps.GetAffiliationMap(), maps.GetJournalMap());
	}
	
	// +++++++ MEMBER FUNCTIONS +++++++
	
	public String Fill(final String sBase, final String sVar, final String sVal) {
		return sBase.replaceAll(sVar, sVal);
	}
	
	public void Write(final File fileName) {
		final OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		final XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(fileName), format);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// ++++ Update Institutions
	
	public Integer AddInstitution(final String sInstitution, final String sType, final String sAdress) {
		final Integer idInstitution = mapAffiliations.GetOrSetInstitution(sInstitution);
		// get xml node
		final Element rootInstitutions = (Element) document.selectSingleNode(sXP_INSTITUTIONS);
		if(rootInstitutions == null) {
			return null;
		}

		// add xml node
		final Element nodeInstitution = DocumentHelper.createElement(BIB_NODES.INSTITUTE.GetNode());
		nodeInstitution.addAttribute("idInst", idInstitution.toString());
		//
		final Element nodeName = DocumentHelper.createElement(BIB_NODES.INSTITUTE_NAME.GetNode());
		nodeName.addText(sInstitution);
		nodeInstitution.add(nodeName);
		//
		final Element nodeType = DocumentHelper.createElement(BIB_NODES.INSTITUTE_TYPE.GetNode());
		nodeType.addText(sType);
		nodeInstitution.add(nodeType);
		//
		final Element nodeAddress = DocumentHelper.createElement(BIB_NODES.INSTITUTE_ADRESS.GetNode());
		nodeAddress.addText(sAdress);
		nodeInstitution.add(nodeAddress);
		//
		rootInstitutions.add(nodeInstitution);
		
		return idInstitution;
	}
	
	// ++++ Update Affiliations
	
	public void AddAffiliation(final AffiliationObj affiliation) {
		final Integer idTmpInstitution = mapAffiliations.GetInstitution(affiliation.sInstitution);
		final Integer idInstitution;
		if(idTmpInstitution == null) {
			idInstitution = this.AddInstitution(affiliation.sInstitution, affiliation.sType, affiliation.sAdress);
		} else {
			idInstitution = idTmpInstitution;
		}
		
		// Affiliation
		final Integer idAffiliation = mapAffiliations.Put(affiliation);
		
		// get xml node
		final Element rootDep = (Element) document.selectSingleNode(sXP_DEPARTMENTS);
		if(rootDep == null) {
			return;
		}

		// add xml node
		final Element nodeAffil = DocumentHelper.createElement(BIB_NODES.AFFILIATION.GetNode());
		nodeAffil.addAttribute("idAffil", idAffiliation.toString());
		//
		final Element nodeInstit = DocumentHelper.createElement(BIB_NODES.INSTITUTE.GetNode());
		nodeInstit.addAttribute("idInstRef", idInstitution.toString());
		nodeAffil.add(nodeInstit);
		//
		final Element nodeDepartment = DocumentHelper.createElement(BIB_NODES.INSTITUTE_DEPT.GetNode());
		nodeDepartment.addText(affiliation.sDepartment);
		nodeAffil.add(nodeDepartment);
		//
		rootDep.add(nodeAffil);
	}
	
	// ++++ Update Authors
	
	public void AddAffil(final AuthorObj author, final AffiliationObj affiliation) {
		if(affiliation.idAffil == null) {
			this.AddAffiliation(affiliation);
		} else {
			// TODO: update existing Affiliation
		}
		// TODO: update Author Info
		// get xml node
		final String sXP_AUTHOR_AFFIL = this.Fill(Updater.sXP_AUTHORS_AFFIL, "\\$Author", author.idAuthor.toString());
		final Element rootAfill = (Element) document.selectSingleNode(sXP_AUTHOR_AFFIL);
		if(rootAfill == null) {
			return;
		}
		// add xml node
		final Element nodeAffil = DocumentHelper.createElement(BIB_NODES.AUTHOR_AFFIL.GetNode());
		nodeAffil.addAttribute("idAffilRef", affiliation.idAffil.toString());
		//
		rootAfill.add(nodeAffil);
	}
}
