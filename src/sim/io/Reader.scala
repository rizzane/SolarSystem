package sim.io

import java.io._

import scala.io.Source

import simulator._

class Reader {
    
  /**Returns whole data from textfile as String.*/
  def getData(filename: String): String = {
    val file = Source.fromFile(filename).getLines().toVector    
    var data = ""
    for(line <- file) {
      data += line + "\n"
    }
    data
  }  
  
  /**True if line starts with "#".*/
  def isHeader(line: String): Boolean = {
    if(line != null && line.startsWith("#")) {
      true
    } else {
      false
    }
  }
  
  /**True if line is data.*/
  def isData(line: String): Boolean = {
    if(line != null && line.contains(":")) {
      true
    } else {
      false
    }
  }
  
  /**Removes extra whitespace from data.*/
  def trimData(line: String): Array[String] = {
    if(line != null && isData(line)) {
      line.trim.split(":").map(_.trim)
    } else {
      null
    }
  }
  
  /**Sets bodies to states according to text file.*/
  def load(inputfile: String) = {
    var input = new StringReader(getData(inputfile))

    val lineReader = new BufferedReader(input)
    try {
      var line = lineReader.readLine()
      while(line != null && isHeader(line)) {
        if(line == "#Comments") {
          line = lineReader.readLine()
          while(line != null && !isHeader(line)) {
            line = lineReader.readLine()            
          }
        }
        if(isHeader(line)) {
          var name = (line.trim.split("#"))(1)
          var position = new Array[Double](3)
          var radius = 0f
          var velocity = new Array[Double](3)
          var mass = 0.0          
          line = lineReader.readLine()
          while(isData(line)) {
            var data = trimData(line)  
            data(0) match {
              case "location" => {
                var p = data(1).replaceAll("[^-,EeAUau.0-9]", "").split(",")
                for(i <- 0 until p.size) {
                  if(p(i).contains("AU") || p(i).contains("au") || p(i).contains("Au") || p(i).contains("aU")) {
                    position(i) = p(i).replaceAll("[^-Ee.0-9]", "").toDouble * 149.6e9
                  } else {
                    position(i) = p(i).toDouble
                  }
                }
              }
              case "radius" => {
                var r = data(1).trim()
                radius = r.toFloat
              }
              case "velocity" => {        
                velocity = data(1).replaceAll("[^-Ee,.0-9]", "").split(",").map(_.toDouble)                
              }
              case "mass" => {
                var m = data(1).trim
                mass = m.toDouble                
              }
              case _ => 
            }
            line = lineReader.readLine()
          }
          if(name == "Sun") {
            position = Array(0.0,0.0,0.0)
          }
          if(name == "Satellite") {
            SolarSystem.controller.add(new Satellite(new Vector3D(position(0),position(1),position(2)), 
                radius, new Vector3D(velocity(0),velocity(1),velocity(2)), mass))
          } else {
            SolarSystem.controller.add(new HeavenlyBody(name, 
                new Vector3D(position(0),position(1),position(2)), radius, 
                new Vector3D(velocity(0),velocity(1),velocity(2)), mass, SolarSystem.textures.get(name).get))        
          }
        }        
      }
    } catch {
      case e: IOException => throw new CorruptedFileException("Reading data failed.")
    }
    
  }
  
  /**Saves states of bodies in simulator to text file.*/
  def save() = {
    val pw = new PrintWriter(new File("save.txt"))
    val bodies = SolarSystem.getBodies
    for(i <- 0 until bodies.size) {
      pw.write(bodies(i) + "\n")
    }
    pw.close()
  }
  
  
}