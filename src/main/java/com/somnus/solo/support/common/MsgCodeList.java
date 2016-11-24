package com.somnus.solo.support.common;

/**
 * 300xxx 系统错误 301xxx 报文错误 302xxx 日间交易异常 303xxx 日终异常
 */
public class MsgCodeList {
	/** 处理成功 */
	public static final String SUCCESS_000000 = "000000";
	
	/** 处理失败 */
	public static final String FAILED_222222 = "222222";
	
	/** 处理中 */
	public static final String HANDING_111111 = "111111";

	/** 系统异常 */
	public static final String ERROR_999999 = "999999";

	/** 系统错误：系统配置文件{0}不存在！ */
	public static final String ERROR_300001 = "300001";

	/** 系统错误：ACTION返回报文实例为NULL！ */
	public static final String ERROR_300002 = "300002";

	/** 系统错误: 报文内容签名与签名字段不符合，报文被篡改！ */
	public static final String ERROR_300003 = "300003";

	/** 系统错误: 报文签名验证异常！ */
	public static final String ERROR_300004 = "300004";
	
	/** 借贷函数调用异常！ */
	public static final String ERROR_300005 = "300005";

	/** 系统错误:解密失败! */
	public static final String ERROR_300007 = "300007";

	/** 系统错误:{0}获取密钥失败! */
	public static final String ERROR_300008 = "300008";
	
	/** 报文中，关键字段{0}不可为空！ */
	public static final String ERROR_301001 = "301001";

	/** 报文中，关键字段{0}必须为数字或金额！ */
	public static final String ERROR_301002 = "301002";

	/** 报文中，关键字段{0}格式必须为{1}！ */
	public static final String ERROR_301003 = "301003";

	/**  报文中，{0}元素个数超过限制，最多{1}个！ */
	public static final String ERROR_301004 = "301004";

	/** 报文中，{0}元素长度不正确！ */
	public static final String ERROR_301005 = "301005";

	/** 报文中，当前报文交易代码未指定！ */
	public static final String ERROR_301006 = "301006";

	/** 数据错误: 当前交易未开通使用权限！ */
	public static final String ERROR_301007 = "301007";
	
	/** 报文中，字段{0}无效！  */
	public static final String ERROR_301008 = "301008";
	
	/** 报文中，字段发生额(tranamt)不可为负数！ */
	public static final String ERROR_301010 = "301010";

	/** 报文中，关键字段{0}必须为金额或格式不正确！ */
	public static final String ERROR_301009 = "301009";

	/** 报文中，关键字段{0}必须为整数！ */
	public static final String ERROR_301011 = "301011";
	
	/** 报文中，关键字段{0}必须为正整数！ */
	public static final String ERROR_301012 = "301012";

	/** 交易异常:{0}不可为空！ */
	public static final String ERROR_302001 = "302001";

	/** 交易异常:{0}账户信息无效 */
	public static final String ERROR_302002 = "302002";

	/** 交易异常:记账服务调用异常 */
	public static final String ERROR_302003 = "302003";

	/** 交易异常：{0}交易流水已经生成台帐 */
	public static final String ERROR_302004 = "302004";

	/** 交易异常：{0}交易流水台帐已完全记账 */
	public static final String ERROR_302005 = "302005";

	/** 交易出款帐户必须为同一个账户！ */
	public static final String ERROR_302006 = "302006";

	/** 本批次的{0}与实际数据不符！ */
	public static final String ERROR_302007 = "302007";

	/** 交易异常：{0}应当为{1}，而不是{2} */
	public static final String ERROR_302008 = "302008";

	/** 本批次{0}报文的总金额与前置流水的总笔数不相等 */
	public static final String ERROR_302009 = "302009";

	/** 本批次{0}存在失败的账务前置流水 */
	public static final String ERROR_302010 = "302010";

	/** 本批次{0}手续费总额与账务前置主流水的总手续费不相等 */
	public static final String ERROR_302011 = "302011";

	/** 转账不可以重复{0} */
	public static final String ERROR_302012 = "302012";
	
	/** 交易异常：批次号 {0} 没有需要处理的交易信息 */
	public static final String ERROR_302013 = "302013";
	
	/** 交易异常：转账中商户账户号和对方交易账户号不能相同！ */
	public static final String ERROR_302014 = "302014";
	
	/** 交易异常：不能成功扣取手续费！ */
	public static final String ERROR_302015 = "302015";
	
	/** 更新异常： 余额不为0不可销户！余额为：{} */
	public static final String ERROR_302018 = "302018";
	
	/** 交易流水不能为空 */
	public static final String ERROR_302019 = "302019";
	
