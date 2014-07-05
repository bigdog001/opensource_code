package com.facemail.server.mobile.bean.exm.pk;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LessonPK implements Serializable {
	private Date date_PK;
	private long time_Pk;

	@Column
	public Date getDate_PK() {
		return date_PK;
	}

	public void setDate_PK(Date date_PK) {
		this.date_PK = date_PK;
	}

	@Column
	public long getTime_Pk() {
		return time_Pk;
	}

	public void setTime_Pk(long time_Pk) {
		this.time_Pk = time_Pk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date_PK == null) ? 0 : date_PK.hashCode());
		result = prime * result + (int) (time_Pk ^ (time_Pk >>> 32));
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
		LessonPK other = (LessonPK) obj;
		if (date_PK == null) {
			if (other.date_PK != null)
				return false;
		} else if (!date_PK.equals(other.date_PK))
			return false;
		if (time_Pk != other.time_Pk)
			return false;
		return true;
	}

}
