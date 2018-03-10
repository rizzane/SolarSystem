package sim.GUI

import processing.core._
import scala.collection.mutable.Map

class Textures {
    
  /**Loads graphics.*/
  def loadTextures(p: PApplet, Map: Map[String, PImage]) = {
    Map += ("Sun" -> p.loadImage("textures/sun.jpg"))
    Map += ("Mercury" -> p.loadImage("textures/mercury.jpg"))
    Map += ("Venus" -> p.loadImage("textures/venus.jpg"))
    Map += ("Earth" -> p.loadImage("textures/earth.jpg"))
    Map += ("Mars" -> p.loadImage("textures/mars.jpg"))
    Map += ("Jupiter" -> p.loadImage("textures/jupiter.jpg"))
    Map += ("Saturn" -> p.loadImage("textures/saturn.jpg"))
    Map += ("Uranus" -> p.loadImage("textures/uranus.jpg"))
    Map += ("Neptune" -> p.loadImage("textures/neptune.jpg"))
    Map += ("Pluto" -> p.loadImage("textures/pluto.jpg"))
  }    
  
}