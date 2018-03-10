package simulator

import org.junit.Assert._
import org.junit.Test
import sim.io._

class UnitTests {
  
  @Test def testAdd {
    val v1 = new Vector3D(346, 245, 10)
    val v2 = new Vector3D(123, 54, 67)
    val result = v1.add(v2)
    val v = new Vector3D(469, 299, 77)
    assert(true, v == result)
  }
  
  @Test def testSub {
    val v1 = new Vector3D(346, 245, 10)
    val v2 = new Vector3D(123, 54, 67)
    val result = v1.add(v2)
    val v = new Vector3D(223, 191, -57)
    assert(true, v == result)    
  }
  
  @Test def testMag {
    val v = new Vector3D(10, 20, 5)
    val result = 22.9128784747792
    assert(true, result == v.magnitude)
  }
  
  @Test def testNormalize {
    val v = new Vector3D(2, 3, 5)
    val result = new Vector3D(0.3244428422615251, 0.48666426339228763, 0.8111071056538127)
    assert(true, result == v.normalize)
    assert(true, v.normalize.magnitude == 1)
  }
}