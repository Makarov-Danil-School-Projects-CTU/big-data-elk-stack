import org.apache.spark.sql.SparkSession
val spark = SparkSession.builder.getOrCreate()

// Read CSV files into DataFrames
val food_prices = spark.read.format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load("/opt/bitnami/spark/originalDataset/food_prices.csv")
val missile_strikes = spark.read.format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load("/opt/bitnami/spark/originalDataset/missile_strikes.csv")
val refugees = spark.read.format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load("/opt/bitnami/spark/originalDataset/refugees.csv")
val russia_losses_equipment = spark.read.format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load("/opt/bitnami/spark/originalDataset/russia_losses_equipment.csv")
val russia_losses_personnel = spark.read.format("csv").option("sep", ",").option("inferSchema", "true").option("header", "true").load("/opt/bitnami/spark/originalDataset/russia_losses_personnel.csv")

// Join tables on 'date' column
val joined_table = missile_strikes
    .join(russia_losses_personnel, Seq("date"), "left_outer")
    .join(russia_losses_equipment, Seq("date"), "left_outer")
    .join(refugees, Seq("date"), "left_outer")

val selected_columns = joined_table.select(russia_losses_equipment("date"), 
col("model"),
col("launched"),
col("destroyed"),
col("refugees"),
col("aircraft"),
col("helicopter"),
col("tank"),
col("APC"),
col("field_artillery"),
col("MRL"),
col("drone"),
col("naval_ship"),
col("anti_aircraft_warfare"),
col("special_equipment"),
col("vehicles_and_fuel_tanks"),
col("cruise_missiles"),
col("submarines"),
col("personnel")
).filter(col("date").geq(lit("2022-08-28")) && col("date").lt(lit("2023-11-29")))

var food_prices_filtered = food_prices.filter(col("date").geq(lit("2014-03-15")) && col("date").lt(lit("2022-02-15")))

// Write the result to a single CSV file (consider partitioning for larger datasets)
selected_columns.coalesce(1).write.format("csv").option("sep", ",").option("header", "true").save("/opt/bitnami/spark/processedDataset/processed_merged.csv")
food_prices_filtered.coalesce(1).write.format("csv").option("sep", ",").option("header", "true").save("/opt/bitnami/spark/processedDataset/processed_food.csv")

spark.stop()
