package com.fh.util;

/**
 * 返回消息模板
 *
 * Created by 95307 on 2016/12/10.
 */
public class MessageTemplate {

    /**********************************************通用*******************************************************/
    public static final String common_TokenExpire = "token已过期";
    public static final String common_SystemErr = "系统内部错误";
    public static final String common_QueryDataSuccess = "获取数据成功";
    public static final String common_NoDataFailure = "暂无数据";
    public static final String common_Advice = "您的意见已反馈到我们的平台，谢谢";
    public static final String common_Success = "成功";
    public static final String common_Failure = "失败";

    // 版本消息
    public static final String common_VersionNew = "已是最新版本";
    public static final String common_VersionOld = "版本过于老旧请到官网更新客户端";

    // 搜索
    public static final String common_InputSearchCondition = "请输入您要查询的条件";

    // 支付
    public static final String common_GetPaySignSuccess = "获取支付码成功";
    public static final String common_paySuccess = "支付成功";
    public static final String common_payFailure = "支付失败";
    public static final String common_OrderNPoErr = "订单号有误";
    public static final String common_SignErr = "密匙有误";
    public static final String ocmmon_MoneyErr = "订单金额有误";

    // 手机验证码
    public static final String verifyCode_getFailure = "获取验证码失败,请60秒后重新获取";
    public static final String verifyCode_getSuccess = "获取验证码成功";
    public static final String verifyCode_InputMobile = "请输入手机号码";
    public static final String verifyCode_input = "请输入验证码";
    public static final String verifyCode_Expire = "验证码已过期,请重新获取验证码";
    public static final String vefiryCode_Err = "验证码错误";
    public static final String verifyCode_validateSuccess = "验证成功";

    // 登陆、注册、修改信息
    public static final String login_NoSuchUserName = "您输入的用户名不正确";
    public static final String login_Success = "登陆成功";
    public static final String login_userNameOrPwdErr = "用户名或密码错误";
    public static final String login_mobileOrPwdErr = "手机号码或密码错误";
    public static final String login_NoPassword = "请输入密码";
    public static final String Login_noLoginPower = "您无此登陆权限";
    public static final String login_IncompletePersionalInfo = "您的个人信息不完善请完善个人信息"; // 个人信息不完善，不能下单

    public static final String delete_NnDelForOrder = "用户积分订单已确认，禁止删除";
    public static final String delete_NnDelForTeam = "该用户已组建团队，禁止删除";
    public static final String delete_Success = "删除成功";

    public static final String update_PwdSuccess = "修改密码成功";
    public static final String update_RecordSuccess = "修改记录成功";
    public static final String udpate_InfoSuccess = "用户信息更新成功";
    public static final String udpate_SexErr = "性别格式错误 0:没填 1:男 2:女 3:保密";
    public static final String udpate_OldPwdErr = "原密码不正确";

    public static final String register_PerfectInfo = "请完善您的注册信息";
    public static final String register_PerfectTrueName = "请完善您的真实姓名";
    public static final String register_SexIsNone = "请输入您的性别";
    public static final String register_NoUserName = "用户名不允许为空";
    public static final String register_NoPwd = "密码不允许为空";
    public static final String register_NoInviter = "推荐人不允许为空";
    public static final String register_NoMobile = "手机号不允许为空";
    public static final String register_IsExistMobile = "您的手机号已经注册，可以直接登录";
    public static final String register_ErrModuleForMobile = "请输入正确的手机号码";
    public static final String register_BindBankCard = "请绑定您的银行卡信息";
    public static final String register_BankCardErr = "请填写正确的银行卡号";
    public static final String register_BankCardSaveSuccess = "银行卡信息保存成功";
    public static final String register_EmailErr = "请输入正确的邮箱地址";
    public static final String register_PerfectIdCard = "请完善您的身份证信息";
    public static final String register_IdCardErr = "请填写正确的身份证号";
    public static final String register_UserNameExist = "用户名已存在";
    public static final String register_BankBinded = "银行卡已经被绑定";
    public static final String register_PersonalDataInitFailure = "个人账户数据初始化失败";
    public static final String register_Success = "注册成功";
    public static final String register_Failure = "注册失败";
    public static final String register_CheckPass = "校验信息通过";
    public static final String register_PerfectInviter = "您还没有网络位置，请补全有效的推荐人";
    public static final String register_GetBankListSuccess = "获取银行列表成功";
    public static final String register_PayPwdSubSuccess = "支付密码提交成功";
    public static final String register_PayPwdSubFailure = "支付密码提交失败";
    public static final String register_ResetPassword = "请重新设置密码";
    /***************************************社区******************************************************************/
    // 提现
    public static final String cash_NoCashPower = "本功能仅周二周三开放";

