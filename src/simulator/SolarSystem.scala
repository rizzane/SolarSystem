package simulator

import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import controlP5.ControlP5
import peasy.PeasyCam
import processing.core.PApplet
import processing.core.PConstants
import processing.core.PImage
import processing.core.PMatrix3D
import processing.opengl.PGraphics3D
import sim.GUI._
import sim.io._



class SolarSystem extends PApplet{
  
  val reader = new Reader

  var cam: PeasyCam = _
  var c: ControlP5 = _
  var menu: Menu = _
  
  override def settings() {
    fullScreen(PConstants.P3D)
  }
  
  /**Sets up simulator, this is called when simulator starts.*/
  override def setup() {
    background(0)    
    frameRate(40)
    smooth
    val g = new Textures
    g.loadTextures(this, SolarSystem.textures)
    reader.load("planets.txt")    
    val bodies = SolarSystem.getBodies
    cam = new PeasyCam(this, bodies(0).state.scaledPosition.getX, bodies(0).state.scaledPosition.getY, bodies(0).state.scaledPosition.getZ, 830)        
    c = new ControlP5(this)
    menu = new Menu(this, c, cam)
    cam.setMaximumDistance(7100)
    cam.setMinimumDistance(250)        
    menu.createGui()
  }

  /**Main Loop that updates and draws.*/
  override def draw() {    
    var bodies = SolarSystem.getBodies
    background(0)     
    SolarSystem.controller.update(this, c.getValue("Speed"))
    SolarSystem.controller.draw(this)  
    menu.gui()
  }
  
  override def mousePressed() {
    menu.mousePressed(reader)
  }
  
}

object SolarSystem {
  
  var controller: Controller = new Controller
  
  /**True when vectors are drawn.*/
  var toggleVector: Boolean = false
  
  /**True when simulator is paused.*/
  var paused: Boolean = false
    
  /**True when names show next to HeavenlyBodies.*/
  var showNames = true
      
  /**Textures of HeavenlyBodies, their names are keys.*/
  var textures: Map[String,PImage] = Map[String,PImage]()
  
  /**Returns every existing HeavenlyBody in Array.*/
  def getBodies: Array[HeavenlyBody] = controller.bodies
  
  /**Returns every existing Satellite in Buffer.*/
  def getSatellites: Buffer[Satellite] = controller.satellites
  
  /**HeavenlyBodies' names are keys and values are indexes for array of heavenlybodies.*/
  def getIndex = Map[String, Int]("Sun" -> 0, "Mercury" -> 1, "Venus" -> 2, "Earth" -> 3, "Mars" -> 4,
      "Jupiter" -> 5, "Saturn" -> 6, "Uranus" -> 7, "Neptune" -> 8, "Pluto" -> 9)
}
