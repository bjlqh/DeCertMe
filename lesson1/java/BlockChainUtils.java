public class BlockChainUtils {

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
        System.out.printf("出块耗时：%s 毫秒 %n", (endTime - startTime));
        return hash;
    }
}
