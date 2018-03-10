package simulator

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import scala.collection.mutable.Buffer

abstract class Body {
  
  def getMass: Double
  
  var mass: Double
  var radius: Float
  var bodies = SolarSystem.getBodies   
  var satellites = SolarSystem.getSatellites 
  var state: State
  var derivative: Derivative
  val phys = new Physics   
     
  /**Checks if body collides with another body.*/
  def checkCollision(body: Body): Boolean = {
    val pos = state.scaledPosition
    if(this != body) {
      //Bodies' scaled positions are within the distance of 4 pixels.
      if(body.state.scaledPosition.sub(pos).magnitude <= 4) {
        return true
      }
    }
    return false
  }
  
  
  /**Draws acceleration and velocity vectors.
   * Acceleration with red line and velocity with whie line.*/
  def vectors(p: PApplet) = {    
    val sun = bodies(0).state.position 
    p.pushMatrix()
    p.translate(state.scaledPosition.getX, state.scaledPosition.getY, state.scaledPosition.getZ)    
    val aDir = derivative.acceleration.normalize
    val vDir = derivative.velocity.normalize
    p.strokeWeight(3)
    p.stroke(255,0,0)
    p.line(aDir.getX * 20f, aDir.getY * 20f, aDir.getZ * 20f, 0, 0, 0)
    p.noStroke()
    p.stroke(255)
    p.line(vDir.getX * 20f, vDir.getY * 20f, vDir.getZ * 20f, 0, 0, 0)
    p.noStroke()
    p.popMatrix()    
  }  
  
  /**Evaluates derivative, part of Runge-Kutta method.*/
  def nextDerivative(initial: State, d: Derivative, dt: Double): Derivative = {
    
    var s = new State(new Vector3D(0,0,0),new Vector3D(0,0,0))
    
    s.velocity.x = initial.vx + d.dvx * dt
    s.velocity.y = initial.vy + d.dvy * dt
    s.velocity.z = initial.vz + d.dvz * dt
    
    var acceleration = phys.acceleration(this, bodies, satellites)
   
    var derivative = new Derivative 
    derivative.velocity = s.velocity
    derivative.acceleration = acceleration
    derivative
  }  
  
  /**Integration, Runge-Kutta method.*/
  def integrate(initial: State, dt: Double): Unit = {
    
    val d0 = new Derivative

    val a = this.nextDerivative(initial, d0, dt * 0.0)
    val b = this.nextDerivative(initial, a, dt * 0.5)
    val c = this.nextDerivative(initial, b, dt * 0.5)
    val d = this.nextDerivative(initial, c, dt)
         
    val dxdt = 1.0/6.0 * (a.dx + 2.0*(b.dx + c.dx) + d.dx)
    val dydt = 1.0/6.0 * (a.dy + 2.0*(b.dy + c.dy) + d.dy)
    val dzdt = 1.0/6.0 * (a.dz + 2.0*(b.dz + c.dz) + d.dz)
    val dvxdt = 1.0/6.0 * (a.dvx + 2.0*(b.dvx + c.dvx) + d.dvx)
    val dvydt = 1.0/6.0 * (a.dvy + 2.0*(b.dvy + c.dvy) + d.dvy)
    val dvzdt = 1.0/6.0 * (a.dvz + 2.0*(b.dvz + c.dvz) + d.dvz)
    
    derivative.acceleration = new Vector3D(dvxdt, dvydt, dvzdt)
    derivative.velocity = new Vector3D(dxdt, dydt, dzdt)
    
    initial.velocity = initial.velocity.add(derivative.acceleration.mul(dt))
    initial.position = initial.position.add(derivative.velocity.mul(dt))   
  }   
    
  def draw(p: PApplet): Unit
  
  def update(t: Double) {
    var dt = t
    //Loop reduces mistake when delta time is high
    for(i <- 0 until Math.round(dt / 10d).toInt) {
      integrate(this.state, dt)  
    }
  }  
}