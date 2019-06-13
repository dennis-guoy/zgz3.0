package com.cwdt.plat.dataopt;

import java.io.Serializable;
import java.util.HashMap;

public class NotifyReceivers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5715934810504681790L;
	/**
	 * 选中的企业分组，<分组ID，分组名称>
	 */
	public HashMap<String, String> SelectedGroups=new HashMap<String, String>();
	/**
	 * 选中的企业 <企业ID，企业名称>
	 */
	public HashMap<String, String> SelectedCompanys=new HashMap<String, String>();
	/**
	 * 选中的内部人员
	 */
	public HashMap<String, String> Selectedneiburenyaun=new HashMap<String, String>();
	
	public HashMap<String, String> getSelectedGroups() {
		return SelectedGroups;
	}
	public void setSelectedGroups(HashMap<String, String> selectedGroups) {
		SelectedGroups = selectedGroups;
	}
	public HashMap<String, String> getSelectedCompanys() {
		return SelectedCompanys;
	}
	public void setSelectedCompanys(HashMap<String, String> selectedCompanys) {
		SelectedCompanys = selectedCompanys;
	}
	
	public HashMap<String, String> getSelectedneiburenyaun() {
		return Selectedneiburenyaun;
	}
	public void setSelectedneiburenyaun(HashMap<String, String> selectedGroups) {
		Selectedneiburenyaun = selectedGroups;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
