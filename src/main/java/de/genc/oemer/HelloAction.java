package de.genc.oemer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HelloAction extends AnAction {
    public HelloAction() {
        super("Hello");
    }

    public void actionPerformed(AnActionEvent event) {
        String classpathStr = System.getProperty("java.class.path");
        System.out.print(classpathStr);
        System.out.println("I am running");
        InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("spark-version-info.properties");
        if (resourceStream != null) {
            System.out.println("Found the shizzle");
        } else {
            System.out.println("Did not found the shizzle");
        }
//    Project project = event.getProject();
//    Messages.showMessageDialog(project, "Hello world!", "Greeting", Messages.getInformationIcon());

        SparkConf sparkConf = new SparkConf()
                .setMaster("local[*]")
                .set("spark.sql.crossJoin.enabled", "true")
                .setAppName("RecipeContentAggregatorStructuredStreamApplication");

        SparkSession sparkSession = SparkSession.builder()
                .config(sparkConf)
                .getOrCreate();
        sparkSession.sparkContext()
                .setLogLevel("WARN");


        List<Row> rowList = Stream.of(1, 2, 3, 4).map(RowFactory::create).collect(Collectors.toList());

        Dataset<Row> dataFrame = sparkSession.createDataFrame(rowList, Encoders.INT().schema());
        dataFrame.createOrReplaceTempView("frame");
        Dataset<Row> dataset = sparkSession.sql("SELECT value as theSecondInt FROM frame");
        dataset.show();
        dataset.collectAsList().forEach(System.out::println);
    }
}