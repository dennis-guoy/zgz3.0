package com.cwdt.plat.dataopt;

public class single_app_info extends BaseSerializableData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6827034972275442238L;
	/**
	 * 模块ID
	 */
	public String id = "0";
	/**
	 * 模块类名使用 getClass().getName() 获得的完整类名
	 */
	public String modelclass = "";

	/**
	 * 模块标题 用于显示在模块功能启动界面中
	 */
	public String modeltitle = "";
	/**
	 * 模块图标 用于显示在模块功能启动界面中
	 */
	public String modelicon = "";

	/**
	 * 模块索引 用于表示在模块功能启动界面中当前模块所处位置
	 */
	public String modelindex = "0";
	/**
	 * 模块分组 用于表示在模块功能启动界面中当前模块所在分组
	 */
	public String modelgroup = "0";

	/**
	 * 模块是否显示 0：显示 1：不显示 （这里仅是用于模块的禁用功能，与权限无关）
	 */
	public String isshown = "0";

	/**
	 * 用于显示右上角小红点默认0不显示
	 */
	public String isread = "0";
	/**
	 * 以分号分隔的键值对数据，用来为启动模块提供扩展的数据支持 如 param1=1;param2=test;param3=hello word;
	 * 当信息中包含数据时，将使用如下的方式向被启动模块传递参数： 
	 * intent.putExtra("param1", "1");
	 * intent.putExtra("param2", "test");
	 * intent.putExtra("param3", "hello word");
	 */
	public String extdata="";
}
