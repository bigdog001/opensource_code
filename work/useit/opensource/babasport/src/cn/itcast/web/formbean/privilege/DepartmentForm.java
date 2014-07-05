package cn.itcast.web.formbean.privilege;

import cn.itcast.web.formbean.BaseForm;

public class DepartmentForm extends BaseForm {
	private String name;
	private String departmentid;

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
