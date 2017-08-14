# generic
通过配置实现实体信息及页面信息同步修改及代码生成。
开发目的：在项目的需求确定阶段，实体的封装经常改变，pojo、dao、service、页面代码，这一套改下来经常导致码农们精疲力竭，笔者由于项目的特殊性而饱受其苦，因而决定设计并开发了本项目。
项目功能：将实体信息及数据库表信息配置到配置文件中，利用反射、泛型技术使用通用的后台逻辑代码，并通过IO流生成与实体信息相对应的页面文件。