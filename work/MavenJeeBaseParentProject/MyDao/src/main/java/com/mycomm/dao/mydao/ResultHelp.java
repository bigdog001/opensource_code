/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mycomm.dao.mydao;

/**
 *
 * @author jw362j
 */
import java.util.List;
public class ResultHelp<T> {
	private List<T> resultlist;
	private long totalrecord;
	
	public List<T> getResultlist() {
		return resultlist;
	}
	public void setResultlist(List<T> resultlist) {
		this.resultlist = resultlist;
	}
	public long getTotalrecord() {
		return totalrecord;
	}
	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}

    @Override
    public String toString() {
        return "ResultHelp{" + "resultlist=" + resultlist + ", totalrecord=" + totalrecord + '}';
    }
        
        
}