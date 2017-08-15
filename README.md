# generic
通过配置实现实体信息及页面信息同步修改及代码生成。
开发目的：在项目的需求确定阶段，实体的封装经常改变，pojo、dao、service、页面代码，这一套改下来经常导致码农们精疲力竭，笔者由于项目的特殊性而饱受其苦，因而决定设计并开发了本项目。
项目功能：将实体信息及数据库表信息配置到配置文件中，利用反射、泛型技术使用通用的后台逻辑代码，并通过IO流生成与实体信息相对应的页面文件。
阅读项目过程中，重点关注/resources/generic/table.properties;/resources/generic/table/*.json两处配置文件，com.topsec.tsm.generic.core.ApplicationConfig;com.topsec.tsm.generic.core.Generator两个类。
/webapp/WEB-INF/templates/generic/table/*.ftl文件为Generator类生成的页面模板文件。