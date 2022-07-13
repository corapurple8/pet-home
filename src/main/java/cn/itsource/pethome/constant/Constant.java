package cn.itsource.pethome.constant;

public class Constant {
    //状态
    //0正常 1待审核 -1禁用 2待激活 3驳回
    /**禁用*/
    public static final Integer DISABLED = -1;
    /** 正常*/
    public static final Integer NORMAL = 0;
    /**待审核*/
    public static final Integer AUDIT = 1;
    /**待激活*/
    public static final Integer ACTIVITY = 2;
    /**驳回*/
    public static final Integer REJECT = 3;

    //文件上传
    /**使用fastdfs的路径前缀*/
    public static final String FASTDFS_HOST = "http://121.37.194.36";

    //发送手机验证码
    /**手机注册验证码保存的键前缀*/
    public static final String PHONEREG_CODE_KEY = "phone_code";
    /**手机登录验证码保存的键前缀*/
    public static final String PHONELOG_CODE_KEY = "phone_code_log";
    /**验证码有效时间 5分钟*/
    public static final Integer CODE_EXPIRE_TIME = 300;


    //用户登录
    /**用户登录保存redis中的实效 30分钟 时间s*/
    public static final Integer USER_EXPIRE_TIME = 30*60;
    /**用户登录后访问的请求头*/
    public static final String USER_TOKEN = "User-Token";
    /**用户登录区分前后台的请求头*/
    public static final String USER_TYPE = "User-Type";


    //微信登录获取信息
    /**微信获取TOKEN的URL*/
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    /**微信开发者权限的appid*/
    public static final String APPID = "wxd853562a0548a7d0";
    /**微信开发者权限的秘钥*/
    public static final String SECRET = "4a5d5615f93f24bdba2ba8534642dbb6";
    /**微信授权获取用户资源的url*/
    public static final String GET_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";


    /**百度地图开放平台秘钥*/
    public static final String AK = "DHrGPlk6OlX0Dg8vvPsrHeNwiUk23PEu";

}
