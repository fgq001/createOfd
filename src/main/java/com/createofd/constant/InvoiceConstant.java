package com.createofd.constant;

/**
 * 
* <p>Title: InvoiceConstant</p>
* <p>Description: 开票常量类</p>
* <p>Company: 百旺金赋</p> 
* 
* @date 上午11:09:53
 */
public class InvoiceConstant {

	//编码表 版本号
	public static  final String BMB_BBH = "32.0";

	//加密版本号(创建PDF方法用到)
	public static  final String JM_BBH = "20190220100839113555";

	/**
	 * 成功
	 */
	public static  final String INVOICE_SUCCESS = "SUCCESS";
	//服务端异常
	public static  final String SERVER_ERROR = "system exception";
	//参数校验异常
	public static  final String BIND_ERROR = "params checking exception";
	//接口请求太频繁，请稍后再试
	public static  final String REQUEST_ERROR = "Requests are too frequent. Please try again later!";


	//开具发票模块相关  5001XX
	/**
	 * 验证传递的参数相关
	 */
	//token为空  500101
	public static  final String TOKEN_NULL_ERROR = "token is null";
	//token错误  500102
	public static  final String TOKEN_ERROR = "token is error";
	//数据为空	 500103
	public static  final String DATA_NULL_ERROR = "data is null";
	//销售方税号为空  500104
	public static  final String XSF_NSRSBH_NULL_ERROR = "xsf_nsrsbh is null";
	//销售方名称为空  500105
	public static  final String XSF_MC_NULL_ERROR = "xsf_mc is null";
	//数据源为空 	500106
	public static  final String DATA_RESOURCES_NULL_ERROR = "data_resources is null";
	//单据号为空	500107
	public static  final String ORDER_NUM_NULL_ERROR = "order_num is null";
	//单据号重复	500108
	public static  final String ORDER_NUM_ERROR = "order_num is already exist";
	//税收编码版本号为空 500109
	public static  final String BMB_BBH_NULL_ERROR = "bmb_bbh is null";
	//征税方式为空	500110
	public static  final String ZSFS_NULL_ERROR = "zsfs is null";
	//销售方地址、电话为空 500111
	public static  final String XSF_DZDH_NULL_ERROR = "xsf_dzdh is null";
	//购买方名称为空	500112
	public static  final String GMF_MC_NULL_ERROR = "gmf_mc is null";
	//开票人为空 	500113
	public static  final String KPR_NULL_ERROR = "kpr is null";
	//价税合计为空 500114
	public static  final String JSHJ_NULL_ERROR = "jshj is null";
	//合计金额为空	500115
	public static  final String HJJE_NULL_ERROR = "hjje is null";
	//合计税额为空  500116
	public static  final String HJSE_NULL_ERROR = "hjse is null";
	//发票行性质为空  500117
	public static  final String FPHXZ_NULL_ERROR = "fphxz is null";
	//商品编码为空  500118
	public static  final String SPBM_NULL_ERROR = "spbm is null";
	//项目名称为空	500119
	public static  final String XMMC_NULL_ERROR = "xmmc is null";
	//项目金额为空	500120
	public static  final String XMJE_NULL_ERROR = "xsf_dzdh is null";
	//税率为空		500121
	public static  final String SL_NULL_ERROR = "sl is null";
	//税额为空		500122
	public static  final String SE_NULL_ERROR = "se is null";

	//证书不存在	500123
	public static  final String CA_CERTIFICATE_NULL_ERROR = "CA Certificate does not exist";
	//注册码不存在  500124
	public static  final String NEBULA_KEY_NULL_ERROR = "nebula key does not exist";


