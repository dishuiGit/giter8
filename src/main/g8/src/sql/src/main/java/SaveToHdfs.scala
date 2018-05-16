package io.dishui

import org.apache.spark.sql.SparkSession

object SaveToHdfs {

  def main(args: Array[String]): Unit = {

    var inputFile: String = ""
    var outputPath: String = ""
    var fileType: String = "csv"
    if (args.length < 3) {
      println("Usage: io.dishui.SaveToHdfs inputFile outputPath fileType")
      System.exit(1)
    }else{
      inputFile = args(0)
      outputPath = args(1)
      fileType = args(2)
    }

    val spark = SparkSession.builder()
      .appName("SaveToHdfs")
      .getOrCreate()

    inputFile.toTable(spark, "t", fileType)

    spark.sql("select count(*) from t").rdd.saveAsTextFile(outputPath)

    spark.close()
  }
}
