package io.dishui.common.util

import jep.{Jep, JepConfig, NamingConventionClassEnquirer}
import org.json4s._
import org.json4s.native.JsonMethods._
object Faker {
  private val jep = new Jep(new JepConfig().setClassEnquirer(new NamingConventionClassEnquirer()))
  jep.eval("import json")
  jep.eval("from faker import Factory")
  jep.eval("fake = Factory().create('zh_CN')")

  def testJson :String = {
    jep.eval(
      s"""
         |data = {
         |        'id' : fake.random_int(min=0, max=9999),
         |        'name' : fake.name(),
         |        'phone_number' : fake.phone_number(),
         |        'address' : fake.address(),
         |        'email' : fake.email(),
         |        'ipv4' : fake.ipv4(),
         |        'str' : fake.pystr(min_chars=6, max_chars=8)
         |    }
       """.stripMargin)
    jep.eval("json_str = json.dumps(data,ensure_ascii=False).encode('utf-8')")
    jep.getValue("json_str").toString
  }

  def testJson2 :(String,String) = {
    val json = parse(testJson)
    val id = (json \ "id").values.toString
    val value = testJson
    (id,value)
  }

  def testJson3 :(String,String) = {
    jep.eval("id = fake.random_int(min=0, max=9999)")
    jep.eval("str = fake.pystr(min_chars=6, max_chars=8)")
    jep.eval("str1 = fake.pystr(min_chars=6, max_chars=8)")
    jep.eval("str2 = fake.pystr(min_chars=6, max_chars=8)")
    val id = jep.getValue("id").toString
    val str = jep.getValue("str+' '+str1+' '+str2").toString
    (id,str)
  }


}
