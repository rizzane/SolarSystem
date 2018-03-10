package sim.GUI

import controlP5._
import processing.core._
import processing.opengl._
import simulator._
import sim.io._
import peasy.PeasyCam
import scala.collection.mutable.Buffer


class Menu(p: PApplet, c: ControlP5, cam: PeasyCam) {
  
  private var curr = new PMatrix3D
  private var g3 = new PGraphics3D
  
  var bodies = SolarSystem.getBodies
  
  /** Adds buttons and slider to the simulator.*/
  def createGui() = {
    c.addSlider("Speed", 0,1000, 150, p.width/2 - 200, p.height - 55, 400, 30)
    c.addButton("Pause").setPosition(p.width/2 - 300, p.height - 55).setSize(50,30)
    c.addButton("Save").setPosition(0, 0).setSize(65, 40)    
    c.addButton("Load save").setPosition(65, 0).setSize(65, 40)
    c.addButton("Default").setPosition(130, 0).setSize(65, 40)
    c.addButton("Show vectors").setPosition(195, 0).setSize(65, 40)
    c.addButton("Names").setPosition(260, 0).setSize(65, 40)
    c.addButton("Add satellites").setPosition(325, 0).setSize(65, 40)    
    c.addButton("Mercury").setPosition(390, 0).setSize(65, 40)
    c.addButton("Venus").setPosition(455, 0).setSize(65, 40)
    c.addButton("Earth").setPosition(520, 0).setSize(65, 40)
    c.addButton("Mars").setPosition(585, 0).setSize(65, 40)
    c.addButton("Jupiter").setPosition(640, 0).setSize(65, 40)
    c.addButton("Saturn").setPosition(705, 0).setSize(65, 40)
    c.addButton("Uranus").setPosition(770, 0).setSize(65, 40)
    c.addButton("Neptune").setPosition(835, 0).setSize(65, 40)
    c.addButton("Pluto").setPosition(900, 0).setSize(65, 40)  
    c.addButton("Exit").setPosition(p.width - 65, 0).setSize(65, 40)
    c.setAutoDraw(false)
  }
  
  /**Sets the center of the camera to the */
  def lookAt(body: Body) = { 
    val pos = body.state.scaledPosition
    cam.lookAt(pos.getX, pos.getY, pos.getZ)
  }  
  
  /**Keeps 2D slider and buttons in place in 3D world.
   * http://wiki.bk.tudelft.nl/toi-pedia/Processing_Buttons_and_Sliders*/
  def gui() = {
    p.pushMatrix    
    p.translate(bodies(0).state.position.getX, bodies(0).state.position.getY, bodies(0).state.position.getZ)
    curr = new PMatrix3D(g3.camera)
    p.camera
    c.draw()
    g3.camera = curr    
    p.popMatrix    
  }   
  

  /**Functions for buttons.*/
  def mousePressed(reader: Reader) = {    
    if(p.mouseY > 0 && p.mouseY < 40) {  
      if(p.mouseX > 0 && p.mouseX < 65) {
        reader.save()    //Save button
      } else if(p.mouseX > 65 && p.mouseX < 130) {      
        SolarSystem.controller.satellites = Buffer[Satellite]()
        reader.load("save.txt")         //Load save button        
      } else if(p.mouseX > 130 && p.mouseX < 195) {
        SolarSystem.controller.satellites = Buffer[Satellite]()
        reader.load("planets.txt")
      } else if(p.mouseX > 195 && p.mouseX < 260) {
        SolarSystem.toggleVector match {    //Show vectors button
          case true => SolarSystem.toggleVector = false
          case false => SolarSystem.toggleVector = true
        }      
      } else if(p.mouseX > 260 && p.mouseX < 325) {
        SolarSystem.showNames match {            //Names button
          case true => SolarSystem.showNames = false
          case false => SolarSystem.showNames = true
        }
      } else if(p.mouseX > 325 && p.mouseX < 390) {
        reader.load("Satellites.txt")            //Add satellites button
      } else if(p.mouseX >= p.width - 65 && p.mouseX <= p.width) {
        p.dispose    //Exit button
        System.exit(1)
        //Pause button                         
      } else if(p.mouseX > 390 && p.mouseX < 455) {
        lookAt(bodies(1))  //Mercury
      } else if(p.mouseX > 455 && p.mouseX < 520) {
        lookAt(bodies(2))  //Venus
      } else if(p.mouseX > 520 && p.mouseX < 585) {
        lookAt(bodies(3))  //Earth
      } else if(p.mouseX > 585 && p.mouseX < 650) {
        lookAt(bodies(4))  //Mars
      } else if(p.mouseX > 650 && p.mouseX < 705) {
        lookAt(bodies(5))  //Jupiter
      } else if(p.mouseX > 705 && p.mouseX < 770) {
        lookAt(bodies(6))  //Saturn
      } else if(p.mouseX > 770 && p.mouseX < 835) {
        lookAt(bodies(7))  //Uranus
      } else if(p.mouseX > 835 && p.mouseX < 900) {
        lookAt(bodies(8))  //Neptune
      } else if(p.mouseX > 900 && p.mouseX < 965) {
        lookAt(bodies(9))  //Pluto
      }
    } else if(p.mouseX > p.width/2 - 300 && p.mouseX < p.width/2 - 250 && p.mouseY > p.height - 55 && p.mouseY < p.height - 25) {
        SolarSystem.paused match {
          case true => SolarSystem.paused = false
          case false => SolarSystem.paused = true 
        }
    }
  }  
}