	/** 下发明细不能为空 */
	public static final String ERROR_302020 = "302020";
	
	/** 下发总笔数不等于明细总笔数 */
	public static final String ERROR_302021 = "302021";
	
	/** 下发订单总额不等于明细订单总额 */
	public static final String ERROR_302022 = "302022";
	
	/** 下发手续费总额不等于明细手续费总额 */
	public static final String ERROR_302023 = "302023";
	
	/** 明细交易总额不等于明细手续费与订单总额之和  */
	public static final String ERROR_302024 = "302024";
	
	/** 明细交易总额不等于明细订单总额 */
	public static final String ERROR_302025 = "302025";
	
	/** errNo为空！ */
	public static final String ERROR_302026 = "302026";
	
	/** 差异不存在！ */
	public static final String ERROR_302027 = "302027";
	
	/** 记账流水:{0}不存在 */
	public static final String ERROR_302028 = "302028";
	
	/** 台账:{0}不存在 */
	public static final String ERROR_302029 = "302029";
	
	/** 扣划对应冻结交易不存在 */
	public static final String ERROR_302030 = "302030";
	
	/** 解冻金额 :{0}大于可解冻金额:{1} */
	public static final String ERROR_302031 = "302031";
	
	/** 记账流水不能为空 */
	public static final String ERROR_302032 = "302032";
	
	/** 业务类型不正确 */
	public static final String ERROR_302033 = "302033";
	
	/** 流动资金账户为空！ */
	public static final String ERROR_302034 = "302034";
	
	/** 冻结交易不存在！ */
	public static final String ERROR_302035 = "302035";
	
	/** 该交易已被冻结，请不要重复发起冻结操作 */
	public static final String ERROR_302036 = "302036";
	
	/** 该交易已退款， 不能做冻结操作 */
	public static final String ERROR_302037 = "302037";
	
	/** 该交易已拒付， 不能做冻结操作 */
	public static final String ERROR_302038 = "302038";
	
	/** 原交易不存在无法退票! */
	public static final String ERROR_302039 = "302039";
	
	/** 手续费收取方式为第三方时,第三方手续费账号不能为空! */
	public static final String ERROR_302040 = "302040";
	
	/** 账户余额信息不存在 */
	public static final String ERROR_302041 = "302041";
	
	/** 更新划款信息表失败 */
	public static final String ERROR_302042 = "302042";
	
	/**  原交易已拒付，不能再次拒付 */
	public static final String ERROR_302043 = "302043";
	
	/** 原交易已部分拒付，不能进行全部拒付 */
	public static final String ERROR_302044 = "302044";
	
	/** 原交易未入账，不允许部分拒付 */
	public static final String ERROR_302045 = "302045";
	
	/** 原交易已退款，不能再次拒付 */
	public static final String ERROR_302046 = "302046";
	
	/** 原交易未入账，拒付金额大于原订单金额，不允许拒付 */
	public static final String ERROR_302047 = "302047";
	
	/** 原交易入账状态不可拒付 */
	public static final String ERROR_302048 = "302048";
	
	/** {0}应收银行未开户! */
	public static final String ERROR_302049 = "302049";
	
	/** {0}商户交易账户未开户! */
	public static final String ERROR_302050 = "302050";
	
	/** {0}个人账户信息不存在! */
	public static final String ERROR_302051 = "302051";
	
	/** {0}个人交易账户未开户! */
	public static final String ERROR_302052 = "302052";
	
	/** POSP收单交易手续费承担方必须为收款方! */
	public static final String ERROR_302053 = "302053";
	
	/** 原交易入账状态不可冲正 */
	public static final String ERROR_302054 = "302054";
	
	/** 商户{0}出款关闭,无法冲正,交易信息 */
	public static final String ERROR_302055 = "302055";
	
	/** 原交易为保证金交易，未交纳保证金，不能做冲正交易！ */
	public static final String ERROR_302056 = "302056";
	
	/** 核销类型不正确 */
	public static final String ERROR_302057 = "302057";
	
	/** {0}对应的退款信息不存在或无法做核销 */
	public static final String ERROR_302058 = "302058";
	
	/** {0}对应的退款原交易信息不存在或无法做核销 */
	public static final String ERROR_302059 = "302059";

	/** 交易异常：台账条数为： {0} ，此交易台账生成条数应为1条 */
	public static final String ERROR_302017 = "302017";
	
	/** 交易异常： 取手续费失败！ */
	public static final String ERROR_302016 = "302016";
	
	/**  昨日日终未完成，本次日终无法正常启动！ */
	public static final String ERROR_303001 = "303001";

