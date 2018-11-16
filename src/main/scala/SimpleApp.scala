import org.apache.spark.sql.SparkSession
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.io.compress.Compression
import org.apache.hadoop.hbase.regionserver.BloomType
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.commons.lang.StringUtils
import org.apache.hadoop.hbase.{HBaseConfiguration, HColumnDescriptor, HTableDescriptor, TableName}
import org.apache.hadoop.hbase.client.ConnectionFactory
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.io.compress.Compression
import org.apache.hadoop.hbase.regionserver.BloomType
import org.apache.hadoop.hbase.util.Bytes


import scala.collection.mutable


object SimpleApp {

    def main(args: Array[String]): Unit = {

        val sparkConf = new SparkConf()
          .setMaster("local[2]")
          .setAppName("CountingSheep")
        val sc = new SparkContext(sparkConf)

            val conf = HBaseConfiguration.create()
            val tableName = "enterprises"
        //
//            conf.addResource("/home/alex/spark/projects/hbase1/src/main/scala/hbase-site.xml")
        sparkConf.set("spark.hbase.host", "hbase")
//           conf.set("zookeeper.znode.parent", "/hbase-unsecure")
        conf.set("hbase.zookeeper.quorum","zoo")

        //
            val connection = ConnectionFactory.createConnection(conf)


        System.out.println("---> zookeeper.znode.parent = " + conf.get("zookeeper.znode.parent"));
        System.out.println("---> hbase.zookeeper.quorum = " + conf.get("hbase.zookeeper.quorum"));
        System.out.println("---> HBaseConfiguration = " + conf);

            val admin = connection.getAdmin()
        //
            val listtables=admin.listTables()
            listtables.foreach(println)

//            val table = connection.getTable(TableName.valueOf(tableName))



            sc.stop()


    }






}