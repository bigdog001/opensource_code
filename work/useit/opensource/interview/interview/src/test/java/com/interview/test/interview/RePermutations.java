/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.interview.test.interview;

/**
 *
 * @author bigdog
 */
public class RePermutations {
    private String testData="";

    public RePermutations(String data) {
        this.testData = data;
    }
    
    public void permutations(){
        int len = testData.length();
        if(len <= 0) return;
        for(int i = 0;i<len;i++){
            for(int j = i+1;j<len;j++){
            System.out.println(builder(testData,i,j));
            }
        }
    }
    private String builder(String d,int one,int two){
        StringBuilder sb = new StringBuilder();
        if(d == null) return null;
        int s = d.length();
        if(one>=s||one<0)return null;
        if(two>=s||two<0)return null;
        if(one==two)return d;
         
        for(int x = 0;x<s;x++){
            if(x==one)
                sb.append(d.charAt(two));
            else if(x==two)
                sb.append(d.charAt(one));
            else
            sb.append(d.charAt(x));
        }
        return sb.toString();
        
    }
}
