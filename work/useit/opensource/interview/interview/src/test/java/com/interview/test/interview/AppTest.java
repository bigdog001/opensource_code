package com.interview.test.interview;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest
{
     @BeforeClass
    public static void set(){
    
    }
    
     @Test
    public void permutations() {
        System.out.println("==============permutations==============="); 
        RePermutations p = new RePermutations("ABCEG");
        p.permutations();
        System.out.println(); 
        System.out.println("==============permutations==============="); 
        System.out.println(); 
    }
    
     @Test
    public void bTreeReconstruct() {
        System.out.println("==============bTreeReconstruct==============="); 
         ReconstructTreePreOrderInOrder build=new ReconstructTreePreOrderInOrder();  
        int[] preOrder = {7,10,4,3,1,2,8,11};  
        int[] inOrder = {4,10,3,1,7,11,8,2};  
  
        ReconstructTreePreOrderInOrder.Node root=build.buildTreePreOrderInOrder(preOrder,0,preOrder.length-1,inOrder,0,preOrder.length-1);  
        build.preOrder(root);  
        System.out.println();  
        build.inOrder(root);  
        System.out.println();
        System.out.println("==============bTreeReconstruct==============="); 
    }
    
    
}
