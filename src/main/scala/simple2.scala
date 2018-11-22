/**
  * Created by alex on 21.11.18.
  */

import org.apache.spark._
import org.apache.hadoop._
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.mapreduce.TableInputFormat
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.util.Bytes

import scala.collection.mutable.ArrayBuffer

object simple2 {

  val COLUMN_FAMILY = "Office".getBytes
  val QUALIFIER = "Address".getBytes()
  val NAME_OF_TABLE = "test3"

  def createTable(nameOfTable: String, columnFamily: Array[Byte], admin: Admin): Unit = {

    val tableName = TableName.valueOf(nameOfTable)
    val descriptor = new HTableDescriptor(tableName)
    descriptor.addFamily(new HColumnDescriptor(columnFamily))
    val table = admin.getConnection.getTable(tableName)

    if (!admin.tableExists(tableName)) {
      admin.createTable(table.getTableDescriptor)
      println("the table $nameOfTable was creted")
    } else println("the table" + " is present ")


  }

  def scanTable(table: Table, columnFamily: Array[Byte], qualifier: Array[Byte]) {

    val scan = new Scan()
    scan.addColumn(columnFamily, qualifier)
    //          scan.setRowPrefixFilter("1000".getBytes())
    val resultScaner = table.getScanner(scan)
    val hbaseArray = new ArrayBuffer[String]
    val result = resultScaner.iterator()

    while (result.hasNext) {
      hbaseArray += Bytes.toString(result.next().value())
    }

    hbaseArray.toArray.foreach(println)
    resultScaner.close()

  }

  def writeToTable(table: Table, rowkey: Array[Byte], column_family: Array[Byte], qualifier: Array[Byte], admin: Admin, value: String): Unit = {

    if (admin.isTableAvailable(table.getName)) {
      val theput = new Put("2000".getBytes)
      theput.addColumn(column_family, qualifier, value.getBytes())
      table.put(theput)
      println("puted $value ")
    } else {
      println("the table ${table.getName} is not available ")
    }

  }

  def readRDD(sc: SparkContext, conf: Configuration): Unit = {

    conf.set(TableInputFormat.INPUT_TABLE, NAME_OF_TABLE)
    val hBaseRDD = sc.newAPIHadoopRDD(conf, classOf[TableInputFormat], classOf[ImmutableBytesWritable], classOf[Result])
    println("Number of Records found : " + hBaseRDD.count())
    hBaseRDD.foreach(item =>

      {
        println("value= " + item._2)
        println("immutableWritestByte= " + item._1.toString)
      }
    )

  }


  def main(args: Array[String]): Unit = {

    val sparkConf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("SimpleApp")
    val sc = new SparkContext(sparkConf)
    val conf = HBaseConfiguration.create()
    sparkConf.set("spark.hbase.host", "hbase")
    conf.set("hbase.zookeeper.quorum", "zoo")

    val connection = ConnectionFactory.createConnection(conf)
    val admin = connection.getAdmin()
    val table = admin.getConnection.getTable(TableName.valueOf(NAME_OF_TABLE))

    createTable(NAME_OF_TABLE, COLUMN_FAMILY, admin)
    writeToTable(table, "200".getBytes(), COLUMN_FAMILY, QUALIFIER, admin, "test_value1")
    scanTable(table, COLUMN_FAMILY, QUALIFIER)
    readRDD(sc, conf)
  }

}
