package samples

import org.junit._
import Assert._
import org.bigdog.scalas.TestScala

@Test  
class AppTest {    

    @Test 
  def testScalaJunit(): Unit = { 
   var v = new TestScala()   
    println(v.foo("The First Scala Unit Test:hello world")); 
  } 
}


