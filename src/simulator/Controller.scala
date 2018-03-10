package simulator

import scala.collection.mutable.Buffer
import processing.core._

class Controller {
  
  var bodies = new Array[HeavenlyBody](10)
  var satellites = Buffer[Satellite]()
  val phys = new Physics
  
  /**Adds body to collection.*/
  def add(body: Body) {
    if(body.isInstanceOf[Satellite]) {
      satellites += body.asInstanceOf[Satellite]
    } else {
      val index = SolarSystem.getIndex.get(body.asInstanceOf[HeavenlyBody].name).get
      bodies(index) = body.asInstanceOf[HeavenlyBody]
    }
  }      
  
  /**Removes body from collection.s*/
  def remove(body: Body) {
    if(body.isInstanceOf[Satellite]) {
      satellites -= body.asInstanceOf[Satellite]
    } else {
      val index = SolarSystem.getIndex.get(body.asInstanceOf[HeavenlyBody].name).get
      bodies(index) = null         
    }
  }
  
  /**Draws acceleration and velocity vectors for each body.*/
  def showVectors(body: Body, p: PApplet) = { 
    if(SolarSystem.toggleVector && body != bodies(0)) {
      body.vectors(p)
    }
  }

  /**Draws every existing body in simulator.*/
  def draw(p: PApplet) {
    for(i <-bodies) {
      if(i != null) {
        i.draw(p)
        showVectors(i, p)
      }
    }
    for(i <- satellites) {
      i.draw(p)
      showVectors(i, p)
    }
  }
  
  /**Updates every existing body in simulator*/
  def update(p: PApplet, t: Double) {
    if(!SolarSystem.paused) {
      for(i <- bodies) {       
        if(i != null) {
          i.update(t)        
          for(j <- bodies) {                        //if heavenlybody collides with heavenlybody      
            if(j != null && i.checkCollision(j)) {  //Bigger HeavenlyBody absorbs smaller one
              val m1 = i.getMass
              val m2 = j.getMass
              val (body, removed) = m1 >= m2 match {
                case true => (i, j)
                case false => (j, i)
              }
              body.mass = m1 + m2
              body.state.velocity = phys.momentum(body, removed)
              remove(removed)
              return
            }
          }
        }
      }
      for(i <- satellites) {
        i.update(t)
        for(j <- bodies; k <- satellites) {
          val m1 = i.getMass
          if(j != null && i.checkCollision(j)) {  //If heavenlybody collides with satellite
            val m2 = j.getMass                    
            val (body, removed) = m1 >= m2 match {
              case true => (i, j)
              case false => (j, i)
            }
            body.mass = m1 + m2
            body.state.velocity = phys.momentum(body, removed)
            remove(removed)
            return
          } else if(i.checkCollision(k)) {  //If satellites collides with another satellite
            val m2 = k.getMass              //the one with greater mass absorbs smaller one
            val (newSat, removed) = m1 >= m2 match {
              case true => (i, k)
              case false => (k, i)
            }
            newSat.mass = m1 + m2
            newSat.state.velocity = phys.momentum(i, k)
            remove(removed)
            return
          }
        }        
      }              
    }
  }
  
}