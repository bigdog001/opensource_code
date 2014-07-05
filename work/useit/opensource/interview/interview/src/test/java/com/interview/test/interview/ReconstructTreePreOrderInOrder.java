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
public class ReconstructTreePreOrderInOrder  {/** 
     * Build Binary Tree from PreOrder and InOrder 
     *  _______7______ 
       /              \ 
    __10__          ___2 
   /      \        / 
   4       3      _8 
            \    / 
             1  11 
              
     */         
  
    public Node buildTreePreOrderInOrder(int[] preOrder,int begin1,int end1,int[] inOrder,int begin2,int end2){  
        if(begin1>end1||begin2>end2){  
            return null;  
        }  
        int rootData=preOrder[begin1];  
        Node head=new Node(rootData);  
        int divider=findIndexInArray(inOrder,rootData,begin2,end2);  
        int offSet=divider-begin2-1;  
        Node left=buildTreePreOrderInOrder(preOrder,begin1+1,begin1+1+offSet,inOrder,begin2,begin2+offSet);  
        Node right=buildTreePreOrderInOrder(preOrder,begin1+offSet+2,end1,inOrder,divider+1,end2);  
        head.left=left;  
        head.right=right;  
        return head;  
    }  
      
    public int findIndexInArray(int[] a,int x,int begin,int end){  
        for(int i=begin;i<=end;i++){  
            if(a[i]==x)return i;  
        }  
        return -1;  
    }  
    public void preOrder(Node n){  
        if(n!=null){  
            System.out.print(n.val+",");  
            preOrder(n.left);  
            preOrder(n.right);  
        }  
    }  
    public void inOrder(Node n){  
        if(n!=null){  
            inOrder(n.left);  
            System.out.print(n.val+",");  
            inOrder(n.right);  
        }  
    }  
      
    class Node{  
        Node left;  
        Node right;  
        int val;  
  
    public Node(int val){  
        this.val=val;  
    }  
        public Node getLeft(){  
            return left;  
        }  
  
    public Node getRight(){  
            return right;  
        }  
  
    public int getVal(){  
            return val;  
        }  
  
  
    }} 
