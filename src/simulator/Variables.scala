package simulator



class State(var position: Vector3D, var velocity: Vector3D ) {
  
  def x: Double = position.x      // x-position
  
  def y: Double = position.y      // y-position   
  
  def z: Double = position.z      // z-position
  
  def vx: Double = velocity.x     // x-velocity

  def vy: Double = velocity.y     // y-velocity
  
  def vz: Double = velocity.z     // z-velocity
  
  /**Scales meters to pixels.*/
  def scaledPosition = {
    val p = 3779.52755905511e6 / 2.5    
    position.div(p)
  }
  
}

class Derivative {
  
  var velocity = new Vector3D(0,0,0)
  
  var acceleration = new Vector3D(0,0,0)
  
  def dx: Double = velocity.x       // dx/dt = x-velocity
  
  def dy: Double = velocity.y       // dy/dt = y-velocity
  
  def dz: Double = velocity.z       // dz/dt = z-velocity
  
  def dvx: Double = acceleration.x      // dvx/dt = x-acceleration

  def dvy: Double = acceleration.y      // dvy/dt = y-acceleration
  
  def dvz: Double = acceleration.z      // dvz/dt = z-acceleration

}