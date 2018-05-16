package io.dishui.common

import java.io.File
import java.util.Properties

import scala.io

package object dummy {

  val basePath = {
    val uri = Thread.currentThread.getContextClassLoader.getResource("")
    new File(uri.getPath).getParentFile.getParentFile.getPath + "/data"
  }



  implicit class StrConversions(val fileName: String) {


    def toPath = basePath + "/" + fileName

    def toStr = io.Source.fromFile(toPath).mkString

    def propertiesToMap = {
      val propInputStream = getClass.getClassLoader.getResourceAsStream(fileName)
      val prop = new Properties()
      prop.load(propInputStream)
      import scala.collection.JavaConversions.propertiesAsScalaMap
      prop.toMap
    }

  }
}
