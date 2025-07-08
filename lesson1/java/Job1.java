/**
 * 实践 POW， 编写程序（编程语言不限）用自己的昵称 + nonce，不断修改nonce 进行 sha256 Hash 运算：
 * <p>
 * 直到满足 4 个 0 开头的哈希值，打印出花费的时间、Hash 的内容及Hash值。
 * 再次运算直到满足 5 个 0 开头的哈希值，打印出花费的时间、Hash 的内容及Hash值。
 */
public class Job1 {

    public static void main(String[] args) {
        String input = "max";
        findPow(input, 4);
        System.out.println();
        findPow(input, 5);
    }

    public static String findPow(String name, int n) {

        long startTime = System.currentTimeMillis();

        String frontZone = "0".repeat(n);
        String hash = "";
        String input = "";
        long nonce = 0L;

        //获取开头前n位为0的hash值
        while (true) {
            input = name + nonce;
            hash = HashUtils.sha256(input);
            //判断前n位数是否为0
            if (hash.startsWith(frontZone)) {
                break;
            }
            nonce++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("原始输入: " + input);
        System.out.printf("先导为【%s】的hash值为：%s %n", frontZone, hash);
        System.out.printf("花费时间：%s 毫秒 %n", (endTime - startTime));
        return hash;
    }

}
