package simulator

import scala.math._
import scala.collection.mutable.Buffer


class Physics {
  
  /**Gravitational constant*/
  private val gConstant = 6.67408e-11
  
  /** F = G*m1*m2/r^2 */
  def getForce(mass1: Double, mass2: Double, distance: Double) ={
    ((gConstant * mass1 * mass2) / (distance * distance))
  }
  
  /**Acceleration from Newton's 2nd law F = m*a
   *  a = F/m */
  def acceleration(body: Body, bodies: Array[HeavenlyBody], satellites: Buffer[Satellite]) = {
    var a = new Vector3D(0,0,0)
    for(i <- 0 until bodies.length) {
      if(bodies(i) != null && bodies(i) != body) {
        var direction = bodies(i).state.position.sub(body.state.position)
        var normal = direction.normalize
        var distance = (body.state.position.sub(bodies(i).state.position)).magnitude
        a = a.add(normal.mul(getForce(bodies(i).getMass, body.getMass, distance) / body.getMass))
      }
    }
    if(!satellites.isEmpty) {
      for(s <- satellites) {
        if(s != body) {
          var direction = s.state.position.sub(body.state.position)
          var normal = direction.normalize
          var distance = (body.state.position.sub(s.state.position)).magnitude
          a = a.add(normal.mul(getForce(s.getMass, body.getMass, distance) / body.getMass))          
        }
      }
    }
    a
  }  
  
  /** The Law of Momentum conservation,
   *  m1*v1 + m2*v2 = (m1 + m2)*u,
   * u = (m1*v1 + m2*v2)/(m1 + m2)*/
  def momentum(body1: Body, body2: Body) = {
    val v1 = body1.state.velocity
    val m1 = body1.getMass
    val v2 = body2.state.velocity
    val m2 = body2.getMass
    (v1.mul(m1).add(v2.mul(m2))).div(m1 + m2)
  }
  
}