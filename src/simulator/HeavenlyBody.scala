package simulator

import processing.core._
import scala.collection.mutable.Buffer
import processing._


class HeavenlyBody(var name: String, location: Vector3D, var radius: Float, speed: Vector3D, var mass: Double, image: PImage) extends Body {
  
  def getMass: Double = this.mass
    
  var state = new State(location,speed)
  var derivative = new Derivative
  val img: PImage = image  
  
  /**Draws HeavenlyBody.*/
  def draw(p: PApplet) = {
    p.pushMatrix()
    p.stroke(255)
    p.translate(state.scaledPosition.getX, state.scaledPosition.getY, state.scaledPosition.getZ)
    p.noStroke()    
    val g = p.createShape(PConstants.SPHERE, radius.toFloat)
    g.rotateX(-Math.PI.toFloat / 2f)
    g.setTexture(img)      //Wraps texture around the sphere.
    p.shape(g)
    p.strokeWeight(2)
    if(SolarSystem.showNames) {
      p.text(name, radius, 0)
    }
    p.popMatrix()
  }      
  
  override def toString = {
    "#" + name + "\n" + 
    "location: " + state.position + "\n" +
    "radius: " + radius + "\n" +
    "velocity: " + state.velocity + "\n" +
    "mass: " + getMass
  }
  
}