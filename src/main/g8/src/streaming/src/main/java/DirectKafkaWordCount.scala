package io.dishui.streaming23

import java.util.concurrent.atomic.AtomicLong
import java.util.{HashMap => JHashMap}

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.internal.Logging
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.scheduler.{StreamingListener, StreamingListenerBatchCompleted, StreamingListenerBatchStarted, StreamingListenerBatchSubmitted}
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.JavaConverters._


object StreamingLog extends Logging {
  def setStreamingLogLevels() {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      // We first log something to initialize Spark's default logging, then we override the
      // logging level.
      logInfo("Setting log level to [WARN] for streaming example." +
        " To override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)
    }
  }
}

object DirectKafkaWordCount {


  def main(args: Array[String]): Unit = {

    StreamingLog.setStreamingLogLevels()

    val sparkConf = new SparkConf().setAppName("DirectKafkaWordCount").setMaster("local[4]")
    val ssc = new StreamingContext(sparkConf,Seconds(4))

    ssc.start()
    ssc.awaitTermination()


  }
}


}