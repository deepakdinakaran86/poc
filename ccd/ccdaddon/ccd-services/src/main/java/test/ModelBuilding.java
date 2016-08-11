package test;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pcs.ccd.bean.AttachEquipment;
import com.pcs.ccd.bean.Contact;
import com.pcs.ccd.bean.Equipment;
import com.pcs.ccd.bean.EquipmentContacts;
import com.pcs.ccd.bean.SearchEquipment;
import com.pcs.ccd.bean.Tenant;
import com.pcs.ccd.bean.TenantContact;
import com.pcs.ccd.bean.TenantEquipment;
import com.pcs.ccd.enums.ContactType;

public class ModelBuilding {
	public static void main(String[] args) {

		//tenant();
		//equipmentPersist();
		//equipmentContacts();
		//searchEquipment();
		//tenantContact();
		//attachEquipment();
		//tenantObj();
	}
	
	private static void attachEquipment(){
		AttachEquipment a = new AttachEquipment();
		a.setContactId("6217e4b0-6ab2-4f26-9876-32b183a54881");
		List<String> eIds = new ArrayList<String>();
		eIds.add("54c9e8dc-cb3f-4431-bf4c-4973498449f3");
		eIds.add("03f9b4c8-f8a0-4f65-b6b5-59bcec730265");
		a.setEquipmentIds(eIds);
		a.setTenantId("Drake And Scull");
		Gson gson = new Gson();
		System.out.println(gson.toJson(a));
	}
	
	private static void tenantObj(){
		Tenant t =new Tenant();
		t.setTenantName("Drake And Scull");
		t.setContactFName("Mr.Abi");
		t.setContactLName("L");
		Gson gson = new Gson();
		System.out.println(gson.toJson(t));
	}
	private static void tenantContact(){
		TenantContact t = new TenantContact();
		List<Contact> contacts = new ArrayList<Contact>();
		Contact c1 = new Contact();
		c1.setContactType(ContactType.MANAGER);
		c1.setContactNumber("0506745231");
		c1.setEmail("seenakr@pacificcontrols.net");
		c1.setName("Seena Kattil R");
		contacts.add(c1);
		Contact c2 = new Contact();
		c2.setContactType(ContactType.OPERATOR);
		c2.setContactNumber("0507878564");
		c2.setEmail("seenajyothish@gmail.com");
		c2.setName("Seena Jyothish");
		contacts.add(c2);
		t.setContacts(contacts);
		
		Tenant tenant = new Tenant();
		tenant.setTenantName("Drake And Scull");
		tenant.setContactFName("Mr.Abi");
		tenant.setContactLName("L");
		t.setTenant(tenant);
		Gson gson = new Gson();
		System.out.println(gson.toJson(t));
	}
	
	private static void searchEquipment(){
		SearchEquipment s = new SearchEquipment();
		Equipment e = new  Equipment();
		e.setAssetName("engine101");
		e.setEngineMake("PCS");
		e.setEngineModel("EDCP");
		s.setEquipment(e);
		Gson gson = new Gson();
		System.out.println(gson.toJson(s));
	}

	private static void tenant() {
		TenantContact t = new TenantContact();
		Tenant t1 = new Tenant();
		t1.setTenantName("Metito");
		t1.setContactFName("Jane");
		t1.setContactLName("May");
		t.setTenant(t1);

		List<Contact> contacts = new ArrayList<Contact>();
		Contact c = new Contact();
		c.setName("seena");
		c.setEmail("seena@pacific.com");
		c.setContactNumber("0508978890");
		contacts.add(c);
		
		Contact c1 = new Contact();
		c1.setName("mae");
		c1.setEmail("mae@pacific.com");
		c1.setContactNumber("0503673231");
		contacts.add(c1);

		t.setContacts(contacts);

		Gson gson = new Gson();
		System.out.println(gson.toJson(t));

	}


	private static void equipmentPersist() {
		TenantEquipment te = new TenantEquipment();
		List<Equipment> equipments = new ArrayList<Equipment>();
		Equipment e = new Equipment();
		e.setEngineMake("Cummins");
		e.setEngineModel("Q567");
		e.setAssetName("GJHG6575755AAABC");
		equipments.add(e);
		
		te.setEquipments(equipments);
		
		Tenant t = new Tenant();
		t.setTenantName("Oasis");
		t.setContactFName("Abi");
		t.setContactLName("N");
		te.setTenant(t);
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(te));

	}
	
	private static void equipmentContacts(){
		EquipmentContacts ec = new EquipmentContacts();
		
		ec.setEquipmentId("dc09fd43-d7d9-4d44-bd01-507a5e64482a");
		
		List<String> contacts = new ArrayList<String>();
		contacts.add("5d61eb91-3b29-4ce1-8523-1804fab373a7");
		contacts.add("3c6b243d-e411-4835-a95f-541c1e69ea0b");
		
		ec.setContactIds(contacts);
		
		ec.setTenantId("Drake And Scull");
		
		Gson gson = new Gson();
		System.out.println(gson.toJson(ec));
		
	}

}