	/** 定时程序调用返回异常 */
	public static final String ERROR_303002 = "303002";

	/** 当前交易未开通使用权限！ */
	public static final String ERROR_303003 = "303003";

	/** 校验台帐流水失败 */
	public static final String ERROR_303004 = "303004";

	/** 校验台帐与账户失败 */
	public static final String ERROR_303005 = "303005";

	/** 总账核对失败 */
	public static final String ERROR_303006 = "303006";

	/** {0}账户余额不足！ */
	public static final String ERROR_304001 = "304001";

	/** 余额更新失败！ */
	public static final String ERROR_304002 = "304002";

	/** 账户明细录入失败 */
	public static final String ERROR_304003 = "304003";

	/** 批次请求操作的账户不一致！ */
	public static final String ERROR_304004 = "304004";

	/** 批次请求操作的借贷方向不一致！ */
	public static final String ERROR_304005 = "304005";
	
	/** 台账分录类型未注册，非法分录！ */
	public static final String ERROR_304006 = "304006";

	/**************** 开户交易异常 ************/
	/**参数{0}不能为空，交易失败！ */
	public static final String ERROR_305001 = "305001";
	
	/**记录{0}不存在，交易失败！ */
	public static final String ERROR_305002 = "305002";
	
	/**记录{0}存在，交易失败！ */
	public static final String ERROR_305003 = "305003";
	
	/**参数{0}日期格式错误，交易失败！ */
	public static final String ERROR_305004 = "305004";
	
	/**一级总户号和二级总户号不一致{0}，交易失败！ */
	public static final String ERROR_305005 = "305005";

	/** 无法调用创建商户并建立与角色组的关联服务，关键字段{0}为空! */
	public static final String ERROR_305007 = "305007";
	
	/** 调用创建商户并建立与角色组的关联服务失败! */
	public static final String ERROR_305008 = "305008";

	/** 没有找到机构号{0}对应的机构名称 */
	public static final String ERROR_305006 = "305006";
	
	/**{0}商户信息不存在*/
	public static final String ERROR_307001 = "307001";

	/**{0}商户账户信息不存在*/
	public static final String ERROR_307002 = "307002";

	/**{0}商户账户无效*/
	public static final String ERROR_307003 = "307003";

	/**mercode meracccode两者必须输入至少一个*/
	public static final String ERROR_307004 = "307004";

	/**************** posp交易异常 ************/
	/**参数{0}不能为空，交易失败！*/
	public static final String ERROR_505001 = "505001";
	
	/**记录{0}不存在，交易失败！*/
	public static final String ERROR_505002 = "505002";
	
	/**记录{0}存在，交易失败！*/
	public static final String ERROR_505003 = "505003";
	
	/**参数{0}日期格式错误，交易失败！*/
	public static final String ERROR_505004 = "505004"; 
	
	/**参数{0}不是数字，交易失败！*/
	public static final String ERROR_505005 = "505005";
	
	/**参数{0}无交易账户号！*/
	public static final String ERROR_505006 = "505006";
	
	/**报文批次、处理 日期与文件內容不一致*/
	public static final String ERROR_505007 = "505007"; 
	
	/**机构代码{0}无对应银行号！*/
	public static final String ERROR_505008 = "505008";
	
	/**文件{0}下载失败！*/
	public static final String ERROR_505009 = "505009";
	/**文件{0}解压失败！*/
	public static final String ERROR_505010 = "505010";
	
	/**导入流水失败！*/
	public static final String ERROR_505011 = "505011";
	
	/**导入结算失败！*/
	public static final String ERROR_505012 = "505012";
	
	/**导入差错失败！*/
	public static final String ERROR_505013 = "505013";
	
	/**导入划款失败！*/
	public static final String ERROR_505014 = "505014";
	
	/**台帐试算不平衡！*/
	public static final String ERROR_505015 = "505015";
	
	/**{0}商户账户信息不存在！*/
	public static final String ERROR_505016 = "505016";
	
	/**{0}出款功能被关闭，无法完成您申请的操作!*/
	public static final String ERROR_505017 = "505017";
	
	/**交易账户号{0}基础产品{1}配置信息不存在！*/
	public static final String ERROR_505018 = "505018";
	
	/**调用内部账户开通失败！*/
	public static final String ERROR_505019 = "505019";
	
	/**{0}不能进行收单!*/
	public static final String ERROR_505020 = "505020";
	
	/** 校验失败：{0}校验不通过,{1}！*/
	public static final String ERROR_100001 = "100001";
	
	/** 验签失败：{0}校验不通过！*/		
	public static final String ERROR_100002 = "100002";

}
