package simulator

import processing.core._
import scala.collection.mutable.Buffer
import processing._


class Satellite(location: Vector3D, var radius: Float, speed: Vector3D, var mass: Double) extends Body {
  
  def getMass: Double = this.mass
  
  
  var state = new State(location,speed)
  var derivative = new Derivative   
  
  /**Draws satellite.*/
  def draw(p: PApplet) {
    p.pushMatrix()
    p.stroke(255)
    p.translate(state.scaledPosition.getX, state.scaledPosition.getY, state.scaledPosition.getZ)
    p.noStroke()
    val g = p.createShape(PConstants.SPHERE, radius.toFloat)
    p.shape(g)
    p.popMatrix()
  }  
    

  override def toString = {
    "#" + "Satellite" + "\n" + 
    "location: " + state.position + "\n" +
    "radius: " + radius + "\n" +
    "velocity: " + state.velocity + "\n" +
    "mass: " + getMass
  }   
}