    // 积分
    public static final String integral_GetNetValueFailure = "无法获取到当日净值";
    public static final String integral_GetNetValueSuccess = "获取到当日净值成功";
    public static final String integral_Get7NetValueFailure = "无法获取到7日净值";
    public static final String integral_Get7NetValueSuccess = "获取到7日净值成功";
    public static final String integral_GetRewardSuccess = "获取奖金成功";
    public static final String integral_RevokeSuccess = "撤销成功";
    public static final String integral_NoPowerOrder = "退单状态下不能购单";
    public static final String integral_OrderSubmitSuccess = "订单提交成功";
    public static final String integral_GetStepInfoSuccess = "查询步值成功";
    public static final String integral_GetStepInfoFailure = "查询步值失败";
    public static final String integral_NoPowerUpdateInfoForTeam = "账号已有下级成员，不允许修改个人信息";
    public static final String integral_NoPowerUpdateInfoForOrder = "账号已有订单记录，不允许修改个人信息";
    public static final String integral_GetPersionInfoSuccess = "个人信息获取成功";

    // 奖励
    public static final String reward_BalanceLow = "账户没有可用余额,请刷新后重新使用";
    public static final String reward_TransferSuccess = "转账成功";

    // 医疗
    public static final String treatment_CreateOrderSuccess = "预约订单已生成";
    public static final String treatment_DataTransferErr = "数据传输错误";
    public static final String treatment_GetDoctorListSuccess = "获取医生列表成功";
    public static final String treatment_NoSuchOffice = "暂未开通此科室";
    public static final String treatment_NoDoctorForOffice = "暂无该科室医生坐诊";
    public static final String treatment_NoDcotor = "暂无医生";
    public static final String treatment_IsTheLastOne = "已经是最后一页了";
    public static final String treatment_GetMessageSuccess = "获取对话列表消息成功";
    public static final String treatment_GetOfficeSuccess = "获取科室信息成功";
    public static final String treatment_NoOfficeInfo = "暂无科室信息";
    public static final String treatment_NoMessage = "暂无对话列表消息";
    public static final String treatment_IsEndChange = "对话咨询状态已更新";
    public static final String treatment_GetDoctorAndComment = "获取医生和评论列表信息成功";
    public static final String treatment_SetIsOpenSuccess = "用户消息是否公开设置完成";
    public static final String treatment_GetCommentSuccess = "获取评价页面信息成功";
    public static final String treatment_SaveCommentSuccess = "保存用户评价完成";
    public static final String treatment_GetHotDiseaseSuccess = "获取高发疾病信息成功";
    public static final String treatment_GetCommentLabelSuccess = "获取评价标签列表成功";
    public static final String treatment_PerfectConsultCondition = "请输入完整的咨询信息";
    public static final String treatment_PatientNameIsEmpty = "就诊人姓名不能为空";
    public static final String treatment_PatientAgeIsEmpty = "就诊人年龄不能为空";
    public static final String treatment_SavePatientSuccess = "新增就诊人完成";
    public static final String treatment_GetPatientListSuccess = "获取当前用户就诊人列表成功";
    public static final String treatment_DelPatientSuccess = "删除就诊人成功";
    public static final String treatment_NoOrder = "您还没有订单信息";
    public static final String treatment_GetOrderSuccess = "获取订单信息成功";
    public static final String treatment_NoHospital = "请补全医院名称";
    public static final String treatment_NoCity = "请补全城市名";
    public static final String treatment_NoOffice = "请补全科室信息";
    public static final String treatment_NoTitle = "请补全职称信息";
    public static final String treatment_NOCredentials = "请上传执业医生证或胸牌";
    public static final String treatment_NoPhoto = "请设置头像";
    public static final String treatment_AuthFailure = "认证失败";
    public static final String treatment_AuthSuccess = "认证成功";
    public static final String treatment_OpenServiceFailure = "开通服务失败，请再试一次";
    public static final String treatment_OpenServiceSuccess = "开通服务成功，恭喜您";


