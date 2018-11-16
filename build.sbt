name := "hbase1"

version := "1.0"

scalaVersion := "2.11.12"



libraryDependencies ++= Seq(
 "org.apache.spark" %% "spark-core" % "2.3.0",
 "org.apache.spark" %% "spark-sql" % "2.3.0" ,
// "org.apache.spark" %% "spark-hive" % "2.0.0",
// "org.apache.spark" %% "spark-streaming" % "2.0.0",
// "org.apache.spark" %% "spark-graphx" % "2.0.0"  ,
// "org.apache.spark" %% "spark-mllib" % "2.0.0",
// "org.apache.hbase" % "hbase-spark" % "2.0.0-alpha3",
 "org.apache.hbase" % "hbase-common" % "1.2.0",
 "org.apache.hbase" % "hbase-server" % "1.2.0",
 "org.apache.hbase" % "hbase-client" % "1.2.0",
 "org.apache.hbase" % "hbase-hadoop-compat" % "1.2.0",
 "org.apache.hbase" % "hbase-hadoop2-compat" % "1.2.0",
 "org.apache.hbase" % "hbase-protocol" % "1.2.0"

)


//docker exec -it hbase bash