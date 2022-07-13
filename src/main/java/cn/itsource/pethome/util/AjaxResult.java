package cn.itsource.pethome.util;

/**
 * 返回前端所传的参数
 */
public class AjaxResult {

    /**执行描述*/
    private String msg;

    /**执行结果*/
    private boolean success=true;

    /**传回数据*/
    private Object data;

    /**
     * 私有化构造方法
     */
    private AjaxResult() {
    }

    public static AjaxResult me(){
        return new AjaxResult();
    }

    public String getMsg() {
        return msg;
    }

    public AjaxResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public AjaxResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getData() {
        return data;
    }

    public AjaxResult setData(Object data) {
        this.data = data;
        return this;
    }
}