    // 证券
    public static final String securities_NoInvestmentProject = "投资项目不存在";
    public static final String securities_NoPowerOrderForInviter = "该用户不存在推荐人，不允许购买产品";
    public static final String securities_NoPowerOrderForDebt = "该用户存在未结清购买记录,请结清订单再进行下次购买";
    public static final String securities_OrderSuccess = "购买成功";
    public static final String securities_urchasablePLow = "可购买证券数不足";
    public static final String securities_WalletLow = "钱包余额不足";
    public static final String securities_NoWalletInfo = "未找到该用户钱包信息";
    public static final String securities_NoSuchManagerID = "该管理者ID不存在，请输入有效的管理者ID";
    public static final String securities_NoSuchManagerNetInfo = "该管理者ID网络信息无效，请输入有效的管理者ID";
    public static final String securities_NoInviter = "用户没有推荐人";
    public static final String securities_NoNetLeader = "用户没有网络上级";
    public static final String securities_NoNetIdentification = "用户没有网络标识";
    public static final String securities_RewardSuccess = "奖励成功";
    public static final String securities_NoOrderList = "用户没有符合条件的订单记录";
    public static final String securities_NoPowerUpdateInfoForOrder = "用户已经下过订单，不允许修改推荐人信息";
    public static final String securities_InviterNoTeamIdentification = "您的推荐人没有团队标识";
    public static final String securities_NetIdentificationSaveSuccess = "新用户网络标识保存成功";
    public static final String securities_NetIdentificationExist = "新用户已存在网络标识";
    public static final String securities_InviterNoNetIdentification = "推荐人还没有生成网络标识";
    public static final String securities_RightExist1 = "该用户右区存在用户,至少有一个可选位置";
    public static final String securities_RightExist2 = "该用户右区还没有用户,至少有两个可选位置";
    public static final String securities_DefaultLeft = "该推荐人左,右区都没有用户将默认分配到左区";
    public static final String securities_NoIntegralToWallet = "您没有可以转到证券钱包的积分";
    public static final String securities_IntegralToWalletSuccess = "积分转入钱包成功";
    public static final String securities_CashFrozenChange = "现金冻结状态更改完成";
    public static final String securities_NoOrderToRevoke = "该笔订单不存在,无法撤销";
    public static final String securities_NoPowerToRevoke = "该笔订单为正常下单,无法撤销";
    public static final String securities_NoOrderToUpdate = "该笔订单不存在,无法修改";
    public static final String securities_NoPowerToUpdate = "该笔订单为正常下单,无法修改";
    public static final String securities_NoPowerToUpdateRewardDistribute = "奖励已发放无法修改";
    public static final String securities_NoOrder = "该笔交易不存在";
    public static final String securities_NoNeedBackfill = "您无需回填欠款";
    public static final String securities_BackfillSuccess = "回填成功";
    public static final String securities_GetNetValueFailure = "无法获得交易净值";
    public static final String securities_NoPowerToCash = "可提现证券数不足，不能提现";
    public static final String securities_WithdrawalsSuccess = "提现成功";

    // 金融
    public static final String finance_NoPowerConfirmOrder = "该用户上级未激活，此订单不能确认";
    public static final String finance_SettlementSuccess = "结算成功";
    public static final String finance_ConfirmSuccess = "确认成功";
    public static final String finance_BankConfirm = "退单已确认";
    public static final String finance_WithDraweConfirm = "提现已确认";
    public static final String finance_DelOrderForTimeOut36 = "已经删除"+36+"小时未确认订单";

}
