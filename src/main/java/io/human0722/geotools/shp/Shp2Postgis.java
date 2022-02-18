package io.human0722.geotools.shp;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureWriter;
import org.geotools.data.Transaction;
import org.geotools.data.shapefile.ShapefileDataStore;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.jdbc.JDBCDataStore;
import org.geotools.referencing.CRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class Shp2Postgis {
    /**
     * 获取数据库连接对象
     *
     * @return
     * @throws Exception
     */
    public JDBCDataStore getDataStore(){

        Map<String, Object> params = new HashMap<>();
        // 必须是字符串 postgis
        params.put("dbtype", "postgis");
        // ip
        params.put("host", "127.0.0.1");
        // 端口
        params.put("port", 5432);
        // 数据库模式
        params.put("schema", "public");
        // 数据库名称
        params.put("database", "template_postgis");
        params.put("user", "postgres");
        params.put("passwd", "wmywan1314");

        JDBCDataStore dataStore = null;

        try {
            DataStore ds = DataStoreFinder.getDataStore(params);

            if(ds==null){
                System.out.println("连接postgres失败");
            }else {
                dataStore = (JDBCDataStore) ds;
                System.out.println("连接postgres成功");
            }

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接postres出错");
        }

        return dataStore;
    }


    /**
     * 读取shapefile文件
     *
     * @param file
     * @return
     */
    public SimpleFeatureSource readShp(File file){
        SimpleFeatureSource featureSource = null;

        ShapefileDataStore shpDataStore = null;
        try {
            shpDataStore = new ShapefileDataStore(file.toURL());

            //设置编码
            Charset charset = Charset.forName("GBK");
            shpDataStore.setCharset(charset);
            String tableName = shpDataStore.getTypeNames()[0];
            featureSource = shpDataStore.getFeatureSource(tableName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            shpDataStore.dispose();
        }
        return featureSource;

    }


    /**
     * 创建表
     *
     * @param ds
     * @param source
     * @return
     */
    public JDBCDataStore createTable(JDBCDataStore ds , SimpleFeatureSource source)  {

        try {
            String crsCode = String.valueOf(CRS.lookupEpsgCode(source.getSchema().getCoordinateReferenceSystem(),true));
            SimpleFeatureType retype = SimpleFeatureTypeBuilder.retype(source.getSchema(), CRS.decode("EPSG:" + crsCode));
            ds.createSchema(retype);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("创建postgres数据表失败");
        }

        return ds;
    }


    /**
     * 将shapefile的数据存入数据表中
     *
     * @param ds
     * @param source
     */
    public void shp2Table(JDBCDataStore ds , SimpleFeatureSource source){
        SimpleFeatureIterator features  = null;

        //表名为shp文件的名字
        String tableName = source.getSchema().getTypeName();

        // 坐标系读取写入
        CoordinateReferenceSystem coordinateReferenceSystem = source.getSchema().getCoordinateReferenceSystem();


        try {

            FeatureWriter<SimpleFeatureType, SimpleFeature> writer = ds.getFeatureWriter(tableName, Transaction.AUTO_COMMIT);
            SimpleFeatureCollection featureCollection = source.getFeatures();

            //创建图层数据迭代器
            features = featureCollection.features();

            //进行逐行写入
            while (features.hasNext()) {
                try {
                    writer.hasNext();
                    SimpleFeature next = writer.next();
                    SimpleFeature feature = features.next();
                    for (int i = 0; i < feature.getAttributeCount(); i++) {
                        next.setAttribute(i, feature.getAttribute(i));
                    }
                    writer.write();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("添加数据的方法错误：" + e.toString());
                    continue;
                }
            }
            writer.close();

            System.out.println("导入成功");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("创建写入条件失败："+e.toString());
        }finally {
            ds.dispose();
            features.close();
        }

    }


    //进行测试
    public static void main(String[] args) {
        Shp2Postgis shp2Pgsql = new Shp2Postgis();

//        File file = new File("/Users/rebot0722/Desktop/puhou/Shapefile/龙潭跨江大桥临时施工设施占地红线/龙潭跨江大桥临时施工设施占地红线.shp");
//        File file = new File("/Users/rebot0722/Desktop/puhou/Shapefile/yunhe-line/4549/yunhe.shp");
        File file = new File("/Users/rebot0722/Desktop/puhou/Shapefile/省级重要湿地 上传 几何修正/江苏省省级重要湿地 几何修正.shp");

        Long startTime = System.currentTimeMillis();

        //获取postgres连接对象
        JDBCDataStore dataStore = shp2Pgsql.getDataStore();

        //读取shapefile文件
        SimpleFeatureSource simpleFeatureSource = shp2Pgsql.readShp(file);

        //创建数据表
        JDBCDataStore ds = shp2Pgsql.createTable(dataStore, simpleFeatureSource);


        //进行数据写入
        shp2Pgsql.shp2Table(ds , simpleFeatureSource);

        Long endTime = System.currentTimeMillis();

        Long time = (endTime - startTime)/1000;

        System.out.println("时间为: "+time+" 秒");

    }
}
