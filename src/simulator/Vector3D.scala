package simulator

class Vector3D(var x: Double, var y: Double, var z: Double)  {
  
  //Returns vectors x, y and z -values as float
  def getX = x.toFloat
  
  def getY = y.toFloat
  
  def getZ = z.toFloat
  
  /**Returns sum of two vectors*/
  def add(v: Vector3D) = {
    new Vector3D(x + v.x, y+ v.y, z + v.z)
  }
  
  /**Returns product of constant and vector*/
  def mul(k: Double) = {
    new Vector3D(x * k, y * k, z * k)
  }
  
  /**Subtraction of two vectors*/
  def sub(v: Vector3D) = {
    new Vector3D(x - v.x, y - v.y, z - v.z)
  }

  /**Divides vector with constant*/
  def div(n: Double) = {
    new Vector3D(x / n, y / n, z / n)
  }
  
  /**Dot product of two vectors*/
  def dot(v: Vector3D) = {
    x * v.x + y * v.y + z * v.z
  }
  
  /**Cross product of two vectors*/
  def cross(v: Vector3D) = {
    new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)
  }
  
  /**Returns vectors length*/
  def magnitude = {
    Math.sqrt((x * x) + (y * y) + (z * z))
  }
  
  /**Returns vectors unit vector*/
  def normalize = {
    new Vector3D(x / this.magnitude, y / this.magnitude, z / this.magnitude)
  }
  
  override def toString = "[ " + this.x + "  ,  " + this.y + "  ,  " + this.z + " ]"
}