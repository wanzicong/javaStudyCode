package constants;

/**
 * 状态码 constant = 常量
 */
public interface StatusCode {
    /*
     * 接口中的"变量"默认都是 "public static final"类型，即为常量，
     * 因此接口可以省略"public static final"而直接写成 "type variable"。
     */
//	public static final int OK=20000;//成功
    int OK=20000;//成功
    int ERROR=20001;//失败
    int LOGINERROR =20002;//用户名或密码错误
    int ACCESSERROR =20003;//权限不足
    int REMOTEERROR =20004;//远程调用失败
    int REPERROR =20005;//重复操作
}
