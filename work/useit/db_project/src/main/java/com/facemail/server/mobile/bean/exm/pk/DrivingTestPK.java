package com.facemail.server.mobile.bean.exm.pk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DrivingTestPK implements Serializable {
	private int clientNO_PK;
	private Date date_PK;

	@Column
	public int getClientNO_PK() {
		return clientNO_PK;
	}

	public void setClientNO_PK(int clientNO_PK) {
		this.clientNO_PK = clientNO_PK;
	}

	@Column
	public Date getDate_PK() {
		return date_PK;
	}

	public void setDate_PK(Date date_PK) {
		this.date_PK = date_PK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + clientNO_PK;
		result = prime * result + ((date_PK == null) ? 0 : date_PK.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DrivingTestPK other = (DrivingTestPK) obj;
		if (clientNO_PK != other.clientNO_PK)
			return false;
		if (date_PK == null) {
			if (other.date_PK != null)
				return false;
		} else if (!date_PK.equals(other.date_PK))
			return false;
		return true;
	}

}
