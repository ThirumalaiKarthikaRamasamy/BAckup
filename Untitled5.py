
# coding: utf-8

# In[4]:

import os
import sys


spark_path = "C:\Users\tr809419\Desktop\Spark\spark\spark-1.6.1-bin-hadoop2.6" 

os.environ['SPARK_HOME'] = spark_path
os.environ['HADOOP_HOME'] = spark_path

sys.path.append(spark_path + "/bin")
sys.path.append(spark_path + "/python")
sys.path.append(spark_path + "/python/pyspark/")
sys.path.append(spark_path + "/python/lib")
sys.path.append(spark_path + "/python/lib/pyspark.zip")
sys.path.append(spark_path + "/python/lib/py4j-0.9-src.zip")

from pyspark import SparkContext
from pyspark import SparkConf

sc = SparkContext("local", "test") 


# In[106]:

from pyspark.sql import Row
from pyspark.sql import SQLContext
sqlContext = SQLContext(sc)


# In[22]:

import json


dist_details=sc.textFile("test_unit.csv")
#remove the first line
dist_datalines=dist_details.filter(lambda x: "TURBINE_ID" not in x)
dist_datalines.count()
dist_split=dist_datalines.map(lambda l: l.split(","))
dist_map=dist_split.map(lambda p:Row(SITE_ID=p[0],SITE_NAME=p[1],SITE_ADDRESS=p[2],SITE_ADDRESS2=p[3],SITE_CITY=p[4],SITE_STATE=p[5],SITE_COUNTRY=p[6],SITE_ZIPCODE=p[7],GEPS_REGION_DESC=p[8],GPS_LATITUDE=p[9],GPS_LONGITUDE=p[10],INDUSTRY_TYPE=p[11],CSA_SITE_MGR_SSO=p[12],SERVICE_MGR_SSO=p[13],PRIMARY_CONTACT_SSO=p[14],PRIMARY_PHONE=p[15],CUSTOMER_ID=p[16],CREATED_BY=p[17],CREATION_DATE=p[18],LAST_UPDATED_BY=p[19],LAST_UPDATE_DATE=p[20],SITE_CUSTOMER_NAME=p[21]))
#dist_map.collect()

#Infer the scehma and register the DataFrame as a table
dist_df=sqlContext.createDataFrame(dist_map)
dist_df.registerTempTable("tpo_unit_config")
dist_df.show()
tcp_interactions = sqlContext.sql("""SELECT CUSTOMER_ID FROM tpo_unit_config """)
tcp_interactions.show()


# In[26]:

# distributor dataframe
dist_details=sc.textFile("tpo_customer.csv")
#remove the first line
#"CUSTOMER_ID","CUSTOMER_NAME","CUSTOMER_ADDRESS1","CUSTOMER_ADDRESS2","CUSTOMER_CITY","CUSTOMER_STATE","CUSTOMER_COUNTRY","CUSTOMER_ZIPCODE","CREATED_BY","CREATION_DATE","LAST_UPDATED_BY","LAST_UPDATE_DATE"
dist_datalines=dist_details.filter(lambda x: '"CUSTOMER_ID"' not in x)
dist_datalines.count()


dist_split=dist_datalines.map(lambda l: l.split(","))
dist_map1=dist_split.map(lambda p:Row(CUSTOMER_ID=p[0],CUSTOMER_NAME=p[1],CUSTOMER_ADDRESS1=p[2],CUSTOMER_ADDRESS2=p[3],CUSTOMER_CITY=p[4],CUSTOMER_STATE=p[5],CUSTOMER_COUNTRY=p[6],CUSTOMER_ZIPCODE=p[7],CREATED_BY=p[8],CREATION_DATE=p[9],LAST_UPDATED_BY=p[10],LAST_UPDATE_DATE=p[11]))
dist_map1.collect()
#Infer the scehma and register the DataFrame as a table
dist_df1=sqlContext.createDataFrame(dist_map1)
dist_df1.registerTempTable("tpo_cutomer")
#dist_df1.show()

tcp_interactions = sqlContext.sql("""SELECT CUSTOMER_ID,CUSTOMER_NAME,CUSTOMER_ADDRESS1,CUSTOMER_ADDRESS2,CUSTOMER_CITY,CUSTOMER_STATE,CUSTOMER_COUNTRY,CUSTOMER_ZIPCODE FROM tpo_cutomer where CUSTOMER_ID='1' """)
tcp_interactions.show()


# In[31]:

dist_details=sc.textFile("test_tpo.csv")
#remove the first line
dist_datalines=dist_details.filter(lambda x: "SITE_ID" not in x)
dist_datalines.count()


dist_split=dist_datalines.map(lambda l: l.split(","))
dist_map=dist_split.map(lambda p:Row(SITE_ID=p[0],SITE_NAME=p[1],SITE_ADDRESS=p[2],SITE_ADDRESS2=p[3],SITE_CITY=p[4],SITE_STATE=p[5],SITE_COUNTRY=p[6],SITE_ZIPCODE=p[7],GEPS_REGION_DESC=p[8],GPS_LATITUDE=p[9],GPS_LONGITUDE=p[10],INDUSTRY_TYPE=p[11],CSA_SITE_MGR_SSO=p[12],SERVICE_MGR_SSO=p[13],PRIMARY_CONTACT_SSO=p[14],PRIMARY_PHONE=p[15],CUSTOMER_ID=p[16],CREATED_BY=p[17],CREATION_DATE=p[18],LAST_UPDATED_BY=p[19],LAST_UPDATE_DATE=p[20],SITE_CUSTOMER_NAME=p[21]))
#dist_map.collect()
#Infer the scehma and register the DataFrame as a table
dist_df=sqlContext.createDataFrame(dist_map)
dist_df.registerTempTable("tpo_site")
#dist_df.show()
tcp_interactions1 = sqlContext.sql("""SELECT SITE_ID,SITE_CUSTOMER_NAME,SITE_CITY,SITE_COUNTRY FROM tpo_site where SITE_ID=19596145 """)
tcp_interactions1.show()


# In[ ]:



