package com.aison.common;

/**
 * 常量定义
 *
 * @author Zhenfeng Li
 * @version 1.0.0
 * @date 2019-09-19 09:02:33
 */
public interface Msg {

    Integer OK = 200;
    String TEXT_OK = "请求成功";
    String TEXT_LOGIN_OK = "登录成功";
    String TEXT_LOGOUT_OK = "注销成功";
    String TEXT_SAVE_OK = "保存成功";

    String TEXT_UPDATE_OK = "修改成功";

    String TEXT_DELETE_OK = "删除成功";
    String TEXT_QUERY_OK = "查询成功";


    Integer LOGIN_FAIL = 300;
    String LOGIN_USER_PWD_ERROR_FAIL = "用户名或密码错误";
    String LOGIN_ACCOUNT_FORBIDDEN_FAIL = "账号被禁用，请联系管理员";
    String LOGIN__ERROR_FAIL = "登录异常，解析密码错误";

    Integer CAPTCHA_FAIL = 310;
    String CAPTCHA_EXPIRE_FAIL = "验证码已过期";
    String CAPTCHA_ERROR_FAIL = "验证码不正确";
    String CAPTCHA_EXPIRE_ERROR_FAIL = "验证码不存在或已过期";


    Integer TOKEN_FAIL = 320;
    String TEXT_TOKEN_INVALID_FAIL = "TOKEN已过期，请重新登录";

//    Integer IMAGE_FAIL = 320;
//    String TEXT_IMAGE_FAIL = "图片格式无法识别";

    Integer SAVE_FAIL = 330;
    String TEXT_SAVE_FAIL = "保存失败";
    String TEXT_SKU_NUM_FAIL = "超出实际库存数量";


    Integer DATA_FAIL = 340;
    String TEXT_DATA_FAIL = "数据不存在";
    String TEXT_DATA_REPEAT_FAIL = "数据重复";


    Integer ADDRESS_FAIL = 380;
    String TEXT_ADDRESS_NOT_EXIST_FAIL = "修改失败，当前修改地址不存在";


    Integer LOGIN_LIMIT_FAIL = 420;
    String TEXT_LIMIT_FAIL = "用户名密码多次输入错误，已限制您的登录";

    Integer AUTHORITY_FAIL = 440;
    String TEXT_AUTHORITY_FAIL = "权限不足";

    Integer WECHAT_FAIL = 450;
    String TEXT_WECHAT_SESSION_KEY_TIMEOUT_FAIL = "微信sessionKey过期";

    Integer PASSWORD_FAIL = 460;
    String TEXT_PASSWORD_INIT_FAIL = "当前账户密码为初始密码,请修改当前密码";
    String TEXT_MODIFY_PASSWORD_UNSAFE_FAIL = "密码安全度过低,请重新设置当前密码";

    Integer FILE_FAIL = 470;
    String TEXT_EXCEL_DOWNLOAD_FAIL = "excel下载异常";
    String TEXT_EXCEL_UPLOAD_FAIL = "excel上传异常";
    String TEXT_EXCEL_ANALYSIS_FAIL = "excel解析异常";

    Integer SYSTEM_FAIL = 500;
    String TEXT_SYSTEM_FAIL = "系统正在升级，请稍后重试";

    Integer REQUEST_FAIL = 501;
    String TEXT_REQUEST_FAIL = "请求方式错误，当前请求方式为[#nowReq#],实际支持请求方式为[#req#]";


}
