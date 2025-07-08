import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 用自己熟悉的语言模拟实现最小的区块链， 包含两个功能：
 * <p>
 * POW 证明出块，难度为 4 个 0 开头
 * 每个区块包含previous_hash 让区块串联起来。
 * block = {
 * 'index': 1,
 * 'timestamp': 1506057125,
 * 'transactions': [
 * { 'sender': "xxx",
 * 'recipient': "xxx",
 * 'amount': 5, } ],
 * 'proof': 324984774000,
 * 'previous_hash': "xxxx"
 * }
 */
public class MiniBlockchain {

    public static void main(String[] args) {

        Block head = null;      //第一个区块
        Block tail = null;      //最新的区块
        long index = 1;         //区块编号

        //生成3个区块
        for (int i = 0; i < 3; i++) {

            String input = "max" + UUID.randomUUID().toString().substring(0, 6);
            String proof = BlockChainUtils.findPow(input, 4);

            //每个区块包含3笔交易
            List<Transaction> list = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                Transaction transaction = new Transaction();
                transaction.amount = j;
                //随机生成from 和 to
                transaction.recipient = "rec_" + UUID.randomUUID().toString().substring(0, 6);
                transaction.sender = "sender_" + UUID.randomUUID().toString().substring(0, 6);
                list.add(transaction);
            }

            Block block = new Block();
            block.index = index++;
            block.timestamp = System.currentTimeMillis();
            block.proof = proof;
            block.transactions = list;
            block.previousHash = (tail == null) ? "GENESIS" : HashUtils.sha256(tail.toString());

            //加入链表
            if (head == null) {
                head = block;
                tail = block;
            }else {
                tail.next = block;
                tail = block;
            }

        }
        System.out.println(head);
    }

    //区块对象
    static class Block {
        long index;
        long timestamp;
        String proof;
        String previousHash;
        List<Transaction> transactions;

        Block next;

        @Override
        public String toString() {
            return "Block{" +
                    "index=" + index +
                    ", timestamp=" + timestamp +
                    ", proof='" + proof + '\'' +
                    ", previousHash='" + previousHash + '\'' +
                    ", transactions=" + transactions +
                    ", next=" + next +
                    '}';
        }
    }

    //交易对象
    static class Transaction {
        String sender;
        String recipient;
        double amount;

        @Override
        public String toString() {
            return "Transaction{" +
                    "sender='" + sender + '\'' +
                    ", recipient='" + recipient + '\'' +
                    ", amount=" + amount +
                    '}';
        }
    }
}
