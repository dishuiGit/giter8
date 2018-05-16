package io
import org.apache.spark.sql.SparkSession

package object dishui {
  implicit class StrConversions(val fileName: String) {
    def toTable(spark:SparkSession,table:String,t:String) = {
      t match {
        case \"json\" => spark.read.json(fileName).createOrReplaceTempView(table)
      }

    }
  }
}
