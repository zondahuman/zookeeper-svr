/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-2-25
 * Time: 下午9:13
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
        Integer a = 10;
        Integer b = new Integer(10);
        System.out.println((a == b));
        int c = 10;
        System.out.println((a == c));
        System.out.println((b == c));
    }
}
