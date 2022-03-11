![tools](https://www.geotools.org/_static/img/geotools-logo.png)

``` text
+--- src  
|   +--- main  
|   |   +--- java  
|   |   |   +--- io  
|   |   |   |   +--- human0722  
|   |   |   |   |   +--- geotools  
|   |   |   |   |   |   +--- raster  
|   |   |   |   |   |   |   +--- BoxScale.java 缩放 .jp2 box解决JAI 无法读取问题(Fail) 
|   |   |   |   |   |   |   +--- Jp2kConverter.java  剪裁遥感图
|   |   |   |   |   |   |   +--- Operations.md  Geotools 提供的 raster 操作列表
|   |   |   |   |   |   +--- shp 
|   |   |   |   |   |   |   +--- FixGeometry.java  修复非法的Geometry
|   |   |   |   |   |   |   +--- Shp2Postgis.java  矢量图文件保存到 Postgis
|   |   |   |   |   |   |   +--- ValidateGeometry.java  验证矢量图是否非法
|   |   |   |   |   |   +--- runtime
|   |   |   |   |   |   |   +--- FixGeometry.java  修复非法的Geometry
|   |   |   +--- org  geotools 官方案例
|   |   |   |   +--- geotools  
|   |   |   |   |   +--- tutorial  
|   |   |   |   |   |   +--- coverage  
|   |   |   |   |   |   |   +--- ImageTiler.java  
|   |   |   |   |   |   |   +--- RunArguments.txt  
|   |   |   |   |   |   +--- crs  
|   |   |   |   |   |   |   +--- CRSLab.java  
|   |   |   |   |   |   +--- raster  
|   |   |   |   |   |   |   +--- ImageLab.java  
|   |   |   |   |   |   |   +--- RasterAvailableOperations.java  
```