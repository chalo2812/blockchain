import com.google.gson.GsonBuilder;
import org.slf4j.*;

import java.util.ArrayList;

public class BlockMain {

    public static ArrayList<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 5;
    public static Logger log = LoggerFactory.getLogger(BlockMain.class);

    public static void main(String[] args) {

        blockchain.add(new Block("Hi im the first block", "0"));
        log.info("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
        log.info("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
        log.info("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        log.info("\nBlockchain is Valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        log.info("\nThe block chain: ");
        log.info(blockchainJson);

    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                log.info("Current Hashes not equal");
                return false;
            }
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                log.info("Previous Hashes not equal");
                return false;
            }
            if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
                log.info("This block hasn't been mined");
                return false;
            }
        }
        return true;
    }

}