	//销售方PDF信息效验成功 0
	public static  final String XSF_PDFINFO_SUCCESS = "xsf pdf_information checking OK!";
	//销售方 子表CA信息效验成功	0
	public static  final String XSF_CA_SUB_INFO_SUCCESS = "xsf ca_sub_information checking OK!";
	//销售方 CA信息效验成功	0
	public static  final String XSF_CA_INFO_SUCCESS = "xsf ca_information checking OK!";
	//没有权限访问该接口	500125
	public static  final String ACCESS_INTERFACE_ERROR = "do not have access to this interface";
	//serviceKey 为空	500126
	public static  final String SERVICEKEY_ERROR = "serviceKey is null";
	//serviceKey 或者 销方税号为空	500127
	public static  final String SERVICEKEY_OR_NSRSBH_ERROR = "serviceKey or nsrsbh is null";
	//销售方PDF信息 不存在	500128
	public static  final String XSF_PDF_INFO_ERROR = "xsf pdf information is incomplete!";
	//销售方CA 印章信息 不存在	500129
	public static  final String XSF_CAGIF_INFO_ERROR = "xsf caGif information is incomplete!";
	//销售方 CA信息 不存在	500130
	public static  final String XSF_CA_INFO_ERROR = "xsf ca information is incomplete!";
	//销售方 CA证书信息 不存在	500131
	public static  final String XSF_CAPFX_INFO_ERROR = "xsf caPfx information is incomplete!";

	//skserver开票服务器返回解析异常	500132
	public static  final String ANALYSIS_INFO_ERROR = "return analysis exception";

	//未找到客户印章或模板  500133
	public static  final String CUSTOMER_SEAL_OR_TEMPLATE_ERROR = "Customer Seal or Template Not Finded";
	//500134  税控服务器返回的错误信息

	//税控服务器错误	500135
	public static  final String SKSERVER_EXCEPTION = "skServer exception";

	//红冲接口相关
	//原发票代码为空	500136
	public static  final String YFP_DM_NULL_ERROR = "yfp_dm is null";
	//原发票号码为空	500137
	public static  final String YFP_HM_NULL_ERROR = "yfp_hm is null";
	//该发票已经红冲过	500138
	public static  final String RED_RUSH_ERROR = "This invoice is red rush over";
	//该发票不存在		500139
	public static  final String INVOICE_EXIST_ERROR = "invoice is not exist";

	//取票码不存在(邮箱推送,发票查询相关)		500140
	public static  final String QPM_NULL_ERROR = "qpm is null";

	//邮箱推送地址不存在(邮箱推送相关)		500141
	public static  final String MAIL_ADDRESS_NULL_ERROR = "mail_address is null";

	//发票信息不存在		500142
	public static  final String INVOICE_NULL_ERROR = "invoice does not exist";

	//邮箱地址验证错误		500143
	public static  final String MAIL_ADDRESS_CHECKING_ERROR = "the mail format is incorrect";
	//传入的openid或者appsecret为空		500144
	public static  final String OPENID_OR_APPSECRET_NULL_ERAROR = "openid or appsecret is null!";
	//客户信息不存在 	500145
	public static  final String CUSTOMER_NOT_EXIST_ERROR = "customer does not exist";
	//CA信息 文件流不存在 	500146
	public static  final String CA_INFO_STREAM_ERROR = " caPfx information stream is null!";
	//创建PDF服务失败 	500147
	public static  final String CREATE_PDF = " create PDF server is failed!";
	//发票请求流水号为空 	500148	tmpPath
	public static  final String FPHM_INFO = " fphm info is null or not exist!";
	//PDF模板路径为空 	500149
	public static  final String TMPPATH_INFO = " tmpPath info is null!";
	//印章图片路径为空 	500150
	public static  final String GIFMAP_INFO = " gifMap info is null!";
	//签章路径为空 	500151
	public static  final String PFXMAP_INFO = " pfxMap info is null!";
	//创建PDF异常 	500152
	public static  final String CREATE_PDF_EXCEPTION = " create PDF is exception!";

	//创建OFD异常 	400151
	public static  final String CREATE_OFD_EXCEPTION = " create OFD is exception!";
	//xmlContent为空 	400152
	public static  final String xmlContent = " xmlContent info is null!";
	//创建ofd的XML失败 	400153
	public static  final String CREATE_XML = " createXML is failed!";
	//生成临时ofd文件失败 400154
	public static  final String CREATE_TEM_OFD = " create temporary OFD is failed!";
	//给临时ofd签名失败 400155
	public static  final String SIGN_TEM_OFD = " sign temporary OFD is failed!";
	//给验签章失败 400156
	public static  final String Verify_OFD_Sign = " Verify OFD Sign is failed!";
}
