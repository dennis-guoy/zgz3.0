package com.cwdt.plat.dataopt;

import java.io.Serializable;

/**
 * 企业预警基本信息
 * 
 * @author Administrator
 *
 */

public class singleComponyWarning implements Serializable {
	/**
	 * 
	 */

	private static final long serialVersionUID = 8968486489844222648L;
	public String warning_id="";//预警id
	public String read_type="";//已读标记
	public String companytaxcode = "";// 税号
	public String company_name = "";// 企业名称
	public String warningtime = "";// 预警时间
	public String IndexSmallClass = "";// 指标小类
	public String indexclass = "";// 指标类别
	public String Indexname = "";// 指标名称
	public String frequency = "";// 频度
	public String weightvalue="";//权值
	public String warningcount = "";// 预警次数
	public String warningscore = "";// 预警分值
	public String warningclass = "";// 预警级别
	public String warningalertstate = "";// 预警提醒任务发起状态
	public String warningassessmentstate = "";// 预警评估任务发起状态
	public String warningprojectstate = "";// 专项专案任务发起状态
	public String warningassessstate = "";// 提醒转评估任务发起状态
	public String mark_type = "";// 任务状态
}
